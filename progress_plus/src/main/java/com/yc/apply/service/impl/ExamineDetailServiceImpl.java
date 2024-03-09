package com.yc.apply.service.impl;

import com.yc.apply.mapper.ExamineDetailMapper;
import com.yc.apply.service.ExamineDetailService;
import com.yc.vo.other.ExamineDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 考核详情表;(examine_detail)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
@Service
public class ExamineDetailServiceImpl  implements ExamineDetailService{

    @Autowired
    private ExamineDetailMapper examineDetailMapper;

    @Override
    public Integer addExamineDetailList(List<ExamineDetailVo> details) {
        return examineDetailMapper.addExamineDetailList(details);
    }
}
