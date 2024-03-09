package com.yc.standard.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.standard.entity.TechResultsType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科技成果分类;(tech_results_type)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface TechResultsTypeMapper  extends BaseMapper<TechResultsType>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TechResultsType> selectByPage(IPage<TechResultsType> page , @Param(Constants.WRAPPER) Wrapper<TechResultsType> wrapper);

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
     * 查询启用的项目类型名和id
     * @return 查询的项目类型名和id集合
     */
    List<TechResultsType> selectTrname();

    /**
     * 修改项目类别状态
     * @param status 将被修改的状态
     * @param trid 被修改的类别id
     * @return 0 失败 1成功
     */
    int modifyStatus(@Param("status") Integer status, @Param("trid") Integer trid);

    /**
     * 查询计算依据
     * @param trid 项目id
     * @return 计算依据
     */
    String selectAccording(@Param("trid") String trid);
}