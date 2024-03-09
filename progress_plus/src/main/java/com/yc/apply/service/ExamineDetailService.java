package com.yc.apply.service;

import com.yc.vo.other.ExamineDetailVo;

import java.util.List;

/**
 * 考核详情表;(examine_detail)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
public interface ExamineDetailService {

    /**
     * 批量添加 考核详情
     * @param details
     */
    public Integer addExamineDetailList(List<ExamineDetailVo> details);
}
