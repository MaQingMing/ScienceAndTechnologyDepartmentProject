package com.yc.common.secure.process;

import com.alibaba.fastjson.JSON;
import com.yc.common.utils.ServletUtil;
import com.yc.vo.Result;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: model_project
 * @description: 登录失败处理
 * @author: 作者 huchaojie
 * @create: 2023-06-13 17:18
 */
@Component
public class SecureAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException {
        Result result = new Result(0,"登录失败");

        if (e instanceof InternalAuthenticationServiceException) {
            result.setMsg("账号不存在请联系管理员,添加账号信息!");
        }

        if (e instanceof UsernameNotFoundException) {
            result.setMsg("账号不存在请联系管理员,添加账号信息!");
        }

        if (e instanceof LockedException) {
            result.setMsg("用户冻结!");
        }

        if (e instanceof BadCredentialsException) {
            result.setMsg("账号密码不正确,请检查密码!");
        }

        if (e instanceof DisabledException) {
            result.setMsg("用户未启用!");
        }

        if(e instanceof AuthenticationServiceException){
            result.setMsg("账号填写错误或不存在(请联系管理员)!");
        }
        ServletUtil.write(JSON.toJSONString(result));
    }
}
