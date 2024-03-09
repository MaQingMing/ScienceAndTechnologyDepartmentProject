package com.yc.common.handler;

import com.yc.common.utils.AllConstans;
import com.yc.common.utils.DateUtil;
import com.yc.entity.ProveFile;
import com.yc.vo.other.ProveFileVo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.PumpStreamHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * @PACKAGE_NAME: com.yc.common.handler
 * @NAME:  数据库备份处理器
 * @USER: Administrator
 * @DATE: 2023/11/27
 * @MONTH_NAME_SHORT: 11月
 * @MONTH_NAME_FULL: 十一月
 **/
@Slf4j
@Component
public class MysqlBackupHandler {

    // 用户名
    @Value("${spring.datasource.username}")
    private String username;

    // 密码
    @Value("${spring.datasource.password}")
    private String password;

    // 最大备份文件数量
    @Value("${yc.backup.max-age}")
    private Duration maxAge;

    // 锁，防止并发备份
    private Lock lock = new ReentrantLock();

    // 日期格式化
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");


    /**
     * 获取 数据库备份文件列表
     * @return
     */
    @SneakyThrows
    public List<ProveFileVo> listFiles(){
        Path dirPath = Paths.get(AllConstans.STU_BACKUP_SAVE_PATH);
        List<ProveFileVo> proveFiles = new ArrayList<>();

        Files.walk(dirPath)
                .filter(Files::isRegularFile)
                .forEach(path -> {
                    try {
                        ProveFileVo file = new ProveFileVo();
                        BasicFileAttributes attrs = Files.readAttributes(path, BasicFileAttributes.class);

                        file.setFileSize(formatFileSize(attrs.size()));
                        file.setFileName(path.getFileName().toString());

                        Instant instant = attrs.creationTime().toInstant();
                        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String formattedDateTime = localDateTime.format(formatter);
                        file.setFileType(formattedDateTime);

                        file.setPath( "../backups/" + path.getFileName().toString());
                        proveFiles.add(file);
                    } catch (IOException e) {
                        log.error("文件或路径不存在",e);
                    }
                });
        return proveFiles;
    }

    /**
     * 文件大小智能转换
     * 会将文件大小转换为最大满足单位
     * @param size（文件大小，单位为B）
     * @return 文件大小
     */
    public static String formatFileSize(Long size) {
        String sizeName = null;
        if(1024*1024 > size && size >= 1024 ) {
            sizeName = String.format("%.2f",size.doubleValue()/1024) + "KB";
        }else if(1024*1024*1024 > size && size >= 1024*1024 ) {
            sizeName = String.format("%.2f",size.doubleValue()/(1024*1024)) + "MB";
        }else if(size >= 1024*1024*1024 ) {
            sizeName = String.format("%.2f",size.doubleValue()/(1024*1024*1024)) + "GB";
        }else {
            sizeName = size.toString() + "B";
        }
        return sizeName;
    }


    /**
     * 备份文件
     * @return
     * @throws Exception
     */
    public Path backup() throws Exception {
        if (!this.lock.tryLock()) {
            throw new Exception("备份任务进行中！");
        }
        try {
            LocalDateTime now = LocalDateTime.now();
            Path path = Paths.get(AllConstans.STU_BACKUP_SAVE_PATH);
            // 备份的SQL文件
            Path sqlFile = path.resolve(Paths.get(now.format(formatter) + ".sql"));
            if (Files.exists(sqlFile)) {
                // 文件已经存在，则添加后缀
                for (int i = 1; i >= 1; i ++) {
                    sqlFile = path.resolve(Paths.get(now.format(formatter) + "-" + i + ".sql"));
                    if (!Files.exists(sqlFile)) {
                        break;
                    }
                }
            }

            // 初始化目录
            if (!Files.isDirectory(sqlFile.getParent())) {
                Files.createDirectories(sqlFile.getParent());
            }

            // 创建备份文件文件
            Files.createFile(sqlFile);

            // 标准流输出的内容就是 SQL 的备份内容
            try (OutputStream stdOut = new BufferedOutputStream(
                    Files.newOutputStream(sqlFile, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING))) {

                // 监视狗。执行超时时间，1小时
                ExecuteWatchdog watchdog = new ExecuteWatchdog(TimeUnit.HOURS.toMillis(1));

                // 子进程执行器
                DefaultExecutor defaultExecutor = new DefaultExecutor();
                // defaultExecutor.setWorkingDirectory(null); // 工作目录
                defaultExecutor.setWatchdog(watchdog);
                defaultExecutor.setStreamHandler(new PumpStreamHandler(stdOut, System.err));

                // 进程执行命令
                CommandLine commandLine = new CommandLine("mysqldump.exe");
                //commandLine.addArgument("-h" + "81.71.42.116");
                //commandLine.addArgument("-P" + "3306");
                commandLine.addArgument("-u" + this.username);
                commandLine.addArgument("-p" + this.password);
                commandLine.addArgument("progess_pulse");

                log.info("备份 SQL 数据");
                // 同步执行，阻塞直到子进程执行完毕。
                int exitCode = defaultExecutor.execute(commandLine);

                if (defaultExecutor.isFailure(exitCode)) {
                    throw new Exception("备份任务执行异常：exitCode=" + exitCode);
                }
            }

            if (!this.maxAge.isZero()) {
                for (Path file : Files.list(path).collect(Collectors.toList())) {
                    // 获取文件的创建时间
                    LocalDateTime createTime = LocalDateTime.ofInstant(Files.readAttributes(file, BasicFileAttributes.class).creationTime().toInstant(), ZoneId.systemDefault());
                    if (createTime.plus(this.maxAge).isBefore(now)) {
                        log.info("删除过期文件：{}", file.toAbsolutePath().toString());
                        // 删除文件
                        Files.delete(file);
                    }
                }
            }
            return sqlFile;
        } finally {
            this.lock.unlock();
        }
    }
}
