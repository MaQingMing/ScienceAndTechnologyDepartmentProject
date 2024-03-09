package com.yc.service;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.entity.Log;
import com.yc.mapper.LogMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Service
public class LogService extends ServiceImpl<LogMapper, Log> {


    @Resource
    private HttpServletRequest request;

    /**
     * session 中 获取用户
     * @return
     */
//    public User getUser() {
//        User user = (User) request.getSession().getAttribute("user");
//        if (user == null) {
//            throw new CustomException("-1", "请登录");
//        }
//        return user;
//    }

    /**
     * 日志记录
     * @param content
     */
    public void log(String content, String username) {
        Log log = new Log();
        log.setUser(username);
        log.setTime(DateUtil.formatDateTime(new Date()));
        log.setIp(getIpAddress());
        log.setContent(content);
        save(log);
    }

    /**
     * 日志记录
     * @param content
     */
    public void log(String content, String username,int status) {
        Log log = new Log();
        log.setUser(username);
        log.setTime(DateUtil.formatDateTime(new Date()));
        log.setIp(getIpAddress());
        log.setContent(content);
        log.setStatus(status);
        save(log);
    }


    /**
     * 描述：获取IP地址
     */
    public String getIpAddress() {

        String ip = request.getHeader("x-forwarded-for");

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {

            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
