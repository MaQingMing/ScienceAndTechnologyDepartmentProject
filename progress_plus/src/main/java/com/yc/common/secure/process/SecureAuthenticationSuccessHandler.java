package com.yc.common.secure.process;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yc.common.utils.ServletUtil;
import com.yc.vo.Result;
import io.swagger.models.auth.In;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: model_project
 * @description: 登录成功处理
 * @author: 作者 huchaojie
 * @create: 2023-06-13 16:50
 */
@Component
public class SecureAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication a) throws IOException {
        Result result = Result.success("登录成功");
        JSONObject json = (JSONObject) JSONObject.toJSON(a.getPrincipal());
        boolean isAdmin = (boolean) request.getSession().getAttribute("isAdmin");
        String tiscence =(String) request.getSession().getAttribute("tiscence");
        String tname = (String) request.getSession().getAttribute("tname");
        Map<String,Object> data = new HashMap<>(5);
        data.put("user",json);
        data.put("tname",tname);
        data.put("status",request.getSession().getAttribute("status"));
        if (tiscence==null){
            data.put("tiscence","");
        }else {
            data.put("tiscence",tiscence);
        }
        if (isAdmin){
            data.put("identity",1);
        }else {
            data.put("identity",0);
        }
        result.setData(data);
        ServletUtil.write(JSON.toJSONString(result));
    }
}
