package com.yc.standard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.InventStandard;
import com.yc.vo.EditProject;

import java.util.List;

/**
 * 发明专利;(invent_standard)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface InventStandardService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param iid 主键
     * @return 实例对象
     */
    InventStandard queryById(Integer iid);
    
    /**
     * 分页查询
     *
     * @param inventStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<InventStandard> paginQuery(InventStandard inventStandard, long current, long size);
    /** 
     * 新增数据
     *
     * @param inventStandard 实例对象
     * @return 实例对象
     */
    InventStandard insert(InventStandard inventStandard);
    /** 
     * 更新数据
     *
     * @param inventStandard 实例对象
     * @return 实例对象
     */
    InventStandard update(InventStandard inventStandard);
    /** 
     * 通过主键删除数据
     *
     * @param iid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer iid);

    /**
     * 添加发明专利项目
     * @param editProject 添加的属性vo类
     * @return 0 失败 1 成功
     */
    int addInvent(EditProject editProject);

    /**
     * 查询发明专利
     * @param pageNum 当前页码
     * @param pageSize 当前查询条数
     * @param leid    级别id
     * @return 分页查询结果
     */
    Page<EditProject> selectInvent(Integer pageNum, Integer pageSize, String leid);

    /**
     * 修改发明专利项目
     * @param editProject 修改的属性vo类
     * @return 0 失败 1 成功
     */
    int updateInvent(EditProject editProject);

    /**
     * 查询专利对应的分数
     * @return
     */
    List<EditProject> selectLevel();
}