package com.yc.apply.scoring.techresults;

import com.yc.apply.mapper.BookApplyInfoMapper;
import com.yc.apply.scoring.TechResultsInterface;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 学术专著(含著/编著/译著) 计分处理器
 * @author zxy
 */
@Component
public class BookScoringHandler implements TechResultsInterface {

    @Autowired
    private BookApplyInfoMapper bookApplyInfoMapper;

    /**
     * 非正常类型的计分方法
     * @param userdata 用户申请数据（用于计分
     * @return
     */
    public int fzctype(Map<String,Object> userdata){
        Map<String,Object> scoreandmax = new HashMap<>();
        if (userdata.get("xmtype").equals("专著")==true){
            scoreandmax = bookApplyInfoMapper.selectmaxscore("专著");
            // 该类别的id
            int id = (int) scoreandmax.get("leid");
            return id;
        }else if (userdata.get("xmtype").equals("编著")==true||userdata.get("xmtype").equals("译著")==true){
            scoreandmax = bookApplyInfoMapper.selectmaxscore("编著");
            // 该类别的id
            int id = (int) scoreandmax.get("leid");
            return id;
        }else{
            return -1;
        }
    }

    @Override
    public Result scoringScore(ApplyVo applyVo) {
        // 用户数据（用于计算
        Map<String,Object> userdata = new HashMap<>();
        // 基础分与最高分
        Map<String,Object> scoreandmax = new HashMap<>();
        userdata = applyVo.getUserdata();
        // 科技分
        int score = 0;
        int moneyscore = 0;
        // 第一层判断 根据文档作者排序仅第一与独著允许计分
        if (userdata.get("xmorder").equals("第一")==true||userdata.get("xmorder").equals("独著")==true){
            // 第二层判断 判断著作类型
            if (userdata.get("xmtype").equals("专著")==true){
                scoreandmax = bookApplyInfoMapper.selectmaxscore("专著");
                // 多少分/万字
                int jc = (int) scoreandmax.get("score");
                // 最高多少分
                int max = (int) scoreandmax.get("max_score");
                // 该类别的id
                int id = (int) scoreandmax.get("leid");
                // 是否能换钱 0不能1能
                int cash = (int) scoreandmax.get("cash");
                // 依据
                String yj = (String) scoreandmax.get("lname");
                yj = yj+","+(String) scoreandmax.get("remarks");
                // 字数
                int wordnumber = Integer.parseInt((String) userdata.get("xmwordnumber"));
                if (wordnumber<150){
                    // 低于150分 科技分为0
                    score = 0;
                }else {
                    score = (wordnumber/10) * jc;
                    if (score>max){
                        score = max;
                    }
                }
                if (cash == 1){
                    moneyscore = score;
                }else {
                    moneyscore = 0;
                }
                Map<String,Object> scoreandyj = new HashMap<>();
                scoreandyj.put("moneyscore",moneyscore);
                scoreandyj.put("score",score);
                scoreandyj.put("yj",yj);
                scoreandyj.put("id",id);
                return Result.success(scoreandyj);
            }else if (userdata.get("xmtype").equals("编著")==true||userdata.get("xmtype").equals("译著")==true){
                // 编著、译著 50分/万字 不超过500分
                scoreandmax = bookApplyInfoMapper.selectmaxscore("编著");
                // 多少分/万字
                int jc = (int) scoreandmax.get("score");
                // 最高多少分
                int max = (int) scoreandmax.get("max_score");
                // 该类别的id
                int id = (int) scoreandmax.get("leid");
                // 是否能换钱 0不能1能
                int cash = (int) scoreandmax.get("cash");
                // 依据
                String yj = (String) scoreandmax.get("lname");
                yj = yj+","+(String) scoreandmax.get("remarks");
                // 字数
                int wordnumber = Integer.parseInt((String) userdata.get("xmwordnumber"));
                // 计算
                if (wordnumber<150){
                    score = 0;
                }else {
                    score = (wordnumber/10) * jc;
                    if (score>max){
                        score = max;
                    }
                }
                if (cash == 1){
                    moneyscore = score;
                }else {
                    moneyscore = 0;
                }
                Map<String,Object> scoreandyj = new HashMap<>();
                scoreandyj.put("moneyscore",moneyscore);
                scoreandyj.put("score",score);
                scoreandyj.put("yj",yj);
                scoreandyj.put("id",id);
                return Result.success(scoreandyj);
            }else {
                // 其他类型
                score = 0;
                moneyscore = 0;
                String yj = "非计分类型";
                Map<String,Object> scoreandyj = new HashMap<>();
                scoreandyj.put("score",score);
                scoreandyj.put("moneyscore",moneyscore);
                scoreandyj.put("yj",yj);
                scoreandyj.put("id",-1);
                return Result.success(scoreandyj);
            }
        }else{
            // 作者排序非第一
            score = 0;
            moneyscore = 0;
            String yj = "作者排序非第一";
            Map<String,Object> scoreandyj = new HashMap<>();
            scoreandyj.put("score",score);
            scoreandyj.put("yj",yj);
            scoreandyj.put("moneyscore",moneyscore);
            scoreandyj.put("id",fzctype(userdata));
            return Result.success(scoreandyj);
        }

    }
}
