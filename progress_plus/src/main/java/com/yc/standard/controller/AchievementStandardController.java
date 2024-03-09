package com.yc.standard.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.Systemuser;
import com.yc.standard.entity.AchievementStandard;
import com.yc.standard.mapper.AchievementStandardMapper;
import com.yc.standard.service.AchievementStandardService;
import com.yc.standard.service.TechResultsLevelService;
import com.yc.vo.EditProject;
import com.yc.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 科技成果奖;(achievement_standard)表控制层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "科技成果奖对象功能接口")
@RestController
@RequestMapping("/achievementStandard")
public class AchievementStandardController {
    @Resource
    private AchievementStandardService achievementStandardService;
    @Resource
    private TechResultsLevelService techResultsLevelService;
    @Resource
    private AchievementStandardMapper achievementStandardMapper;

    private String tableName = "achievement_standard";
    private String idName = "asid";

    /**
     * 通过ID查询单条数据
     *
     * @param asid 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{asid}")
    public ResponseEntity<AchievementStandard> queryById(Integer asid) {
        return ResponseEntity.ok(achievementStandardService.queryById(asid));
    }


    @ApiOperation("添加科技成果")
    @PostMapping("/add")
    public Result addAchievement(@RequestBody EditProject editProject, @SessionAttribute("governuser") Map governuser) {
        editProject.setCreateBy(governuser.get("id") + "");
        editProject.setUpdateBy(governuser.get("id") + "");
        int row = achievementStandardService.addAchievement(editProject);
        return Result.success("添加成功");
    }

    @ApiOperation("查询科技成果")
    @GetMapping("/get/{pageNum}/{pageSize}")
    public Result selectAchievement(@ApiParam(name = "pageNum", value = "当前页码")
                                    @PathVariable("pageNum") Integer pageNum,
                                    @ApiParam(name = "pageSize", value = "当前查询条数")
                                    @PathVariable("pageSize") Integer pageSize,
                                    @RequestParam(value = "context", required = false) String context,
                                    @RequestParam(value = "leid", required = false) String leid) {
        Page<EditProject> page = new Page<>();
        if(pageNum == -100 && pageSize == 1){
            page = achievementStandardService.selectAchievement(0, 1000, context, leid,1);
            for (EditProject record : page.getRecords()) {
                record.setRemarks(null);
                record.setStatus(null);
                record.setPosit(null);
            }
        }else{
            page = achievementStandardService.selectAchievement(pageNum, pageSize, context, leid,0);
        }

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

    @ApiOperation("修改科技成果")
    @PostMapping("/update")
    public Result updateAchievement(@RequestBody EditProject editProject, @SessionAttribute("governuser") Map governuser) {
        editProject.setCreateBy(governuser.get("id") + "");
        editProject.setUpdateBy(governuser.get("id") + "");
        int row = achievementStandardService.updateAchievement(editProject);
        return Result.success("修改成功");
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
        return Result.success(achievementStandardMapper.queryLevels());
    }


}