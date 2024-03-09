package com.yc.apply.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.apply.entity.BaseScore;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

 /**
 * 底分表;(base_score)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
@Mapper
public interface BaseScoreMapper  extends BaseMapper<BaseScore> {
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<BaseScore> selectByPage(IPage<BaseScore> page , @Param(Constants.WRAPPER) Wrapper<BaseScore> wrapper);
}