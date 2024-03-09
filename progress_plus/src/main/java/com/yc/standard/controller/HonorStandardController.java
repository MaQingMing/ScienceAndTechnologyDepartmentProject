package com.yc.standard.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.Systemuser;
import com.yc.standard.entity.HonorStandard;
import com.yc.standard.mapper.BookStandardMapper;
import com.yc.standard.service.HonorStandardService;
import com.yc.standard.service.TechResultsLevelService;
import com.yc.vo.EditProject;
import com.yc.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 科技类荣誉(称号);(honor_standard)表控制层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "科技类荣誉(称号)对象功能接口")
@RestController
@RequestMapping("/honorStandard")
public class HonorStandardController {
    @Autowired
    private HonorStandardService honorStandardService;

    @Resource
    private TechResultsLevelService techResultsLevelService;
    @Resource
    private BookStandardMapper bookStandardMapper;
    // 表名 主键名
    private String tableName = "honor_standard";
    private String idName = "hsid";

    /**
     * 通过ID查询单条数据
     *
     * @param hsid 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{hsid}")
    public ResponseEntity<HonorStandard> queryById(Integer hsid) {
        return ResponseEntity.ok(honorStandardService.queryById(hsid));
    }

    @ApiOperation("添加科技荣誉项目")
    @PostMapping("/add")
    public Result addHonor(@RequestBody EditProject editProject, @SessionAttribute("governuser") Map governuser) {
        editProject.setCreateBy(governuser.get("id") + "");
        editProject.setUpdateBy(governuser.get("id") + "");
        int result = honorStandardService.addHonor(editProject);
        return Result.success("添加成功!");
    }

    @ApiOperation("查询科技荣誉")
    @GetMapping("/get/{pageNum}/{pageSize}")
    public Result selectHonor(@ApiParam(name = "pageNum", value = "当前页码")
                              @PathVariable("pageNum") Integer pageNum,
                              @ApiParam(name = "pageSize", value = "当前查询条数")
                              @PathVariable("pageSize") Integer pageSize,
                              @RequestParam(value = "leid", required = false) String leid) {
        Page<EditProject> page = honorStandardService.selectHonor(pageNum, pageSize, leid);
        List<EditProject> records = page.getRecords();
        long total = page.getTotal();
        if (total <= 0) {
            return Result.error("暂无信息");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", records);
        map.put("total", total);
        return Result.success(map);
    }

    @ApiOperation("修改科技荣誉项目")
    @PostMapping("/update")
    public Result updateHonor(@RequestBody EditProject editProject, @SessionAttribute("governuser") Map governuser) {
        editProject.setCreateBy(governuser.get("id") + "");
        editProject.setUpdateBy(governuser.get("id") + "");
        int result = honorStandardService.updateHonor(editProject);
        return Result.success("修改成功!");
    }

    @ApiOperation("修改项目是否能换现")
    @PostMapping("/cash/{id}/{cash}")
    public Result modifyCash(@ApiParam(name = "id", value = "项目id")
                             @PathVariable("id") Integer id,
                             @ApiParam(name = "cash", value = "是否能换现")
                             @PathVariable("cash") Integer cash) {
        int row = techResultsLevelService.modifyCash(tableName, cash, idName, id);
        return Result.success("修改成功!");
    }

    @ApiOperation("修改项目状态")
    @PostMapping("/project/{id}/{status}")
    public Result modifyProjectStatus(@ApiParam(name = "id", value = "项目id")
                                      @PathVariable("id") Integer id,
                                      @ApiParam(name = "status", value = "项目级别状态")
                                      @PathVariable("status") Integer status){
        int row = techResultsLevelService.modifyProjectStatus(tableName, status, idName,id);
        return Result.success("修改成功!");
    }

    @ApiOperation("查询所有等级")
    @GetMapping("/queryLevels")
    public Result queryLevels(){
        return Result.success(bookStandardMapper.queryLevels());
    }

    @ApiOperation("查询荣誉级别对应的分数")
    @GetMapping("/level")
    public Result selectLevel(){
        List<EditProject> results = honorStandardService.selectLevel();
        return Result.success(results);
    }
}