package com.yc.task;

import com.yc.common.handler.MysqlBackupHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

/**
 * @PACKAGE_NAME: com.yc.task
 * @NAME: mysql 数据备份
 * @USER: Administrator
 * @DATE: 2023/11/27
 * @MONTH_NAME_SHORT: 11月
 * @MONTH_NAME_FULL: 十一月
 **/
@Slf4j
@Configuration
@EnableScheduling
public class MysqlBackupTask {

    @Autowired
    private MysqlBackupHandler backupService;

    /**
     * 每 7 天执行一次
     */
    @Scheduled(cron = "0 0 */7 * * ?")
    public void backup () {
        try {
            Path file = this.backupService.backup();
            log.info("备份成功：{}", file.toAbsolutePath().toString());
        } catch (Exception e) {
            log.error("备份任务执行异常： {}", e.getMessage());
        }
    }
}
