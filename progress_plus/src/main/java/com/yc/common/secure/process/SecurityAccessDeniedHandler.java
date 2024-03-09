package com.yc.common.secure.process;

import com.alibaba.fastjson.JSON;
import com.yc.vo.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @program: model_project
 * @description: 没有权限自定义处理类
 * @author: 作者 huchaojie
 * @create: 2023-06-11 10:28
 */
@Component
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException {
        if(com.yc.common.utils.ServletUtil.isAjax(request)){
            com.yc.common.utils.ServletUtil.write(JSON.toJSONString(Result.error(403, "暂无权限!")));
        }else{
            response.sendRedirect("/403");
        }
    }


    /**
     * Describe: 获取 HttpServletResponse 对象
     * Param null
     * Return HttpServletResponse
     */
    public HttpServletResponse getResponse() {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return servletRequestAttributes.getResponse();
    }
}
