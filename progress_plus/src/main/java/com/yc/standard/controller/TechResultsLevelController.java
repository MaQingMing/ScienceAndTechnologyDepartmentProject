package com.yc.standard.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.Systemuser;
import com.yc.standard.entity.TechResultsLevel;
import com.yc.standard.service.TechResultsLevelService;
import com.yc.vo.Result;
import com.yc.vo.TechResultsLevelAndTrname;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhupanlin
 * @version 1.0
 * @description: TODO
 * @date 2023/10/22 15:17
 */
@Api(tags = "科技成果级别对象功能接口")
@RestController
@RequestMapping("/techResultsLevel")
public class TechResultsLevelController {

    @Resource
    private TechResultsLevelService techResultsLevelService;

    @ApiOperation("分页查询项目级别")
    @GetMapping("/get/{currentPage}/{pageSize}")
    public Result selectTechResultsLevelByPage(@RequestParam(value = "trid", required = false) String trid, @RequestParam(value = "lname", required = false) String lname, @ApiParam(name = "currentPage", value = "当前页") @PathVariable("currentPage") Integer currentPage, @ApiParam(name = "pageSize", value = "查询条数") @PathVariable("pageSize") Integer pageSize) {
        Page<TechResultsLevelAndTrname> results = techResultsLevelService.selectTechResultsLevelByPage(trid, lname, currentPage, pageSize);
        List<TechResultsLevelAndTrname> records = results.getRecords();
        long total = results.getTotal();
        if (records.size() <= 0) {
            return Result.error("暂无信息");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", records);
        map.put("total", total);
        return Result.success(map);
    }

    @ApiOperation("添加项目级别")
    @PostMapping("/add")
    public Result addTechResultsLevel(@RequestBody TechResultsLevel techResultsLevel, @SessionAttribute("governuser") Map governuser) {
        techResultsLevel.setCreateBy(governuser.get("id") + "");
        techResultsLevel.setUpdateBy(governuser.get("id") + "");
        techResultsLevelService.addTechResultsLevel(techResultsLevel);
        return Result.success("添加成功!");
    }

    @ApiOperation("修改项目级别")
    @PostMapping("/update")
    public Result modifyTechResultsLevel(@RequestBody TechResultsLevel techResultsLevel,@SessionAttribute("governuser") Map governuser) {
        techResultsLevel.setCreateBy(governuser.get("id") + "");
        techResultsLevel.setUpdateBy(governuser.get("id") + "");
        techResultsLevelService.modifyTechResultsLevel(techResultsLevel);
        return Result.success("修改成功!");
    }

    @ApiOperation("修改项目级别状态")
    @PostMapping("/status/{leid}/{status}")
    public Result modifyStatus(@ApiParam(name = "leid", value = "项目级别id") @PathVariable("leid") Integer leid, @ApiParam(name = "status", value = "项目级别状态") @PathVariable("status") Integer status) {
        String tableName = "tech_results_level";
        int row = techResultsLevelService.modifyStatus(tableName, status, leid);
        return Result.success("修改成功!");
    }

    @ApiOperation("根据类型id查询项目级别")
    @GetMapping("/get/{trid}")
    public Result selectLevelByTrid(@ApiParam(name = "trid", value = "项目类别id") @PathVariable("trid") Integer trid) {
        List<TechResultsLevel> techResultsLevels = techResultsLevelService.selectLevelByTrid(trid);
        return Result.success(techResultsLevels);
    }

}
