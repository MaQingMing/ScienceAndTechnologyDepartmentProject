package com.yc.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.yc.apply.entity.*;
import com.yc.apply.mapper.*;
import com.yc.apply.service.ApplylogService;
import com.yc.common.handler.EasyExcelCustomCellWriteHandler;
import com.yc.common.utils.AlyExcelUtil;
import com.yc.common.utils.CustomCellWriteHeightConfig;
import com.yc.common.utils.CustomCellWriteWeightConfig;
import com.yc.entity.Systemuser;
import com.yc.exception.CustomException;
import com.yc.mapper.SystemuserMapper;
import com.yc.standard.mapper.TechResultsLevelMapper;
import com.yc.vo.*;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.standard.HonorHisPage;
import com.yc.vo.standard.InventHisPage;
import com.yc.vo.standard.PaperHisPage;
import com.yc.vo.other.*;
import com.yc.vo.standard.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.yc.apply.entity.GainApply;
import com.yc.apply.mapper.GainApplyMapper;
import com.yc.apply.service.GainApplyService;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * import org.springframework.beans.factory.annotation.Autowired;
 * import org.springframework.stereotype.Service;
 * /**
 * 科技成果申请;(gain_apply)表服务实现类
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class GainApplyServiceImpl implements GainApplyService {
    @Autowired
    private GainApplyMapper gainApplyMapper;
    @Resource
    private ApplylogService applylogService;
    @Resource
    private RedisTemplate redisTemplate;
    @Resource
    private TechResultsLevelMapper techResultsLevelMapper;
    @Resource
    private PaperApplyInfoMapper paperApplyInfoMapper;
    @Resource
    private BookApplyInfoMapper bookApplyInfoMapper;
    @Resource
    private DirectionApplyInfoMapper directionApplyInfoMapper;
    @Resource
    private TransverseApplyInfoMapper transverseApplyInfoMapper;
    @Resource
    private InventApplyInfoMapper inventApplyInfoMapper;
    @Resource
    private ScientificApplyInfoMapper scientificApplyInfoMapper;
    @Resource
    private AchievementApplyInfoMapper achievementApplyInfoMapper;
    @Resource
    private HonorApplyInfoMapper honorApplyInfoMapper;
    @Resource
    private SystemuserMapper systemuserMapper;
    @Resource
    private AccountScoreMapper accountScoreMapper;
    @Resource
    private ApplylogMapper applylogMapper;
    @Resource
    private ScoreApplyInfoMapper scoreApplyInfoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param gaid 主键
     * @return 实例对象
     */
    @Override
    public GainApply queryById(Integer gaid) {
        return gainApplyMapper.selectById(gaid);
    }

    /**
     * 查询驳回原因
     *
     * @param gaid
     * @return
     */
    public Map<String, Object> selectRejection(Integer gaid) {
        Map<String, Object> rjanddp = new HashMap<>();
        rjanddp.put("rj", gainApplyMapper.selectRejection(gaid));
        rjanddp.put("dp", applylogMapper.selectdept(gaid));
        return rjanddp;
    }

    /**
     * 新增数据
     *
     * @param gainApply 实例对象
     * @return 实例对象
     */
    @Override
    public GainApply insert(GainApply gainApply) {
        gainApplyMapper.insert(gainApply);
        return gainApply;
    }

    /**
     * 更新数据
     *
     * @param gainApply 实例对象
     * @return 实例对象
     */
    @Override
    public GainApply update(GainApply gainApply) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<GainApply> chainWrapper = new LambdaUpdateChainWrapper<GainApply>(gainApplyMapper);
        if (StrUtil.isNotBlank(gainApply.getSid())) {
            chainWrapper.eq(GainApply::getSid, gainApply.getSid());
        }
        if (StrUtil.isNotBlank(gainApply.getStatus())) {
            chainWrapper.eq(GainApply::getStatus, gainApply.getStatus());
        }
        if (StrUtil.isNotBlank(gainApply.getRejection())) {
            chainWrapper.eq(GainApply::getRejection, gainApply.getRejection());
        }
        if (StrUtil.isNotBlank(gainApply.getTrtid())) {
            chainWrapper.eq(GainApply::getTrtid, gainApply.getTrtid());
        }
        if (StrUtil.isNotBlank(gainApply.getChildid())) {
            chainWrapper.eq(GainApply::getChildid, gainApply.getChildid());
        }
        if (StrUtil.isNotBlank(gainApply.getTeam())) {
            chainWrapper.eq(GainApply::getTeam, gainApply.getTeam());
        }
        if (StrUtil.isNotBlank(gainApply.getDate())) {
            chainWrapper.eq(GainApply::getDate, gainApply.getDate());
        }
        if (StrUtil.isNotBlank(gainApply.getRemarks())) {
            chainWrapper.eq(GainApply::getRemarks, gainApply.getRemarks());
        }
        //2. 设置主键，并更新
        chainWrapper.set(GainApply::getGaid, gainApply.getGaid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(gainApply.getGaid());
        } else {
            return gainApply;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param gaid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer gaid) {
        int total = gainApplyMapper.deleteById(gaid);
        return total > 0;
    }

    /**
     * 添加科技成果申请记录
     *
     * @param applyVo 科技成果申请vo
     * @return 1 成功 0 失败
     */
    @Override
    public int addGainApply(ApplyVo applyVo) {
        applyVo.setTeam((applyVo.getSid().contains(",") ? 1 : 0) + "");
        int row = gainApplyMapper.addGainApply(applyVo);
        if (row <= 0) {
            throw new CustomException("0", "添加失败");
        }
        Applylog applylog = new Applylog();
        applylog.setApplyid(applyVo.getGaid());
        applylog.setStatus(0);
        // 添加申请记录
        applylogService.insert(applylog);
        return row;
    }

    /**
     * @Description id包括申请人的id，申请主表id，通过的分数，是否可换钱
     * isDept表示执行通过操作的领导身份
     * uid是领导id
     * @Return
     * @Author dm
     * @Date Created in 2023/11/23
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int pass(String passes, String isDept, String uid) {
        List<AccountScore> accountScores = new ArrayList<>();
        List<Applylog> applylogs = new ArrayList<>();
        String gaids = "";
        for (String pass : passes.split(";")) {
            String[] gaid_sid = pass.split("::");
            gaids += (gaid_sid[0] + ",");
            Applylog applylog = new Applylog();
            //是部门认可还是学院认可
            applylog.setStatus("1".equals(isDept) ? 2 : 1);
            applylog.setSysid(Integer.valueOf(uid));
            applylog.setMark("1".equals(isDept) ? 2 : 1);
            applylog.setApplyid(Integer.valueOf(gaid_sid[0]));
            applylogs.add(applylog);

            if (!"1".equals(isDept)) {
                continue;
            }

            //查询具体的分数
            //这里不能直接查询该用户的所有申请记录，而只能查询进行操作的那一条记录，如果查询该用户的所有记录就会导致分数被重复的计算
            //导致贷款分数被重复扣除
            List<ScoreApplyInfo> scoreApplyInfos = scoreApplyInfoMapper.queryDetails(gaid_sid[0]);
            //如果是团队申请
            for (ScoreApplyInfo scoreApplyInfo : scoreApplyInfos) {
                double can_scores = scoreApplyInfo.getCanScore();
                double cannot_scores = scoreApplyInfo.getCannotScore();
                //先更新一个项目所产生的分数计算之后的结果
                //首先查询出该用户的分数详情，这里设置为自动更新分数，所以对原先存在的分数不进行计算
                Systemuser systemuser = systemuserMapper.selectById(scoreApplyInfo.getSysid());

                if (can_scores > 0) {
                    AccountScore accountScore = new AccountScore();
                    accountScore.setScore(can_scores);
                    accountScore.setStatus(1);
                    accountScore.setType(1);
                    accountScore.setApplyid(gaid_sid[0]);
                    accountScore.setSysid(gaid_sid[1]);
                    accountScore.setRemark("");
                    accountScore.setCreateBy(uid);
                    accountScores.add(accountScore);
                }
                if (cannot_scores > 0) {
                    AccountScore accountScore = new AccountScore();
                    accountScore.setScore(cannot_scores);
                    accountScore.setStatus(2);
                    accountScore.setType(1);
                    accountScore.setApplyid(gaid_sid[0]);
                    accountScore.setSysid(gaid_sid[1]);
                    accountScore.setRemark("");
                    accountScore.setCreateBy(uid);
                    accountScores.add(accountScore);
                }
                //不对贷款字段进行操作
                systemuser.setScoreBalance(systemuser.getScoreBalance() + can_scores);
                systemuser.setNonScoreBalance(systemuser.getNonScoreBalance() + cannot_scores);
                systemuserMapper.updateById(systemuser);
            }
        }
        //学院初审，只需要改变申请主表中的状态即可
        int pass = gainApplyMapper.pass(gaids.substring(0, gaids.length() - 1), isDept);
        //审核记录表批量添加
        int batch = applylogMapper.insertBatch(applylogs);

        if (batch > 0 && pass > 0) {
            if ("1".equals(isDept) && accountScores.size() > 0) {
                //这里思索片刻还是觉得先加入到审核记录表中比较稳妥，可以直接计算能换钱和不能换钱的总分
                accountScoreMapper.insertBatch(accountScores);
            }
            return 1;
        } else {
            return -1;
        }
    }

    /**
     * @Description ids包括驳回
     * @Return
     * @Author dm
     * @Date Created in 2023/11/23
     **/
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int reject(String passes, String rejectContent, String fuser, String isDept) {
        List<Applylog> applylogs = new ArrayList<>();
        String gaids = "";
        for (String pass : passes.split(";")) {
            String[] gaid_sid = pass.split("::");
            gaids += (gaid_sid[0] + ",");
            Applylog applylog = new Applylog();
            //是部门认可还是学院认可
            applylog.setStatus(-1);
            applylog.setSysid(Integer.valueOf(fuser));
            applylog.setMark("1".equals(isDept) ? 2 : 1);
            applylog.setApplyid(Integer.valueOf(gaid_sid[0]));
            applylogs.add(applylog);

            //查询具体的分数
            //这里不能直接查询该用户的所有申请记录，而只能查询进行操作的那一条记录，如果查询该用户的所有记录就会导致分数被重复的计算
            //导致贷款分数被重复扣除
            List<ScoreApplyInfo> scoreApplyInfos = scoreApplyInfoMapper.queryDetails(gaid_sid[0]);
            Applylog one = applylogMapper.queryOne(gaid_sid[0]);
            if (one == null) {
                //直接驳回就不需要进行用户分数的计算以及对账户明细表的操作
                continue;
            }
            for (ScoreApplyInfo scoreApplyInfo : scoreApplyInfos) {
                //先更新一个项目所产生的分数计算之后的结果
                //首先查询出该用户的分数详情，这里设置为自动更新分数，所以对原先存在的分数不进行计算
                Systemuser systemuser = systemuserMapper.selectById(scoreApplyInfo.getSysid());
                double can_scores = scoreApplyInfo.getCanScore();
                double cannot_scores = scoreApplyInfo.getCannotScore();

                //看驳回的状态，是认可之后驳回还是还没认可之前就驳回
                //先扣除不能换钱的分数
                double loanScore = systemuser.getLoanScore();
                //如果已有的分数够减
                if (systemuser.getNonScoreBalance() - cannot_scores >= 0) {
                    systemuser.setNonScoreBalance(systemuser.getNonScoreBalance() - cannot_scores);
                } else {
                    //如果已有的分数不够减
                    systemuser.setNonScoreBalance(0d);
                    systemuser.setLoanScore(systemuser.getLoanScore() + (can_scores - systemuser.getNonScoreBalance()));
                }
                if (systemuser.getScoreBalance() - can_scores >= 0) {
                    systemuser.setScoreBalance(systemuser.getScoreBalance() - can_scores);
                } else {
                    systemuser.setScoreBalance(0d);
                    systemuser.setLoanScore(systemuser.getLoanScore() + (can_scores - systemuser.getScoreBalance()));
                }
                systemuserMapper.updateById(systemuser);
            }
        }
        String ids = gaids.substring(0, gaids.length() - 1);
        //驳回，主表改变状态
        int reject = gainApplyMapper.reject(ids, rejectContent, fuser);
        //记录表中增加数据
        applylogMapper.insertBatch(applylogs);
        if (reject > 0) {
            accountScoreMapper.updateBatch(gaids);
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public void downloadError(HttpServletResponse response) throws UnsupportedEncodingException {
        // 设置响应头，影响浏览器下载行为,下面繁琐的代码的目的是为了适应网页那端能够展示出用户保存考勤表到指定位置的框所需要改变的
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //生成唯一标识符
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        //加入redis形成新的错误衍生文件
        redisTemplate.opsForValue().set(uuid, "1");
        // 使用URLEncoder对文件名进行编码，避免中文乱码
        response.setHeader("Content-disposition", "attachment;filename=" +
                URLEncoder.encode("错误信息表_" + date + "_" + uuid, String.valueOf(StandardCharsets.UTF_8)) + ".xls");
        ServletOutputStream out = null;
        try {
            //追加内容难以实现插入多个头部，后面的头部会覆盖前面的，所以采用多个sheet的方法实现多个考勤表的导出
            // 文件输出位置
            out = response.getOutputStream();
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
            // 指定文件，自定义列宽，注册样式
            ExcelWriter excelWriter = EasyExcel.write(out)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerWriteHandler(new HorizontalCellStyleStrategy(null, contentWriteCellStyle))
                    .registerWriteHandler(new EasyExcelCustomCellWriteHandler()).build();
            //sheet个数
            for (String s : fileClass.keySet()) {
                //获取sheet0对象
                WriteSheet mainSheet = EasyExcel.writerSheet(sheetNum++, s).head(fileClass.get(s)).build();
                excelWriter.write(errorContents.get(s), mainSheet);
            }
            //这一步很关键，不然文件会损坏
            excelWriter.finish();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("0", "系统异常");
        }
    }

    @Override
    public Long findGainApplyByStatus(Integer status) {
        LambdaQueryWrapper wrapper = Wrappers.<GainApply>lambdaQuery()
                .eq(GainApply::getStatus, status);

        return gainApplyMapper.selectCount(wrapper);
    }

    @Override
    public Result<?> downloadTemplate(HttpServletResponse response) throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        org.springframework.core.io.Resource[] resources = resourcePatternResolver.
                getResources("classpath*:" + File.separator + "static" +
                        File.separator + "file" + File.separator + "template.xls");
        org.springframework.core.io.Resource resource = resources[0];
        InputStream inputStream = null;
        OutputStream out = null;
        try {
            //根据文件在服务器的路径读取该文件转化为流
            inputStream = resource.getInputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            Date date = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String time = simpleDateFormat.format(date);
            //生成唯一标识符
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            redisTemplate.opsForValue().set(uuid, "1");
            String fileName = "批量添加科技成果申请模板表-" + time + "_" +
                    uuid + ".xls";
            //设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("multipart/form-data");
            //设置文件头：最后一个参数是设置下载文件名（设置编码格式防止下载的文件名乱码）
            response.setHeader("Content-Disposition",
                    "attachment;fileName=" + new String(fileName.getBytes("UTF-8"),
                            "ISO8859-1"));
            out = response.getOutputStream();
            int b = 0;
            while (b != -1) {
                b = inputStream.read(buffer);
                //写到输出流(out)中
                out.write(buffer, 0, b);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error(1, "下载失败");
        } finally {
            try {
                inputStream.close();
                out.close();
                out.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //查询所有用户姓名以及所处学院
    private List<SimpleGovernuser> users;
    //查询所有标准表中存在的等级
    private List<ChildLevel> levels;
    //展示给用户看的错误信息
    private String errorContent = "";
    private boolean begin = true;
    //动态错误信息表头
    private WriteSheet sheet1 = null;
    //动态错误信息表格
    private WriteTable table = null;
    //错误信息主体
    private Map<String, List<List<Object>>> errorContents;
    //头部信息
    public HashMap<String, Class> fileClass;
    //科技成果细致等级
    List<String> achievements = new ArrayList<>();
    //纵向细致等级
    List<String> directions = new ArrayList<>();
    //错误的sheet的个数
    private int sheetNum = 1;
    //初始化头部信息
    private List<List<String>> headTitle;
    private int year;

    /**
     * 上传数据
     *
     * @param inputStream 数据流
     * @return 是否成功
     */
    @Override
    public String upload(InputStream inputStream, String fileName, int user) {
        errorContents = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        // 获取当前年
        year = calendar.get(Calendar.YEAR);
        // 获取当前月
        int month = calendar.get(Calendar.MONTH) + 1;
        // 获取当前日
        int day = calendar.get(Calendar.DATE);
        //查询所有用户姓名以及所处学院
        users = systemuserMapper.queryUsers();
        //查询所有等级
        levels = techResultsLevelMapper.queryLevels();
        //查询是否上传过该表格，若没有就扫描全部的数据，若有错误信息就只扫描其中的错误信息
        Object history = redisTemplate.opsForValue().get(fileName);
        if (history == null) {
            //说明已经上传过这个文件并且生成了错误文件，这个上传的是原本的错误文件
            return "请上传此文件的衍生错误文件!";
        } else if ("0".equals(history)) {
            return "该文件已经上传并且数据全部添加成功!";
        }
        //科技成果申请模板表格所有的类别
        List<String> applyTypes = Arrays.asList("学术论文", "学术专著", "发明专利", "横向科研", "纵向科研", "科技成果", "科研平台", "科技荣誉");
        Workbook workbook = null;
        try {
            // 根据传递过来的文件输入流创建一个workbook对象（对应Excel中的工作簿）
            workbook = WorkbookFactory.create(inputStream);
            // 创建完成，关闭输入流
            inputStream.close();
            int numberOfSheets = workbook.getNumberOfSheets();
            int a;
            fileClass = new HashMap<>();
            for (int i = 0; i < numberOfSheets; i++) {
                //获取工作表对象，即第一个工作表 （工作簿里面有很多张工作表，这里取第一张工作表）
                Sheet sheet = workbook.getSheetAt(i);
                String sheetName = sheet.getSheetName();
                //判断sheet名是否被更改
                if (!applyTypes.contains(sheetName)) {
                    throw new CustomException("500", "工作表名sheet被修改，请根据模板的标准更改");
                }
                //获取工作表的总行数
                int rowLength = sheet.getLastRowNum() + 1;
                //获取工作表第一行数据
                Row row = sheet.getRow(0);
                //获取工作表总列数的长度
                int colLength = row.getLastCellNum();
                begin = true;
                //初始化头部信息
                headTitle = Lists.newArrayList();
                String[] rowData = getRowData(1, sheet, 0);
                String[] rowData1 = getRowData(colLength, sheet, 1);
                for (String s : rowData1) {
                    headTitle.add(Lists.newArrayList(rowData[0], s));
                }
                List<GainApply> Gdatas = new ArrayList<>();
                List<String> applylogs = new ArrayList<>();
                List<ScoreApplyInfo> scoreApplyInfos = new ArrayList<>();
                switch (sheetName) {
                    case "纵向科研":
                        List<DirectionApplyInfo> Ddatas = new ArrayList<>();
                        for (a = 2; a < rowLength; a++) {
                            // 双重循环 因为一个单元格由行和列的索引下表构成
                            DirectionApplyInfo directionApplyInfo = new DirectionApplyInfo();
                            GainApply gainApply = new GainApply();
                            row = sheet.getRow(a);
                            if (!AlyExcelUtil.isRowEmpty(row)) {
                                continue;
                            }
                            //判断是否有这个等级，有这个等级再将数据批量添加到数据库当中，如果没有这个等级直接提前掐死
                            String level = AlyExcelUtil.getString(row.getCell(9));
                            String username = AlyExcelUtil.getString(row.getCell(11));
                            String dept = AlyExcelUtil.getString(row.getCell(1));
                            String additionLevel = AlyExcelUtil.getString(row.getCell(10));
                            int upos = isUserExists(users, username, dept);
                            int lpos = isLevelExists(levels, level, "纵向科研", additionLevel);
                            if (lpos == -1 || upos == -1) {
                                begin = false;
                                //判断错误文件输出文件是否为空
                                if (!fileClass.containsKey("纵向科研")) {
                                    fileClass.put("纵向科研", DirectionHead.class);
                                }
                                if (!errorContents.containsKey("纵向科研")) {
                                    errorContents.put("纵向科研", new ArrayList<>());
                                }
                                errorContents.get("纵向科研").
                                        add(Lists.newArrayList(
                                                AlyExcelUtil.getString(row.getCell(0)), AlyExcelUtil.getString(row.getCell(1)),
                                                AlyExcelUtil.getString(row.getCell(2)), AlyExcelUtil.getString(row.getCell(3)),
                                                AlyExcelUtil.getString(row.getCell(4)), AlyExcelUtil.getString(row.getCell(5)),
                                                AlyExcelUtil.getString(row.getCell(6)), AlyExcelUtil.getString(row.getCell(7)),
                                                AlyExcelUtil.getString(row.getCell(8)), AlyExcelUtil.getString(row.getCell(9)),
                                                AlyExcelUtil.getString(row.getCell(10)), AlyExcelUtil.getString(row.getCell(11)),
                                                AlyExcelUtil.getString(row.getCell(12)), AlyExcelUtil.getString(row.getCell(13)),
                                                AlyExcelUtil.getString(row.getCell(14)), AlyExcelUtil.getString(row.getCell(15)),
                                                AlyExcelUtil.getString(row.getCell(16)), AlyExcelUtil.getString(row.getCell(17)),
                                                AlyExcelUtil.getString(row.getCell(18)), AlyExcelUtil.getString(row.getCell(19)),
                                                AlyExcelUtil.getString(row.getCell(20)), AlyExcelUtil.getString(row.getCell(21)),
                                                AlyExcelUtil.getString(row.getCell(22)), AlyExcelUtil.getString(row.getCell(23)),
                                                AlyExcelUtil.getString(row.getCell(24)), AlyExcelUtil.getString(row.getCell(25)),
                                                AlyExcelUtil.getString(row.getCell(26)), AlyExcelUtil.getString(row.getCell(27)),
                                                AlyExcelUtil.getString(row.getCell(28))
                                        ));
                                continue;
                            }
                            String[] data = getRowData(colLength, sheet, a);
                            directionApplyInfo.setName(data[7]);
                            directionApplyInfo.setType(data[3]);
                            directionApplyInfo.setStage(data[24]);
                            directionApplyInfo.setLeid(levels.get(lpos).getId());
                            directionApplyInfo.setFile(data[23]);
                            directionApplyInfo.setIdentifier(data[15]);
                            directionApplyInfo.setDept(data[7]);
                            directionApplyInfo.setAccording(data[25]);
                            directionApplyInfo.setSchool("".equals(data[2]) ? "湖南工学院" : data[2]);
                            directionApplyInfo.setSubjectCategory(data[4]);
                            directionApplyInfo.setStartProjectYear("".equals(data[5]) ? year + "" : data[5]);
                            directionApplyInfo.setBelongUnit(data[6]);
                            directionApplyInfo.setDeclareApproveTime(data[12]);
                            directionApplyInfo.setStartTime(data[13]);
                            directionApplyInfo.setPlanFinishTime(data[14]);
                            directionApplyInfo.setTotalFund(isNumeric(data[16]) == -1 ? 0 : isNumeric(data[16]));
                            directionApplyInfo.setReceiptTotalFund(isNumeric(data[17]) == -1 ? 0 : isNumeric(data[17]));
                            directionApplyInfo.setBearUnit(data[18]);
                            directionApplyInfo.setProjectStatus(data[19]);
                            directionApplyInfo.setCompleteTime(data[20]);
                            directionApplyInfo.setSubjectType(data[21]);
                            directionApplyInfo.setNationalEconomyCategory(data[22]);

                            gainApply.setSid(users.get(upos).getId());
                            gainApply.setChildid(levels.get(lpos).getId());
                            gainApply.setTeam("0");
                            gainApply.setTrtid("1");
                            gainApply.setRemarks(data[28]);
                            gainApply.setAccording(data[25]);

                            //批量加入申请分数表格
                            ScoreApplyInfo scoreApplyInfo = new ScoreApplyInfo();
                            scoreApplyInfo.setSysid(users.get(upos).getId());
                            scoreApplyInfo.setHost(1);
                            scoreApplyInfo.setCanScore(Double.valueOf(data[26]));
                            scoreApplyInfo.setCannotScore(0.0);
                            scoreApplyInfos.add(scoreApplyInfo);

                            Gdatas.add(gainApply);
                            Ddatas.add(directionApplyInfo);
                        }
                        if (Gdatas.size() > 0 && Ddatas.size() > 0) {
                            originMethod(Gdatas, Ddatas, applylogs, scoreApplyInfos, "纵向科研", user);
                        }
                        break;
                    case "横向科研":
                        List<TransverseApplyInfo> Tdatas = new ArrayList<>();
                        for (a = 2; a < rowLength; a++) {
                            // 双重循环 因为一个单元格由行和列的索引下表构成
                            TransverseApplyInfo transverseApplyInfo = new TransverseApplyInfo();
                            GainApply gainApply = new GainApply();
                            row = sheet.getRow(a);
                            if (!AlyExcelUtil.isRowEmpty(row)) {
                                continue;
                            }
                            //判断是否有这个等级，有这个等级再将数据批量添加到数据库当中，如果没有这个等级直接提前掐死
                            String level = AlyExcelUtil.getString(row.getCell(16));
                            String username = AlyExcelUtil.getString(row.getCell(11));
                            String dept = AlyExcelUtil.getString(row.getCell(1));
                            String additionLevel = AlyExcelUtil.getString(row.getCell(3));
                            int upos = isUserExists(users, username, dept);
                            int lpos = isLevelExists(levels, level, "横向科研", additionLevel);
                            if (lpos == -1 || upos == -1) {
                                begin = false;
                                //判断错误文件输出文件是否为空
                                if (!fileClass.containsKey("横向科研")) {
                                    fileClass.put("横向科研", TransctionHead.class);
                                }
                                if (!errorContents.containsKey("横向科研")) {
                                    errorContents.put("横向科研", new ArrayList<>());
                                }
                                errorContents.get("横向科研").
                                        add(Lists.newArrayList(
                                                AlyExcelUtil.getString(row.getCell(0)), AlyExcelUtil.getString(row.getCell(1)),
                                                AlyExcelUtil.getString(row.getCell(2)), AlyExcelUtil.getString(row.getCell(3)),
                                                AlyExcelUtil.getString(row.getCell(4)), AlyExcelUtil.getString(row.getCell(5)),
                                                AlyExcelUtil.getString(row.getCell(6)), AlyExcelUtil.getString(row.getCell(7)),
                                                AlyExcelUtil.getString(row.getCell(8)), AlyExcelUtil.getString(row.getCell(9)),
                                                AlyExcelUtil.getString(row.getCell(10)), AlyExcelUtil.getString(row.getCell(11)),
                                                AlyExcelUtil.getString(row.getCell(12)), AlyExcelUtil.getString(row.getCell(13)),
                                                AlyExcelUtil.getString(row.getCell(14)), AlyExcelUtil.getString(row.getCell(15)),
                                                AlyExcelUtil.getString(row.getCell(16)), AlyExcelUtil.getString(row.getCell(17)),
                                                AlyExcelUtil.getString(row.getCell(18)), AlyExcelUtil.getString(row.getCell(19)),
                                                AlyExcelUtil.getString(row.getCell(20)), AlyExcelUtil.getString(row.getCell(21)),
                                                AlyExcelUtil.getString(row.getCell(22)), AlyExcelUtil.getString(row.getCell(23)),
                                                AlyExcelUtil.getString(row.getCell(24)), AlyExcelUtil.getString(row.getCell(25)),
                                                AlyExcelUtil.getString(row.getCell(26))
                                        ));
                                continue;
                            }
                            String[] data = getRowData(colLength, sheet, a);
                            transverseApplyInfo.setCard(data[18]);
                            transverseApplyInfo.setName(data[7]);
                            transverseApplyInfo.setMoney(data[17]);
                            transverseApplyInfo.setAccording(data[25]);
                            transverseApplyInfo.setLeid(levels.get(lpos).getId());
                            transverseApplyInfo.setFirstSign("是".equals(data[24]) ? 1 : 0);
                            transverseApplyInfo.setSchool("".equals(data[2]) ? "湖南工学院" : data[2]);
                            transverseApplyInfo.setSubjectCategory(data[4]);
                            transverseApplyInfo.setStartProjectYear("".equals(data[5]) ? year + "" : data[5]);
                            transverseApplyInfo.setBelongUnit(data[6]);
                            transverseApplyInfo.setSignContractTime(data[12]);
                            transverseApplyInfo.setStartTime(data[13]);
                            transverseApplyInfo.setPlanFinishTime(data[14]);
                            transverseApplyInfo.setIdentifier(data[15]);
                            transverseApplyInfo.setTotalFund(isNumeric(data[16]) == -1 ? 0 : isNumeric(data[15]));
                            transverseApplyInfo.setBearUnit(data[9]);
                            transverseApplyInfo.setProjectStatus(data[19]);
                            transverseApplyInfo.setContractNumber(data[20]);
                            transverseApplyInfo.setCompleteTime(data[21]);
                            transverseApplyInfo.setSubjectType(data[22]);
                            transverseApplyInfo.setNationalEconomyCategory(data[23]);
                            transverseApplyInfo.setType(data[10]);

                            gainApply.setSid(users.get(upos).getId());
                            gainApply.setChildid(levels.get(lpos).getId());
                            gainApply.setRemarks(data[8]);
                            gainApply.setTeam("0");
                            gainApply.setTrtid("2");
                            gainApply.setAccording(data[25]);

                            //批量加入申请分数表格
                            ScoreApplyInfo scoreApplyInfo = new ScoreApplyInfo();
                            scoreApplyInfo.setSysid(users.get(upos).getId());
                            scoreApplyInfo.setHost(1);
                            scoreApplyInfo.setCanScore(Double.valueOf(data[26]));
                            scoreApplyInfo.setCannotScore(0.0);

                            scoreApplyInfos.add(scoreApplyInfo);
                            Gdatas.add(gainApply);
                            Tdatas.add(transverseApplyInfo);
                        }
                        if (Gdatas.size() > 0 && Tdatas.size() > 0) {
                            originMethod(Gdatas, Tdatas, applylogs, scoreApplyInfos, "横向科研", user);
                        }
                        break;
                    case "科技成果":
                        List<AchievementApplyInfo> Adatas = new ArrayList<>();
                        for (a = 2; a < rowLength; a++) {
                            // 双重循环 因为一个单元格由行和列的索引下表构成
                            AchievementApplyInfo achievementApplyInfo = new AchievementApplyInfo();
                            GainApply gainApply = new GainApply();
                            row = sheet.getRow(a);
                            if (!AlyExcelUtil.isRowEmpty(row)) {
                                continue;
                            }
                            //判断是否有这个等级，有这个等级再将数据批量添加到数据库当中，如果没有这个等级直接提前掐死
                            String level = AlyExcelUtil.getString(row.getCell(11));
                            String username = AlyExcelUtil.getString(row.getCell(1));
                            String dept = AlyExcelUtil.getString(row.getCell(9));
                            String additionLevel = AlyExcelUtil.getString(row.getCell(5));
                            int upos = isUserExists(users, username, dept);
                            int lpos = isLevelExists(levels, level, "科技成果", additionLevel);
                            if (lpos == -1 || upos == -1) {
                                begin = false;
                                //判断错误文件输出文件是否为空
                                if (!fileClass.containsKey("科技成果")) {
                                    fileClass.put("科技成果", AchievementHead.class);
                                }
                                if (!errorContents.containsKey("科技成果")) {
                                    errorContents.put("科技成果", new ArrayList<>());
                                }
                                errorContents.get("科技成果").
                                        add(Lists.newArrayList(
                                                AlyExcelUtil.getString(row.getCell(0)), AlyExcelUtil.getString(row.getCell(1)),
                                                AlyExcelUtil.getString(row.getCell(2)), AlyExcelUtil.getString(row.getCell(3)),
                                                AlyExcelUtil.getString(row.getCell(4)), AlyExcelUtil.getString(row.getCell(5)),
                                                AlyExcelUtil.getString(row.getCell(6)), AlyExcelUtil.getString(row.getCell(7)),
                                                AlyExcelUtil.getString(row.getCell(8)), AlyExcelUtil.getString(row.getCell(9)),
                                                AlyExcelUtil.getString(row.getCell(10)), AlyExcelUtil.getString(row.getCell(11)),
                                                AlyExcelUtil.getString(row.getCell(12)), AlyExcelUtil.getString(row.getCell(13))
                                        ));
                                continue;
                            }
                            String[] data = getRowData(colLength, sheet, a);
                            achievementApplyInfo.setContent(data[2]);
                            achievementApplyInfo.setDept(data[3]);
                            achievementApplyInfo.setName(data[4]);
                            achievementApplyInfo.setLevel(data[5]);
                            achievementApplyInfo.setFirstSign("是".equals(data[12]) ? 1 : 0);
                            achievementApplyInfo.setSchoolOrder(Integer.valueOf(data[13].trim()));
                            achievementApplyInfo.setWorkersOrder(Integer.valueOf(data[14].trim()));

                            gainApply.setSid(users.get(upos).getId());
                            gainApply.setChildid(levels.get(lpos).getId());
                            gainApply.setTeam("0");
                            gainApply.setTrtid("3");
                            gainApply.setRemarks(data[10]);
                            gainApply.setAccording(data[6]);

                            //批量加入申请分数表格
                            ScoreApplyInfo scoreApplyInfo = new ScoreApplyInfo();
                            scoreApplyInfo.setSysid(users.get(upos).getId());
                            scoreApplyInfo.setHost(1);
                            scoreApplyInfo.setCanScore(Double.valueOf(data[8]));
                            scoreApplyInfo.setCannotScore(0.0);
                            scoreApplyInfos.add(scoreApplyInfo);

                            Gdatas.add(gainApply);
                            Adatas.add(achievementApplyInfo);
                        }
                        if (Gdatas.size() > 0 && Adatas.size() > 0) {
                            originMethod(Gdatas, Adatas, applylogs, scoreApplyInfos, "科技成果", user);
                        }
                        break;
                    case "学术论文":
                        List<PaperApplyInfo> Pdatas = new ArrayList<>();
                        for (a = 2; a < rowLength; a++) {
                            PaperApplyInfo paperApplyInfo = new PaperApplyInfo();
                            GainApply gainApply = new GainApply();
                            row = sheet.getRow(a);
                            if (!AlyExcelUtil.isRowEmpty(row)) {
                                continue;
                            }
                            //判断是否有这个等级，有这个等级再将数据批量添加到数据库当中，如果没有这个等级直接提前掐死
                            String level = AlyExcelUtil.getString(row.getCell(6));
                            //判断是否该申请用户/
                            String username = AlyExcelUtil.getString(row.getCell(1));
                            //申请用户所在部门
                            String dept = AlyExcelUtil.getString(row.getCell(9));
                            int upos = isUserExists(users, username, dept);
                            int lpos = isLevelExists(levels, level, "学术论文", "");
                            //如果申请等级或者申请用户不存在就都不加入数据库
                            if (lpos == -1
                                    || upos == -1) {
                                //判断错误文件输出文件是否为空
                                if (!fileClass.containsKey("学术论文")) {
                                    fileClass.put("学术论文", PaperHead.class);
                                }
                                if (!errorContents.containsKey("学术论文")) {
                                    errorContents.put("学术论文", new ArrayList<>());
                                }
                                begin = false;
                                errorContents.get("学术论文").
                                        add(Lists.newArrayList(
                                                AlyExcelUtil.getString(row.getCell(0)), AlyExcelUtil.getString(row.getCell(1)),
                                                AlyExcelUtil.getString(row.getCell(2)), AlyExcelUtil.getString(row.getCell(3)),
                                                AlyExcelUtil.getString(row.getCell(4)), AlyExcelUtil.getString(row.getCell(5)),
                                                AlyExcelUtil.getString(row.getCell(6)), AlyExcelUtil.getString(row.getCell(7)),
                                                AlyExcelUtil.getString(row.getCell(8)), AlyExcelUtil.getString(row.getCell(9)),
                                                AlyExcelUtil.getString(row.getCell(10)), AlyExcelUtil.getString(row.getCell(11))
                                        ));
                                continue;
                            }
                            String[] data = getRowData(colLength, sheet, a);

                            paperApplyInfo.setName(data[2]);
                            paperApplyInfo.setPeriodicalName(data[3]);
                            paperApplyInfo.setCnnum(data[4]);
                            paperApplyInfo.setOrder(data[5]);
                            paperApplyInfo.setScore(Double.valueOf(data[8]));
                            paperApplyInfo.setInstitute(data[10]);

                            //如果走到了这一步就说明申请用户是存在的
                            gainApply.setSid(users.get(upos).getId());
                            gainApply.setChildid(levels.get(lpos).getId());
                            gainApply.setRemarks(data[11]);
                            gainApply.setAccording(data[7]);
                            gainApply.setTeam("0");
                            gainApply.setTrtid("4");
                            Gdatas.add(gainApply);
                            Pdatas.add(paperApplyInfo);

                            //批量加入申请分数表格
                            ScoreApplyInfo scoreApplyInfo = new ScoreApplyInfo();
                            scoreApplyInfo.setSysid(users.get(upos).getId());
                            scoreApplyInfo.setHost(1);
                            scoreApplyInfo.setCanScore(Double.valueOf(data[8]));
                            scoreApplyInfo.setCannotScore(0.0);
                            scoreApplyInfos.add(scoreApplyInfo);
                        }
                        if (Gdatas.size() > 0 && Pdatas.size() > 0) {
                            originMethod(Gdatas, Pdatas, applylogs, scoreApplyInfos, "学术论文", user);
                        }
                        break;
                    case "学术专著":
                        List<BookApplyInfo> Bdatas = new ArrayList<>();
                        for (a = 2; a < rowLength; a++) {
                            // 双重循环 因为一个单元格由行和列的索引下表构成
                            BookApplyInfo bookApplyInfo = new BookApplyInfo();
                            GainApply gainApply = new GainApply();
                            row = sheet.getRow(a);
                            if (!AlyExcelUtil.isRowEmpty(row)) {
                                continue;
                            }
                            //判断是否有这个等级，有这个等级再将数据批量添加到数据库当中，如果没有这个等级直接提前掐死
                            String level = AlyExcelUtil.getString(row.getCell(4));
                            String username = AlyExcelUtil.getString(row.getCell(1));
                            String dept = AlyExcelUtil.getString(row.getCell(8));
                            int upos = isUserExists(users, username, dept);
                            int lpos = isLevelExists(levels, level, "学术专著", "");
                            if (lpos == -1 || upos == -1) {
                                //判断错误文件输出文件是否为空
                                if (!fileClass.containsKey("学术专著")) {
                                    fileClass.put("学术专著", BookHead.class);
                                }
                                if (!errorContents.containsKey("学术专著")) {
                                    errorContents.put("学术专著", new ArrayList<>());
                                }
                                begin = false;
                                errorContents.get("学术专著").
                                        add(Lists.newArrayList(
                                                AlyExcelUtil.getString(row.getCell(0)), AlyExcelUtil.getString(row.getCell(1)),
                                                AlyExcelUtil.getString(row.getCell(2)), AlyExcelUtil.getString(row.getCell(3)),
                                                AlyExcelUtil.getString(row.getCell(4)), AlyExcelUtil.getString(row.getCell(5)),
                                                AlyExcelUtil.getString(row.getCell(6)), AlyExcelUtil.getString(row.getCell(7)),
                                                AlyExcelUtil.getString(row.getCell(8)), AlyExcelUtil.getString(row.getCell(9)),
                                                AlyExcelUtil.getString(row.getCell(10))
                                        ));
                                continue;
                            }
                            String[] data = getRowData(colLength, sheet, a);
                            bookApplyInfo.setName(data[2]);
                            bookApplyInfo.setPressName(data[3]);
                            bookApplyInfo.setAcademicType(data[4]);
                            bookApplyInfo.setWordsNum(data[5]);
                            bookApplyInfo.setOrder(data[6]);
                            bookApplyInfo.setScore(Double.valueOf(data[9]));

                            gainApply.setSid(users.get(upos).getId());
                            gainApply.setChildid(levels.get(lpos).getId());
                            gainApply.setTeam("0");
                            gainApply.setTrtid("5");
                            gainApply.setRemarks(data[10]);
                            gainApply.setAccording(data[7]);

                            //批量加入申请分数表格
                            ScoreApplyInfo scoreApplyInfo = new ScoreApplyInfo();
                            scoreApplyInfo.setSysid(users.get(upos).getId());
                            scoreApplyInfo.setHost(1);
                            scoreApplyInfo.setCanScore(Double.valueOf(data[9]));
                            scoreApplyInfo.setCannotScore(0.0);

                            scoreApplyInfos.add(scoreApplyInfo);
                            Gdatas.add(gainApply);
                            Bdatas.add(bookApplyInfo);
                        }
                        if (Gdatas.size() > 0 && Bdatas.size() > 0) {
                            originMethod(Gdatas, Bdatas, applylogs, scoreApplyInfos, "学术专著", user);
                        }
                        break;
                    case "发明专利":
                        List<InventApplyInfo> Idatas = new ArrayList<>();
                        for (a = 2; a < rowLength; a++) {
                            // 双重循环 因为一个单元格由行和列的索引下表构成
                            InventApplyInfo inventApplyInfo = new InventApplyInfo();
                            GainApply gainApply = new GainApply();
                            row = sheet.getRow(a);
                            if (!AlyExcelUtil.isRowEmpty(row)) {
                                continue;
                            }
                            //判断是否有这个等级，有这个等级再将数据批量添加到数据库当中，如果没有这个等级直接提前掐死
                            String level = AlyExcelUtil.getString(row.getCell(5));
                            String username = AlyExcelUtil.getString(row.getCell(9));
                            String pusername = AlyExcelUtil.getString(row.getCell(12));
                            String dept = AlyExcelUtil.getString(row.getCell(1));
                            String additionLevel = AlyExcelUtil.getString(row.getCell(18));
                            int upos = isUserExists(users, username, dept);
                            int pupos = isUserExists(users, pusername, dept);
                            int lpos = isLevelExists(levels, level, "发明专利", additionLevel);
                            if (lpos == -1 || upos == -1 || pupos == -1) {
                                //判断错误文件输出文件是否为空
                                if (!fileClass.containsKey("发明专利")) {
                                    fileClass.put("发明专利", InventHead.class);
                                }
                                if (!errorContents.containsKey("发明专利")) {
                                    errorContents.put("发明专利", new ArrayList<>());
                                }
                                begin = false;
                                errorContents.get("发明专利").
                                        add(Lists.newArrayList(
                                                AlyExcelUtil.getString(row.getCell(0)), AlyExcelUtil.getString(row.getCell(1)),
                                                AlyExcelUtil.getString(row.getCell(2)), AlyExcelUtil.getString(row.getCell(3)),
                                                AlyExcelUtil.getString(row.getCell(4)), AlyExcelUtil.getString(row.getCell(5)),
                                                AlyExcelUtil.getString(row.getCell(6)), AlyExcelUtil.getString(row.getCell(7)),
                                                AlyExcelUtil.getString(row.getCell(8)), AlyExcelUtil.getString(row.getCell(9)),
                                                AlyExcelUtil.getString(row.getCell(10)), AlyExcelUtil.getString(row.getCell(11)),
                                                AlyExcelUtil.getString(row.getCell(12)), AlyExcelUtil.getString(row.getCell(13)),
                                                AlyExcelUtil.getString(row.getCell(14)), AlyExcelUtil.getString(row.getCell(15)),
                                                AlyExcelUtil.getString(row.getCell(16)), AlyExcelUtil.getString(row.getCell(17)),
                                                AlyExcelUtil.getString(row.getCell(18)), AlyExcelUtil.getString(row.getCell(19)),
                                                AlyExcelUtil.getString(row.getCell(20)), AlyExcelUtil.getString(row.getCell(21))
                                        ));
                                continue;
                            }
                            String[] data = getRowData(colLength, sheet, a);
                            inventApplyInfo.setAuthorization(data[10]);
                            inventApplyInfo.setStage(data[18]);
                            inventApplyInfo.setName(data[4]);
                            inventApplyInfo.setType(data[5]);
                            inventApplyInfo.setMoney(isNumeric(data[19]) == -1 ? 0 : isNumeric(data[19]));
                            inventApplyInfo.setAccording(data[21]);
                            inventApplyInfo.setSchool("".equals(data[2]) ? "湖南工学院" : data[2]);
                            inventApplyInfo.setYear("".equals(data[3]) ? year + "" : data[3]);
                            inventApplyInfo.setLocationUnit(data[8]);
                            inventApplyInfo.setAuthorizeTime(data[11]);
                            inventApplyInfo.setConcurType(data[13]);
                            inventApplyInfo.setSchoolSign(data[14]);
                            inventApplyInfo.setScope(data[15]);
                            inventApplyInfo.setPatentStatus(data[16]);
                            inventApplyInfo.setPowerMan(users.get(pupos).getId());
                            inventApplyInfo.setApplyNumber(data[6]);

                            gainApply.setSid(users.get(upos).getId());
                            gainApply.setTeam("0");
                            gainApply.setTrtid("6");
                            gainApply.setChildid(levels.get(lpos).getId());
                            gainApply.setRemarks(data[17]);
                            gainApply.setAccording(data[21]);

                            //批量加入申请分数表格
                            ScoreApplyInfo scoreApplyInfo = new ScoreApplyInfo();
                            scoreApplyInfo.setSysid(users.get(upos).getId());
                            scoreApplyInfo.setHost(1);
                            scoreApplyInfo.setCanScore(Double.valueOf(data[20]));
                            scoreApplyInfo.setCannotScore(0.0);
                            scoreApplyInfos.add(scoreApplyInfo);

                            Gdatas.add(gainApply);
                            Idatas.add(inventApplyInfo);
                        }
                        if (Gdatas.size() > 0 && Idatas.size() > 0) {
                            originMethod(Gdatas, Idatas, applylogs, scoreApplyInfos, "发明专利", user);
                        }
                        break;
                    case "科研平台":
                        List<ScientificApplyInfo> Sdatas = new ArrayList<>();
                        for (a = 2; a < rowLength; a++) {
                            // 双重循环 因为一个单元格由行和列的索引下表构成
                            //如果数据是以表格的形式展现，就不需要遍历每一行了，只需要知道第一行的信息即可
                            row = sheet.getRow(a);
                            ScientificApplyInfo scientificApplyInfo = new ScientificApplyInfo();
                            GainApply gainApply = new GainApply();
                            if (!AlyExcelUtil.isRowEmpty(sheet.getRow(a))) {
                                continue;
                            }
                            String[] data = getRowData(colLength, sheet, a);
                            //第一行数据就反映了所有
                            //分配分数的详情
                            Map<String, Double> userScores = extractTextAndNumbers(data[8]);
                            double finalScore = 0d;
                            Map<String, String> userScoreIds = new HashMap<>();
                            for (String username : userScores.keySet()) {
                                Double score = userScores.get(username);
                                //判断数据库中是否有该申请用户
                                int upos = isUserExists(users, username, data[7]);
                                if (upos == -1) {
                                    finalScore = -1d;
                                } else {
                                    finalScore += score;
                                }
                                //如果用户不存在就只存分数，如果存在就存储用户id和分数
                                userScoreIds.put(username, upos == -1 ? score + "" : users.get(upos).getId() + "::" + score);
                            }

                            //判断是否有这个等级，有这个等级再将数据批量添加到数据库当中，如果没有这个等级直接提前掐死
                            String level = sheet.getRow(a).getCell(2).getStringCellValue();
                            if (level.contains("\\(")) {
                                level = level.split("\\(")[0];
                            } else if (level.contains("（")) {
                                level = level.split("（")[0];
                            }
                            int lpos = isLevelExists(levels, level, "科研平台", "");
                            if (lpos == -1 || finalScore < 0) {
                                begin = false;
                                //判断错误文件输出文件是否为空
                                if (!fileClass.containsKey("科研平台")) {
                                    fileClass.put("科研平台", ScienceHead.class);
                                }
                                if (!errorContents.containsKey("科研平台")) {
                                    errorContents.put("科研平台", new ArrayList<>());
                                }
                                errorContents.get("科研平台").
                                        add(Lists.newArrayList(
                                                AlyExcelUtil.getString(row.getCell(0)), AlyExcelUtil.getString(row.getCell(1)),
                                                AlyExcelUtil.getString(row.getCell(2)), AlyExcelUtil.getString(row.getCell(3)),
                                                AlyExcelUtil.getString(row.getCell(4)), AlyExcelUtil.getString(row.getCell(5)),
                                                AlyExcelUtil.getString(row.getCell(6)), AlyExcelUtil.getString(row.getCell(7)),
                                                AlyExcelUtil.getString(row.getCell(8))
                                        ));
                                userScoreIds.remove(0);
                                //得到第一排的序号
                                int index = Integer.parseInt(AlyExcelUtil.getString(row.getCell(0)));
                                for (String username : userScoreIds.keySet()) {
                                    errorContents.get("科研平台").add(
                                            Lists.newArrayList(
                                                    ++index, username,
                                                    AlyExcelUtil.getString(row.getCell(2)), AlyExcelUtil.getString(row.getCell(3)),
                                                    AlyExcelUtil.getString(row.getCell(4)), AlyExcelUtil.getString(row.getCell(5)),
                                                    userScoreIds.get(username),
                                                    AlyExcelUtil.getString(row.getCell(7)), AlyExcelUtil.getString(row.getCell(8))
                                            )
                                    );
                                }
                                continue;
                            }
                            scientificApplyInfo.setName(data[3]);
                            scientificApplyInfo.setType(data[2]);
                            scientificApplyInfo.setDept(data[4]);
                            scientificApplyInfo.setScore(finalScore);
                            for (String username : userScoreIds.keySet()) {
                                //批量加入申请分数表格
                                ScoreApplyInfo scoreApplyInfo = new ScoreApplyInfo();
                                scoreApplyInfo.setSysid(userScoreIds.get(username).split("::")[0]);
                                scoreApplyInfo.setHost(1);
                                scoreApplyInfo.setCanScore(Double.valueOf(userScoreIds.get(username).split("::")[1]));
                                scoreApplyInfo.setCannotScore(0.0);
                                scoreApplyInfos.add(scoreApplyInfo);
                            }
                            //加一个特殊数据以作为分隔符
                            ScoreApplyInfo scoreApplyInfo = new ScoreApplyInfo();
                            scoreApplyInfo.setGaid(-1);
                            scoreApplyInfos.add(scoreApplyInfo);
                            //排名第一的是负责人
                            gainApply.setSid(users.get(isUserExists(users, data[1], data[7])).getId());
                            gainApply.setChildid(levels.get(lpos).getId());
                            gainApply.setTeam("1");
                            gainApply.setTrtid("7");
                            gainApply.setAccording(data[5]);

                            Gdatas.add(gainApply);
                            Sdatas.add(scientificApplyInfo);
                        }
                        if (Gdatas.size() > 0 && Sdatas.size() > 0) {
                            originMethod(Gdatas, Sdatas, applylogs, scoreApplyInfos, "科研平台", user);
                        }
                        break;
                    case "科技荣誉":
                        List<HonorApplyInfo> Hdatas = new ArrayList<>();
                        for (a = 2; a < rowLength; a++) {
                            // 双重循环 因为一个单元格由行和列的索引下表构成
                            HonorApplyInfo honorApplyInfo = new HonorApplyInfo();
                            GainApply gainApply = new GainApply();
                            row = sheet.getRow(a);
                            if (!AlyExcelUtil.isRowEmpty(row)) {
                                continue;
                            }
                            //判断是否有这个等级，有这个等级再将数据批量添加到数据库当中，如果没有这个等级直接提前掐死
                            String level = AlyExcelUtil.getString(row.getCell(2));
                            if (level.contains("\\(")) {
                                level = level.split("\\(")[0];
                            } else if (level.contains("（")) {
                                level = level.split("（")[0];
                            }
                            String username = AlyExcelUtil.getString(row.getCell(1));
                            String dept = AlyExcelUtil.getString(row.getCell(6));
                            int upos = isUserExists(users, username, dept);
                            int lpos = isLevelExists(levels, level, "科技荣誉", "");
                            if (lpos == -1 || upos == -1) {
                                begin = false;
                                //判断错误文件输出文件是否为空
                                if (!fileClass.containsKey("科技荣誉")) {
                                    fileClass.put("科技荣誉", HonourHead.class);
                                }
                                if (!errorContents.containsKey("科技荣誉")) {
                                    errorContents.put("科技荣誉", new ArrayList<>());
                                }
                                errorContents.get("科技荣誉").
                                        add(Lists.newArrayList(
                                                AlyExcelUtil.getString(row.getCell(0)), AlyExcelUtil.getString(row.getCell(1)),
                                                AlyExcelUtil.getString(row.getCell(2)), AlyExcelUtil.getString(row.getCell(3)),
                                                AlyExcelUtil.getString(row.getCell(4)), AlyExcelUtil.getString(row.getCell(5)),
                                                AlyExcelUtil.getString(row.getCell(6)), AlyExcelUtil.getString(row.getCell(7))
                                        ));
                                continue;
                            }
                            String[] data = getRowData(colLength, sheet, a);
                            honorApplyInfo.setType(data[2]);
                            honorApplyInfo.setDept(data[3]);
                            honorApplyInfo.setName(data[4]);
                            honorApplyInfo.setScore(Double.valueOf(data[6]));

                            gainApply.setSid(users.get(upos).getId());
                            gainApply.setTeam("0");
                            gainApply.setTrtid("8");
                            gainApply.setChildid(levels.get(lpos).getId());
                            gainApply.setAccording(data[5]);

                            //批量加入申请分数表格
                            ScoreApplyInfo scoreApplyInfo = new ScoreApplyInfo();
                            scoreApplyInfo.setSysid(users.get(upos).getId());
                            scoreApplyInfo.setHost(1);
                            scoreApplyInfo.setCanScore(Double.valueOf(data[6]));
                            scoreApplyInfo.setCannotScore(0.0);

                            scoreApplyInfos.add(scoreApplyInfo);
                            Gdatas.add(gainApply);
                            Hdatas.add(honorApplyInfo);
                        }
                        if (Gdatas.size() > 0 && Hdatas.size() > 0) {
                            originMethod(Gdatas, Hdatas, applylogs, scoreApplyInfos, "科技荣誉", user);
                        }
                        break;
                }
                if (!begin) {
                    if (errorContent.length() == 0) {
                        errorContent = "上传的文件中的数据有一些类别的科技等级或者申请人不存在，这些数据整合在了新下载的文件中，\n" +
                                "请修改其中的数据使其中的数据中的等级或者申请人存在再上传该文件，请勿上传旧文件";
                    }

                    //但凡有错误信息就将原本存在redis中的记录删掉
                    if (redisTemplate.opsForValue().get(fileName) != null) {
                        redisTemplate.delete(fileName);
                    }
                }
            }

            if (errorContent.length() == 0) {
                errorContent = "批量增加科技申请信息成功!";
                //说明全部数据都提娜姬成功，将存在redis中的键值对设置为0，防止重复传递数据
                redisTemplate.opsForValue().set(fileName, "0");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return errorContent;
    }


    //批量上传事务精细化控制代码抽取

    @Transactional(rollbackFor = Exception.class)
    public void originMethod(List<GainApply> Gdatas, Object datas, List<String> applylogs, List<ScoreApplyInfo> scoreApplyInfos, String type, int user) {
        //先批量加入主表申请信息
        gainApplyMapper.insertBatch(Gdatas);
        switch (type) {
            case "纵向科研":
                List<DirectionApplyInfo> Ddatas = (List<DirectionApplyInfo>) datas;
                //批量设置外键id
                for (int j = 0; j < Ddatas.size(); j++) {
                    Ddatas.get(j).setGaid(Gdatas.get(j).getGaid());
                    applylogs.add(Gdatas.get(j).getGaid() + "");
                    scoreApplyInfos.get(j).setGaid(Gdatas.get(j).getGaid());
                }
                //批量添加一个表格的数据
                directionApplyInfoMapper.insertBatch(Ddatas, user);
                break;
            case "横向科研":
                List<TransverseApplyInfo> Tdatas = (List<TransverseApplyInfo>) datas;
                //批量设置外键id
                for (int j = 0; j < Tdatas.size(); j++) {
                    Tdatas.get(j).setGaid(Gdatas.get(j).getGaid());
                    applylogs.add(Gdatas.get(j).getGaid() + "");
                    scoreApplyInfos.get(j).setGaid(Gdatas.get(j).getGaid());
                }
                transverseApplyInfoMapper.insertBatch(Tdatas, user);
                int i = 1 / 0;
                break;
            case "科技成果":
                List<AchievementApplyInfo> Adatas = (List<AchievementApplyInfo>) datas;
                //批量设置外键id
                for (int j = 0; j < Adatas.size(); j++) {
                    Adatas.get(j).setGaid(Gdatas.get(j).getGaid());
                    applylogs.add(Gdatas.get(j).getGaid() + "");
                    scoreApplyInfos.get(j).setGaid(Gdatas.get(j).getGaid());
                }
                achievementApplyInfoMapper.insertBatch(Adatas, user);
                break;
            case "学术论文":
                List<PaperApplyInfo> Pdatas = (List<PaperApplyInfo>) datas;
                //批量设置外键id
                for (int j = 0; j < Pdatas.size(); j++) {
                    Pdatas.get(j).setGaid(Gdatas.get(j).getGaid());
                    applylogs.add(Gdatas.get(j).getGaid() + "");
                    scoreApplyInfos.get(j).setGaid(Gdatas.get(j).getGaid());
                }
                //批量添加一个表格的数据
                paperApplyInfoMapper.insertBatch(Pdatas, user);
                break;
            case "学术专著":
                List<BookApplyInfo> Bdatas = (List<BookApplyInfo>) datas;
                //批量设置外键id
                for (int j = 0; j < Bdatas.size(); j++) {
                    Bdatas.get(j).setGaid(Gdatas.get(j).getGaid());
                    applylogs.add(Gdatas.get(j).getGaid() + "");
                    scoreApplyInfos.get(j).setGaid(Gdatas.get(j).getGaid());
                }
                bookApplyInfoMapper.insertBatch(Bdatas, user);
                break;
            case "发明专利":
                List<InventApplyInfo> Idatas = (List<InventApplyInfo>) datas;
                //批量设置外键id
                for (int j = 0; j < Idatas.size(); j++) {
                    Idatas.get(j).setGaid(Gdatas.get(j).getGaid());
                    applylogs.add(Gdatas.get(j).getGaid() + "");
                    scoreApplyInfos.get(j).setGaid(Gdatas.get(j).getGaid());
                }
                inventApplyInfoMapper.insertBatch(Idatas, user);
                break;
            case "科研平台":
                List<ScientificApplyInfo> Sdatas = (List<ScientificApplyInfo>) datas;
                //批量设置外键id
                int index = 0;
                for (int j = 0; j < Sdatas.size(); j++) {
                    Sdatas.get(j).setGaid(Gdatas.get(j).getGaid());
                    applylogs.add(Gdatas.get(j).getGaid() + "");
                    while (scoreApplyInfos.get(index).getGaid() != -1) {
                        scoreApplyInfos.get(index++).setGaid(Gdatas.get(j).getGaid());
                    }
                }
                scientificApplyInfoMapper.insertBatch(Sdatas, user);
                break;
            case "科技荣誉":
                List<HonorApplyInfo> Hdatas = (List<HonorApplyInfo>) datas;
                //批量设置外键id
                for (int j = 0; j < Hdatas.size(); j++) {
                    Hdatas.get(j).setGaid(Gdatas.get(j).getGaid());
                    applylogs.add(Gdatas.get(j).getGaid() + "");
                    scoreApplyInfos.get(j).setGaid(Gdatas.get(j).getGaid());
                }
                honorApplyInfoMapper.insertBatch(Hdatas, user);
                break;
        }
        //批量添加日志信息
        applylogMapper.insertSpecialBatch(user, applylogs);
        //批量添加分数信息
        scoreApplyInfoMapper.insertBatch(scoreApplyInfos, user);
    }

    //判断等级是字符串还是数字
    public static double isNumeric(String str) {
        try {
            double value = Double.parseDouble(str);
            return value;
        } catch (Exception e) {
            return -1d;
        }
    }

    //查询是否存在该科技等级
    public int isLevelExists(List<ChildLevel> levels, String level, String type, String addition) {
        String compileRule = "(\\d+)万-(\\d+)万";
        if (StringUtils.isEmpty(level) || StringUtils.isBlank(level) || "null".equals(level)) {
            return -1;
        }

        //此变量有用处减少后续的无用判断
        boolean begin = false;
        switch (type) {
            case "纵向科研":
                for (int i = 0; i < levels.size(); i++) {
                    //还要考虑横向的标准，只给顶范围
                    if ("1".equals(levels.get(i).getTrid())) {
                        begin = true;
                        ChildLevel childLevel = levels.get(i);
                        if (splitStringFormat(childLevel.getLname()).contains(level) &&
                                childLevel.getAddition().contains(addition)) {
                            return i;
                        } else {
                            continue;
                        }
                        //这里步骤的目的是减少判断后面的不符合的数据
                    } else if (!begin) {
                        continue;
                    } else {
                        break;
                    }
                }
                break;
            case "横向科研":
                for (int i = 0; i < levels.size(); i++) {
                    //判断等级是字符串还是数字，如果是数字就代表判断的是横向项目
                    double value = isNumeric(level) / 10000;
                    //还要考虑横向的标准，只给顶范围
                    if (value < 0) {
                        //不符合规定
                        return -1;
                    }
                    if ("2".equals(levels.get(i).getTrid())) {
                        begin = true;
                        ChildLevel childLevel = levels.get(i);
                        String lname = childLevel.getLname();
                        if (lname.contains("-")) {
                            int top = 0, deep = 0;
                            // 使用正则表达式匹配数字
                            Pattern pattern = Pattern.compile(compileRule);
                            Matcher matcher = pattern.matcher(lname);

                            // 提取匹配的数字
                            if (matcher.find()) {
                                deep = Integer.parseInt(matcher.group(1));
                                top = Integer.parseInt(matcher.group(2));
                            }
                            if (value > deep && value <= top && addition.equals(childLevel.getAddition())) {
                                return i;
                            }
                        } else {
                            int score = Integer.parseInt(lname.substring(0, lname.indexOf("万")));
                            //最高或者最低
                            if (lname.contains("及以上")) {
                                //最低
                                if (value >= score && addition.equals(childLevel.getAddition())) {
                                    return i;
                                }
                            } else {
                                //最高
                                if (value <= score && addition.equals(childLevel.getAddition())) {
                                    return i;
                                }
                            }
                        }
                        //这里步骤的目的是减少判断后面的不符合的数据
                    } else if (!begin) {
                        continue;
                    } else {
                        break;
                    }
                }
                break;
            case "科技成果":
                for (int i = 0; i < levels.size(); i++) {
                    //还要考虑横向的标准，只给顶范围
                    if ("3".equals(levels.get(i).getTrid())) {
                        begin = true;
                        ChildLevel childLevel = levels.get(i);
                        if (splitStringFormat(childLevel.getLname()).contains(level) &&
                                childLevel.getAddition().contains(addition)) {
                            return i;
                        } else {
                            continue;
                        }
                        //这里步骤的目的是减少判断后面的不符合的数据
                    } else if (!begin) {
                        continue;
                    } else {
                        break;
                    }
                }
                break;
            case "学术论文":
                for (int i = 0; i < levels.size(); i++) {
                    //还要考虑横向的标准，只给顶范围
                    if ("4".equals(levels.get(i).getTrid())) {
                        begin = true;
                        ChildLevel childLevel = levels.get(i);
                        if (childLevel.getLname().contains(level)) {
                            return i;
                        } else {
                            continue;
                        }
                        //这里步骤的目的是减少判断后面的不符合的数据
                    } else if (!begin) {
                        continue;
                    } else {
                        break;
                    }
                }
                break;
            case "学术专著":
                for (int i = 0; i < levels.size(); i++) {
                    //还要考虑横向的标准，只给顶范围
                    if ("5".equals(levels.get(i).getTrid())) {
                        begin = true;
                        String lname = levels.get(i).getLname();
                        if (lname.contains(level)) {
                            return i;
                        } else {
                            continue;
                        }
                        //这里步骤的目的是减少判断后面的不符合的数据
                    } else if (!begin) {
                        continue;
                    } else {
                        break;
                    }
                }
                break;
            case "发明专利":
                for (int i = 0; i < levels.size(); i++) {
                    //还要考虑横向的标准，只给顶范围
                    if ("6".equals(levels.get(i).getTrid())) {
                        begin = true;
                        ChildLevel childLevel = levels.get(i);
                        if (childLevel.getLname().contains(level) &&
                                childLevel.getAddition().contains(addition)) {
                            return i;
                        } else {
                            continue;
                        }
                        //这里步骤的目的是减少判断后面的不符合的数据
                    } else if (!begin) {
                        continue;
                    } else {
                        break;
                    }
                }
                break;
            case "科研平台":
                for (int i = 0; i < levels.size(); i++) {
                    //还要考虑横向的标准，只给顶范围
                    if ("7".equals(levels.get(i).getTrid())) {
                        begin = true;
                        if (levels.get(i).getLname().contains(level)) {
                            return i;
                        } else {
                            continue;
                        }
                        //这里步骤的目的是减少判断后面的不符合的数据
                    } else if (!begin) {
                        continue;
                    } else {
                        break;
                    }
                }
                break;
            case "科技荣誉":
                for (int i = 0; i < levels.size(); i++) {
                    //还要考虑横向的标准，只给顶范围
                    if ("8".equals(levels.get(i).getTrid())) {
                        begin = true;
                        String levelCp = level.substring(0, 3);
                        if (levels.get(i).getLname().contains(levelCp)) {
                            return i;
                        } else {
                            continue;
                        }
                        //这里步骤的目的是减少判断后面的不符合的数据
                    } else if (!begin) {
                        continue;
                    } else {
                        break;
                    }
                }
                break;
        }
        return -1;
    }

    //查询是否存在该申请用户
    public static int isUserExists(List<SimpleGovernuser> users, String username, String dept) {
        username = username.trim();
        dept = dept.trim();
        for (int i = 0; i < users.size(); i++) {
            SimpleGovernuser u = users.get(i);
            if (u.getUsername().contains(username) && u.getDept().contains(dept)) {
                return i;
            }
        }
        return -1;
    }

    //得到某一行的具体数据
    public static String[] getRowData(int colLength, Sheet sheet, int row) {
        StringBuilder result = new StringBuilder();
        for (int b = 0; b < colLength; b++) {
            Cell cell = sheet.getRow(row).getCell(b);
            // 分别取出第 a行 b列的单元格数据
            // 设置单元格的类型是String类型
            String stringCellValue = null;
            if (cell != null) {
                // 获取单元格的数据
                cell.setCellType(CellType.STRING);
                stringCellValue = cell.getStringCellValue();
            }
            if (!"null".equals(stringCellValue) && Objects.nonNull(stringCellValue)) {
                result.append(stringCellValue).append(" @#`");
            }
        }

        return result.toString().split("@#`");
    }

    //分割指定格式的数据并返回需要的内容格式
    public static String splitStringFormat(String originalString) {
        // 定义正则表达式
        String regex;
        if (originalString.contains("(")) {
            regex = "(.*?)\\((.*?)\\)(.*)";
        } else if (originalString.contains("（")) {
            regex = "(.*?)\\（(.*?)\\）(.*)";
        } else {
            return originalString;
        }

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(originalString);

        // 如果匹配成功，则重新组合字符串
        if (matcher.matches()) {
            String part1 = matcher.group(1);
            String part2 = matcher.group(2);
            String part3 = matcher.group(3);

            // 将字符串组合成'XZ,XYZ'样式
            String resultString = part1 + part3 + "," + part1 + part2 + part3;
            return resultString;
        } else {
            // 如果不匹配，返回原始字符串
            return originalString;
        }
    }

    //解析分数分配
    public static Map<String, Double> extractTextAndNumbers(String inputText) {
        Map<String, Double> resultMap = new HashMap<>();
        // 使用正则表达式提取文字和数字
        Pattern pattern = Pattern.compile("([\\u4e00-\\u9fa5]+)(\\d+)");
        Matcher matcher = pattern.matcher(inputText);
        // 遍历匹配结果并放入Map集合
        while (matcher.find()) {
            String text = matcher.group(1); // 文字部分
            Double number = Double.parseDouble(matcher.group(2)); // 数字部分
            resultMap.put(text, number);
        }
        return resultMap;
    }

    private static double extractFloat(String input) {
        // 定义匹配数字模式的正则表达式
        String regex = "(\\d+\\.\\d+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        // 查找匹配的数字
        if (matcher.find()) {
            // 提取匹配的部分并转换为浮点数
            String match = matcher.group(1);
            return Double.parseDouble(match);
        } else {
            // 如果没有找到匹配的数字，可以根据实际情况进行处理，例如返回默认值或抛出异常
            System.out.println("未找到匹配的数字");
            return 0.0;
        }
    }

    //查询所有申请所处年份范围
    @Override
    public List<String> queryYears() {
        return gainApplyMapper.queryYears();
    }

    //导出具体年份信息
    @Override
    public void exportHis(String years, String types, HttpServletResponse response) throws IOException {
        errorContents = new HashMap<>();
        //读取模板文件中的头部信息
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        org.springframework.core.io.Resource[] resources = resourcePatternResolver.
                getResources("classpath*:" + File.separator + "static" +
                        File.separator + "file" + File.separator + "template.xls");
        org.springframework.core.io.Resource resource = resources[0];
        InputStream inputStream = null;
        inputStream = resource.getInputStream();

        Workbook workbook = WorkbookFactory.create(inputStream);
        // 根据传递过来的文件输入流创建一个workbook对象（对应Excel中的工作簿）
        // 创建完成，关闭输入流
        inputStream.close();
        int numberOfSheets = workbook.getNumberOfSheets();
        int a;
        fileClass = new HashMap<>();

        // 设置响应头，影响浏览器下载行为,下面繁琐的代码的目的是为了适应网页那端能够展示出用户保存考勤表到指定位置的框所需要改变的
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        // 使用URLEncoder对文件名进行编码，避免中文乱码
        response.setHeader("Content-disposition", "attachment;filename=" +
                URLEncoder.encode("科技成果申请历史信息表_" + years, String.valueOf(StandardCharsets.UTF_8)) + ".xls");
        ServletOutputStream out = null;
        try {
            //追加内容难以实现插入多个头部，后面的头部会覆盖前面的，所以采用多个sheet的方法实现多个考勤表的导出
            // 文件输出位置
            out = response.getOutputStream();

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
            // 指定文件，自定义列宽，注册样式
            ExcelWriter excelWriter = EasyExcel.write(out)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerWriteHandler(new HorizontalCellStyleStrategy(null, contentWriteCellStyle))
                    .registerWriteHandler(new EasyExcelCustomCellWriteHandler()).build();

            for (String type : types.split(",")) {
                //这里使用模板的头部，头部信息随着模板的变化而变化，方便后期维护
                //获取工作表对象，即第一个工作表 （工作簿里面有很多张工作表，这里取第一张工作表）
                Sheet sheet = workbook.getSheetAt(Integer.valueOf(type) - 1);
                //获取工作表第一行数据
                Row row = sheet.getRow(0);
                //获取工作表总列数的长度
                int colLength = row.getLastCellNum();
                begin = true;
                //初始化头部信息
                headTitle = Lists.newArrayList();
                String[] rowData = getRowData(1, sheet, 0);
                String[] rowData1 = getRowData(colLength, sheet, 1);

                for (String s : rowData1) {
                    headTitle.add(Lists.newArrayList(rowData[0], s));
                }
                List<List<Object>> contentBody = new ArrayList<>();
                AtomicInteger col = new AtomicInteger(1);
                //内容部分
                switch (type) {
                    case "1":
                        //纵向
                        List<DirectionHisPage> directionHisPages = directionApplyInfoMapper.queryPaperHisPage(-1l, -1l, "-1",
                                null, null, null, null, null,
                                null, null, null, null, years);
                        //去除尾部的核对信息
                        headTitle.remove(headTitle.size() - 1);
                        //判断是团队申请还是个人申请
                        headTitle.add(Lists.newArrayList(rowData[0], "具体分数分配信息"));
                        directionHisPages.forEach(directionHisPage -> {
                            String scoreFormart = scoreFormart(directionHisPage.getScoreInfo());
                            contentBody.add(Lists.newArrayList(
                                    col.getAndIncrement(),
                                    directionHisPage.getUserDept() != null ? directionHisPage.getUserDept() : "",
                                    directionHisPage.getSchool() != null ? directionHisPage.getSchool() : "湖南工学院",
                                    directionHisPage.getType() != null ? directionHisPage.getType() : "",
                                    directionHisPage.getSubjectCategory() != null ? directionHisPage.getSubjectCategory() : "",
                                    directionHisPage.getStartProjectYear() != null ? directionHisPage.getStartProjectYear() : "",
                                    directionHisPage.getBelongUnit() != null ? directionHisPage.getBelongUnit() : "",
                                    directionHisPage.getName() != null ? directionHisPage.getName() : "",
                                    directionHisPage.getDept() != null ? directionHisPage.getDept() : "",
                                    directionHisPage.getLevel() != null ? directionHisPage.getLevel().split(":")[0] : "",
                                    directionHisPage.getLevel() != null ? directionHisPage.getLevel().split(":")[1] : "",
                                    directionHisPage.getUsername() != null ? directionHisPage.getUsername() : "",
                                    directionHisPage.getDeclareApproveTime() != null ? directionHisPage.getDeclareApproveTime() : "",
                                    directionHisPage.getStartTime() != null ? directionHisPage.getStartTime() : "",
                                    directionHisPage.getPlanFinishTime() != null ? directionHisPage.getPlanFinishTime() : "",
                                    directionHisPage.getIdentifier() != null ? directionHisPage.getIdentifier() : "",
                                    directionHisPage.getTotalFund() != null ? directionHisPage.getTotalFund() : "",
                                    directionHisPage.getReceiptTotalFund() != null ? directionHisPage.getReceiptTotalFund() : "",
                                    directionHisPage.getBearUnit() != null ? directionHisPage.getBearUnit() : "",
                                    directionHisPage.getProjectStatus() != null ? directionHisPage.getProjectStatus() : "",
                                    directionHisPage.getCompleteTime() != null ? directionHisPage.getCompleteTime() : "",
                                    directionHisPage.getSubjectType() != null ? directionHisPage.getSubjectType() : "",
                                    directionHisPage.getNationalEconomyCategory() != null ? directionHisPage.getNationalEconomyCategory() : "",
                                    directionHisPage.getFile() != null ? directionHisPage.getFile() : "",
                                    directionHisPage.getStage() != null ? directionHisPage.getStage() : "",
                                    directionHisPage.getAccording() != null ? directionHisPage.getAccording() : "",
                                    Double.valueOf(scoreFormart.split(":")[0]),
                                    directionHisPage.getRemarks() != null ? directionHisPage.getRemarks() : "",
                                    "1".equals(directionHisPage.getTeam()) ? scoreFormart.split(":")[1] : null
                            ));
                        });
                        //先加入头部信息
                        fileClass.put("纵向科研", DirectionHead.class);
                        errorContents.put("纵向科研", contentBody);
                        break;
                    case "2":
                        //横向
                        List<TransverseHisPage> transverseHisPages = transverseApplyInfoMapper.queryPaperHisPage(-1l, -1l, "-1",
                                null, null, null, null, null,
                                null, null, null, null, years);
                        //判断是团队申请还是个人申请
                        headTitle.add(Lists.newArrayList(rowData[0], "具体分数分配信息"));
                        transverseHisPages.forEach(transverseHisPage -> {
                            String scoreFormart = scoreFormart(transverseHisPage.getScoreInfo());
                            contentBody.add(Lists.newArrayList(
                                    col.getAndIncrement(),
                                    transverseHisPage.getUserDept() != null ? transverseHisPage.getUserDept() : "",
                                    transverseHisPage.getSchool() != null ? transverseHisPage.getSchool() : "湖南工学院",
                                    transverseHisPage.getLevel() != null ? transverseHisPage.getLevel().split(":")[1] : "",
                                    transverseHisPage.getStartProjectYear() != null ? transverseHisPage.getStartProjectYear() : "",
                                    transverseHisPage.getSubjectCategory() != null ? transverseHisPage.getSubjectCategory() : "",
                                    transverseHisPage.getBelongUnit() != null ? transverseHisPage.getBelongUnit() : "",
                                    transverseHisPage.getName() != null ? transverseHisPage.getName() : "",
                                    transverseHisPage.getSourceUnit() != null ? transverseHisPage.getSourceUnit() : "",
                                    transverseHisPage.getBearUnit() != null ? transverseHisPage.getBearUnit() : "",
                                    transverseHisPage.getType() != null ? transverseHisPage.getType() : "",
                                    transverseHisPage.getUsername() != null ? transverseHisPage.getUsername() : "",
                                    transverseHisPage.getSignContractTime() != null ? transverseHisPage.getSignContractTime() : "",
                                    transverseHisPage.getStartTime() != null ? transverseHisPage.getStartTime() : "",
                                    transverseHisPage.getPlanFinishTime() != null ? transverseHisPage.getPlanFinishTime() : "",
                                    transverseHisPage.getIdentifier() != null ? transverseHisPage.getIdentifier() : "",
                                    transverseHisPage.getTotalFund() != null ? transverseHisPage.getTotalFund() : "",
                                    transverseHisPage.getMoney() != null ? transverseHisPage.getMoney() : "",
                                    transverseHisPage.getCard() != null ? transverseHisPage.getCard() : "",
                                    transverseHisPage.getProjectStatus() != null ? transverseHisPage.getProjectStatus() : "",
                                    transverseHisPage.getContractNumber() != null ? transverseHisPage.getContractNumber() : "",
                                    transverseHisPage.getCompleteTime() != null ? transverseHisPage.getCompleteTime() : "",
                                    transverseHisPage.getSubjectType() != null ? transverseHisPage.getSubjectType() : "",
                                    transverseHisPage.getNationalEconomyCategory() != null ? transverseHisPage.getNationalEconomyCategory() : "",
                                    transverseHisPage.getFirstSign() != null ? transverseHisPage.getFirstSign() : "",
                                    transverseHisPage.getAccording() != null ? transverseHisPage.getAccording() : "",
                                    Double.valueOf(scoreFormart.split(":")[0]),
                                    transverseHisPage.getAccording() != null ? transverseHisPage.getAccording() : "",
                                    "1".equals(transverseHisPage.getTeam()) ? scoreFormart.split(":")[1] : null
                            ));
                        });
                        fileClass.put("横向科研", TransctionHead.class);
                        errorContents.put("横向科研", contentBody);
                        break;
                    case "3":
                        //科技成果
                        List<AchievementHisPage> achievementHisPages = achievementApplyInfoMapper.queryPaperHisPage(-1l, -1l, "-1",
                                null, null, null, null, null,
                                null, null, null, null, years);
                        //判断是团队申请还是个人申请
                        headTitle.add(Lists.newArrayList(rowData[0], "具体分数分配信息"));
                        achievementHisPages.forEach(achievementHisPage -> {
                            String scoreFormart = scoreFormart(achievementHisPage.getScoreInfo());
                            contentBody.add(Lists.newArrayList(
                                    col.getAndIncrement(),
                                    achievementHisPage.getUsername() != null ? achievementHisPage.getUsername() : "",
                                    achievementHisPage.getContent() != null ? achievementHisPage.getContent() : "",
                                    achievementHisPage.getDept() != null ? achievementHisPage.getDept() : "",
                                    achievementHisPage.getName() != null ? achievementHisPage.getName() : "",
                                    achievementHisPage.getLevel() != null ? achievementHisPage.getLevel() : "",
                                    achievementHisPage.getAccording() != null ? achievementHisPage.getAccording() : "",
                                    Double.valueOf(scoreFormart.split(":")[0]),
                                    achievementHisPage.getUserDept() != null ? achievementHisPage.getUserDept() : "",
                                    achievementHisPage.getRemarks() != null ? achievementHisPage.getRemarks() : "",
                                    achievementHisPage.getGrade() != null ? achievementHisPage.getGrade() : "",
                                    achievementHisPage.getFirstSign() != null ? achievementHisPage.getFirstSign() : "",
                                    achievementHisPage.getSchoolOrder() != null ? achievementHisPage.getSchoolOrder() : "",
                                    achievementHisPage.getWorkersOrder() != null ? achievementHisPage.getWorkersOrder() : "",
                                    "1".equals(achievementHisPage.getTeam()) ? scoreFormart.split(":")[1] : null
                            ));
                        });
                        fileClass.put("科技成果", AchievementHead.class);
                        errorContents.put("科技成果", contentBody);
                        break;
                    case "4":
                        //学术论文
                        List<PaperHisPage> paperHisPages = paperApplyInfoMapper.queryPaperHisPage(-1l, -1l, "-1",
                                null, null, null, null, null,
                                null, null, null, null, years);
                        paperHisPages.forEach(paperHisPage -> {
                            contentBody.add(Lists.newArrayList(
                                    col.getAndIncrement(),
                                    paperHisPage.getUsername() != null ? paperHisPage.getUsername() : "",
                                    paperHisPage.getName() != null ? paperHisPage.getName() : "",
                                    paperHisPage.getPeriodicalName() != null ? paperHisPage.getPeriodicalName() : "",
                                    paperHisPage.getCnnum() != null ? paperHisPage.getCnnum() : "",
                                    paperHisPage.getOrder() != null ? paperHisPage.getOrder() : "",
                                    paperHisPage.getLevel() != null ? paperHisPage.getLevel() : "",
                                    paperHisPage.getAccording() != null ? paperHisPage.getAccording() : "",
                                    paperHisPage.getScore() != null ? paperHisPage.getScore() : "",
                                    paperHisPage.getUserDept() != null ? paperHisPage.getUserDept() : "",
                                    paperHisPage.getInstitute() != null ? paperHisPage.getInstitute() : "",
                                    paperHisPage.getRemarks() != null ? paperHisPage.getRemarks() : ""
                            ));
                        });
                        fileClass.put("学术论文", PaperHead.class);
                        errorContents.put("学术论文", contentBody);
                        break;
                    case "5":
                        //学术专著
                        List<BookHisPage> bookHisPages = bookApplyInfoMapper.queryPaperHisPage(-1l, -1l, "-1",
                                null, null, null, null, null,
                                null, null, null, null, years);
                        bookHisPages.forEach(bookHisPage -> {
                            contentBody.add(Lists.newArrayList(
                                    col.getAndIncrement(),
                                    bookHisPage.getUsername() != null ? bookHisPage.getUsername() : "",
                                    bookHisPage.getName() != null ? bookHisPage.getName() : "",
                                    bookHisPage.getPressName() != null ? bookHisPage.getPressName() : "",
                                    bookHisPage.getAcademicType() != null ? bookHisPage.getAcademicType() : "",
                                    bookHisPage.getWordsNum() != null ? bookHisPage.getWordsNum() : "",
                                    bookHisPage.getOrder() != null ? bookHisPage.getOrder() : "",
                                    bookHisPage.getAccording() != null ? bookHisPage.getAccording() : "",
                                    bookHisPage.getUserDept() != null ? bookHisPage.getUserDept() : "",
                                    bookHisPage.getScore() != null ? bookHisPage.getScore() : "",
                                    bookHisPage.getRemarks() != null ? bookHisPage.getRemarks() : ""
                            ));
                        });
                        fileClass.put("学术专著", BookHead.class);
                        errorContents.put("学术专著", contentBody);
                        break;
                    case "6":
                        //发明专利
                        List<InventHisPage> inventHisPages = inventApplyInfoMapper.queryPaperHisPage(-1l, -1l, "-1",
                                null, null, null, null, null,
                                null, null, null, null, years);
                        //判断是团队申请还是个人申请
                        headTitle.add(Lists.newArrayList(rowData[0], "具体分数分配信息"));
                        inventHisPages.forEach(inventHisPage -> {
                            String scoreFormart = scoreFormart(inventHisPage.getScoreInfo());
                            contentBody.add(Lists.newArrayList(
                                    col.getAndIncrement(),
                                    inventHisPage.getUserDept() != null ? inventHisPage.getUserDept() : "",
                                    inventHisPage.getSchool() != null ? inventHisPage.getSchool() : "湖南工学院",
                                    inventHisPage.getYear() != null ? inventHisPage.getYear() : year,
                                    inventHisPage.getName() != null ? inventHisPage.getName() : "",
                                    inventHisPage.getType() != null ? inventHisPage.getType() : "",
                                    inventHisPage.getApplyNumber() != null ? inventHisPage.getApplyNumber() : "",
                                    inventHisPage.getApplyTime() != null ? inventHisPage.getApplyTime() : "",
                                    inventHisPage.getLocationUnit() != null ? inventHisPage.getLocationUnit() : "",
                                    inventHisPage.getUsername() != null ? inventHisPage.getUsername() : "",
                                    inventHisPage.getAuthorization() != null ? inventHisPage.getAuthorization() : "",
                                    inventHisPage.getAuthorizeTime() != null ? inventHisPage.getAuthorizeTime() : "",
                                    inventHisPage.getPowerMan() != null ? inventHisPage.getPowerMan() : "",
                                    inventHisPage.getConcurType() != null ? inventHisPage.getConcurType() : "",
                                    inventHisPage.getSchoolSign() != null ? inventHisPage.getSchoolSign() : "",
                                    inventHisPage.getScope() != null ? inventHisPage.getScope() : "",
                                    inventHisPage.getPatentStatus() != null ? inventHisPage.getPatentStatus() : "",
                                    inventHisPage.getRemarks() != null ? inventHisPage.getRemarks() : "",
                                    inventHisPage.getStage() != null ? inventHisPage.getStage() : "",
                                    Double.valueOf(scoreFormart.split(":")[0]),
                                    inventHisPage.getMoney() != null ? inventHisPage.getMoney() : "",
                                    inventHisPage.getAccording() != null ? inventHisPage.getAccording() : "",
                                    "1".equals(inventHisPage.getTeam()) ? scoreFormart.split(":")[1] : null
                            ));
                        });
                        fileClass.put("发明专利", InventHead.class);
                        errorContents.put("发明专利", contentBody);
                        break;
                    case "7":
                        //科研平台
                        List<ScientificHisPage> scientificHisPages = scientificApplyInfoMapper.queryPaperHisPage(-1l, -1l, "-1",
                                null, null, null, null, null,
                                null, null, null, null, years);
                        //判断是团队申请还是个人申请
                        scientificHisPages.forEach(scientificHisPage -> {
                            String scoreFormart = scoreFormart(scientificHisPage.getScoreInfo());
                            contentBody.add(Lists.newArrayList(
                                    col.getAndIncrement(),
                                    scientificHisPage.getUsername() != null ? scientificHisPage.getUsername() : "",
                                    scientificHisPage.getLevel() != null ? scientificHisPage.getLevel() : "",
                                    scientificHisPage.getName() != null ? scientificHisPage.getName() : "",
                                    scientificHisPage.getDept() != null ? scientificHisPage.getDept() : "",
                                    scientificHisPage.getAccording() != null ? scientificHisPage.getAccording() : "",
                                    Double.valueOf(scoreFormart.split(":")[0]),
                                    scientificHisPage.getUserDept() != null ? scientificHisPage.getUserDept() : "",
                                    "1".equals(scientificHisPage.getTeam()) ? scoreFormart.split(":")[1] : null
                            ));
                        });
                        fileClass.put("科研平台", ScienceHead.class);
                        errorContents.put("科研平台", contentBody);
                        break;
                    case "8":
                        //科技荣誉
                        List<HonorHisPage> honorHisPages = honorApplyInfoMapper.queryPaperHisPage(-1l, -1l, "-2",
                                null, null, null, null, null,
                                null, null, null, null, years);
                        honorHisPages.forEach(honorHisPage -> {
                            contentBody.add(Lists.newArrayList(
                                    col.getAndIncrement(),
                                    honorHisPage.getUsername() != null ? honorHisPage.getUsername() : "",
                                    honorHisPage.getType() != null ? honorHisPage.getType() : "",
                                    honorHisPage.getDept() != null ? honorHisPage.getDept() : "",
                                    honorHisPage.getName() != null ? honorHisPage.getName() : "",
                                    honorHisPage.getAccording() != null ? honorHisPage.getAccording() : "",
                                    honorHisPage.getScore() != null ? honorHisPage.getScore() : "",
                                    honorHisPage.getUserDept() != null ? honorHisPage.getUserDept() : ""
                            ));
                        });
                        fileClass.put("科技荣誉", HonourHead.class);
                        errorContents.put("科技荣誉", contentBody);
                        break;
                }
            }
            //sheet个数
            for (String s : fileClass.keySet()) {
                //获取sheet0对象
                WriteSheet mainSheet = EasyExcel.writerSheet(sheetNum++, s).head(fileClass.get(s)).build();
                excelWriter.write(errorContents.get(s), mainSheet);
            }
            //这一步很关键，不然文件会损坏
            excelWriter.finish();
        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException("0", "系统异常");
        }

    }


    //处理查询出来的分数信息
    public String scoreFormart(String scoreInfos) {
        if (scoreInfos == null) {
            return "0: ";
        }
        double allScore = 0d;
        String scoreContents = "";
        for (String scoreInfo : scoreInfos.split(",")) {
            double lastScores = Double.valueOf(scoreInfo.split("::")[1]) + Double.valueOf(scoreInfo.split("::")[2]);
            allScore += (lastScores);
            scoreContents += (scoreInfo.split("::")[0] + (lastScores));
        }
        return allScore + ":" + scoreContents;
    }
}