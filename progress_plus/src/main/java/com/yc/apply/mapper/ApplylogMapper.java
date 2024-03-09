package com.yc.apply.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.apply.entity.Applylog;
import org.apache.ibatis.annotations.*;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Map;

/**
 * 申请审核记录表;(applylog)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface ApplylogMapper  extends BaseMapper<Applylog>{

    /**
     * 查询驳回记录
     * @param gaid
     * @return
     */
    @Select("select status,mark from applylog where applyid = #{gaid} and status = -1 order by create_time desc limit 1")
    List<Map<String,Object>> selectdept(@Param("gaid") Integer gaid);

    /**
     * 删除申请日志
     * @param gaid
     */
    @Delete("delete from applylog where applyid = #{gaid};")
    void deletebygaid(@Param("gaid") Integer gaid);

    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<Applylog> selectByPage(IPage<Applylog> page , @Param(Constants.WRAPPER) Wrapper<Applylog> wrapper);

    /**
    *@Description 批量添加审核记录
    *@Return
    *@Author dm
    *@Date Created in 2023/11/23
    **/
    int insertBatch(@Param("list") List<Applylog> list);

    /**
    *@Description 批量加入特定的审核记录
    *@Return
    *@Author dm
    *@Date Created in 2023/12/6
    **/
    int insertSpecialBatch(@Param("uid")int uid,@Param("list")List<String> applyIds);

    /**
     * 删除申请细表记录
     * @param tableName 表名
     * @param gaid 申请大表id
     * @return
     */
    int deleteApply(@Param("tableName")String tableName, @Param("gaid")Integer gaid);

    /**
    *@Description 查询驳回之前是否有认可的记录
    *@Return
    *@Author dm
    *@Date Created in 2024/1/4
    **/
    @Select("select count() from applylog where applyid = #{gaid} and status = 2 and mark = 2")
    Applylog queryOne(@Param("gaid")String gaid);
}