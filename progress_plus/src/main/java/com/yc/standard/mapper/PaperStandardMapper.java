package com.yc.standard.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.PaperApplyInfo;
import com.yc.standard.entity.PaperStandard;
import com.yc.vo.EditProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 学术论文(自科/社科);(paper_standard)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface PaperStandardMapper  extends BaseMapper<PaperStandard>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<PaperStandard> selectByPage(IPage<PaperStandard> page , @Param(Constants.WRAPPER) Wrapper<PaperStandard> wrapper);

     /**
      * 添加学术论文项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
     int addPaper(EditProject editProject);

     /**
      * 查询学术论文
      * @param page 分页参数
      * @param leid 级别id
      * @return 分页查询结果
      */
     Page<EditProject> selectPaper(Page<EditProject> page, @Param("leid") String leid);

     /**
      * 修改学术论文项目
      * @param editProject 修改的属性vo类
      * @return 0 失败 1 成功
      */
     int updatePaper(EditProject editProject);

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