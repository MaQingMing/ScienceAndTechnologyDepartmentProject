package com.yc.apply.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.HonorApplyInfo;
import com.yc.vo.apply.ApplyVo;

/**
 * 科技类荣誉;(honor_apply_info)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface HonorApplyInfoService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param haiid 主键
     * @return 实例对象
     */
    HonorApplyInfo queryById(Integer haiid);
    
    /**
     * 分页查询
     *
     * @param honorApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<HonorApplyInfo> paginQuery(HonorApplyInfo honorApplyInfo, long current, long size);
    /** 
     * 新增数据
     *
     * @param honorApplyInfo 实例对象
     * @return 实例对象
     */
    HonorApplyInfo insert(HonorApplyInfo honorApplyInfo);
    /** 
     * 更新数据
     *
     * @param honorApplyInfo 实例对象
     * @return 实例对象
     */
    HonorApplyInfo update(HonorApplyInfo honorApplyInfo);
    /** 
     * 通过主键删除数据
     *
     * @param haiid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer haiid);

    /**
     * 添加荣誉申请
     *
     * @param applyVo
     * @return
     */
    int addHonor(ApplyVo applyVo);
}