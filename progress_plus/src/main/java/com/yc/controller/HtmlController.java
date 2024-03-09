package com.yc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * @program: science_merit
 * @description: 前端页面分发
 * @author: 作者 huchaojie
 * @create: 2023-04-04 16:21
 */
@Controller
public class HtmlController {

    /**
     * 登录界面
     * @return
     */
    @RequestMapping(value = "/")
    public String login() { return "login"; }


    /**
     * 登录界面
     * @return
     */
    @RequestMapping(value = "/login")
    public String loginIndex() { return "login"; }


    /**
     * 提示未登录界面otherlogin
     * @return
     */
    @RequestMapping(value = "/nologin")
    public String nologin() { return "errorpage/nologin"; }

    /**
     * 提示重新登录界面
     * @return
     */
    @RequestMapping(value = "/otherlogin")
    public String otherlogin() { return "errorpage/otherLogin"; }


    /**
     * 提示重新登录界面
     * @return
     */
    @RequestMapping(value = "/403")
    public String accessDenied() { return "errorpage/403"; }

    /**
     * 主界面
     * @return
     */
    @RequestMapping(value = "/frame")
    public String frame() { return "frame"; }


    /**
     * 首页
     * @return
     */
    @RequestMapping(value = "/index")
    public String index() { return "index"; }

    /**
     * 系统用户管理
     * @return
     */
    @RequestMapping(value = "/admin_back/{urlPath}")
    public String user(@PathVariable String urlPath) { return "admin_back/"+urlPath; }

    /**
     * 普通用户
     * @return
     */
    @RequestMapping(value = "/t_back/{urlPath}")
    public String t_back(@PathVariable String urlPath) { return "t_back/"+urlPath; }

    /**
     * 科技成果申请页面
     * @return
     */
    @RequestMapping(value = "/project_application/{urlPath}")
    public String application(@PathVariable String urlPath) { return "project_application/"+urlPath; }

    /**
     * 修改密码
     * @return
     */
    @RequestMapping(value = "/updatePassword")
    public String updatePassword() { return "updatePassword"; }

    /**
     * 普通用户相关功能
     * @param urlPath
     * @return
     */
    @RequestMapping(value = "/plain/{urlPath}")
    public String plain(@PathVariable String urlPath){
        return "plain/" + urlPath;
    }

    /**
     * 科技项目相关功能
     * @param urlPath
     * @return
     */
    @RequestMapping(value = "/admin_back/science/{urlPath}")
    public String science(@PathVariable String urlPath){
        return "admin_back/science/" + urlPath;
    }

    /**
     * 备案相关功能
     * @param urlPath
     * @return
     */
    @RequestMapping(value = "/filing/{urlPath}")
    public String filing(@PathVariable String urlPath){
        return "filing/" + urlPath;
    }


    /**
     * 申请相关功能
     * @param urlPath
     * @return
     */
    @RequestMapping(value = "/apply/{urlPath}")
    public String apply(@PathVariable String urlPath){
        return "apply/" + urlPath;
    }


    /**
     * 考核相关功能
     * @param urlPath
     * @return
     */
    @RequestMapping(value = "/examine/{urlPath}")
    public String examin(@PathVariable String urlPath){
        return "examine/" + urlPath;
    }

}
