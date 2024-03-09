package com.yc.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.Applylog;
import com.yc.apply.entity.BookApplyInfo;
import com.yc.apply.entity.ScoreApplyInfo;
import com.yc.apply.mapper.ApplylogMapper;
import com.yc.apply.mapper.BookApplyInfoMapper;
import com.yc.apply.mapper.GainApplyMapper;
import com.yc.apply.mapper.ScoreApplyInfoMapper;
import com.yc.apply.scoring.TechResultsContext;
import com.yc.apply.service.BookApplyInfoService;
import com.yc.common.utils.UploadFileUtil;
import com.yc.entity.ProveFile;
import com.yc.service.ProveFileService;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.apply.BookApplyVo;
import com.yc.vo.apply.BookGainApplyVo;
import com.yc.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学术专著申请详情;(book_apply_info)表服务实现类
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Slf4j
@Service
public class BookApplyInfoServiceImpl implements BookApplyInfoService {
    @Autowired
    private BookApplyInfoMapper bookApplyInfoMapper;
    @Autowired
    private GainApplyMapper gainApplyMapper;
    @Autowired
    private ProveFileService proveFileService;
    @Autowired
    private ApplylogMapper applylogMapper;
    @Autowired
    private ScoreApplyInfoMapper scoreApplyInfoMapper;
    @Resource
    private TechResultsContext techResultsContext;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result commit(BookApplyVo bookApplyVo, List<MultipartFile> file, HttpServletRequest request) {
        // 著作名称
        String xmname = bookApplyVo.getXmname();
        // 著作类型
        String xmtype = bookApplyVo.getXmtype();
        // 作者排序
        String xmorder = bookApplyVo.getXmorder();
        // 著作字数
        String xmwordnumber = bookApplyVo.getXmwordnumber();
        // 出版单位
        String xmdepartment = bookApplyVo.getXmdepartment();
        // 申请人
        String xmpeople = bookApplyVo.getXmpeople();
        // 申请备注
        String xmremark = bookApplyVo.getXmremark();
        // 账号
        String username = bookApplyVo.getUsername();

        if (xmname.equals("") || xmtype.equals("") || xmorder.equals("") || xmwordnumber.equals("") ||
                xmdepartment.equals("") || xmpeople.equals("") || username.equals("")) {
            return Result.error(0, "缺少必要信息提交失败!");
        } else {
            /* 以下为申请信息处理 */
            // 调用申请实体类
            ApplyVo applyVo = new ApplyVo();
            // 申请类型 5 学术专著
            applyVo.setTechResults(5);
            // 定义用于计算的数据集合
            Map<String, Object> datalist = new HashMap<>();
            // 存放数据到计算数据集合
            datalist.put("username", username);
            datalist.put("xmpeople", xmpeople);
            datalist.put("xmtype", xmtype);
            datalist.put("xmorder", xmorder);
            datalist.put("xmwordnumber", xmwordnumber);
            // 存计算条件
            applyVo.setUserdata(datalist);
            // 计算结果
            Result result = techResultsContext.calcPrice(applyVo);
            // 计算分数与依据
            Map<String, Object> scoreandyj = (Map<String, Object>) result.getData();
            // 分数(科技分
            int score = (int) scoreandyj.get("score");
            // 可换钱分数
            int moneyscore = (int) scoreandyj.get("moneyscore");
            // 依据
            String yj = (String) scoreandyj.get("yj");
            String jbid = String.valueOf((int) scoreandyj.get("id"));// 级别id
            // 添加数据到数据库
            Result result1 =new Result();
            try {
                result1 = addapplication(xmname, xmtype, xmorder, xmwordnumber,file,request,
                        xmdepartment, xmpeople, xmremark, username, score, yj, jbid,moneyscore);
            } catch (Exception e) {
                result1 = Result.error("添加失败!");
                log.error(" commit ",e);
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw  new RuntimeException(" commit ",e);
            }finally {
                return result1;
            }
        }
    }


    /**
     * 添加专著申请
     * @param xmname
     * @param xmtype
     * @param xmorder
     * @param xmwordnumber
     * @param xmdepartment
     * @param xmpeople
     * @param xmremark
     * @param username
     * @param score
     * @param yj
     * @param jbid
     * @return
     */
    public Result addapplication(String xmname, String xmtype, String xmorder, String xmwordnumber,List<MultipartFile> file, HttpServletRequest request, String xmdepartment,
                                 String xmpeople, String xmremark, String username, int score, String yj, String jbid,int moneyscore
    ) {
        // 装填gain_apply表的参数
        BookGainApplyVo bookGainApplyVo = new BookGainApplyVo();
        bookGainApplyVo.setSid(gainApplyMapper.selectsid(username));
        bookGainApplyVo.setTrtid("5");
        String Childid = bookApplyInfoMapper.selectbsid(jbid);
        if (jbid.equals("-1")){
            Childid = "4";
        }
        bookGainApplyVo.setChildid(Childid);
        bookGainApplyVo.setAccording(yj);
        bookGainApplyVo.setRemarks(xmremark);
        // 存gain_apply表
        gainApplyMapper.addbookgainapply(bookGainApplyVo);
        Integer gaid = bookGainApplyVo.getGaid();
        ProveFile proveFile = new ProveFile();
        if (file!=null){
            // 上传到本地的文件夹名（自己定义
            // 调用工具类将文件保存到本地 并获取返回值（Map类型 数据是文件信息
            Map<String, UploadFileUtil.UploadFile> stringUploadFileMap
                    = UploadFileUtil.uploadFiles(request, file);
            // 获取文件名（用于查找map集合中的新地址
            for (MultipartFile multipartFile : file) {
                String originalFilename = multipartFile.getOriginalFilename();
                String newFilePath = stringUploadFileMap.get(originalFilename).getNewFileUrl();
                proveFile = new ProveFile(1, newFilePath, originalFilename, 1);
                // 存文件表
                proveFile.setUseid(gaid);
                proveFileService.insert(proveFile);
            }
        }
        // 存学术专著细表
        bookApplyInfoMapper.insertbookapplication(xmname, xmtype, xmdepartment, xmorder, xmwordnumber, gaid, yj, gainApplyMapper.selectsid(username));
        // 存申请审核表
        applylogMapper.insert(new Applylog(0,gaid, LocalDateTime.now()));
        // 装填分数表的参数
        ScoreApplyInfo scoreApplyInfo = new ScoreApplyInfo();
        scoreApplyInfo.setCanScore((double) moneyscore);
        if (moneyscore != 0){
            scoreApplyInfo.setCannotScore((double) 0);
        }else {
            scoreApplyInfo.setCannotScore((double) score);
        }
        scoreApplyInfo.setGaid(gaid);

        scoreApplyInfo.setSysid(gainApplyMapper.selectsid(username));
        // 存分数表
        scoreApplyInfoMapper.BookApplyScore(scoreApplyInfo);

        return Result.success("提交申请成功!");
    }

    /**
     * 查询用于显示的著作类型与相应的分数
     *
     * @return
     */
    public List<Map<String, Object>> selectlxorfs() {
        return bookApplyInfoMapper.selectlxorfs();
    }

    /**
     * 通过ID查询单条数据
     *
     * @param baiid 主键
     * @return 实例对象
     */
    @Override
    public BookApplyInfo queryById(Integer baiid) {
        return bookApplyInfoMapper.selectById(baiid);
    }

    /**
     * 分页查询
     *
     * @param bookApplyInfo 筛选条件
     * @param current       当前页码
     * @param size          每页大小
     * @return
     */
    @Override
    public Page<BookApplyInfo> paginQuery(BookApplyInfo bookApplyInfo, long current, long size) {
        //1. 构建动态查询条件
        LambdaQueryWrapper<BookApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(bookApplyInfo.getName())) {
            queryWrapper.eq(BookApplyInfo::getName, bookApplyInfo.getName());
        }
        if (StrUtil.isNotBlank(bookApplyInfo.getPressName())) {
            queryWrapper.eq(BookApplyInfo::getPressName, bookApplyInfo.getPressName());
        }
        if (StrUtil.isNotBlank(bookApplyInfo.getAcademicType())) {
            queryWrapper.eq(BookApplyInfo::getAcademicType, bookApplyInfo.getAcademicType());
        }
        if (StrUtil.isNotBlank(bookApplyInfo.getWordsNum())) {
            queryWrapper.eq(BookApplyInfo::getWordsNum, bookApplyInfo.getWordsNum());
        }
        if (StrUtil.isNotBlank(bookApplyInfo.getOrder())) {
            queryWrapper.eq(BookApplyInfo::getOrder, bookApplyInfo.getOrder());
        }
        if (StrUtil.isNotBlank(bookApplyInfo.getAccording())) {
            queryWrapper.eq(BookApplyInfo::getAccording, bookApplyInfo.getAccording());
        }
        //2. 执行分页查询
        Page<BookApplyInfo> pagin = new Page<>(current, size, true);
        IPage<BookApplyInfo> selectResult = bookApplyInfoMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param bookApplyInfo 实例对象
     * @return 实例对象
     */
    @Override
    public BookApplyInfo insert(BookApplyInfo bookApplyInfo) {
        bookApplyInfoMapper.insert(bookApplyInfo);
        return bookApplyInfo;
    }

    /**
     * 更新数据
     *
     * @param bookApplyInfo 实例对象
     * @return 实例对象
     */
    @Override
    public BookApplyInfo update(BookApplyInfo bookApplyInfo) {
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<BookApplyInfo> chainWrapper = new LambdaUpdateChainWrapper<BookApplyInfo>(bookApplyInfoMapper);
        if (StrUtil.isNotBlank(bookApplyInfo.getName())) {
            chainWrapper.eq(BookApplyInfo::getName, bookApplyInfo.getName());
        }
        if (StrUtil.isNotBlank(bookApplyInfo.getPressName())) {
            chainWrapper.eq(BookApplyInfo::getPressName, bookApplyInfo.getPressName());
        }
        if (StrUtil.isNotBlank(bookApplyInfo.getAcademicType())) {
            chainWrapper.eq(BookApplyInfo::getAcademicType, bookApplyInfo.getAcademicType());
        }
        if (StrUtil.isNotBlank(bookApplyInfo.getWordsNum())) {
            chainWrapper.eq(BookApplyInfo::getWordsNum, bookApplyInfo.getWordsNum());
        }
        if (StrUtil.isNotBlank(bookApplyInfo.getOrder())) {
            chainWrapper.eq(BookApplyInfo::getOrder, bookApplyInfo.getOrder());
        }
        if (StrUtil.isNotBlank(bookApplyInfo.getAccording())) {
            chainWrapper.eq(BookApplyInfo::getAccording, bookApplyInfo.getAccording());
        }
        //2. 设置主键，并更新
        chainWrapper.set(BookApplyInfo::getBaiid, bookApplyInfo.getBaiid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(bookApplyInfo.getBaiid());
        } else {
            return bookApplyInfo;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param baiid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer baiid) {
        int total = bookApplyInfoMapper.deleteById(baiid);
        return total > 0;
    }
}