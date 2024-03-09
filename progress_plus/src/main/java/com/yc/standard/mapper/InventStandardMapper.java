package com.yc.standard.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.InventStandard;
import com.yc.vo.EditProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 发明专利;(invent_standard)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface InventStandardMapper  extends BaseMapper<InventStandard>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<InventStandard> selectByPage(IPage<InventStandard> page , @Param(Constants.WRAPPER) Wrapper<InventStandard> wrapper);

     /**
      * 添加发明专利项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
     int addInvent(EditProject editProject);

     /**
      * 查询发明专利
      * @param page 分页参数
      * @param leid 级别id
      * @return 分页查询结果
      */
     Page<EditProject> selectInvent(Page<EditProject> page, @Param("leid") String leid);

     /**
      * 修改发明专利项目
      * @param editProject 修改的属性vo类
      * @return 0 失败 1 成功
      */
     int updateInvent(EditProject editProject);

     /**
      *@Description 根据科技成果分数或者类别或者审核部门筛选小分类
      *@Return
      *@Author dm
      *@Date Created in 2023/10/23
      **/
     int queryTypeId(@Param("common")String common,@Param("value")String value);


     /**
     *@Description 查询发明专利的所有种类
     *@Return
     *@Author dm
     *@Date Created in 2023/10/25
     **/
     List<String> queryInventTypes();

    /**
     *@Description 查询所有的学术论文等级
     *@Return
     *@Author dm
     *@Date Created in 2023/10/31
     **/
    List<String> queryLevels();

    /**
     * 查询专利级别对应的分数
     * @return
     */
    List<EditProject> selectLevel();
}