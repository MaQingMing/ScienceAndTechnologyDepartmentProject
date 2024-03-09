package com.yc.apply.scoring.techresults;

import com.yc.apply.scoring.TechResultsInterface;
import com.yc.standard.service.TransverseStandardService;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.EditProject;
import com.yc.vo.Result;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zhupanlin
 * @version 1.0
 * @description: TODO
 * @date 2023/11/17 13:06
 */
@Component
public class TransverseScoringHandler implements TechResultsInterface {

    @Resource
    private TransverseStandardService transverseStandardService;

    /**
     * 横向项目计分规则
     * @param applyVo 申请信息
     * @return
     */
    @Override
    public Result scoringScore(ApplyVo applyVo) {
        // 设置计分根据
        // 判断是否是第一署名单位
        if (!"1".equals(applyVo.getFirstSign())){
            return Result.success(0);
        }
        // 判断是自科还是社科
        double score = 0;
        List<EditProject> results = transverseStandardService.selectScore(applyVo.getType());
        double money = Double.parseDouble(applyVo.getMoney());
        double tmp = money;
        String pattern = "(\\d+)\\D+-(\\d+)\\D+";
        String replacement = "$1-$2";

        Pattern regex = Pattern.compile(pattern);
        for (int i = 0; i < results.size(); i++) {
            // 获取当前计算的科技分
            double sc = results.get(i).getScore();
            // 获取当前级别
            String tmpStr = results.get(i).getLname();
            String judgeStr = tmpStr;
            Matcher matcher = regex.matcher(tmpStr);
            // 对字符串进行处理
            if (matcher.find()){
                tmpStr = matcher.replaceAll(replacement);
            }else {
                tmpStr = tmpStr.replaceAll("[^0-9]", "");
            }

            String[] split = tmpStr.split("-");
            // 判断是区间，还是单个数字计算
            // 单个数字计算
            if (split.length == 1){
                // 当前计算的分数
                double scoring = Double.parseDouble(split[0]);
                // 以上
                if (judgeStr.indexOf("以上") > -1) {
                    if (money > scoring){
                        score += tmp * sc;
                        break;
                    }
                }else if (judgeStr.indexOf("以下") > -1){
                    // 以下
                    if (money > scoring){
                        score += scoring * sc;
                        tmp -= scoring;
                    }else {
                        score += tmp * sc;
                        applyVo.setScore(score);
                        break;
                    }
                }
            }else {
                // 区间计算
                // 当前计算的分数
                double smallSc = Double.parseDouble(split[0]);
                double bigSc = Double.parseDouble(split[1]);
                if (money > bigSc){
                    score += (bigSc - smallSc) * sc;
                    tmp -= bigSc - smallSc;
                }else {
                    score += tmp * sc;
                    break;
                }
            }
        }
        score = Double.parseDouble(String.format("%.2f", score));
        applyVo.setScore(score);
        return Result.success("计算成功");
    }

}
