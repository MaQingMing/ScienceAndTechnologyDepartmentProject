package com.yc.standard.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.DirectionStandard;
import com.yc.vo.EditProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 纵向项目;(direction_standard)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface DirectionStandardMapper  extends BaseMapper<DirectionStandard>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<DirectionStandard> selectByPage(IPage<DirectionStandard> page , @Param(Constants.WRAPPER) Wrapper<DirectionStandard> wrapper);


     /**
      * 添加纵向项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
     int addDirection(EditProject editProject);

     /**
      * 查询纵向项目
      * @param page 分页参数
      * @param context 项目描述
      * @param leid 级别id
      * @return 分页查询结果
      */
     Page<EditProject> selectDirection(Page<EditProject> page, @Param("context") String context, @Param("leid") String leid);

     /**
      * 修改纵向项目
      * @param editProject 修改的属性vo类
      * @return 0 失败 1 成功
      */
     int updateDirection(EditProject editProject);

    /**
     *@Description 查询所有的学术论文等级
     *@Return
     *@Author dm
     *@Date Created in 2023/10/31
     **/
    List<String> queryLevels();

    /**
     * 查询纵向项目级别对应的分数
     * @return
     */
    List<EditProject> selectLevel();

    /**
    *@Description 查询细致等级
    *@Return
    *@Author dm
    *@Date Created in 2023/11/26
    **/
    List<String> queryContexts();
}