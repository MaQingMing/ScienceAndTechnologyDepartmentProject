package com.yc.standard.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.TransverseStandard;
import com.yc.vo.EditProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 横向项目;(transverse_standard)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface TransverseStandardMapper  extends BaseMapper<TransverseStandard>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TransverseStandard> selectByPage(IPage<TransverseStandard> page , @Param(Constants.WRAPPER) Wrapper<TransverseStandard> wrapper);

     /**
      * 添加横向项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
     int addTransverse(EditProject editProject);

     /**
      * 查询横向项目
      * @param page 分页参数
      * @param context 项目描述
      * @param leid 级别id
      * @return 分页查询结果
      */
     Page<EditProject> selectTransverse(Page<EditProject> page, @Param("context") String context, @Param("leid") String leid);

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

     /**
      *@Description 根据科技成果分数或者类别或者审核部门筛选小分类
      *@Return
      *@Author dm
      *@Date Created in 2023/10/23
      **/
     int queryTypeId(@Param("common")String common,@Param("value")String value);

    /**
     *@Description 查询所有的学术论文等级
     *@Return
     *@Author dm
     *@Date Created in 2023/10/31
     **/
    List<String> queryLevels();
}