package com.yc.standard.service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.ScientificStandard;
import com.yc.vo.EditProject;

/**
 * 科研基地/科学建设;(scientific_standard)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface ScientificStandardService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param ssid 主键
     * @return 实例对象
     */
    ScientificStandard queryById(Integer ssid);
    
    /**
     * 分页查询
     *
     * @param scientificStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<ScientificStandard> paginQuery(ScientificStandard scientificStandard, long current, long size);
    /** 
     * 新增数据
     *
     * @param scientificStandard 实例对象
     * @return 实例对象
     */
    ScientificStandard insert(ScientificStandard scientificStandard);
    /** 
     * 更新数据
     *
     * @param scientificStandard 实例对象
     * @return 实例对象
     */
    ScientificStandard update(ScientificStandard scientificStandard);
    /** 
     * 通过主键删除数据
     *
     * @param ssid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer ssid);

    /**
     * 添加科研平台项目
     * @param editProject 添加的属性vo类
     * @return 0 失败 1 成功
     */
    int addScientific(EditProject editProject);

    /**
     * 查询科研平台
     * @param pageNum 当前页码
     * @param pageSize 当前查询条数
     * @param leid    级别id
     * @return 分页查询结果
     */
    Page<EditProject> selectScientific(Integer pageNum, Integer pageSize, String leid);

    /**
     * 修改科研平台项目
     * @param editProject 修改的属性vo类
     * @return 0 失败 1 成功
     */
    int updateScientific(EditProject editProject);
}