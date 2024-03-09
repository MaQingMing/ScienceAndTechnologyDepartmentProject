package com.yc.apply.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.InventApplyInfo;
import com.yc.vo.apply.ApplyVo;

/**
 * 专利申请详情;(invent_apply_info)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface InventApplyInfoService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param iaid 主键
     * @return 实例对象
     */
    InventApplyInfo queryById(Integer iaid);
    
    /**
     * 分页查询
     *
     * @param inventApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<InventApplyInfo> paginQuery(InventApplyInfo inventApplyInfo, long current, long size);
    /** 
     * 新增数据
     *
     * @param inventApplyInfo 实例对象
     * @return 实例对象
     */
    InventApplyInfo insert(InventApplyInfo inventApplyInfo);
    /** 
     * 更新数据
     *
     * @param inventApplyInfo 实例对象
     * @return 实例对象
     */
    InventApplyInfo update(InventApplyInfo inventApplyInfo);
    /** 
     * 通过主键删除数据
     *
     * @param iaid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer iaid);

    /**
     * 添加专利项目申请
     *
     * @param applyVo
     * @return
     */
    int addInvent(ApplyVo applyVo);
}