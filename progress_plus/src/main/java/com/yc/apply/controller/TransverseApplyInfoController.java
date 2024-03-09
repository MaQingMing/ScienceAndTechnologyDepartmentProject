package com.yc.apply.controller;

import com.yc.apply.entity.TransverseApplyInfo;
import com.yc.apply.mapper.TransverseApplyInfoMapper;
import com.yc.apply.scoring.techresults.TransverseScoringHandler;
import com.yc.apply.service.GainApplyService;
import com.yc.apply.service.ScoreApplyInfoService;
import com.yc.apply.service.TransverseApplyInfoService;
import com.yc.common.utils.JsonUtils;
import com.yc.service.ProveFileService;
import com.yc.service.RecordService;
import com.yc.standard.service.TechResultsTypeService;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.Result;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 横向申请详情;(transverse_apply_info)表控制层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "横向申请详情对象功能接口")
@RestController
@RequestMapping("/transverseApplyInfo")
public class TransverseApplyInfoController {
    @Autowired
    private TransverseApplyInfoService transverseApplyInfoService;

    @Resource
    private GainApplyService gainApplyService;

    @Resource
    private TransverseScoringHandler transverseScoringHandler;

    @Resource
    private ProveFileService proveFileService;

    @Resource
    private TransverseApplyInfoMapper transverseApplyInfoMapper;

    @Resource
    private TechResultsTypeService techResultsTypeService;

    @Resource
    private ScoreApplyInfoService scoreApplyInfoService;

    @Resource
    private RecordService recordService;


    @ApiOperation("计算分数")
    @PostMapping("/score")
    public Result scoring(@RequestBody ApplyVo applyVo) {
        // 进行计算
        transverseScoringHandler.scoringScore(applyVo);
        return Result.success(applyVo.getScore());
    }

    @ApiOperation("添加横向项目申请")
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    public Result addTran(@RequestParam(value = "applyVo") String tmpApplyVo,
                          @RequestParam(value = "files", required = false) List<MultipartFile> files,
                          HttpServletRequest request,
                          @SessionAttribute("systemuser") Map systemuser) {
        ApplyVo applyVo = JsonUtils.jsonToPojo(tmpApplyVo, ApplyVo.class);
        applyVo.setCreateBy(systemuser.get("id") + "");
        applyVo.setUpdateBy(systemuser.get("id") + "");
        // 先添加科技成果记录 再添加横向项目申请记录
        gainApplyService.addGainApply(applyVo);
        transverseApplyInfoService.addTransverse(applyVo);
        scoreApplyInfoService.addScoreInfo(applyVo);
        if (applyVo.getRecordid() != null) {
            recordService.updateConsume(applyVo.getGaid(), applyVo.getRecordid());
        }
        proveFileService.uploadFile(files, request, applyVo);
        return Result.success();
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
        map.put("list", transverseApplyInfoMapper.
                queryPaperHisPage((searchApplyVo.getBegin() - 1) * searchApplyVo.getSize(), searchApplyVo.getSize(),
                        searchApplyVo.getStatus(), searchApplyVo.getLevel(),
                        searchApplyVo.getCommonLike(), searchApplyVo.getDeptName(),
                        searchApplyVo.getDept(), searchApplyVo.getIdentifier(),
                        searchApplyVo.getUserId(), searchApplyVo.getIsDept(),
                        searchApplyVo.getQuery(), searchApplyVo.getCollege(), null));
        map.put("count", transverseApplyInfoMapper.
                queryCount(searchApplyVo.getStatus(), searchApplyVo.getLevel(),
                        searchApplyVo.getCommonLike(), searchApplyVo.getDeptName(),
                        searchApplyVo.getDept(), searchApplyVo.getIdentifier(),
                        searchApplyVo.getUserId(), searchApplyVo.getIsDept(),
                        searchApplyVo.getQuery(), searchApplyVo.getCollege(),
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
        int result = transverseApplyInfoMapper.changeScore(gaid, score,sid);
        if (result < 0) {
            return Result.error("修改失败，请稍后再试");
        } else {
            return Result.success("修改成功");
        }
    }
}