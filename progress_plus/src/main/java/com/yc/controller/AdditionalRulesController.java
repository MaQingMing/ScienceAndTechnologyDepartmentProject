package com.yc.controller;

import com.yc.entity.AdditionalRules;
import com.yc.service.AdditionalRulesService;
import com.yc.vo.other.AdditionalRulesVo;
import com.yc.vo.Result;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/11/19 9:25
 */
@RestController
@RequestMapping("/additional_rules")
public class AdditionalRulesController {

    @Autowired
    private AdditionalRulesService additionalRulesService;

    @ApiOperation("添加基础分值")
    @PostMapping("/addRules")
    public Result addRoles(@RequestParam("context1") String context,
                           @RequestParam("score") int score,
                           @RequestParam("selected") int trid,
                           @RequestParam("updateStatus")boolean updateStatus,
                           @RequestParam("id")int id,
                           @RequestParam("type")int type){
       additionalRulesService.addRoles(context, score, trid, updateStatus, id,type);
       return Result.success("操作成功");
    }
    @ApiOperation("添加百分比")
    @PostMapping("/addRulesRatio")
    public Result addRolesRatio(@RequestParam("context1") String context,
                                @RequestParam("score") int score,
                                @RequestParam("selected") int trid,
                                @RequestParam("updateStatus")boolean updateStatus,
                                @RequestParam("id")int id,
                                @RequestParam("type")int type){
       additionalRulesService.addRolesRatio(context, score, trid, updateStatus, id,type);
       return Result.success("操作成功");
    }
    @ApiOperation("添加百分比")
    @PostMapping("/addRulesLeid")
    public Result addRolesLeid(@RequestParam("context1") String context,
                                @RequestParam("score") int score,
                                @RequestParam("selected") int trid,
                               @RequestParam("leid")int leid,
                               @RequestParam("updateStatus")boolean updateStatus,
                               @RequestParam("id")int id,
                               @RequestParam("type")int type){
       additionalRulesService.addRolesLeid(context, score, trid, leid, updateStatus, id,type);
       return Result.success("操作成功");
    }

    @ApiOperation("添加其他")
    @PostMapping("/addRulesOther")
    public Result addRulesOther(@RequestParam("context1") String context,
                                @RequestParam("selected") int trid,
                                @RequestParam("updateStatus")boolean updateStatus,
                                @RequestParam("id")int id,
                                @RequestParam("type")int type
                       ){
        additionalRulesService.addRulesOther(context, trid, updateStatus, id, type);
        return Result.success("修改成功");
    }
    @ApiOperation("查询细则")
    @PostMapping("/queryRoles")
    public Result queryRoles(@RequestParam("context") String context,
                             @RequestParam("currentPage") int currentPage,
                             @RequestParam("currentSize") int currentSize){
        List<AdditionalRulesVo> list = additionalRulesService.queryRoles(context, currentPage, currentSize);
        return Result.success(list);
    }

    @ApiOperation("查询总数")
    @PostMapping("/queryRolesTotal")
    public Result queryRolesTotal(@RequestParam("context") String context){
        Integer integer = additionalRulesService.queryRolesTotal(context);
        return Result.success(integer);
    }

    @ApiOperation("查出科技项目")
    @PostMapping("/queryLeidByTrid")
    public Result queryLeidByTrid(@RequestParam("trid") Integer trid){
        List<Map<String, Object>> maps = additionalRulesService.queryLeidByTrid(trid);
        return Result.success(maps);
    }

    @ApiOperation("修改状态")
    @PostMapping("/updateStatus")
    public Result updateStatus(@RequestParam("id") Integer rid,@RequestParam("status") int status){
        additionalRulesService.updateStatus(rid,status);
        return Result.success("修改成功");
    }

    @ApiOperation("删除细则")
    @PostMapping("/deleteById")
    public Result deleteById(@RequestParam("id") int id){
        additionalRulesService.deleteById(id);
        return Result.success("删除成功!");
    }

    @ApiOperation("通过项目类型id查询附则")
    @GetMapping("/get/{trtid}")
    public Result queryAdditionalByTrtid(@ApiParam(name = "trtid", value = "项目类型id")
                                         @PathVariable("trtid") String trtid){
        List<AdditionalRules> results = additionalRulesService.queryAdditionalByTrtid(trtid);
        return Result.success(results);
    }

}
