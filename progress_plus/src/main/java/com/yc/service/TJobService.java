package com.yc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.TJob;

 /**
 * 职位/学位;(t_job)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface TJobService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    TJob queryById(Integer id);
    
    /**
     * 分页查询
     *
     * @param tJob 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TJob> paginQuery(TJob tJob, long current, long size);
    /** 
     * 新增数据
     *
     * @param tJob 实例对象
     * @return 实例对象
     */
    TJob insert(TJob tJob);
    /** 
     * 更新数据
     *
     * @param tJob 实例对象
     * @return 实例对象
     */
    TJob update(TJob tJob);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);
}