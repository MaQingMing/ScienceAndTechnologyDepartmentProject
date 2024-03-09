package com.yc.common.secure.support;

import com.yc.common.secure.domain.SecureUserDetailsServiceImpl;
import com.yc.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @program: model_project
 * @description:
 * @author: 作者 huchaojie
 * @create: 2023-06-11 16:29
 */
@Component
public class SecureAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private UserService userService;
    @Resource
    private SecureUserDetailsServiceImpl userDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String identity = request.getParameter("isAdmin");
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String username = userService.login(token.getName(), token.getCredentials().toString(), identity, request);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, token.getCredentials().toString(),userDetails.getAuthorities());

        /*if (username==null){
            throw new BadCredentialsException("用户名或密码错误，请重新登录!");
        }else {
            //UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            return new UsernamePasswordAuthenticationToken(userDetails, token.getCredentials().toString(),userDetails.getAuthorities());
        }*/
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
