package com.yc.apply.scoring.techresults;

import com.yc.apply.scoring.TechResultsInterface;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 科技成果奖 计分处理器
 * @author 1943
 */
@Component
public class AchievementScoringHandler implements TechResultsInterface {

    @Override
    public Result scoringScore(ApplyVo applyVo) {
        System.out.println(applyVo.toString());
        Double score = applyVo.getScore();
        double rt = 1;
        String ratio = applyVo.getRatio();
        String addScore = applyVo.getAddScore();
        String xmschoolorder = applyVo.getXmschoolorder();
        String xmworkersorder = applyVo.getXmworkersorder();

        if(!xmschoolorder.equals("0") && !xmworkersorder.equals("0")){
            score = score/Integer.valueOf(xmschoolorder)/Integer.valueOf(xmworkersorder);
        }
        if (!StringUtils.isEmpty(ratio) && !ratio.equals("1")){
            ratio = ratio.replace("%", "");
            rt = Double.valueOf(ratio) / 100;
            score = score * rt;
        }
        if(addScore.indexOf("200") != -1){
            score += 200;
        }
        if(addScore.indexOf("100") != -1){
            score += 100;
        }
        score = Double.parseDouble(String.format("%.2f", score));
        System.out.println("score = " + score);
        applyVo.setScore(score);
        return Result.success();
    }
}
