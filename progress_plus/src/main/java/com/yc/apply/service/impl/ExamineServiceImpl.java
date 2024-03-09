package com.yc.apply.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.apply.entity.Examine;
import com.yc.apply.entity.ExamineSpecialUser;
import com.yc.apply.mapper.ExamineMapper;
import com.yc.apply.service.ExamineService;
import com.yc.common.handler.EasyExcelCustomCellWriteHandler;
import com.yc.common.utils.DateUtil;
import com.yc.entity.Notice;
import com.yc.exception.CustomException;
import com.yc.mapper.ExamineSpecialUserMapper;
import com.yc.queue.DelayQueueService;
import com.yc.service.NoticeService;
import com.yc.task.ExamineTask;
import com.yc.vo.other.AssExcel;
import com.yc.vo.other.AssInfoExcel;
import com.yc.vo.other.DelayMessageVo;
import com.yc.vo.ExamineVo;
import com.yc.vo.ToAssInfo;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 考核表;(examine)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
@Service
public class ExamineServiceImpl implements ExamineService {

    @Autowired
    private ExamineMapper examineMapper;
    @Autowired
    private ExamineSpecialUserMapper examineSpecialUserMapper;
    @Autowired
    private DelayQueueService delayQueueService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private NoticeService noticeService;
    @Resource
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;
    @Resource
    private ExamineTask examineTask;

    /**
     * 添加考核
     * @param examineVo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertExamine(ExamineVo examineVo) {
        String title = examineVo.getTitle();
        //是否提前抵扣
        String advance = examineVo.getAdvance();
        // 开始时间
        String startDate = examineVo.getStartDate();
        // 结束时间
        String endDate = examineVo.getEndDate();
        // 考勤期间能否申请
        String confirm = examineVo.getConfirm();
        //备注
        String remarks = examineVo.getRemarks();
        //总人数
        Integer total = examineVo.getTotal();
        // 不参与审核原因
        String nonRemarks = examineVo.getNonRemarks();
        // 不参与审核人员 (23,13,9)
        String noNtotal = examineVo.getNoNtotal();
        //任务开始时间
        String beginDate = examineVo.getBeginDate();
        //任务结束时间
        String finishDate = examineVo.getFinishDate();
        //结束公告时间
        String publicityDate = examineVo.getPublicityDate();
        int queryTimeBetween = examineMapper.queryTimeBetween(beginDate);        //判断申请时间的
        if (queryTimeBetween > 0){
            return false;
        }
        Examine examine = new Examine();
        if (advance.equals("true")){
            examine.setAdvance(1);
        }else{
            examine.setAdvance(0);
        }
        if (confirm.equals("true")){
            examine.setConfirm(1);
        }else{
            examine.setConfirm(0);
        }
        examine.setTitle(title);
        examine.setStartDate(startDate);
        examine.setBeginDate(beginDate);
        examine.setFinishDate(finishDate);
        examine.setPublicityDate(publicityDate);
        examine.setRemarks(remarks);
        examine.setEndDate(endDate);
        examine.setSpecial(noNtotal);
        examine.setTotal(total);
        examine.setStatus(0);
        examine.setQualified(0);
        examine.setUnqualified(0);
        examine.setPassrate("0");
        examineMapper.insert(examine);
        //添加公告
        Notice notice = new Notice();
        notice.setTime(DateUtil.strToDate(new Date()));
        notice.setTitle("关于"+title+"的通知");
        notice.setContent("为了全面了解学校各教学院、科研机构等各部门在科技工作方面的成果，" +
                "提高科技创新能力，特制定年度科技成果考核计划，考核年度 "+DateUtil.parseDate(startDate,"yyyy年MM月dd日")+" 到 "+DateUtil.parseDate(endDate,"yyyy年MM月dd日")+" 。具体通告如下：\n" +
                "一、考核时间安排：\n" +
                "开始时间："+DateUtil.parseDate(beginDate,"yyyy年MM月dd日 HH:mm")+"\n" +
                "结束时间："+DateUtil.parseDate(finishDate,"yyyy年MM月dd日 HH:mm")+"\n" +
                "二、公示时间：\n" +
                "开始公示时间："+DateUtil.parseDate(finishDate,"yyyy年MM月dd日 HH:mm")+"\n" +
                "结束公示时间："+DateUtil.parseDate(publicityDate,"yyyy年MM月dd日 HH:mm")+"\n" +
                "三、公示方式：\n" +
                "将在科技与校企合作处科技成果自主申报服务系统中,进行公示,供全体老师查看。\n" +
                "如有异议，请在公示期内以书面材料的形式向科技与校企合作处反映。\n" +
                "四、其他事项：\n" +
                "注意在考核期间，由于系统有大量的数据需要统计和处理，大家将不能在系统里面进行相关的申报,请提前完成科技成果的申报，以免影响考核进程。\n" +
                "\t\t\t\t\t\t\t\t\t\t\t科技与校企合作处\n" +
                "\t\t\t\t\t\t\t\t\t\t\t"+DateUtil.parseDate(DateUtil.strToDate(new Date()),"yyyy年MM月dd日"));
        notice.setEid(examine.getEid());
        noticeService.save(notice);
        Integer eid = examine.getEid();
        DelayMessageVo messageVo = new DelayMessageVo();
        if (!beginDate.equals("")){
            Long beginDateTimes = DateFormat(beginDate);
            messageVo.setId(eid);
            messageVo.setExpireTime(beginDateTimes);
            messageVo.setContent("TASKSTART");
            delayQueueService.put(messageVo);
        }
        if (!finishDate.equals("")){
            //在结束前 30分钟执行 考核
            Long finishDateTimes = DateFormat(finishDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(finishDateTimes);

            // 将时间减去30分钟
            calendar.add(Calendar.MINUTE, -30);
            DelayMessageVo messageVo1 = new DelayMessageVo();
            messageVo1.setId(eid);
            messageVo1.setExpireTime(calendar.getTimeInMillis());
            messageVo1.setContent("EXAMINESTART");
            delayQueueService.put(messageVo1);


            messageVo.setId(eid);
            messageVo.setExpireTime(finishDateTimes);
            messageVo.setContent("MISSIONEND");
            delayQueueService.put(messageVo);
        }
        if (!publicityDate.equals("")){
            Long publicityDateTimes = DateFormat(publicityDate);
            messageVo.setId(eid);
            messageVo.setExpireTime(publicityDateTimes);
            messageVo.setContent("PUBLICITYEND");
            delayQueueService.put(messageVo);
        }
        if (!noNtotal.equals("")){
            String[] split = noNtotal.split(",");
            for (String s : split) {
                ExamineSpecialUser specialUser = new ExamineSpecialUser();
                specialUser.setEid(eid);
                specialUser.setSysid(Integer.parseInt(s));
                specialUser.setRemarks(nonRemarks);
                examineSpecialUserMapper.insert(specialUser);
            }
        }
        return true;
    }

    /**
     * 查询考核
     * @param currentPage
     * @param currentSize
     * @return
     */
    @Override
    public List<Examine> queryExamine(int currentPage,int currentSize) {
        int current=(currentPage-1) * currentSize;
        List<Examine> examines = examineMapper.queryExamine(current, currentSize);
        return examines;
    }

    /**
     * 年度项目统计
     * @param eid
     * @return
     */
    @Override
    public Map<String, Object> queryLnameTotal(Integer eid) {
        Map<String, Object> map = examineMapper.queryLnameTotal(eid);
        return map;
    }

    /**
     * 科技计分统计
     * @param eid
     * @param currentSize
     * @param currentPage
     * @return
     */
    @Override
    public Map<Integer, List<ToAssInfo>>  findToAssInfo(Integer eid, Integer currentSize, Integer currentPage, String queryName) {
        List<ToAssInfo> toAssInfo = examineMapper.findToAssInfo(eid, (currentPage-1)*currentSize, currentSize,queryName);
        // 使用 Map 将数据按 tid 分类
        Map<Integer, List<ToAssInfo>> dataMap = new HashMap<>();
        for (ToAssInfo assInfo : toAssInfo) {
            Integer tid = assInfo.getTid();
            // 如果 Map 中已存在相应 tid 的数据列表，则添加数据到现有列表中，否则创建新列表并添加数据
            if (dataMap.containsKey(tid)) {
                dataMap.get(tid).add(assInfo);
            } else {
                List<ToAssInfo> dataList = new ArrayList<>();
                dataList.add(assInfo);
                dataMap.put(tid, dataList);
            }
        }
        return dataMap;
    }

    /**
     * 统计
     * @param eid
     * @return
     */
    @Override
    public Map<String, Object> queryLnameScore(Integer eid) {
        Map<String, Object> map = examineMapper.queryLnameScore(eid);
        return map;
    }

    /**
     * 查询不参加的人
     * @param ids
     * @param eid
     * @return
     */
    @Override
    public List<Map<String, Object>> queryNoPeople(String ids,Integer eid) {
        String[] split = ids.split(",");
        List<Integer> list =new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            list.add(Integer.parseInt(split[i]));
        }
        List<Map<String, Object>> maps = examineMapper.queryNoPeople(list,eid);
        return maps;
    }

    /**
     * 修改状态
     * @param eid
     */
    @Override
    public void updateBeginExamineStatus(Integer eid,String beginDate) {
        examineMapper.updateBeginExamineStatus(eid);
        //然后再删除延迟队列的结束任务
        Long beginDateTimes = DateFormat(beginDate);
        DelayMessageVo delayMessageVo = new DelayMessageVo();
        delayMessageVo.setId(eid);
        delayMessageVo.setContent("TASKSTART");
        delayMessageVo.setExpireTime(beginDateTimes);
        delayQueueService.remove(delayMessageVo);
    }

    /**
     * 查询任务的时间，用于倒计时
     * @return
     */
    @Override
    public Map<String,Object> queryTimeByRedis() {
        String key = "delay_queue:" + DelayQueueService.getHostAddress();
        // 获取有序集合中的第一个值  就是要执行的任务  查出来的是时间戳
        Set<Object> firstValue = redisTemplate.opsForZSet().range(key, 0, 0);
        if (firstValue != null && !firstValue.isEmpty()) {
            Map<String, Object> map = new HashMap<>();
            //查出来的是时间戳
            DelayMessageVo firstElement = (DelayMessageVo) firstValue.iterator().next();
            long expireTime = firstElement.getExpireTime();
            String content = firstElement.getContent();
            map.put("time",expireTime);
            map.put("content",content);
            return map;
        } else {
            return null;
        }
    }

    /**
     * 科技成果计分统计
     * @param eid
     * @return
     */
    @Override
    public List<Map<String, Object>> queryExamineTypeTotal(Integer eid) {
        List<Map<String, Object>> maps = examineMapper.queryExamineTypeTotal(eid);
        return maps;
    }



    @Override
    public List<Examine> findIfExaminePeriod() {
        LambdaQueryWrapper wrapper = Wrappers.<Examine>lambdaQuery()
                .select(Examine::getEid,Examine::getTitle,Examine::getFinishDate)
                .in(Examine::getStatus,0,1);
        List<Examine> list = examineMapper.selectList(wrapper);
        if(null == list || list.isEmpty()){
            return Collections.emptyList();
        }
        return list;
    }

    /**
     * 手动结束考核
     * @param eid
     * @param finishDate
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finishExamine(Integer eid,String finishDate) {

        threadPoolTaskExecutor.execute(()->{
            //科技成果计分汇总 和 考核扣分
            examineTask.technologicalAchievementsSummary();
        });

        //查询已考核人数，合格人数，不合格人数，合格率
        Map<String, Object> maps = examineMapper.queryExamineDetailTotal(eid);
        Integer totalByZero =  Integer.valueOf(String.valueOf(maps.get("totalByZero")));
        Integer totalByOne = Integer.valueOf(String.valueOf(maps.get("totalByOne")));
        String passrate = String.valueOf(maps.get("passrate"));
        Examine examine = new Examine();
        examine.setQualified(totalByOne);
        examine.setUnqualified(totalByZero);
        examine.setPassrate(passrate);
        examine.setStatus(2);
        examine.setEid(eid);
        examineMapper.updateById(examine);

        //然后再删除延迟队列的结束任务
        Long finishDateTimes = DateFormat(finishDate);
        DelayMessageVo delayMessageVo = new DelayMessageVo();
        delayMessageVo.setId(eid);
        delayMessageVo.setContent("MISSIONEND");
        delayMessageVo.setExpireTime(finishDateTimes);
        delayQueueService.remove(delayMessageVo);
    }

    /**
     * 手动结束公示
     * @param eid
     * @param publicityDate
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void finishPublicityDate(Integer eid, String publicityDate) {
        examineMapper.updatePublicityExamineStatus(eid);
        //然后再删除延迟队列的结束任务
        Long publicityDateTimes = DateFormat(publicityDate);
        DelayMessageVo delayMessageVo = new DelayMessageVo();
        delayMessageVo.setId(eid);
        delayMessageVo.setContent("PUBLICITYEND");
        delayMessageVo.setExpireTime(publicityDateTimes);
        delayQueueService.remove(delayMessageVo);
    }

    /**
     * 清除考核任务
     * @param eid
     * @param beginDate
     * @param finishDate
     * @param publicityDate
     */
    @Override
    public void deleteExamineByEid(Integer eid,String beginDate,String finishDate,String publicityDate) {
        //先删除数据库里面的数据，在清除任务
        examineMapper.deleteExamineByEid(eid);
        examineMapper.deleteNoticeByEid(eid);
        //清除任务
        //先构造redis里面存的对象
        Long beginDates = DateFormat(beginDate);
        Long finishDates = DateFormat(finishDate);
        Long publicityDates = DateFormat(publicityDate);
        // 将时间减去30分钟
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(finishDates);
        calendar.add(Calendar.MINUTE, -30);

        removeTask("TASKSTART", beginDates, eid);
        removeTask("MISSIONEND", finishDates, eid);
        removeTask("EXAMINESTART", calendar.getTimeInMillis(), eid);
        removeTask("PUBLICITYEND", publicityDates, eid);
    }

    /**
     * 清除任务方法
     * @param content
     * @param expireTime
     * @param eid
     */
    private void removeTask(String content, long expireTime, int eid) {
        DelayMessageVo delayMessageVo = new DelayMessageVo();
        delayMessageVo.setId(eid);
        delayMessageVo.setContent(content);
        delayMessageVo.setExpireTime(expireTime);
        delayQueueService.remove(delayMessageVo);
    }

    /**
     *  "yyyy-MM-dd HH:mm:ss"      转时间戳
     * @param date
     * @return
     */
    public static Long DateFormat(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 解析时间字符串为 LocalDateTime 对象
        LocalDateTime DateTime = LocalDateTime.parse(date, formatter);
        long DateTimes = DateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        return DateTimes;
    }

    /**
     * 查询所有年度考核信息
     * @return
     */
    @Override
    public List<Examine> selectExamine(){
        List<Examine> examines = examineMapper.selectExamine();
        if (examines.size() <= 0){
            throw new CustomException("0", "暂无信息");
        }
        return examines;
    }

    /**
     * 通过昵称或工号查询用户所在学院的考核信息
     *
     * @param eid 考核id
     * @param currentPage 当前页码 
     * @param currentSize 当前查询条数
     * @param queryName 查询条件 昵称或工号
     * @param ttid 被查询的部门id
     * @return
     */
    @Override
    public Map<Integer, List<ToAssInfo>> findToAssInfoByNicknameOrUsername(Integer eid, Integer currentPage,
                                                                           Integer currentSize, String queryName, Integer ttid) {
        List<ToAssInfo> toAssInfo = examineMapper.findToAssInfoByNicknameOrUsername(eid, (currentPage - 1) * currentSize, currentSize, queryName, ttid);
        // 使用 Map 将数据按 tid 分类
        Map<Integer, List<ToAssInfo>> dataMap = new HashMap<>();
        for (ToAssInfo assInfo : toAssInfo) {
            Integer tid = assInfo.getTid();
            // 如果 Map 中已存在相应 tid 的数据列表，则添加数据到现有列表中，否则创建新列表并添加数据
            if (dataMap.containsKey(tid)) {
                dataMap.get(tid).add(assInfo);
            } else {
                List<ToAssInfo> dataList = new ArrayList<>();
                dataList.add(assInfo);
                dataMap.put(tid, dataList);
            }
        }
        return dataMap;
    }

    /**
     * 根据学院ID科技分总数
     *
     * @param eid 考核id
     * @param tid 部门id
     * @param queryName 查询条件 昵称或工号
     * @return
     */
    @Override
    public Integer findToAssInfoTotalByTid(Integer eid, Integer tid, String queryName) {
        Integer rows = examineMapper.findToAssInfoTotalByTid(eid, tid, queryName);
        return rows;
    }

    @Override
    public void exportTechAchievementScoringExcel(HttpServletResponse response, HttpSession session, Integer eid) throws IOException {
        Map<Integer, List<ToAssInfo>> toAssInfo = new HashMap<>();
        System.out.println(session.getAttribute("USERTID"));
        if(Objects.nonNull(session.getAttribute("USERTID"))){
            Integer tid = (Integer) session.getAttribute("USERTID");
            //科技处 或 学科中心
            if(tid == 1 || tid == 2){
                toAssInfo = findToAssInfo(eid, 9999, 1, "");
            }else{
                toAssInfo = findToAssInfoByNicknameOrUsername(eid, 1, 9999, "",tid);
            }
        }

        if(Objects.nonNull(toAssInfo) && !toAssInfo.isEmpty()){
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet; application/octet-stream");
            response.setCharacterEncoding("utf-8");

            LambdaQueryWrapper wrapper = Wrappers.<Examine>lambdaQuery()
                    .select(Examine::getStartDate,Examine::getEndDate).eq(Examine::getEid,eid);
            Examine examine = examineMapper.selectOne(wrapper);

            String startDate = DateUtil.parseDate(examine.getStartDate(),"yyyy");
            String endDate= DateUtil.parseDate(examine.getEndDate(),"yyyy");
            String fileName = "";
            if(startDate.equals(endDate)){
                fileName = startDate+ "年科技成果计分汇总-"+DateUtil.parseDate(examine.getEndDate(),"yyyyMMdd")+".xlsx";
            }else{
                fileName = startDate+"年-"+endDate+"年科技成果计分汇总"+DateUtil.parseDate(examine.getStartDate(),"yyyyMMdd")+
                        "-"+DateUtil.parseDate(examine.getEndDate(),"yyyyMMdd")+".xlsx";
            }

            // 人员科技成果计分 详细数据
            List<AssInfoExcel> excels = new ArrayList<>();
            // 各学院合计数据
            List<AssExcel> assExcels = new ArrayList<>();
            AtomicReference<Integer> indexs = new AtomicReference<>(1);
            AtomicReference<Integer> index = new AtomicReference<>(1);
            toAssInfo.forEach((key,val)->{
                //计算各学院 各个项目总分
                double direction = val.stream().mapToDouble(ToAssInfo::getDirection).sum();
                double transverse = val.stream().mapToDouble(ToAssInfo::getTransverse).sum();
                double achievement = val.stream().mapToDouble(ToAssInfo::getAchievement).sum();
                double paper = val.stream().mapToDouble(ToAssInfo::getPaper).sum();

                double book = val.stream().mapToDouble(ToAssInfo::getBook).sum();
                double invent = val.stream().mapToDouble(ToAssInfo::getInvent).sum();
                double scientific = val.stream().mapToDouble(ToAssInfo::getScientific).sum();
                double honor = val.stream().mapToDouble(ToAssInfo::getHonor).sum();

                assExcels.add(new AssExcel(indexs.get(),val.get(0).getTname(),direction,transverse,achievement,paper,book,invent,scientific,honor));
                indexs.getAndSet(indexs.get() + 1);
                //处参加考核人员的各项目信息
                for (ToAssInfo assInfo : val) {
                    excels.add(new AssInfoExcel(index.get(),assInfo.getNickname(),assInfo.getTname(),assInfo.getEdu(),"",
                            assInfo.getAcademy(),assInfo.getAcademyCare(),assInfo.getStandard(),
                            assInfo.getDirection(),assInfo.getTransverse(),assInfo.getAchievement(),
                            assInfo.getPaper(),assInfo.getBook(),assInfo.getInvent(),assInfo.getScientific(),assInfo.getHonor(),
                            assInfo.getCount(),""));
                    index.getAndSet(index.get() + 1);
                }
            });

            //统计各学院的项目合计
            double direction = assExcels.stream().mapToDouble(AssExcel::getDirection).sum();
            double transverse = assExcels.stream().mapToDouble(AssExcel::getTransverse).sum();
            double achievement = assExcels.stream().mapToDouble(AssExcel::getAchievement).sum();
            double paper = assExcels.stream().mapToDouble(AssExcel::getPaper).sum();

            double book = assExcels.stream().mapToDouble(AssExcel::getBook).sum();
            double invent = assExcels.stream().mapToDouble(AssExcel::getInvent).sum();
            double scientific = assExcels.stream().mapToDouble(AssExcel::getScientific).sum();
            double honor = assExcels.stream().mapToDouble(AssExcel::getHonor).sum();

            assExcels.add(new AssExcel(indexs.get()," 合 计 ",direction,transverse,achievement,paper,book,invent,scientific,honor));

            //内容样式策略
            WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
            //垂直居中,水平居中
            contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
            contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
            contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
            contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
            contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
            //设置 自动换行
            contentWriteCellStyle.setWrapped(true);
            // 字体策略
            WriteFont contentWriteFont = new WriteFont();
            // 字体大小
            contentWriteFont.setFontHeightInPoints((short) 12);
            contentWriteCellStyle.setWriteFont(contentWriteFont);

            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream())
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerWriteHandler(new HorizontalCellStyleStrategy(null, contentWriteCellStyle))
                    .registerWriteHandler(new EasyExcelCustomCellWriteHandler()).build();


            WriteSheet writeSheet = EasyExcel.writerSheet(0,"各二级学院成果计分汇总").head(AssExcel.class).build();
            WriteSheet writeSheet1 = EasyExcel.writerSheet(1,"成果计分汇总明细").head(AssInfoExcel.class).build();

            excelWriter.write(assExcels,writeSheet);
            excelWriter.write(excels,writeSheet1);

            // 下载文件的默认名称
            response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

            excelWriter.finish();
            excelWriter.close();
        }
    }

}
