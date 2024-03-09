package com.yc.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.Applylog;
import com.yc.apply.entity.PaperApplyInfo;
import com.yc.apply.entity.ScoreApplyInfo;
import com.yc.apply.mapper.ApplylogMapper;
import com.yc.apply.mapper.GainApplyMapper;
import com.yc.apply.mapper.PaperApplyInfoMapper;
import com.yc.apply.mapper.ScoreApplyInfoMapper;
import com.yc.apply.scoring.TechResultsContext;
import com.yc.apply.service.PaperApplyInfoService;
import com.yc.common.utils.UploadFileUtil;
import com.yc.entity.ProveFile;
import com.yc.service.ProveFileService;
import com.yc.vo.*;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.apply.PaperApplyVo;
import com.yc.vo.apply.PaperGainApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 论文申请详情;(paper_apply_info)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class PaperApplyInfoServiceImpl implements PaperApplyInfoService{
    @Autowired
    private PaperApplyInfoMapper paperApplyInfoMapper;
    @Resource
    private TechResultsContext techResultsContext;
    @Autowired
    private GainApplyMapper gainApplyMapper;
    @Autowired
    private ProveFileService proveFileService;
    @Autowired
    private ApplylogMapper applylogMapper;
    @Autowired
    private ScoreApplyInfoMapper scoreApplyInfoMapper;

    /**
     *  提交申请
     * @param paperApplyVo
     * @param file
     * @param request
     * @return
     */
    @Override
    @Transactional
    public Result commit(PaperApplyVo paperApplyVo, List<MultipartFile> file, HttpServletRequest request) {
        /* 处理传来的参数 */
        // 论文名称
        String xmname = paperApplyVo.getXmname();
        // 发表期刊名
        String periodical = paperApplyVo.getPeriodical();
        // 期号
        String xmwno = paperApplyVo.getXmwno();
        // 研究院
        String xmyjname = paperApplyVo.getXmyjname();
        // 作者排序
        String xmpeopleorder = paperApplyVo.getXmpeopleorder();
        // 项目备注
        String xmremark = paperApplyVo.getXmremark();
        // 学校署名
        String xmschoolorder = paperApplyVo.getXmschoolorder();
        // 用于显示的项目附则
        String showxmother = paperApplyVo.getShowxmother();
        // 选择的类别(存的是leid)
        String xmlb = paperApplyVo.getXmlb();
        // 申请人id
        String userid = paperApplyVo.getUserid();
        System.out.println(xmlb+xmname+periodical+xmwno+xmyjname+xmpeopleorder+xmremark+xmschoolorder+showxmother+userid);
        /* 判断必要信息是否填写 */
        if (xmname.equals("") || periodical.equals("") || xmwno.equals("") || xmpeopleorder.equals("")
                || userid.equals("") || xmschoolorder.equals("") || xmlb.equals("")) {
            return Result.error(0, "缺少必要信息提交失败!");
        }else {
            /* 以下为申请信息处理 */
            // 调用申请实体类
            ApplyVo applyVo = new ApplyVo();
            // 申请类型 4 学术论文
            applyVo.setTechResults(4);
            // 定义用于计算的数据集合
            Map<String, Object> datalist = new HashMap<>();
            // 存放数据到计算数据集合
            datalist.put("xmpeopleorder", xmpeopleorder);
            datalist.put("xmschoolorder", xmschoolorder);
            datalist.put("xmlb", xmlb);
            datalist.put("showxmother", showxmother);
            datalist.put("userid", userid);
            // 存计算条件
            applyVo.setUserdata(datalist);
            // 计算结果
            Result result = techResultsContext.calcPrice(applyVo);
            Map<String,Object> ScoreAndCid = (Map<String, Object>) result.getData();
            // 最终可换钱分数
            Double score = 0.0;
            if (ScoreAndCid.get("score") instanceof Integer) {
                score = ((Integer) ScoreAndCid.get("score")).doubleValue();
            } else if (ScoreAndCid.get("score") instanceof Double) {
                score = (Double) ScoreAndCid.get("score");
            }
            // 最终不可换钱分数
            Double nocashscore = 0.0;
            if (ScoreAndCid.get("nocashscore") instanceof Integer) {
                nocashscore = ((Integer) ScoreAndCid.get("nocashscore")).doubleValue();
            } else if (ScoreAndCid.get("nocashscore") instanceof Double) {
                nocashscore = (Double) ScoreAndCid.get("nocashscore");
            }
            // childid 类别详细id
            String childid = (String) ScoreAndCid.get("cid");
            // according 计算依据
            String according = result.getMsg();
            System.out.println("可换钱分数为"+score);
            System.out.println("不可换钱分数为"+nocashscore);
            System.out.println("cid:"+childid);
            System.out.println("计算依据:"+according);
            /* 以下为主表Gain_apply 数据处理 */
            PaperGainApplyVo paperGainApplyVo = new PaperGainApplyVo();
            paperGainApplyVo.setSid(userid);
            paperGainApplyVo.setTrtid("4");
            paperGainApplyVo.setChildid(childid);
            paperGainApplyVo.setAccording(according);
            paperGainApplyVo.setRemarks(xmremark);
            /* 存gain_apply表 */
            gainApplyMapper.addpapergainapply(paperGainApplyVo);
            // 返回的主键gaid
            Integer gaid = paperGainApplyVo.getGaid();
            /* 以下处理文件数据 */
            ProveFile proveFile = new ProveFile();
            // 如果存在文件
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
                    /* 存文件表 */
                    proveFile.setUseid(gaid);
                    proveFileService.insert(proveFile);
                }
            }
            // 处理作者排序
            if (xmpeopleorder.equals("1")){
                xmpeopleorder = "第一作者";
            }else if (xmpeopleorder.equals("0")){
                xmpeopleorder = "通讯作者";
            }else if (xmpeopleorder.equals("2")){
                xmpeopleorder = "指导学生";
            }
            /* 存学术论文申请表paper_apply */
            paperApplyInfoMapper.addpaperapply(xmname,periodical,xmwno,xmpeopleorder,xmyjname,gaid,according,Integer.valueOf(userid));
            /* 存申请审核表apply_log */
            applylogMapper.insert(new Applylog(0,gaid, LocalDateTime.now()));
            /* 处理分数表的数据 */
            ScoreApplyInfo scoreApplyInfo = new ScoreApplyInfo();
            scoreApplyInfo.setCanScore(score);
            scoreApplyInfo.setCannotScore(nocashscore);
            scoreApplyInfo.setGaid(gaid);
            scoreApplyInfo.setSysid(userid);
            /* 存分数表 */
            scoreApplyInfoMapper.BookApplyScore(scoreApplyInfo);
            return Result.success("提交申请成功!");
        }

    }


    /**
     * 查询用于显示的论文类型与相应的分数
     *
     * @return
     */
    public List<Map<String, Object>> selectlxorfs() {
        return paperApplyInfoMapper.selectlxorfs();
    }

    /**
     * 查询项目细则
     *
     * @return
     */
    public List<Map<String, Object>> selectfz() {
        return paperApplyInfoMapper.selectfz();
    }

    /** 
     * 通过ID查询单条数据 
     *
     * @param paiid 主键
     * @return 实例对象
     */
    @Override
    public PaperApplyInfo queryById(Integer paiid){
        return paperApplyInfoMapper.selectById(paiid);
    }
    
    /**
     * 分页查询
     *
     * @param paperApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @Override
    public Page<PaperApplyInfo> paginQuery(PaperApplyInfo paperApplyInfo, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<PaperApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(paperApplyInfo.getName())){
            queryWrapper.eq(PaperApplyInfo::getName, paperApplyInfo.getName());
        }
        if(StrUtil.isNotBlank(paperApplyInfo.getPeriodicalName())){
            queryWrapper.eq(PaperApplyInfo::getPeriodicalName, paperApplyInfo.getPeriodicalName());
        }
        if(StrUtil.isNotBlank(paperApplyInfo.getCnnum())){
            queryWrapper.eq(PaperApplyInfo::getCnnum, paperApplyInfo.getCnnum());
        }
        if(StrUtil.isNotBlank(paperApplyInfo.getOrder())){
            queryWrapper.eq(PaperApplyInfo::getOrder, paperApplyInfo.getOrder());
        }
        if(StrUtil.isNotBlank(paperApplyInfo.getInstitute())){
            queryWrapper.eq(PaperApplyInfo::getInstitute, paperApplyInfo.getInstitute());
        }
        if(StrUtil.isNotBlank(paperApplyInfo.getAccording())){
            queryWrapper.eq(PaperApplyInfo::getAccording, paperApplyInfo.getAccording());
        }
        //2. 执行分页查询
        Page<PaperApplyInfo> pagin = new Page<>(current , size , true);
        IPage<PaperApplyInfo> selectResult = paperApplyInfoMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param paperApplyInfo 实例对象
     * @return 实例对象
     */
    @Override
    public PaperApplyInfo insert(PaperApplyInfo paperApplyInfo){
        paperApplyInfoMapper.insert(paperApplyInfo);
        return paperApplyInfo;
    }
    
    /** 
     * 更新数据
     *
     * @param paperApplyInfo 实例对象
     * @return 实例对象
     */
    @Override
    public PaperApplyInfo update(PaperApplyInfo paperApplyInfo){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<PaperApplyInfo> chainWrapper = new LambdaUpdateChainWrapper<PaperApplyInfo>(paperApplyInfoMapper);
        if(StrUtil.isNotBlank(paperApplyInfo.getName())){
            chainWrapper.eq(PaperApplyInfo::getName, paperApplyInfo.getName());
        }
        if(StrUtil.isNotBlank(paperApplyInfo.getPeriodicalName())){
            chainWrapper.eq(PaperApplyInfo::getPeriodicalName, paperApplyInfo.getPeriodicalName());
        }
        if(StrUtil.isNotBlank(paperApplyInfo.getCnnum())){
            chainWrapper.eq(PaperApplyInfo::getCnnum, paperApplyInfo.getCnnum());
        }
        if(StrUtil.isNotBlank(paperApplyInfo.getOrder())){
            chainWrapper.eq(PaperApplyInfo::getOrder, paperApplyInfo.getOrder());
        }
        if(StrUtil.isNotBlank(paperApplyInfo.getInstitute())){
            chainWrapper.eq(PaperApplyInfo::getInstitute, paperApplyInfo.getInstitute());
        }
        if(StrUtil.isNotBlank(paperApplyInfo.getAccording())){
            chainWrapper.eq(PaperApplyInfo::getAccording, paperApplyInfo.getAccording());
        }
        //2. 设置主键，并更新
        chainWrapper.set(PaperApplyInfo::getPaiid, paperApplyInfo.getPaiid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(paperApplyInfo.getPaiid());
        }else{
            return paperApplyInfo;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param paiid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer paiid){
        int total = paperApplyInfoMapper.deleteById(paiid);
        return total > 0;
    }
}