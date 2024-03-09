package com.yc.apply.controller;

import com.yc.apply.entity.AccountScore;
import com.yc.apply.service.AccountScoreService;
import com.yc.entity.model.PageBean;
import com.yc.vo.AccountScoreVo;
import com.yc.vo.AccountSystemVo;
import com.yc.vo.SystemVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 账户分数明细表;(account_score)表控制层
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
@Api(tags = "账户分数明细表对象功能接口")
@RestController
@RequestMapping("/accountScore")
public class AccountScoreController{
    @Autowired
    private AccountScoreService accountScoreService;


    @ApiOperation("通过用户Id查询总科技分")
    @GetMapping("findBySid")
    public Map<String, Object> findBySid(@RequestParam Integer sysid){
        Map<String, Object> map = new HashMap<>();
        AccountSystemVo as = new AccountSystemVo();
        try{
            as = accountScoreService.findBySid(sysid);
        }catch (Exception e){
            map.put("code",0);
            map.put("msg", "查询异常");
            e.printStackTrace();
            return map;
        }
        map.put("code", 1);
        map.put("obj", as);
        return map;
    }

    @ApiOperation("查询近 x 月科技分账户详情")
    @GetMapping("findAccount")
    public Map<String, Object> findAccount(@RequestParam Integer sysid, @RequestParam Integer month, @RequestParam int pageno, @RequestParam int pagesize){
        Map<String, Object> map = new HashMap<>();
        PageBean<AccountScoreVo> page = null;
        try{
            page = this.accountScoreService.findAccount(sysid,month,pageno,pagesize);
        }catch (Exception e){
            map.put("code",0);
            map.put("msg", e.getCause());
            e.printStackTrace();
            return map;
        }
        map.put("code",1);
        map.put("obj", page);
        return map;
    }

    @ApiOperation("查询当前月科技分账户详情")
    @GetMapping("findCurrent")
    public Map<String, Object> findCurrent(@RequestParam Integer sysid, @RequestParam int pageno, @RequestParam int pagesize){
        Map<String, Object> map = new HashMap<>();
        PageBean<AccountScoreVo> page = null;
        try{
            page = this.accountScoreService.findCurrent(sysid,pageno,pagesize);
        }catch (Exception e){
            map.put("code",0);
            map.put("msg", e.getCause());
            e.printStackTrace();
            return map;
        }
        map.put("code",1);
        map.put("obj", page);
        return map;
    }

    @ApiOperation("查询用户角色")
    @GetMapping("findJob")
    public Map<String, Object> findJob(@RequestParam Integer sysid){
        Map<String, Object> map = new HashMap<>();
        List<String> list = null;
        try{
            list = accountScoreService.findJob(sysid);
        } catch (Exception e) {
            map.put("code",0);
            map.put("msg", "查询异常");
            e.printStackTrace();
            return map;
        }
        map.put("code", 1);
        map.put("obj", list);
        return map;
    }
}