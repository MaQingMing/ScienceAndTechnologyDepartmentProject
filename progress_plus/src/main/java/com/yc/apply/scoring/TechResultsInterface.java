package com.yc.apply.scoring;

import com.yc.vo.apply.ApplyVo;
import com.yc.vo.Result;
import org.springframework.stereotype.Service;

/**
 *  成果计分 类型 接口
 * @author 1943
 */
@Service
public interface TechResultsInterface {

    /**
     * 科技成果计分
     * @param applyVo 申请信息
     * @return
     */
    Result scoringScore(ApplyVo applyVo);
}
