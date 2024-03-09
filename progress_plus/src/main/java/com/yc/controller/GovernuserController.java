package com.yc.controller;

import com.yc.common.utils.Encrypt;
import com.yc.entity.Governuser;
import com.yc.entity.model.PageBean;
import com.yc.service.GovernUserBiz;
import com.yc.service.GovernuserService;
import com.yc.vo.GovernVo;

import java.util.Date;
import java.util.List;

import com.yc.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * /**
 * 管理员表;(governuser)表控制层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "管理员表对象功能接口")
@RestController
@RequestMapping("/governuser")
public class GovernuserController {

    @Autowired
    private GovernuserService governuserService;
    @Autowired
    private GovernUserBiz governUserBiz;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public ResponseEntity<Governuser> queryById(Integer id) {
        return ResponseEntity.ok(governuserService.queryById(id));
    }

    @ApiOperation("获取服务器时间")
    @GetMapping("/get/time")
    public Result<?> getServerTime() {
        return Result.success(new Date());
    }


    @ApiOperation("查询所有governuser信息")
    @GetMapping("findAllGovern")
    public Map<String, Object> findAllGovern(@RequestParam int pageno, @RequestParam int pagesize) {
        Map<String, Object> map = new HashMap<>();
        PageBean<GovernVo> page = null;
        try {
            page = this.governUserBiz.findAllGovern(pageno, pagesize);
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getCause());
            e.printStackTrace();
            return map;
        }
        map.put("code", 1);
        map.put("data", page);
        return map;
    }

    @ApiOperation("根据nickname/username和role查询所有systemuser信息")
    @RequestMapping(value = "find", method = {RequestMethod.GET})
    public Map<String, Object> find(@RequestParam String name, @RequestParam String role, @RequestParam int pageno, @RequestParam int pagesize) {
        Map<String, Object> map = new HashMap<>();
        PageBean<GovernVo> page = null;
        try {
            page = this.governUserBiz.find(name, name, role, pageno, pagesize);
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getCause());
            e.printStackTrace();
            return map;
        }
        map.put("code", 1);
        map.put("data", page);
        return map;
    }

    @ApiOperation("根据nickname/username查询所有systemuser信息")
    @RequestMapping(value = "findByName", method = {RequestMethod.GET})
    public Map<String, Object> findByName(@RequestParam String name, @RequestParam int pageno, @RequestParam int pagesize) {
        Map<String, Object> map = new HashMap<>();
        PageBean<GovernVo> page = null;
        try {
            page = this.governUserBiz.findByName(name, name, pageno, pagesize);
        } catch (Exception e) {
            map.put("code", 0);
            map.put("msg", e.getCause());
            e.printStackTrace();
            return map;
        }
        map.put("code", 1);
        map.put("data", page);
        return map;
    }

    @ApiOperation("根据role查询所有governuser信息")
    @RequestMapping(value = "findByRole", method = {RequestMethod.GET})
    public Map<String, Object> findByRole(@RequestParam String role, @RequestParam int pageno, @RequestParam int pagesize) {
        Map<String, Object> map = new HashMap<>();
        PageBean<GovernVo> list = null;
        try {
            list = this.governUserBiz.findByRole(role, pageno, pagesize);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            map.put("msg", e.getCause());
            return map;
        }
        map.put("code", 1);
        map.put("data", list);
        return map;
    }

    @ApiOperation("增加governuser信息")
    @RequestMapping(value = "addGovern", method = {RequestMethod.POST})
    public Map<String, Object> addSystem(GovernVo governuser) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.governUserBiz.addGovern(governuser);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("根据id修改密码")
    @RequestMapping(value = "updatePwd", method = {RequestMethod.GET})
    public Map<String, Object> updatePwd(@RequestParam String password, @RequestParam Integer id) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.governUserBiz.updatePwd(password, id);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("重置密码")
    @RequestMapping(value = "resetPwd", method = {RequestMethod.GET})
    public Map<String, Object> resetPwd(@RequestParam String username) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.governUserBiz.resetPwd(username);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("验证密码是否正确")
    @RequestMapping(value = "compare", method = {RequestMethod.GET})
    public Map<String, Object> compare(@RequestParam String password, @RequestParam Integer id) {
        Map<String, Object> map = new HashMap<>();
        String pwd = this.governUserBiz.findPwdById(id);
        String pwd2 = Encrypt.fuc(password);
        System.out.println("pwd = " + pwd);
        System.out.println("pwd2 = " + pwd2);
        if (!pwd.equals(pwd2)) {
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("编辑信息")
    @RequestMapping(value = "updateinfo", method = {RequestMethod.POST})
    public Map<String, Object> dUpdate(@RequestParam String username, @RequestParam String nickname, @RequestParam String phone, @RequestParam Integer updateBy) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.governUserBiz.updateInfo(username, nickname, phone, updateBy);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("根据id删除信息")
    @RequestMapping(value = "delete/{id}", method = {RequestMethod.GET})
    public Map<String, Object> delete(@PathVariable String id) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.governUserBiz.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("编辑所属部门")
    @RequestMapping(value = "updateDept", method = {RequestMethod.GET})
    public Map<String, Object> updateDept(@RequestParam String username, @RequestParam Integer tid, @RequestParam Integer updateBy) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.governUserBiz.updateDept(username, tid, updateBy);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

    @ApiOperation("修改角色")
    @RequestMapping(value = "updateRole", method = {RequestMethod.POST})
    public Map<String, Object> updateRole(GovernVo governuser) {
        Map<String, Object> map = new HashMap<>();
        try {
            this.governUserBiz.updateRole(governuser);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 0);
            return map;
        }
        map.put("code", 1);
        return map;
    }

}