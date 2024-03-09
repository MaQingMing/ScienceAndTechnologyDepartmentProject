package com.yc.common.secure.process;

import com.alibaba.fastjson.JSON;
import com.yc.common.utils.ServletUtil;
import com.yc.vo.Result;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: model_project
 * @description: Security 注销成功 Handler
 * @author: 作者 huchaojie
 * @create: 2023-06-16 09:10
 */
@Component
public class SecureLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        Result result = Result.success("注销成功!");
        ServletUtil.write(JSON.toJSONString(result));
    }
}
