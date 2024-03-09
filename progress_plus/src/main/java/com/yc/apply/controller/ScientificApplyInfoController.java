package com.yc.apply.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yc.apply.mapper.ScientificApplyInfoMapper;
import com.yc.vo.apply.AddScientificApplyInfoVo;
import com.yc.vo.Result;
import com.yc.apply.entity.ScientificApplyInfo;
import com.yc.apply.service.ScientificApplyInfoService;
import com.yc.vo.apply.SearchApplyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 科技基地,学科建设申请详情;(scientific_apply_info)表控制层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "科技基地,学科建设申请详情对象功能接口")
@RestController
@RequestMapping("/scientificApplyInfo")
public class ScientificApplyInfoController {
    @Autowired
    private ScientificApplyInfoService scientificApplyInfoService;
    @Resource
    private ScientificApplyInfoMapper scientificApplyInfoMapper;

    /**
     * @Description 查询科技基地/学科建设所有的级别
     * @Return
     * @Author dm
     * @Date Created in 2023/10/25
     **/
    @ApiOperation("查询科技基地/学科建设所有的级别")
    @GetMapping("/queryScientificTypes")
    public Result queryScientificLevels() {
        return Result.success(scientificApplyInfoMapper.queryScientificTypes());
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
        map.put("list", scientificApplyInfoMapper.
                queryPaperHisPage((searchApplyVo.getBegin() - 1) * searchApplyVo.getSize(), searchApplyVo.getSize(),
                        searchApplyVo.getStatus(), searchApplyVo.getLevel(),
                        searchApplyVo.getCommonLike(), searchApplyVo.getDeptName(),
                        searchApplyVo.getDept(), searchApplyVo.getIdentifier(),
                        searchApplyVo.getUserId(), searchApplyVo.getIsDept(),
                        searchApplyVo.getQuery(), searchApplyVo.getCollege(), null));
        map.put("count", scientificApplyInfoMapper.
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
                              @RequestParam("sid") String sid) {
        int result = scientificApplyInfoMapper.changeScore(gaid, score, sid);
        if (result < 0) {
            return Result.error("修改失败，请稍后再试");
        } else {
            return Result.success("修改成功");
        }
    }

    @ApiOperation("查询已提交的备案")
    @PostMapping("/queryRecords")
    public Result queryRecords(String username) {
        List<Map<String, Object>> maps = scientificApplyInfoService.queryRecords(username);
        return Result.success(maps);
    }

    @ApiOperation("查询科研平台的项目类型")
    @PostMapping("/queryLname")
    public Result queryLname() {
        List<Map<String, Object>> maps = scientificApplyInfoService.queryLname();
        return Result.success(maps);
    }

    @ApiOperation("添加平台申请")
    @PostMapping("/addScientificApply")
    public Result addScientificApply(AddScientificApplyInfoVo addScientificApplyInfoVo,
                                     @RequestParam(value = "file", required = false) List<MultipartFile> file,
                                     HttpServletRequest request) throws Exception {
        int i = scientificApplyInfoService.addScientificApply(addScientificApplyInfoVo, file, request);
        if (i == 0) {
            return Result.error(-1, "备案已被使用");
        } else {
            return Result.success("申请成功");
        }
    }

    @ApiOperation("平台查询细则并处理分数")
    @PostMapping("/queryRoles")
    public Result queryRoles() {
        Result result = scientificApplyInfoService.queryRoles();
        List<Map<String, Object>> maps = (List<Map<String, Object>>) result.getData();
        return Result.success(maps);
    }
}