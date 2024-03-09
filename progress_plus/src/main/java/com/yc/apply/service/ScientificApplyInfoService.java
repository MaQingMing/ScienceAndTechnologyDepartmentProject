package com.yc.apply.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.ScientificApplyInfo;
import com.yc.vo.Result;
import com.yc.vo.apply.AddScientificApplyInfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.awt.image.RescaleOp;
import java.util.List;
import java.util.Map;

/**
 * 科技基地,学科建设申请详情;(scientific_apply_info)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface ScientificApplyInfoService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param saiid 主键
     * @return 实例对象
     */
    ScientificApplyInfo queryById(Integer saiid);
    
    /**
     * 分页查询
     *
     * @param scientificApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<ScientificApplyInfo> paginQuery(ScientificApplyInfo scientificApplyInfo, long current, long size);
    /** 
     * 新增数据
     *
     * @param scientificApplyInfo 实例对象
     * @return 实例对象
     */
    ScientificApplyInfo insert(ScientificApplyInfo scientificApplyInfo);
    /** 
     * 更新数据
     *
     * @param scientificApplyInfo 实例对象
     * @return 实例对象
     */
    ScientificApplyInfo update(ScientificApplyInfo scientificApplyInfo);
    /** 
     * 通过主键删除数据
     *
     * @param saiid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer saiid);

    /**
     * 查询已通过的备案
     * @param username
     * @return
     */
    List<Map<String,Object>> queryRecords(String username);

    /**
     * 查询科研平台下的项目类型
     * @return
     */
    List<Map<String,Object>> queryLname();

    /**
     * 添加平台申请
     * @param addScientificApplyInfoVo
     * @param files
     * @param request
     */
    int addScientificApply(AddScientificApplyInfoVo addScientificApplyInfoVo, List<MultipartFile > files, HttpServletRequest request) throws Exception;

    /**
     * 查询平台细则
     * @return
     */
    Result queryRoles();

}