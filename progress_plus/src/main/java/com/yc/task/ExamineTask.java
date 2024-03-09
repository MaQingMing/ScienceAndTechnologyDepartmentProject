package com.yc.task;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.apply.entity.Examine;
import com.yc.apply.entity.ExamineSpecialUser;
import com.yc.apply.mapper.ExamineMapper;
import com.yc.apply.service.AccountScoreService;
import com.yc.apply.service.ExamineDetailService;
import com.yc.entity.Systemuser;
import com.yc.mapper.ExamineSpecialUserMapper;
import com.yc.mapper.SystemuserMapper;
import com.yc.vo.AccountScoreVo;
import com.yc.vo.other.ExamineDetailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 考核相关的定时任务
 */
@Slf4j
@Component
public class ExamineTask {

    @Autowired
    private ExamineMapper examineMapper;
    @Autowired
    private ExamineDetailService examineDetailService;
    @Autowired
    private SystemuserMapper systemuserMapper;
    @Autowired
    private AccountScoreService accountScoreService;
    @Autowired
    private ExamineSpecialUserMapper examineSpecialUserMapper;

    /**
     * 科技成果计分汇总 和 考核扣分
     */
    public void technologicalAchievementsSummary(){
        try{
            //1.查询待考核信息
            LambdaQueryWrapper wrapper = Wrappers.<Examine>lambdaQuery().eq(Examine::getStatus,1);
            List<Examine> list = examineMapper.selectList(wrapper);
            if(list !=null && list.size() ==1 ){
                //2.查询待考核的人员
                List<Map<String, Object>> treatSystemUser = examineMapper.findTreatSystemUser(list.get(0).getEid());

                //查询不参加考核人员
                LambdaQueryWrapper queryWrapper = Wrappers.<ExamineSpecialUser>lambdaQuery()
                        .eq(ExamineSpecialUser::getEid,list.get(0).getEid());
                List<ExamineSpecialUser> examineSpecialUsers = examineSpecialUserMapper.selectList(queryWrapper);
                Map<Integer, String> examineSpecialUsersMap = new HashMap<>();
                for (ExamineSpecialUser specialUser : examineSpecialUsers) {
                    examineSpecialUsersMap.put(specialUser.getSysid(),specialUser.getRemarks());
                }

                if (treatSystemUser!=null && !treatSystemUser.isEmpty()){
                    //3.根据考核 时间段 查询 多个用户 的每个项目 的总科技分
                    List<Map<String, Object>> techResultsType = examineMapper.findSystemUserTechResultsType(list.get(0).getStartDate(),
                            list.get(0).getEndDate());

                    if(null == techResultsType || techResultsType.isEmpty()){
                        throw new RuntimeException(" 暂无数据 ");
                    }

                    //记录当前用户 每个类别的 分数
                    Map<String,Double> doubleMap = new HashMap<>();
                    for (Map<String, Object> objectMap : techResultsType) {
                        doubleMap.put(objectMap.get("sid")+"::"+objectMap.get("trid"),Double.valueOf(String.valueOf(objectMap.get("score"))));
                    }

                    // 定义日期字符串和日期格式
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    // 将日期字符串解析为LocalDate对象
                    LocalDate startDate = LocalDate.parse(list.get(0).getStartDate(), formatter);
                    LocalDate endDate = LocalDate.parse(list.get(0).getEndDate(), formatter);
                    // 计算相隔的整年数  计算出来 加一年  2023-01-01 到 2023-12-31 算出来 0年
                    long year = ChronoUnit.YEARS.between(startDate, endDate) + 1;

                    //添加 ExamineDetail 表
                    List<ExamineDetailVo> details = new ArrayList<>();
                    //修改 Systemuser 表
                    List<Systemuser> updateList = new ArrayList<>();
                    //添加 账户扣分记录表
                    List<AccountScoreVo> accountScores = new ArrayList<>();
                    ExamineDetailVo examineDetail = null;

                    for (Map<String, Object> item : treatSystemUser) {
                        Integer sysId = Integer.valueOf(String.valueOf(item.get("id")));

                        if(examineSpecialUsersMap.get(sysId) !=null){
                            log.warn("{} 用户不参加考核,原因是 {}",sysId,examineSpecialUsersMap.get(sysId));
                            continue;
                        }
                        if(Objects.nonNull(item.get("examineCount")) && Integer.valueOf(String.valueOf(item.get("examineCount")))>0){
                            log.warn("{} 用户已经参加过考核 ",sysId);
                            continue;
                        }

                        /** 纵向 */
                        Double direction = handleScore(doubleMap.get(sysId+"::"+1));
                        /** 横向 */
                        Double transverse = handleScore(doubleMap.get(sysId+"::"+2));
                        /** 科技成果奖 */
                        Double achievement = handleScore(doubleMap.get(sysId+"::"+3));
                        /** 学术论文(自科/社科) */
                        Double paper = handleScore(doubleMap.get(sysId+"::"+4));
                        /** 学术专著(含著/编著/译著) */
                        Double book = handleScore(doubleMap.get(sysId+"::"+5));
                        /** 发明专利 */
                        Double invent = handleScore(doubleMap.get(sysId+"::"+6));
                        /** 科研基地/科学建设 */
                        Double scientific = handleScore(doubleMap.get(sysId+"::"+7));
                        /** 科技类荣誉(称号) */
                        Double honor = handleScore(doubleMap.get(sysId+"::"+8));

                        if( year > 0 ){
                            //计算总科技分
                            Double count = direction + transverse + achievement + paper + book + invent + scientific + honor;
                            //判断是否合格
                            Integer status = 0;
                            //获取 老师的底分
                            Double base_score = Double.valueOf(String.valueOf(item.get("base_score")));
                            if(count >= base_score){
                                status = 1;
                            }

                            Systemuser systemuser = new Systemuser();
                            //需要扣的科技分
                            double score = base_score * year;

                            if (score < 0) {
                                log.error("无效的扣分数操作 {}",score);
                                throw new RuntimeException("无效的扣分数操作");
                            }

                            //可换钱的分数
                            Double score_balance = 0.0;
                            if(Objects.nonNull(item.get("score_balance"))){
                                score_balance = Double.valueOf(String.valueOf(item.get("score_balance")));
                            }

                            //不可换钱的分数
                            Double non_score_balance = 0.0;
                            if(Objects.nonNull(item.get("non_score_balance"))){
                                non_score_balance = Double.valueOf(String.valueOf(item.get("non_score_balance")));
                            }

                            //还差的分数 (贷款分数)
                            Double loan_score = 0.0;
                            if(Objects.nonNull(item.get("loan_score"))){
                                loan_score = Double.valueOf(String.valueOf(item.get("loan_score")));
                            }

                            systemuser.setId(Integer.valueOf(String.valueOf(item.get("id"))));
                            systemuser.setScoreBalance(score_balance);
                            systemuser.setNonScoreBalance(non_score_balance);
                            systemuser.setLoanScore(loan_score);
                            //计算分数
                            Map<Integer, Object> objectMap = deductScores(systemuser, score, list.get(0).getEid());
                            updateList.add( (Systemuser) objectMap.get(1));
                            //添加 扣分记录
                            List<AccountScoreVo> voList = (List<AccountScoreVo>) objectMap.get(2);
                            for (AccountScoreVo accountScoreVo : voList) {
                                accountScores.add(accountScoreVo);
                            }
                            //添加考核记录
                            examineDetail = new ExamineDetailVo(sysId, Double.valueOf(String.valueOf(item.get("base_score"))) ,direction,
                                    transverse, achievement, paper, book, invent, scientific, honor, count, status, list.get(0).getEid());
                            details.add(examineDetail);
                        }
                    }

                    //4.记录添加考核结果
                    if(details!=null && !details.isEmpty()){
                        //批量添加 考核详情
                        addExamineDetailList(details);
                        //批量添加 账户扣分记录
                        addAccountScoreList(accountScores);
                        //批量更新科技分数
                        for (Systemuser systemuser : updateList) {
                            systemuserMapper.updateSystemUserScoreInfo(systemuser.getScoreBalance(),systemuser.getNonScoreBalance(),systemuser.getLoanScore(),systemuser.getId());
                        }
                    }
                }
            }else{
                log.warn("当年多条考核记录,暂不处理!");
            }
            log.warn("考核结束!");
        }catch (Exception e){
            log.error(" technologicalAchievementsSummary " ,e);
            throw new RuntimeException(" technologicalAchievementsSummary ",e);
        }
    }

    /**
     * 计算账户分数
     * @param systemuser 账户信息
     * @param deductAmount 需要抵扣的分数
     * @param eid
     * @return
     */
    public Map<Integer,Object> deductScores(Systemuser systemuser,double deductAmount,Integer eid){
        double nonScoreBalance = systemuser.getNonScoreBalance();
        double scoreBalance = systemuser.getScoreBalance();

        Map<Integer,Object> result = new HashMap<>();
        List<AccountScoreVo> accountScoreVos = new ArrayList<>();

        //检查是否还有贷款的分数 有的话 +到需要扣的底分里面
        double loanScore = systemuser.getLoanScore();
        if(systemuser.getLoanScore()>0){
            deductAmount+= systemuser.getLoanScore();
            loanScore = 0.0;
        }

        // 优先扣除不能换钱的分数
        if(systemuser.getNonScoreBalance()>0){
            double deductedNonScore = Math.min(nonScoreBalance, deductAmount);
            nonScoreBalance -= deductedNonScore;
            deductAmount -= deductedNonScore;
            //添加扣分记录
            accountScoreVos.add(addAccountScoreInfo(systemuser.getId(),eid,roundToTwoDecimals(deductedNonScore),2));
        }

        // 如果抵扣分数仍大于 0，再抵扣能还钱的分数
        if(deductAmount>0 && scoreBalance>0){
            double deductedScore = Math.min(scoreBalance, deductAmount);
            scoreBalance -= deductedScore;
            deductAmount -= deductedScore;
            //添加扣分记录
            accountScoreVos.add(addAccountScoreInfo(systemuser.getId(),eid,roundToTwoDecimals(deductedScore),1));
        }

        // 如果抵扣分数仍大于 0，并且需要贷款分数足够，则进行贷款
        if(deductAmount>0){
            loanScore += deductAmount;
            //添加扣分记录
            accountScoreVos.add(addAccountScoreInfo(systemuser.getId(),eid,roundToTwoDecimals(deductAmount),3));
        }
        systemuser.setNonScoreBalance(roundToTwoDecimals(nonScoreBalance));
        systemuser.setScoreBalance(roundToTwoDecimals(scoreBalance));
        systemuser.setLoanScore(roundToTwoDecimals(loanScore));

        result.put(1,systemuser);
        result.put(2,accountScoreVos);

        return result;
    }

    /**
     * 添加扣分记录
     * @param sId  用户Id
     * @param eId 考核Id
     * @param score 分数
     * @param status
     * @return
     */
    public AccountScoreVo addAccountScoreInfo(Object sId, Integer eId, Double score, Integer status){
        AccountScoreVo accountScore = new AccountScoreVo();
        accountScore.setSysid(Integer.valueOf(String.valueOf(sId)));
        accountScore.setEid(eId);
        accountScore.setType(2);
        accountScore.setStatus(status);
        accountScore.setScore(score);
        return accountScore;
    }

    /**
     * 将数字精度化到两位小数
     * @param value
     * @return
     */
    private double roundToTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    /**
     * 批量添加 考核详情
     * @param details
     */
    public void addExamineDetailList(List<ExamineDetailVo> details){
        try{
            int listSize = details.size();
            int toIndex = 400;
            for (int i = 0,len = details.size(); i < len; i += 400) {
                //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                if (i + 400 > listSize) {
                    toIndex = listSize - i;
                }
                // 对需要添加的进行批量操作   截取 1000 条记录一次性处理
                List<ExamineDetailVo> newDetails = details.subList(i, i + toIndex);
                examineDetailService.addExamineDetailList(newDetails);
            }
        }catch (Exception e){
            log.error(" addExamineDetailList " ,e);
            throw new RuntimeException(" addExamineDetailList ",e);
        }
    }

    /**
     * 批量添加 账户扣分记录
     * @param accountScores
     */
    public void addAccountScoreList(List<AccountScoreVo> accountScores){
        try{
            int listSizes = accountScores.size();
            int toIndexs = 400;
            for (int i = 0,len = accountScores.size(); i < len; i += 400) {
                //作用为toIndex最后没有100条数据则剩余几条newList中就装几条
                if (i + 400 > listSizes) {
                    toIndexs = listSizes - i;
                }
                // 对需要添加的进行批量操作   截取 1000 条记录一次性处理
                List<AccountScoreVo> newAccountScores = accountScores.subList(i, i + toIndexs);
                accountScoreService.addAccountScoreList(newAccountScores);
            }
        }catch (Exception e){
            log.error(" addAccountScoreList " ,e);
            throw new RuntimeException(" addAccountScoreList ",e);
        }
    }


    /**
     * 处理分数防止 nuLL
     * @param score
     * @return
     */
    private Double handleScore(Double score){
        if(Objects.isNull(score) ||  null == score){
            return 0.0;
        }else{
            return score;
        }
    }
}
