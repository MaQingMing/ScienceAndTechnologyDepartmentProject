package com.yc.apply.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.TransverseApplyInfo;
import com.yc.vo.apply.ApplyVo;

/**
 * 横向申请详情;(transverse_apply_info)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface TransverseApplyInfoService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    TransverseApplyInfo queryById(String undefinedId);
    
    /**
     * 分页查询
     *
     * @param transverseApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TransverseApplyInfo> paginQuery(TransverseApplyInfo transverseApplyInfo, long current, long size);
    /** 
     * 新增数据
     *
     * @param transverseApplyInfo 实例对象
     * @return 实例对象
     */
    TransverseApplyInfo insert(TransverseApplyInfo transverseApplyInfo);
    /** 
     * 更新数据
     *
     * @param transverseApplyInfo 实例对象
     * @return 实例对象
     */
    TransverseApplyInfo update(TransverseApplyInfo transverseApplyInfo);
    /** 
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    boolean deleteById(String undefinedId);

    /**
     * 添加横向项目申请
     *
     * @param applyVo
     * @return
     */
    int addTransverse(ApplyVo applyVo);
}