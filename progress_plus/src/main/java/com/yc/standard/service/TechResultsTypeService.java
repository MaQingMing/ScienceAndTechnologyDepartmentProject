package com.yc.standard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.TechResultsType;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 科技成果分类;(tech_results_type)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface TechResultsTypeService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param trid 主键
     * @return 实例对象
     */
    TechResultsType queryById(Integer trid);
    
    /**
     * 分页查询
     *
     * @param techResultsType 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<TechResultsType> paginQuery(TechResultsType techResultsType, long current, long size);
    /** 
     * 新增数据
     *
     * @param techResultsType 实例对象
     * @return 实例对象
     */
    TechResultsType insert(TechResultsType techResultsType);
    /** 
     * 更新数据
     *
     * @param techResultsType 实例对象
     * @return 实例对象
     */
    TechResultsType update(TechResultsType techResultsType);
    /** 
     * 通过主键删除数据
     *
     * @param trid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer trid);

    /**
     * 添加科技项目类别
     * @param techResultsType 科技项目类别实例
     * @return 1 成功 0 失败
     */
    int addTechResultsType(TechResultsType techResultsType);

    /**
     * 查询科技项目类别
     * @param trname 科技项目类别名称
     * @return  科技项目类别集合
     */
    List<TechResultsType> selectTechResultsType(String trname);

    /**
     * 修改科技项目类别
     * @param techResultsType 科技项目类别名称
     * @return 1 成功 0 失败
     */
    int updateTechResultsType(TechResultsType techResultsType);

    /**
     * 修改项目类别状态
     * @param status 将被修改的状态
     * @param trid 被修改的类别id
     * @return 0 失败 1成功
     */
    int modifyStatus(Integer status, Integer trid);

    /**
     * 查询启用的项目类型名和id
     * @return 查询的项目类型名和id集合
     */
    List<TechResultsType> selectTrname();

    /**
     * 查询计算依据
     * @param trid 项目id
     * @return 计算依据
     */
    String selectAccording(String trid);

    /**
     * 查询所有科技项目类别
     * @param tid
     * @param status
     * @param session
     * @return
     */
    List<String> queryAllType(String tid, String status, HttpSession session);
}