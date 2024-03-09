package com.yc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.common.utils.AllConstans;
import com.yc.common.utils.UploadFileUtil;
import com.yc.entity.ProveFile;
import com.yc.mapper.ProveFileMapper;
import com.yc.service.ProveFileService;
import com.yc.vo.apply.ApplyVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件表;(prove_file)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-11-17
 */
@Slf4j
@Service
public class ProveFileServiceImpl extends ServiceImpl<ProveFileMapper,ProveFile> implements ProveFileService {

    @Autowired
    private ProveFileMapper proveFileMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param pfid 主键
     * @return 实例对象
     */
    @Override
    public ProveFile queryById(Integer pfid){
        return proveFileMapper.selectById(pfid);
    }


    @Override
    public List<ProveFile> queryByuseIdAndStatus(Integer useId, Integer status) {
        List<ProveFile> list = proveFileMapper.queryfile(useId,status);
        if( null == list || list.isEmpty()){
            return Collections.emptyList();
        }

        Map<String, Integer> type = new HashMap<>();
        type.put("PDF",0);
        type.put("XLSX",0);
        type.put("XLS",0);
        type.put("DOC",0);
        type.put("DOCX",0);
        // 处理当前文件是否能直接显示
        for (ProveFile proveFile : list) {
            if(type.get(proveFile.getFileType().toUpperCase())!=null){
                proveFile.setIsShow(0);
            }else{
                proveFile.setIsShow(1);
            }
        }

        return list;
    }

    /**
     * 新增数据
     *
     * @param proveFile 实例对象
     * @return 实例对象
     */
    @Override
    public ProveFile insert(ProveFile proveFile){
        printFileInfo(proveFile);
        proveFileMapper.insert(printFileInfo(proveFile));
        return proveFile;
    }

    @Override
    public int insert(List<ProveFile> proveFile) {
        /*for (ProveFile file : proveFile) {
            file = printFileInfo(file);
        }*/
        saveBatch(proveFile);
        return 1;
    }

    /**
     * 更新数据
     *
     * @param proveFile 实例对象
     * @return 实例对象
     */
    @Override
    public ProveFile update(ProveFile proveFile){
        proveFile = printFileInfo(proveFile);
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<ProveFile> chainWrapper = new LambdaUpdateChainWrapper<ProveFile>(proveFileMapper);
        if(StrUtil.isNotBlank(proveFile.getPath())){
            chainWrapper.eq(ProveFile::getPath, proveFile.getPath());
        }
        if(StrUtil.isNotBlank(proveFile.getFileName())){
            chainWrapper.eq(ProveFile::getFileName, proveFile.getFileName());
        }
        //2. 设置主键，并更新
        chainWrapper.set(ProveFile::getFileType,proveFile.getFileType());
        chainWrapper.set(ProveFile::getFileSize,proveFile.getFileSize());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(proveFile.getPfid());
        }else{
            return proveFile;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param pfid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer pfid){
        //删除文件
        ProveFile proveFile = queryById(pfid);
        deleteFile(proveFile);
        //删除数据库数据
        int total = proveFileMapper.deleteById(pfid);
        return total > 0;
    }

    @Override
    public boolean deletByGaid(Integer gaid) {
        LambdaQueryWrapper wrapper = Wrappers.<ProveFile>lambdaQuery()
                .eq(ProveFile::getUseid,gaid);

        List<ProveFile> list = proveFileMapper.selectList(wrapper);
        if(null == list || list.isEmpty()){
            return true;
        }
        int total = 0;
        for (ProveFile proveFile : list) {
            //删除数据库数据
            total = proveFileMapper.deleteById(proveFile.getPfid());
            deleteFile(proveFile);
        }
        return total > 0;
    }

    @Override
    public void deleteByuseId(Integer useid){
        proveFileMapper.deleteByuseId(useid);
    }


    /**
     * 根据文件路径获取 大小 和 类型
     * @param proveFile
     * @return
     */
    public ProveFile printFileInfo(ProveFile proveFile){
        Path path = Paths.get(AllConstans.STU_PAHT + File.separator + proveFile.getPath());
        try {
            // 获取文件大小
            long fileSize = Files.size(path);
            proveFile.setFileSize(Double.valueOf(String.valueOf(fileSize)));
            // 获取文件类型
            String fileType = path.getFileName().toString();
            int dotIndex = fileType.lastIndexOf(".");
            proveFile.setFileType(fileType.substring(dotIndex + 1));
        } catch (IOException e) {
            log.error("Unable to get file size: " + e.getMessage());
        }finally {
            return proveFile;
        }
    }

    /**
     * 根据文件路径删除文件
     * @param proveFile
     */
    public static void deleteFile(ProveFile proveFile) {
        Path path = Paths.get(AllConstans.STU_PAHT + File.separator +proveFile.getPath());
        try {
            Files.delete(path);
        } catch (IOException e) {
            log.error("Unable to delete file " + proveFile.getPath() + ": " + e.getMessage());
        }
    }

    /**
     * 文件上传
     * @param files 要上传的文件集合
     * @param request 请求
     * @param applyVo 申请vo
     */
    @Override
    public void uploadFile(List<MultipartFile> files, HttpServletRequest request, ApplyVo applyVo) {
        if (files != null && files.size() > 0){
            // 调用工具类将文件保存到本地 并获取返回值（Map类型 数据是文件信息
            // 添加文件记录
            Map<String, UploadFileUtil.UploadFile> stringUploadFileMap
                    = UploadFileUtil.uploadFiles( request, files);
            files.forEach(file -> {
                String originalFilename = file.getOriginalFilename(); // 获取文件名（用于查找map集合中的新地址
                String newFilePath = stringUploadFileMap.get(originalFilename).getNewFileUrl(); // 获取map中的新地址
                ProveFile proveFile = new ProveFile(1,newFilePath,originalFilename, file.getContentType(),
                        (double) file.getSize(),applyVo.getGaid());
                insert(proveFile);
            });
        }
    }
}
