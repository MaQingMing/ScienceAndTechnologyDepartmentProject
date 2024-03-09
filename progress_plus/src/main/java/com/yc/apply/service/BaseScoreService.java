package com.yc.apply.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.BaseScore;

/**
 * 底分表;(base_score)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
public interface BaseScoreService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param bsid 主键
     * @return 实例对象
     */
    BaseScore queryById(Integer bsid);
    
    /**
     * 分页查询
     *
     * @param baseScore 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<BaseScore> paginQuery(BaseScore baseScore, long current, long size);
    /** 
     * 新增数据
     *
     * @param baseScore 实例对象
     * @return 实例对象
     */
    BaseScore insert(BaseScore baseScore);
    /** 
     * 更新数据
     *
     * @param baseScore 实例对象
     * @return 实例对象
     */
    BaseScore update(BaseScore baseScore);
    /** 
     * 通过主键删除数据
     *
     * @param bsid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer bsid);
}