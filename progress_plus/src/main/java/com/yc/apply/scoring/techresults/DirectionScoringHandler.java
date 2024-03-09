package com.yc.apply.scoring.techresults;

import com.yc.apply.scoring.TechResultsInterface;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * @author 林允
 * @version 1.0
 * @description: TODO
 * @date 2023/11/21 18:46
 */
@Component
public class DirectionScoringHandler implements TechResultsInterface {

    /**
     * 纵向计分规则
     * @param applyVo 申请信息
     * @return
     */
    @Override
    public Result scoringScore(ApplyVo applyVo) {
        double rt = 1;
        // 获取项目所经过的阶段
        String stage = applyVo.getStage();
        String[] stages = stage.split(",");
        if (stages.length == 1){
            if ("申报".equals(stages[0])){
                applyVo.setFoundScore(0);
            }else {
                applyVo.setDeclareScore(0);
            }
        }else if (stages.length < 1){
            applyVo.setScore(0.0);
            return Result.error("计算异常,未选取项目阶段");
        }
        double score = applyVo.getDeclareScore() + applyVo.getFoundScore();
        // 判断是否有附则
        String ratio = applyVo.getRatio();
        // 判断计算的类型
        Integer scoreType = applyVo.getScoreType(); 
        // 如果有附则
        if (!StringUtils.isEmpty(ratio) && scoreType != null){
            ratio = ratio.replace("%", "");
            if (scoreType == 1){
                rt = Double.valueOf(ratio) / 100;
                score = score * rt;
            }else if (scoreType == 2){
                rt = Double.valueOf(ratio) / 100 + 1;
                score = score * rt;
            }
        }
        score = Double.parseDouble(String.format("%.2f", score));
        applyVo.setScore(score);
        return Result.success("计算成功");
    }

}
