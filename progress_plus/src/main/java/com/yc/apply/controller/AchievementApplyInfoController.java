package com.yc.apply.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yc.apply.mapper.AchievementApplyInfoMapper;
import com.yc.apply.scoring.techresults.AchievementScoringHandler;
import com.yc.apply.scoring.techresults.TransverseScoringHandler;
import com.yc.apply.service.GainApplyService;
import com.yc.apply.service.ScoreApplyInfoService;
import com.yc.common.utils.JsonUtils;
import com.yc.entity.Record;
import com.yc.entity.Systemuser;
import com.yc.mapper.RecordMapper;
import com.yc.service.ProveFileService;
import com.yc.service.RecordService;
import com.yc.standard.service.TechResultsTypeService;
import com.yc.vo.Result;
import com.yc.apply.entity.AchievementApplyInfo;
import com.yc.apply.service.AchievementApplyInfoService;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.apply.SearchApplyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 科技成果奖申请详情;(achievement_apply_info)表控制层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "科技成果奖申请详情对象功能接口")
@RestController
@RequestMapping("/achievementApplyInfo")
public class AchievementApplyInfoController {
    @Autowired
    private AchievementApplyInfoService achievementApplyInfoService;
    @Resource
    private AchievementApplyInfoMapper achievementApplyInfoMapper;
    @Resource
    private RecordMapper recordMapper;
    @Resource
    private AchievementScoringHandler achievementScoringHandler;
    @Resource
    private TechResultsTypeService techResultsTypeService;
    @Resource
    private GainApplyService gainApplyService;
    @Resource
    private ProveFileService proveFileService;
    @Resource
    private ScoreApplyInfoService scoreApplyInfoService;
    @Resource
    private RecordService recordService;

    /**
     * 通过ID查询单条数据
     *
     * @param aaiid 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{aaiid}")
    public ResponseEntity<AchievementApplyInfo> queryById(Integer aaiid) {
        return ResponseEntity.ok(achievementApplyInfoService.queryById(aaiid));
    }

    /**
     * @Description 查询所有的科技成果奖级别
     * @Return
     * @Author dm
     * @Date Created in 2023/10/25
     **/
    @ApiOperation("查询所有的科技成果奖级别")
    @GetMapping("/queryAchieventmentLevels")
    public Result queryAchieventmentLevels() {
        return Result.success(achievementApplyInfoMapper.queryAchieventmentLevels());
    }

    /**
     * @Description 查询分页数据
     * @Return
     * @Author dm
     * @Date Created in 2023/10/22
     **/
    @ApiOperation("通过gaid查询数据")
    @PostMapping("/queryHisPage")
    public Result queryDetail(@RequestBody SearchApplyVo searchApplyVo) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", achievementApplyInfoMapper.
                queryPaperHisPage((searchApplyVo.getBegin() - 1) * searchApplyVo.getSize(), searchApplyVo.getSize(),
                        searchApplyVo.getStatus(), searchApplyVo.getLevel(), searchApplyVo.getCommonLike(),
                        searchApplyVo.getDept(), searchApplyVo.getIdentifier(),
                        searchApplyVo.getUserId(), searchApplyVo.getIsDept(),
                        searchApplyVo.getQuery(), searchApplyVo.getCollege(),
                        searchApplyVo.getDeptName(), null));
        map.put("count", achievementApplyInfoMapper.queryCount(searchApplyVo.getStatus(), searchApplyVo.getLevel(),
                searchApplyVo.getCommonLike(), searchApplyVo.getDept(),
                searchApplyVo.getIdentifier(), searchApplyVo.getUserId(),
                searchApplyVo.getIsDept(), searchApplyVo.getQuery(),
                searchApplyVo.getCollege(), searchApplyVo.getDeptName(),
                null));
        return Result.success(map);
    }

    /**
     * @return
     * @Description 修改分数
     * @Return
     * @Author dm
     * @Date Created in 2023/11/10
     */
    @ApiOperation("修改分数")
    @GetMapping("/changeScore")
    public Result changeScore(@RequestParam("gaid") String gaid,
                              @RequestParam("score") String score,
                              @RequestParam("sid")String sid) {
        int result = achievementApplyInfoMapper.changeScore(gaid, score,sid);
        if (result < 0) {
            return Result.error("修改失败，请稍后再试");
        } else {
            return Result.success("修改成功");
        }
    }

    /**
     * 根据类型级别查询备案
     * @param standardType
     * @param standardId
     * @return
     */
    @ApiOperation("根据类型级别查询备案")
    @GetMapping("/findRecord")
    public Result findRecord(@RequestParam("standardType")String standardType, @RequestParam("standardId")Integer standardId, @RequestParam("createBy") String createBy){
        List<Record> records = recordMapper.findRecord(standardType, standardId, createBy);
        return Result.success(records);
    }

    @ApiOperation("添加科技成果申请")
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    public Result addTran(@RequestParam(value = "applyVo") String tmpApplyVo,
                          @RequestParam(value = "files", required = false) List<MultipartFile> files,
                          HttpServletRequest request,
                          @SessionAttribute("systemuser") Map systemuser) {
        ApplyVo applyVo = JsonUtils.jsonToPojo(tmpApplyVo, ApplyVo.class);
        System.out.println(applyVo);
        applyVo.setCreateBy(systemuser.get("id") + "");
        applyVo.setUpdateBy(systemuser.get("id") + "");
        // 先添加科技成果记录 再添加科技成果项目申请记录
        gainApplyService.addGainApply(applyVo);
        achievementApplyInfoService.addAchievement(applyVo);
        scoreApplyInfoService.addScoreInfo(applyVo);
        if (applyVo.getRecordid() != null && recordMapper.queryCousumeByRid(applyVo.getRecordid()) == 0) {
            recordService.updateConsume(applyVo.getGaid(), applyVo.getRecordid());
        }
        proveFileService.uploadFile(files, request, applyVo);
        return Result.success();
    }

    @ApiOperation("计算分数")
    @PostMapping("/score")
    public Result scoring(@RequestBody ApplyVo applyVo) {
        // 进行计算
        achievementScoringHandler.scoringScore(applyVo);
        return Result.success(applyVo.getScore());
    }
}