package com.yc.apply.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.apply.entity.ExamineDetail;
import com.yc.vo.other.ExamineDetailVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 考核详情表;(examine_detail)表数据库访问层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface ExamineDetailMapper extends BaseMapper<ExamineDetail> {

    /**
     * 批量添加 考核详情
     * @param details
     * @return
     */
    int addExamineDetailList(List<ExamineDetailVo> details);
}
