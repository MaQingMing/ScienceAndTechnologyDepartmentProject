package com.yc.controller;

import com.yc.entity.Labeldept;
import com.yc.entity.Role;
import com.yc.mapper.LabeldeptMapper;
import com.yc.service.LabeldeptBizImpl;
import com.yc.service.LabeldeptService;
import com.yc.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * 部门表;(labeldept)表控制层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-11-6
 */
@Api(tags = "部门表对象功能接口")
@RestController
@RequestMapping("/labeldept")
public class LabeldeptController {
    @Resource
    private LabeldeptBizImpl labeldeptBiz;

    @GetMapping("/allDept")
    public Result<List<Labeldept>> AllDept() {
        return Result.success(labeldeptBiz.list());
    }

    @GetMapping("/depts")
    public Result<List<Labeldept>> findAll() {
        return Result.success(labeldeptBiz.selectDepts());
    }

    @Autowired
    private LabeldeptService labeldeptService;
    @Resource
    private LabeldeptMapper labeldeptMapper;


    @ApiOperation(value = "新增项目部门", httpMethod = "POST")
    @RequestMapping("addLabelDept")
    public Result addLabelDept(Labeldept labeldept) {
        Labeldept result = labeldeptService.insert(labeldept);
        if (result.getTid() <= 0) {
            return Result.error("新增部门失败!");
        }
        return Result.success("新增部门成功!");
    }

    @ApiOperation(value = "删除项目部门", httpMethod = "POST")
    @RequestMapping("deleteLabelDept")
    public Result deleteLabelDept(Integer tid) {
        if (labeldeptService.deleteById(String.valueOf(tid))) {
            return Result.success("删除成功!");
        }else{
            return Result.error("删除失败!");
        }
    }

    @ApiOperation(value = "修改部门", httpMethod = "POST")
    @RequestMapping("updataLabelDept")
    public Result updataLabelDept(Labeldept labeldept) {
        int result = labeldeptService.update(labeldept);
        if (result <= 0) {
            return Result.error("修改失败!");
        }
        return Result.success("修改成功!");
    }


    @ApiOperation("获取部门信息")
    @PostMapping("/showLabelDept")
    public Result showLabelDept() {
        List<Labeldept> labeldepts = labeldeptService.showAll();
        return Result.success(labeldepts);
    }

    @ApiOperation("查询不属于自科|社科的部门信息")
    @PostMapping("/showNoScience")
    public Result showNoScience() {
        List<Labeldept> labeldepts = labeldeptService.showNoScience();
        return Result.success(labeldepts);
    }

    @ApiOperation("查询所有能够申请的学院部门")
    @GetMapping("/queryColleges")
    public Result queryColleges(){
        Map<String,String> colleges = new HashMap<>();
        for (Labeldept queryCollege : labeldeptMapper.queryColleges()) {
            colleges.put(queryCollege.getTid() + "",queryCollege.getTname());
        }
        return Result.success(colleges);
    }
}
