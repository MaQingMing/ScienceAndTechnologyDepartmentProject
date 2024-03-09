package com.yc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.apply.entity.ExamineSpecialUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 不参加考核的人员;(examine_special_user)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-12-7
 */
@Mapper
public interface ExamineSpecialUserMapper  extends BaseMapper<ExamineSpecialUser> {

}