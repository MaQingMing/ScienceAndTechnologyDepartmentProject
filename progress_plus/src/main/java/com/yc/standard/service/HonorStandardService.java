package com.yc.standard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.HonorStandard;
import com.yc.vo.EditProject;

import java.util.List;

/**
 * 科技类荣誉(称号);(honor_standard)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface HonorStandardService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param hsid 主键
     * @return 实例对象
     */
    HonorStandard queryById(Integer hsid);
    
    /**
     * 分页查询
     *
     * @param honorStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<HonorStandard> paginQuery(HonorStandard honorStandard, long current, long size);
    /** 
     * 新增数据
     *
     * @param honorStandard 实例对象
     * @return 实例对象
     */
    HonorStandard insert(HonorStandard honorStandard);
    /** 
     * 更新数据
     *
     * @param honorStandard 实例对象
     * @return 实例对象
     */
    HonorStandard update(HonorStandard honorStandard);
    /** 
     * 通过主键删除数据
     *
     * @param hsid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer hsid);

    /**
     * 添加科技荣誉项目
     * @param editProject 添加的属性vo类
     * @return 0 失败 1 成功
     */
    int addHonor(EditProject editProject);

    /**
     * 查询科技荣誉
     * @param pageNum 当前页码
     * @param pageSize 当前查询条数
     * @param leid    级别id
     * @return 分页查询结果
     */
    Page<EditProject> selectHonor(Integer pageNum, Integer pageSize, String leid);

    /**
     * 修改科技荣誉项目
     * @param editProject 修改的属性vo类
     * @return 0 失败 1 成功
     */
    int updateHonor(EditProject editProject);

    /**
     * 查询荣誉级别对应的分数
     * @return
     */
    List<EditProject> selectLevel();
}