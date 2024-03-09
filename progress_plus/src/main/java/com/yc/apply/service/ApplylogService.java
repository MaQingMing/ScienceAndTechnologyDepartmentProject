package com.yc.apply.service;

import com.yc.apply.entity.Applylog;
import com.yc.vo.apply.ApplyVo;

/**
 * 申请审核记录表;(applylog)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface ApplylogService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    Applylog queryById(Integer id);

    /** 
     * 新增数据
     *
     * @param applylog 实例对象
     * @return 实例对象
     */
    Applylog insert(Applylog applylog);


     /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

     int addApplylog(ApplyVo applyVo);

    /**
     * 删除申请细表记录
     * @param tableName 表名
     * @param gaid 申请大表id
     * @return
     */
    int deleteApply(String tableName, Integer gaid);
 }