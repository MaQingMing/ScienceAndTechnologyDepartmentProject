package com.yc.standard.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.BookStandard;
import com.yc.vo.EditProject;
import com.yc.vo.SystemVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 学术专著(含著/编著/译著);(book_standard)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface BookStandardMapper  extends BaseMapper<BookStandard>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<BookStandard> selectByPage(IPage<BookStandard> page , @Param(Constants.WRAPPER) Wrapper<BookStandard> wrapper);

     /**
      * 添加学术专著项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
    int addBook(EditProject editProject);

     /**
      * 查询科技成果
      * @param page 分页参数
      * @param leid 级别id
      * @return 分页查询结果
      */
     Page<EditProject> selectBook(Page<EditProject> page, @Param("leid") String leid);

     /**
      * 修改学术专著项目
      * @param editProject 编辑的实体类
      * @return 0 失败 1 成功
      */
     int updateBook(EditProject editProject);

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