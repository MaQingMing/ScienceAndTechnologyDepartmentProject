package com.yc.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.entity.Labeldept;
import com.yc.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;
import java.util.Map;

/**
 * 部门表;(labeldept)表数据库访问层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-11-6
 */
@Mapper
public interface LabeldeptMapper extends BaseMapper<Labeldept> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<Labeldept> selectByPage(IPage<Labeldept> page, @Param(Constants.WRAPPER) Wrapper<Labeldept> wrapper);

    /**
     * 查询所有的部门信息
     *
     * @return 结果集
     */
    @Select("select a.tid,a.tname,a.tscience,a.status from labeldept a ORDER BY tid DESC")
    List<Labeldept> showAll();

    /**
     * 查询不属于自科|社科的部门信息
     *
     * @return 结果集
     */
    @Select("select a.tid,a.tname,a.tscience from labeldept a where a.tscience is NULL ORDER BY tid DESC")
    List<Labeldept> showNoScience();

    /**
     * 查询所有的部门
     * @return
     */
    @Select(" select tname from labeldept ")
    Set<String> selectAllDept();

    /**
     * @Description 查询所有能够申请的学院
     * @Return
     * @Author dm
     * @Date Created in 2023/11/22
     **/
    List<Labeldept> queryColleges();

    /**
     * 查询除普通用户的角色
     * @return
     */
    @Select("select tid,tname from labeldept where tid not in(1,2)")
    List<Labeldept> selectDepts();
}