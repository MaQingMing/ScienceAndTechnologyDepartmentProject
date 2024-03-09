package com.yc.apply.scoring.techresults;

import com.yc.apply.mapper.PaperApplyInfoMapper;
import com.yc.apply.scoring.TechResultsInterface;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 学术论文 计分处理器
 *
 * @author zxy
 */
@Component
public class PaperScoringHandler implements TechResultsInterface {

    @Autowired
    private PaperApplyInfoMapper paperApplyInfoMapper;

    /**
     * 计分信息处理方法
     *
     * @param applyVo 申请信息
     * @return 分数
     */
    @Override
    public Result scoringScore(ApplyVo applyVo) {
        // 最终分数
        Double score = 0.0;
        Map<String, Object> datalist = new HashMap<>();
        datalist = applyVo.getUserdata();
        String lb = (String) datalist.get("xmlb");
        // 附则
        String fz = (String) datalist.get("showxmother");
        // 作者排序
        String xmpeopleorder = (String) datalist.get("xmpeopleorder");
        // 学校排序
        String xmschoolorder = (String) datalist.get("xmschoolorder");
        // 最终分数和cid
        Map<String,Object> ScoreAndCid = new HashMap<>();
        Result result = new Result();
        /*   以下是附则不为空的情况   */
        if (!"".equals(fz)) {
            ScoreAndCid = Score(fz,lb);
            result.setMsg(according(lb)+","+fz);
        } else {
            /*   以下是附则为空的情况   */
            // 根据计分方法第七条 我校教师作为第一作者且湖南工学院为第一署名单位才可计分
            if ("1".equals(xmpeopleorder)&&"1".equals(xmschoolorder)){
                ScoreAndCid = BaseScore(lb);
//                score = Double.valueOf((Integer) ScoreAndCid.get("score"));
                result.setMsg(according(lb));
            }else {
                /* 学校非第一署名且作者非第一 */
                ScoreAndCid = BaseScore(lb);
                ScoreAndCid.put("score",score);
                ScoreAndCid.put("nocashscore",score);
                result.setMsg("学校署名非第一或作者署名非第一," + according(lb));
            }
        }
        result.setData(ScoreAndCid);
        return result;
    }

    /**
     * 计算依据
     * @param lb
     * @return
     */
    private String according (String lb){
        String[] leidlist = lb.split(",");
        String according = "";
        for (String leid : leidlist) {
            leid = leid.replace("\"", "");
            if (!leid.equals("")){
                Map<String,Object> lmap = paperApplyInfoMapper.selectfs(leid);
                // 类别全称
                String lname = (String) lmap.get("lname");
                if (according.equals("")){
                    according = lname;
                }else {
                    according = according + "," + lname;
                }
            }
        }
        return according;
    }

    /**
     * 附则计算分数方法
     *
     * @return 分数
     */
    private Map<String,Object> Score(String fz,String lb) {
        // 替换匹配到的百分比数字为#2
        fz = Pattern.compile("\\d+%").matcher(fz).replaceAll("#2");
        // 附则的 id 和 计算分数的百分比
        Map<String, Object> fzIdAndRatio = paperApplyInfoMapper.selectfzid(fz);
        // 百分比
        String ratio = (String) fzIdAndRatio.get("ratio");
        // 将去除百分比符号后的字符串转换为 double 类型
        double p = Double.parseDouble(ratio.replaceAll("%", "")) / 100;
        // 最终计算分数
        Map<String,Object> ScoreAndCid = BaseScore(lb);
        Double score = Double.valueOf((Integer) ScoreAndCid.get("score")) * p;
        Double nocashscore = Double.valueOf((Integer) ScoreAndCid.get("nocashscore")) * p;
        ScoreAndCid.put("score",score);
        ScoreAndCid.put("nocashscore",nocashscore);
        return ScoreAndCid;
    }

    /**
     * 类别判断方法 并得出初步分数
     *
     * @return
     */
    private Map<String,Object> BaseScore(String lb) {
        Map<String,Object> ScoreAndCid = new HashMap<>();
        String[] leidlist = lb.split(",");
        // 可换钱最高类别的分数
        Integer maxlbscore = 0;
        // 不可换钱分数
        Integer nocashscore = 0;
        // 存全部类别的分数
        Map<String,Object> allscore = new HashMap<>();
        // 存全部的lname
        String lnameall = "";
        // 最高级别的 childid
        String childid = "";
        // 查询lname和分数 并储存
        for (String leid : leidlist) {
            leid = leid.replace("\"", "");
            if (!leid.equals("")){
                Map<String,Object> lmap = paperApplyInfoMapper.selectfs(leid);
                // 类别全称
                String lname = (String) lmap.get("lname");
                // childid
                Integer cid = (Integer) lmap.get("psid");
                Integer lscore = (Integer) lmap.get("score");
                lnameall  = lnameall + lname;
                Integer cash = (Integer) lmap.get("cash");
                // 判断该分数是否可换钱
                if (cash==1){
                    allscore.put(lname+"m","1");
                }else{
                    allscore.put(lname+"m","0");
                }
                System.out.println(lmap.get("cash"));
                allscore.put(lname,lscore);
                allscore.put(lname+"id",cid);
            }
        }
        // 判断出最高类别
        if (lnameall.contains("国际顶级刊物（Nature、science、cell）")){
            if (allscore.get("国际顶级刊物（Nature、science、cell）m").equals("1")){
                maxlbscore = (Integer) allscore.get("国际顶级刊物（Nature、science、cell）");
            }else {
                nocashscore = (Integer) allscore.get("国际顶级刊物（Nature、science、cell）");
            }
            childid = String.valueOf(allscore.get("国际顶级刊物（Nature、science、cell）id"));
        } else if (lnameall.contains("A+")){
            if (allscore.get("A+m").equals("1")){
                maxlbscore = (Integer) allscore.get("A+");
            }else {
                nocashscore = (Integer) allscore.get("A+");
            }
            childid = String.valueOf(allscore.get("A+id"));
        } else if (lnameall.contains("A")){
            if (allscore.get("Am").equals("1")){
                maxlbscore = (Integer) allscore.get("A");
            }else {
                nocashscore = (Integer) allscore.get("A");
            }
            childid = String.valueOf(allscore.get("Aid"));
        } else if (lnameall.contains("B1")){
            if (allscore.get("B1m").equals("1")){
                maxlbscore = (Integer) allscore.get("B1");
            }else {
                nocashscore = (Integer) allscore.get("B1");
            }
            childid = String.valueOf(allscore.get("B1id"));
        } else if (lnameall.contains("B2")){
            if (allscore.get("B2m").equals("1")){
                maxlbscore = (Integer) allscore.get("B2");
            }else {
                nocashscore = (Integer) allscore.get("B2");
            }
            childid = String.valueOf(allscore.get("B2id"));
        } else if (lnameall.contains("C")){
            if (allscore.get("Cm").equals("1")){
                maxlbscore = (Integer) allscore.get("C");
            }else {
                nocashscore = (Integer) allscore.get("C");
            }
            childid = String.valueOf(allscore.get("Cid"));
        } else if (lnameall.contains("D1")){
            if (allscore.get("D1m").equals("1")){
                maxlbscore = (Integer) allscore.get("D1");
            }else {
                nocashscore = (Integer) allscore.get("D1");
            }
            childid = String.valueOf(allscore.get("D1id"));
        } else if (lnameall.contains("D2")){
            if (allscore.get("D2m").equals("1")){
                maxlbscore = (Integer) allscore.get("D2");
            }else {
                nocashscore = (Integer) allscore.get("D2");
            }
            childid = String.valueOf(allscore.get("D2id"));
        } else if (lnameall.contains("D3")){
            if (allscore.get("D3m").equals("1")){
                maxlbscore = (Integer) allscore.get("D3");
            }else {
                nocashscore = (Integer) allscore.get("D3");
            }
            childid = String.valueOf(allscore.get("D3id"));
        }else if (lnameall.contains("ESI")){

        }else {
            if (allscore.get("Em").equals("1")){
                maxlbscore = (Integer) allscore.get("E");
            }else {
                nocashscore = (Integer) allscore.get("E");
            }
            childid = String.valueOf(allscore.get("Eid"));
        }
        // ESI热点论文 和 ESI高被引论文 单独计算分数
        if (lnameall.contains("ESI热点论文（全球0.1%范围内）")){
            if (allscore.get("ESI热点论文（全球0.1%范围内）m").equals("1")){
                Integer s = (Integer) allscore.get("ESI热点论文（全球0.1%范围内）");
                maxlbscore = maxlbscore + s;
            }else {
                Integer s = (Integer) allscore.get("ESI热点论文（全球0.1%范围内）");
                nocashscore = nocashscore + s;
            }
            if (childid.equals("")){
                childid = String.valueOf(allscore.get("ESI热点论文（全球0.1%范围内）id"));
            }else {
                childid = childid+","+String.valueOf(allscore.get("ESI热点论文（全球0.1%范围内）id"));
            }
        }
        if (lnameall.contains("ESI高被引论文（全球1%范围内）")){
            if (allscore.get("ESI高被引论文（全球1%范围内）m").equals("1")){
                Integer s = (Integer) allscore.get("ESI高被引论文（全球1%范围内）");
                maxlbscore = maxlbscore + s;
            }else {
                Integer s = (Integer) allscore.get("ESI高被引论文（全球1%范围内）");
                nocashscore = nocashscore + s;
            }
            if (childid.equals("")){
                childid = String.valueOf(allscore.get("ESI高被引论文（全球1%范围内）id"));
            }else {
                childid = childid+","+String.valueOf(allscore.get("ESI高被引论文（全球1%范围内）id"));
            }
        }
        ScoreAndCid.put("nocashscore",nocashscore);
        ScoreAndCid.put("score",maxlbscore);
        ScoreAndCid.put("cid",childid);
        return ScoreAndCid;
    }


}
