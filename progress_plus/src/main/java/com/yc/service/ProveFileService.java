package com.yc.service;

import com.yc.entity.ProveFile;
import com.yc.vo.apply.ApplyVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 文件表;(prove_file)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-11-17
 */
public interface ProveFileService{

    /**
     * 通过ID查询单条数据
     *
     * @param pfid 主键
     * @return 实例对象
     */
    ProveFile queryById(Integer pfid);


    /**
     * 使用者 Id  和 status
     * @param useId
     * @param status
     * @return
     */
    List<ProveFile> queryByuseIdAndStatus(Integer useId,Integer status);

    /**
     * 新增数据
     * 不需要 传 文件类型 文件大小
     * @param proveFile 实例对象
     * @return 实例对象
     */
    ProveFile insert(ProveFile proveFile);


    /**
     * 批量新增数据
     * 不需要 传 文件类型 文件大小
     * @param proveFile 实例对象
     * @return 实例对象
     */
    int insert(List<ProveFile> proveFile);

    /**
     * 更新数据
     *
     * @param proveFile 实例对象
     * @return 实例对象
     */
    ProveFile update(ProveFile proveFile);

    /**
     * 通过主键删除数据
     *
     * @param pfid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer pfid);

    /**
     * 通过使用者Id 批量删除
     * @param gaid
     * @return
     */
    boolean deletByGaid(Integer gaid);

    /**
     * 根据备案id删除
     * @param useid
     */
    void deleteByuseId(Integer useid);

    /**
     * 文件上传
     * @param files 要上传的文件集合
     * @param request 请求
     * @param applyVo 申请vo
     */
    void uploadFile(List<MultipartFile> files, HttpServletRequest request, ApplyVo applyVo);
}
