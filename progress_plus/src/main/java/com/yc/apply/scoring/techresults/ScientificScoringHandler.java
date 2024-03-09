package com.yc.apply.scoring.techresults;

import com.yc.apply.scoring.TechResultsInterface;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.Result;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 科技平台计分
 */
@Component
public class ScientificScoringHandler implements TechResultsInterface {

    @Override
    public Result scoringScore(ApplyVo applyVo) {
        List<Map<String, Object>> maps = applyVo.getScientificData();
        for (Map<String, Object> map : maps) {
            String content = (String) map.get("content");
            if (content.contains("#1")){
                //替换#1  基础分
                Integer score = (Integer) map.get("score");
                content=content.replace("#1",String.valueOf(score));
            }
            if (content.contains("#2")){
                //替换#2  百分比
                String ratio1 =(String) map.get("ratio");
                content = content.replace("#2",ratio1);
            }
            if (content.contains("#3")){
                //替换#3  替换子项目名称
                String lname = (String)map.get("lname");
                content = content.replace("#3",lname);
            }
            //百分比  20%
            String ratio = (String) map.get("ratio");
            double ratioFinal = Double.parseDouble(ratio.replace("%", "")) / 100.0;

            Integer foundScoreInt = (Integer) map.get("foundScore");
            Integer checkScoreInt = (Integer) map.get("checkScore");
            double foundScore = (double) foundScoreInt;
            double checkScore = (double) checkScoreInt;
            foundScore =  (foundScore * ratioFinal);
            checkScore =  (checkScore * ratioFinal);
            // 使用新计算出的分数更新地图中的值
            map.put("foundScore", foundScore);
            map.put("checkScore", checkScore);
            map.put("content",content);
            map.put("leid",0);
            map.put("cash",1);
        }
        return Result.success(maps);
    }
}
