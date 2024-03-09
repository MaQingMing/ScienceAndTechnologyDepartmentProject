package com.yc.common.handler;

import com.yc.entity.Governuser;
import com.yc.entity.Systemuser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @program: lore_quiz
 * @description: 自定义烂拦截器
 * @author: 作者 huchaojie
 * @create: 2023-03-23 16:36
 */
@Slf4j
public class WebInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate redisTemplate;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        try {
            if(request.getSession() != null ){
                String sessionId = request.getSession().getId();
                if(request.getSession().getAttribute("isAdmin") != null){
                    boolean falg = (boolean) request.getSession().getAttribute("isAdmin");
                    String userId = "";
                    if(falg){
                        Governuser governuser = (Governuser) request.getSession().getAttribute("governuser");
                        userId = String.valueOf(governuser.getId());
                    }else{
                        Systemuser systemuser = (Systemuser) request.getSession().getAttribute("systemuser");
                        userId = String.valueOf(systemuser.getId());
                    }
                    Object object = redisTemplate.opsForHash().get("USERLOGINTOKEN",userId);
                    if(Objects.isNull(object) && request.getRequestURI().indexOf("login")>0){
                        return true;
                    }

                    if(!sessionId.equals(String.valueOf(object))){
                        response.sendRedirect(contextPath+"/otherlogin");
                        return false;
                    }
                }
            }
            Object loginUser = request.getSession().getAttribute("isAdmin");
            if(Objects.isNull(loginUser)){
                response.sendRedirect(contextPath+"/nologin");
                return false;
            }
            //这里设置拦截以后重定向的页面，一般设置为登陆页面地址
        } catch (Exception e) {
            log.error(" preHandle Exception ",e);
        }
        return true;
        //如果设置为false时，被请求时，拦截器执行到此处将不会继续操作
        //如果设置为true时，请求将会继续执行后面的操作
    }

}
