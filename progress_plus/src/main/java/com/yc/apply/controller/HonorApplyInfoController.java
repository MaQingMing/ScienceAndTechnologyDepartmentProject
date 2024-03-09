package com.yc.apply.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yc.apply.entity.Applylog;
import com.yc.apply.mapper.HonorApplyInfoMapper;
import com.yc.apply.service.ApplylogService;
import com.yc.apply.service.GainApplyService;
import com.yc.apply.service.ScoreApplyInfoService;
import com.yc.common.utils.JsonUtils;
import com.yc.service.ProveFileService;
import com.yc.standard.service.TechResultsTypeService;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.Result;
import com.yc.apply.entity.HonorApplyInfo;
import com.yc.apply.service.HonorApplyInfoService;
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
 * 科技类荣誉;(honor_apply_info)表控制层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "科技类荣誉对象功能接口")
@RestController
@RequestMapping("/honorApplyInfo")
public class HonorApplyInfoController {
    @Autowired
    private HonorApplyInfoService honorApplyInfoService;
    @Resource
    private HonorApplyInfoMapper honorApplyInfoMapper;
    @Resource
    private ApplylogService applylogService;
    @Resource
    private GainApplyService gainApplyService;
    @Resource
    private ProveFileService proveFileService;
    @Resource
    private TechResultsTypeService techResultsTypeService;
    @Resource
    private ScoreApplyInfoService scoreApplyInfoService;

    /**
     * 通过ID查询单条数据
     *
     * @param haiid 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{haiid}")
    public ResponseEntity<HonorApplyInfo> queryById(Integer haiid) {
        return ResponseEntity.ok(honorApplyInfoService.queryById(haiid));
    }


    /**
     * @Description 查询科技类荣誉所有的类别
     * @Return
     * @Author dm
     * @Date Created in 2023/10/25
     **/
    @ApiOperation("查询科技类荣誉所有的类别")
    @GetMapping("/queryHonorTypes")
    public Result queryHonorTypes() {
        return Result.success(honorApplyInfoMapper.queryHonorTypes());
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
        map.put("list", honorApplyInfoMapper.
                queryPaperHisPage((searchApplyVo.getBegin() - 1) * searchApplyVo.getSize(), searchApplyVo.getSize(),
                        searchApplyVo.getStatus(), searchApplyVo.getLevel(),
                        searchApplyVo.getCommonLike(), searchApplyVo.getQuery(),
                        searchApplyVo.getDept(), searchApplyVo.getIdentifier(),
                        searchApplyVo.getUserId(), searchApplyVo.getIsDept(),
                        searchApplyVo.getCollege(), searchApplyVo.getDeptName(), null));
        map.put("count", honorApplyInfoMapper.
                queryCount(searchApplyVo.getStatus(), searchApplyVo.getLevel(),
                        searchApplyVo.getCommonLike(), searchApplyVo.getQuery()
                        , searchApplyVo.getDept(), searchApplyVo.getIdentifier(),
                        searchApplyVo.getUserId(), searchApplyVo.getIsDept(),
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
        int result = honorApplyInfoMapper.changeScore(gaid, score,sid);
        if (result < 0) {
            return Result.error("修改失败，请稍后再试");
        } else {
            return Result.success("修改成功");
        }
    }

    @ApiOperation("添加荣誉项目申请")
    @PostMapping("/add")
    @Transactional(rollbackFor = Exception.class)
    public Result addHonor(@RequestParam(value = "applyVo") String tmpApplyVo,
                           @RequestParam(value = "files", required = false) List<MultipartFile> files,
                           HttpServletRequest request,
                           @SessionAttribute("systemuser") Map systemuser) {
        ApplyVo applyVo = JsonUtils.jsonToPojo(tmpApplyVo, ApplyVo.class);
        applyVo.setCreateBy(systemuser.get("id") + "");
        applyVo.setUpdateBy(systemuser.get("id") + "");
        // 查询计算依据
        applyVo.setAccording(techResultsTypeService.selectAccording(applyVo.getTrtid())+";科技类荣誉(称号)标准计算");
        applyVo.setTeam("0");
        // 先添加科技成果记录 再添加横向项目申请记录
        gainApplyService.addGainApply(applyVo);
        honorApplyInfoService.addHonor(applyVo);
        scoreApplyInfoService.addScoreInfo(applyVo);
        Applylog applylog = new Applylog();
        applylog.setStatus(0);
        applylog.setSysid((Integer) systemuser.get("id"));
        applylog.setMark("1".equals(systemuser.get("status"))?2:1);
        applylog.setApplyid(applyVo.getGaid());
        applylogService.insert(applylog);
        proveFileService.uploadFile(files, request, applyVo);
        return Result.success();
    }
}