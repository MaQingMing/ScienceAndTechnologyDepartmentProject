package com.yc.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.Applylog;
import com.yc.apply.entity.GainApply;
import com.yc.apply.entity.ScientificApplyInfo;
import com.yc.apply.entity.ScoreApplyInfo;
import com.yc.apply.mapper.ApplylogMapper;
import com.yc.apply.mapper.GainApplyMapper;
import com.yc.apply.mapper.ScientificApplyInfoMapper;
import com.yc.apply.mapper.ScoreApplyInfoMapper;
import com.yc.apply.scoring.techresults.ScientificScoringHandler;
import com.yc.apply.service.ScientificApplyInfoService;
import com.yc.common.utils.UploadFileUtil;
import com.yc.entity.ProveFile;
import com.yc.mapper.RecordMapper;
import com.yc.service.ProveFileService;
import com.yc.vo.Result;
import com.yc.vo.apply.AddScientificApplyInfoVo;
import com.yc.vo.apply.ApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 科技基地,学科建设申请详情;(scientific_apply_info)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class ScientificApplyInfoServiceImpl implements ScientificApplyInfoService{
    @Autowired
    private ScientificApplyInfoMapper scientificApplyInfoMapper;
    @Autowired
    private GainApplyMapper gainApplyMapper;
    @Autowired
    private ProveFileService proveFileService;
    @Autowired
    private ApplylogMapper applylogMapper;
    @Autowired
    private ScoreApplyInfoMapper scoreApplyInfoMapper;
    @Autowired
    private RecordMapper recordMapper;
    @Resource
    private ScientificScoringHandler scientificScoringHandler;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param saiid 主键
     * @return 实例对象
     */
    @Override
    public ScientificApplyInfo queryById(Integer saiid){
        return scientificApplyInfoMapper.selectById(saiid);
    }
    
    /**
     * 分页查询
     *
     * @param scientificApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @Override
    public Page<ScientificApplyInfo> paginQuery(ScientificApplyInfo scientificApplyInfo, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<ScientificApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(scientificApplyInfo.getType())){
            queryWrapper.eq(ScientificApplyInfo::getType, scientificApplyInfo.getType());
        }
        if(StrUtil.isNotBlank(scientificApplyInfo.getName())){
            queryWrapper.eq(ScientificApplyInfo::getName, scientificApplyInfo.getName());
        }
        if(StrUtil.isNotBlank(scientificApplyInfo.getDept())){
            queryWrapper.eq(ScientificApplyInfo::getDept, scientificApplyInfo.getDept());
        }
        if(StrUtil.isNotBlank(scientificApplyInfo.getAccording())){
            queryWrapper.eq(ScientificApplyInfo::getAccording, scientificApplyInfo.getAccording());
        }
        //2. 执行分页查询
        Page<ScientificApplyInfo> pagin = new Page<>(current , size , true);
        IPage<ScientificApplyInfo> selectResult = scientificApplyInfoMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param scientificApplyInfo 实例对象
     * @return 实例对象
     */
    @Override
    public ScientificApplyInfo insert(ScientificApplyInfo scientificApplyInfo){
        scientificApplyInfoMapper.insert(scientificApplyInfo);
        return scientificApplyInfo;
    }
    
    /** 
     * 更新数据
     *
     * @param scientificApplyInfo 实例对象
     * @return 实例对象
     */
    @Override
    public ScientificApplyInfo update(ScientificApplyInfo scientificApplyInfo){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<ScientificApplyInfo> chainWrapper = new LambdaUpdateChainWrapper<ScientificApplyInfo>(scientificApplyInfoMapper);
        if(StrUtil.isNotBlank(scientificApplyInfo.getType())){
            chainWrapper.eq(ScientificApplyInfo::getType, scientificApplyInfo.getType());
        }
        if(StrUtil.isNotBlank(scientificApplyInfo.getName())){
            chainWrapper.eq(ScientificApplyInfo::getName, scientificApplyInfo.getName());
        }
        if(StrUtil.isNotBlank(scientificApplyInfo.getDept())){
            chainWrapper.eq(ScientificApplyInfo::getDept, scientificApplyInfo.getDept());
        }
        if(StrUtil.isNotBlank(scientificApplyInfo.getAccording())){
            chainWrapper.eq(ScientificApplyInfo::getAccording, scientificApplyInfo.getAccording());
        }
        //2. 设置主键，并更新
        chainWrapper.set(ScientificApplyInfo::getSaiid, scientificApplyInfo.getSaiid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(scientificApplyInfo.getSaiid());
        }else{
            return scientificApplyInfo;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param saiid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer saiid){
        int total = scientificApplyInfoMapper.deleteById(saiid);
        return total > 0;
    }

    /**
     * 查询个人的所有备案
     * @param username
     * @return
     */
    @Override
    public List<Map<String, Object>> queryRecords(String username) {
        List<Map<String, Object>> records = scientificApplyInfoMapper.queryRecords(username);
        return records;
    }

    /**
     * 查询科研平台下的子项目类型
     * @return
     */
    @Override
    public List<Map<String, Object>> queryLname() {
        List<Map<String, Object>> maps = scientificApplyInfoMapper.queryLname();
        return maps;
    }

    /**
     * 添加平台申请
     * @param addScientificApplyInfoVo
     * @param files
     * @param request
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public int addScientificApply(AddScientificApplyInfoVo addScientificApplyInfoVo, List<MultipartFile> files, HttpServletRequest request) throws Exception {
        //批准单位
        String department = addScientificApplyInfoVo.getDepartment();
        //项目名
        String lname = addScientificApplyInfoVo.getLname();
        //备注
        String remark = addScientificApplyInfoVo.getRemark();
        //备案id
        Integer rid = addScientificApplyInfoVo.getRecordId();
        // 验收，立项
        String xmlory = addScientificApplyInfoVo.getXmlory();
        //备案名
        String name = addScientificApplyInfoVo.getName();
        //申请人id
        Integer UserId = addScientificApplyInfoVo.getId();
        //项目leid
        Integer leid = addScientificApplyInfoVo.getLeid();
        String date = addScientificApplyInfoVo.getDate();
        Integer cash = addScientificApplyInfoVo.getCash();
        Double score = 0.0;
        //姓名，id 分数 ； 姓名，账号 分数 ；
        String xmpeople = addScientificApplyInfoVo.getXmpeople();
        String[] split = xmpeople.split(";");
        //ids = [2, 10]
        List<String> ids =new ArrayList<>();
        if (rid!=null){
            int byRid = recordMapper.queryCousumeByRid(rid);
            if (byRid!=0){
                return 0;
            }
        }
        for (int i = 0; i < split.length; i++) {
            String[] split1 = split[i].split(",");
            //账号 分数
            String[] split2 = split1[1].split("::");
            score+=Double.parseDouble(split2[1]);
            ids.add(split2[0]);
        }
        //String id = String.join(",", ids);
        //计分依据
        String according= lname+xmlory+score;
        GainApply gainApply = new GainApply();
        gainApply.setAccording(according);
        gainApply.setRecordid(rid);
        if(leid!=-1){
            gainApply.setChildid(String.valueOf(leid));
        }
        gainApply.setRemarks(remark);
        gainApply.setScore(String.valueOf(score));
        gainApply.setSid(String.valueOf(UserId));
        gainApply.setStatus(String.valueOf(0));
        if (ids.size()==1){
            gainApply.setTeam(String.valueOf(0));
        }else {
            gainApply.setTeam(String.valueOf(1));
        }
        gainApply.setTrtid(String.valueOf(7));
        gainApply.setDate(date);
        //添加成功返回的gaid
        gainApplyMapper.insert(gainApply);
        Integer gaid = gainApply.getGaid();
        for (int i = 0; i < split.length; i++) {
            ScoreApplyInfo scoreApplyInfo = new ScoreApplyInfo();
            String[] split1 = split[i].split(",");
            //账号 分数
            String[] split2 = split1[1].split("::");
            if (split2[0].equals(String.valueOf(UserId))){
                scoreApplyInfo.setHost(1);
            }else {
                scoreApplyInfo.setHost(0);
            }
            if (cash!=1){
                scoreApplyInfo.setCannotScore(Double.parseDouble(split2[1]));
            }else {
                scoreApplyInfo.setCanScore(Double.parseDouble(split2[1]));
            }
            scoreApplyInfo.setSysid(split2[0]);
            scoreApplyInfo.setGaid(gaid);
            scoreApplyInfoMapper.addScoreInfo(scoreApplyInfo);
        }
        Applylog applylog = new Applylog();
        applylog.setStatus(0);
        applylog.setApplyid(gaid);
        applylogMapper.insert(applylog);
        ScientificApplyInfo scientificApplyInfo = new ScientificApplyInfo();
        scientificApplyInfo.setType(lname);
        scientificApplyInfo.setName(name);
        scientificApplyInfo.setDept(department);
        scientificApplyInfo.setGaid(gaid);
        scientificApplyInfoMapper.insert(scientificApplyInfo);
        recordMapper.updateConsume(gaid,rid);
        if (files != null && !files.isEmpty()){
            Map<String, UploadFileUtil.UploadFile> stringUploadFileMap
                    = UploadFileUtil.uploadFiles(request, files);
            for (MultipartFile multipartFile : files) {
                String filename = multipartFile.getOriginalFilename();
                String newFileUrl = stringUploadFileMap.get(filename).getNewFileUrl();
                ProveFile proveFile = new ProveFile(1, newFileUrl, filename, gaid);
                proveFileService.insert(proveFile);
            }
        }
        return 1;
    }

    /**
     * 细则的处理
     * @return
     */
    @Override
    public Result queryRoles() {
        List<Map<String, Object>> maps = scientificApplyInfoMapper.queryAdditionalRules();
        ApplyVo applyVo = new ApplyVo();
        applyVo.setScientificData(maps);
        Result result = scientificScoringHandler.scoringScore(applyVo);
        return result;
    }

}