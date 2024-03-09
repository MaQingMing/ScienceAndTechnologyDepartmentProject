package com.yc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.Labeldept;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 部门表;(labeldept)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-11-6
 */
public interface LabeldeptService{

    /**
     * 通过ID查询单条数据 
     *
     * @param tid 主键
     * @return 实例对象
     */
    Labeldept queryById(String tid);

    /**
     * 分页查询
     *
     * @param labeldept 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<Labeldept> paginQuery(Labeldept labeldept, long current, long size);
    /**
     * 新增数据
     *
     * @param labeldept 实例对象
     * @return 实例对象
     */
    Labeldept insert(Labeldept labeldept);
    /**
     * 更新数据
     *
     * @param labeldept 实例对象
     * @return 实例对象
     */
    int update(Labeldept labeldept);
    /**
     * 通过主键删除数据
     *
     * @param tid 主键
     * @return 是否成功
     */
    boolean deleteById(String tid);

    /**
     * 查询所有的部门信息
     * @return  结果集
     */
    List<Labeldept> showAll();

    /**
     * 查询不属于自科|社科的部门信息
     * @return  结果集
     */
    List<Labeldept> showNoScience();
}
