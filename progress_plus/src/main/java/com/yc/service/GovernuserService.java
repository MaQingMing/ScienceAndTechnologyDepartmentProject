package com.yc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.Governuser;

 /**
 * 管理员表;(governuser)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface GovernuserService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    Governuser queryById(Integer id);
    
    /**
     * 分页查询
     *
     * @param governuser 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<Governuser> paginQuery(Governuser governuser, long current, long size);
    /** 
     * 新增数据
     *
     * @param governuser 实例对象
     * @return 实例对象
     */
    Governuser insert(Governuser governuser);
    /** 
     * 更新数据
     *
     * @param governuser 实例对象
     * @return 实例对象
     */
    Governuser update(Governuser governuser);
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);
}