package com.yc.standard.controller;

import com.yc.entity.Systemuser;
import com.yc.standard.entity.TechResultsType;
import com.yc.standard.mapper.TechResultsLevelMapper;
import com.yc.standard.service.TechResultsTypeService;
import com.yc.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 科技成果分类;(tech_results_type)表控制层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "科技成果分类对象功能接口")
@RestController
@RequestMapping("/techResultsType")
public class TechResultsTypeController{
    @Autowired
    private TechResultsTypeService techResultsTypeService;
    @Resource
    private TechResultsLevelMapper techResultsLevelMapper;

    @ApiOperation("查询启用的项目类型名和id")
    @GetMapping("/get/trname")
    public Result selectTrname(){
        return Result.success(techResultsTypeService.selectTrname());
    }

     @ApiOperation("修改项目类别状态")
     @PostMapping("/status/{trid}/{status}")
     public Result deleteTechResultsLevel(@ApiParam(name = "trid", value = "项目类别id")
                                          @PathVariable("trid") Integer trid,
                                          @ApiParam(name = "status", value = "项目类别状态")
                                          @PathVariable("status") Integer status){
         int row = techResultsTypeService.modifyStatus(status, trid);
         return Result.success("修改成功!");
     }

    @ApiOperation("修改科技项目类别")
    @PostMapping("/update")
    public Result updateTechResultsType(@RequestBody TechResultsType techResultsType, @SessionAttribute("governuser") Map governuser){
        techResultsType.setCreateBy(governuser.get("id") + "");
        techResultsType.setUpdateBy(governuser.get("id") + "");
        techResultsTypeService.updateTechResultsType(techResultsType);
        return Result.success("修改成功");
    }

    @ApiOperation("查询科技项目类型")
    @GetMapping("/get/{trname}")
    public Result selectTechResultsType(@ApiParam(name = "trname", value = "科技项目类型名") @PathVariable("trname")String trname){
        List<TechResultsType> results = techResultsTypeService.selectTechResultsType(trname);
        if (results.size() <= 0){
            return Result.error("暂无信息!");
        }
        return Result.success(results);
    }

    @ApiOperation("添加科技项目类型")
    @PostMapping("/add")
    public Result addTechResultsType(@RequestBody TechResultsType techResultsType, @SessionAttribute("governuser") Map governuser){
        techResultsType.setCreateBy(governuser.get("id") + "");
        techResultsType.setUpdateBy(governuser.get("id") + "");
        techResultsTypeService.addTechResultsType(techResultsType);
        return Result.success("添加成功!");
    }

    @ApiOperation("查询所有科技项目类别")
    @GetMapping("/queryAllTypes")
    public Result queryAllTypes(@RequestParam(required = false) String tid, @RequestParam String status, HttpSession session) {
        return Result.success(techResultsTypeService.queryAllType(tid,status,session));
    }
}