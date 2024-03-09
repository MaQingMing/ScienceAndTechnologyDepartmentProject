package com.yc.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.entity.TJob;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.yc.entity.TJob;
import org.apache.ibatis.annotations.Select;

import java.util.Set;

/**
 * 职位/学位;(t_job)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface TJobMapper  extends BaseMapper<TJob>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TJob> selectByPage(IPage<TJob> page , @Param(Constants.WRAPPER) Wrapper<TJob> wrapper);

    /**
     * 查询所有的部门
     * @return
     */
    @Select(" select context from t_job ")
    Set<String> selectAllTJob();
}