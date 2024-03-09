package com.yc.controller;

import com.alibaba.excel.util.StringUtils;
import com.yc.common.utils.AlyExcelUtil;
import com.yc.common.utils.Encrypt;
import com.yc.entity.Governuser;
import com.yc.entity.Systemuser;
import com.yc.entity.model.PageBean;
import com.yc.mapper.SystemuserMapper;
import com.yc.service.SystemUserBiz;
import com.yc.service.impl.SystemUserBizImpl;
import com.yc.vo.Result;
import com.yc.vo.SystemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 普通用户表：(systemuser)表控制层
 *
 */
@Api(tags = "普通用户表对象功能接口")
@RestController
@RequestMapping("/systemuser")
public class SystemuserController {

    @Autowired
    private SystemUserBiz systemUserBiz;
    @Autowired
    private SystemuserMapper systemuserMapper;

    @ApiOperation("查询所有systemuser信息")
    @GetMapping("findAllSystem")
    public Map<String, Object> findAllSystem(@RequestParam int pageno, @RequestParam int pagesize){
        Map<String, Object> map = new HashMap<>();
        PageBean<SystemVo> page = null;
        try{
            page = this.systemUserBiz.findAllSystem(pageno,pagesize);
        }catch (Exception e){
            map.put("code",0);
            map.put("msg", e.getCause());
            e.printStackTrace();
            return map;
        }
        map.put("code",1);
        map.put("data", page);
        return map;
    }

    @ApiOperation("根据nickname/username查询所有systemuser信息")
    @RequestMapping(value = "findByName", method = {RequestMethod.GET})
    public Map<String, Object> findByName(@RequestParam String name, @RequestParam int pageno, @RequestParam int pagesize){
        Map<String, Object> map = new HashMap<>();
        PageBean<SystemVo> page = null;
        try{
            page = this.systemUserBiz.findByName(name,name,pageno,pagesize);
        }catch (Exception e){
            map.put("code",0);
            map.put("msg", e.getCause());
            e.printStackTrace();
            return map;
        }
        map.put("code",1);
        map.put("data", page);
        return map;
    }

    @ApiOperation("增加systemuser信息")
    @RequestMapping(value = "addSystem", method = {RequestMethod.POST})
    public Map<String, Object> addSystem(Systemuser systemuser){
        Map<String, Object> map = new HashMap<>();
        try{
            this.systemUserBiz.addSystem(systemuser);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("根据id修改密码")
    @RequestMapping(value = "updatePwd", method = {RequestMethod.GET})
    public Map<String, Object> updatePwd(@RequestParam String password, @RequestParam Integer id){
        Map<String, Object> map = new HashMap<>();
        try{
            this.systemUserBiz.updatePwd(password, id);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("重置密码")
    @RequestMapping(value = "resetPwd", method = {RequestMethod.GET})
    public Map<String, Object> resetPwd(@RequestParam String username){
        Map<String, Object> map = new HashMap<>();
        try{
            this.systemUserBiz.resetPwd(username);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("验证密码是否正确")
    @RequestMapping(value = "compare", method = {RequestMethod.GET})
    public Map<String, Object> compare(@RequestParam String password,@RequestParam Integer id){
        Map<String, Object> map = new HashMap<>();
        String pwd = this.systemUserBiz.findPwdById(id);
        String pwd2 = Encrypt.fuc(password);
        if(!pwd.equals(pwd2)){
            map.put("code",0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("编辑信息")
    @RequestMapping(value = "updateinfo", method = {RequestMethod.POST})
    public Map<String, Object> dUpdate(Systemuser systemuser){
        Map<String, Object> map = new HashMap<>();
        try{
            this.systemUserBiz.updateInfo(systemuser);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("根据id删除systemuser信息")
    @RequestMapping(value = "delete/{id}", method = {RequestMethod.GET})
    public Map<String, Object> delete(@PathVariable String id){
        Map<String, Object> map = new HashMap<>();
        try{
            this.systemUserBiz.delete(id);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("编辑是否归属研究院")
    @RequestMapping(value = "updateAcademy", method = {RequestMethod.GET})
    public Map<String, Object> updateAcademy(@RequestParam String id, @RequestParam Integer academy){
        Map<String, Object> map = new HashMap<>();
        try{
            this.systemUserBiz.updateAcademy(id, academy);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("编辑是否承担研究院管理工作")
    @RequestMapping(value = "updateAcademyCare", method = {RequestMethod.GET})
    public Map<String, Object> updateAcademyCare(@RequestParam String id, @RequestParam Integer academyCare){
        Map<String, Object> map = new HashMap<>();
        try{
            this.systemUserBiz.updateAcademyCare(id, academyCare);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("编辑所属部门")
    @RequestMapping(value = "updateDept", method = {RequestMethod.GET})
    public Map<String, Object> updateDept(@RequestParam String username, @RequestParam Integer tid, @RequestParam Integer updateBy){
        Map<String, Object> map = new HashMap<>();
        try{
            this.systemUserBiz.updateDept(username,tid,updateBy);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("查询普通用户总人数")
    @GetMapping("syc")
    public Result findSystemUserCount(){
        Long aLong = systemuserMapper.selectCount(null);
        if(aLong>0){
            return Result.success(aLong);
        }else{
            return Result.error("暂无数据!");
        }
    }

    /**
     * 上传文件
     * @param file 上传的文件对象
     * @param uid 前端接收的当前操作的用户id
     * @return
     * @throws IOException
     */
    @ApiOperation("上传已经编辑好的excel信息表格")
    @PostMapping("/upload")
    public Result upload(MultipartFile file, @Param("uploadData.uid") String uid) throws IOException {
        Result<String> result = new Result<>();
        Map<String, Object> errorMap = new HashMap<>();
        this.systemUserBiz.processFile(file, uid, errorMap);
        if (!errorMap.isEmpty()) {
            // 处理错误信息，例如记录日志或者返回给用户
            result.setMsg("文件格式错误，错误行数及错误信息：" + errorMap.toString());
            result.setCode(0);
            return result;
        }
        // 其他处理逻辑
        result.setMsg("上传成功！");
        result.setCode(1);
        return result;
    }
}
