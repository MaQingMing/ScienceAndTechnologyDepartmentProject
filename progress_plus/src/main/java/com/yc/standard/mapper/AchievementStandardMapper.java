package com.yc.standard.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.AchievementStandard;
import com.yc.vo.EditProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
import java.util.List;

/**
 * 科技成果奖;(achievement_standard)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface AchievementStandardMapper  extends BaseMapper<AchievementStandard>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<AchievementStandard> selectByPage(IPage<AchievementStandard> page , @Param(Constants.WRAPPER) Wrapper<AchievementStandard> wrapper);

     /**
      *@Description 查询所有的学术论文等级
      *@Return
      *@Author dm
      *@Date Created in 2023/10/31
      **/
     List<String> queryLevels();

    /**
     * 添加科技成果
     * @param editProject 添加的实体类
     * @return 0 失败 1 成功
     */
    int addAchievement(EditProject editProject);

    /**
     * 查询科技成果
     * @param page 分页参数
     * @param context 项目描述
     * @param leid 级别id
     * @return 分页查询结果
     */
    Page<EditProject> selectAchievement(Page<EditProject> page, @Param("context") String context, @Param("leid") String leid,@Param("status") Integer status);

    /**
     * 修改科技成果
     * @param editProject
     * @return 0 失败 1 成功
     */
    int updateAchievement(EditProject editProject);

    /**
    *@Description 查询细致等级context
    *@Return
    *@Author dm
    *@Date Created in 2023/11/26
    **/
    List<String> queryContexts();
}