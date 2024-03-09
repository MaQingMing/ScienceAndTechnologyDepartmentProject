package com.yc.standard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.TransverseStandard;
import com.yc.vo.EditProject;

import java.util.List;

/**
 * 横向项目;(transverse_standard)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface TransverseStandardService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param tsid 主键
     * @return 实例对象
     */
    TransverseStandard queryById(Integer tsid);
    
    /**
     * 分页查询
     *
     * @param transverseStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TransverseStandard> paginQuery(TransverseStandard transverseStandard, long current, long size);
    /** 
     * 新增数据
     *
     * @param transverseStandard 实例对象
     * @return 实例对象
     */
    TransverseStandard insert(TransverseStandard transverseStandard);
    /** 
     * 更新数据
     *
     * @param transverseStandard 实例对象
     * @return 实例对象
     */
    TransverseStandard update(TransverseStandard transverseStandard);
    /** 
     * 通过主键删除数据
     *
     * @param tsid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer tsid);

    /**
     * 添加横向项目
     * @param editProject 添加的属性vo类
     * @return 0 失败 1 成功
     */
    int addTransverse(EditProject editProject);

    /**
     * 查询横向项目
     * @param pageNum 当前页码
     * @param pageSize 当前查询条数
     * @param context 项目描述
     * @param leid 级别id
     * @return 分页查询结果
     */
    Page<EditProject> selectTransverse(Integer pageNum, Integer pageSize, String context, String leid);

    /**
     * 修改横向项目
     * @param editProject 修改的属性vo类
     * @return 0 失败 1 成功
     */
    int updateTransverse(EditProject editProject);

    /**
     * 查询横向项目级别对应的分数
     * @return
     */
    List<EditProject> selectLevel();

    /**
     * 查询类型对应的分数
     * @param type 自科 | 社科
     * @return
     */
    List<EditProject> selectScore(String type);
}