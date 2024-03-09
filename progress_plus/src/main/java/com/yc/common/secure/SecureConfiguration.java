package com.yc.common.secure;

import com.yc.common.property.SecurityProperty;
import com.yc.common.secure.domain.SecureUserDetailsServiceImpl;
import com.yc.common.secure.process.*;
import com.yc.common.secure.support.SecureAuthenticationProvider;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.Resource;

/**
 * @program: model_project
 * @description: Security 配置
 * @author: 作者 huchaojie
 * @create: 2023-06-11 09:59
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(SecurityProperty.class)
public class SecureConfiguration extends WebSecurityConfigurerAdapter {

    @Resource
    private SecurityProperty securityProperty;

    @Resource
    private SecureAuthenticationEntryPoint secureAuthenticationEntryPoint;

    @Resource
    private SecurityAccessDeniedHandler securityAccessDeniedHandler;

    @Resource
    private SecureAuthenticationProvider secureAuthenticationProvider;

    @Resource
    private SecureUserDetailsServiceImpl userDetailsService;

    @Resource
    private SecureAuthenticationSuccessHandler secureAuthenticationSuccessHandler;

    @Resource
    private SecureAuthenticationFailureHandler secureAuthenticationFailureHandler;

    @Resource
    private SecureSessionExpiredHandler secureSessionExpiredHandler;

    /* @Resource
    private SessionRegistry sessionRegistry;*/

    @Resource
    private SecureLogoutHandler secureLogoutHandler;

    @Resource
    private SecureLogoutSuccessHandler secureLogoutSuccessHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(secureAuthenticationProvider)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    /**
     * 将认证入口AuthenticationManager注入容器中用于用户认证
     **/
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(securityProperty.getOpenApi()).permitAll()
                //需要登录后才能访问
                .anyRequest().authenticated().and()
                .httpBasic().authenticationEntryPoint(secureAuthenticationEntryPoint)
                .and().formLogin().loginPage("/login").loginProcessingUrl("/login")
                // 配置登录成功自定义处理类
                .successHandler(secureAuthenticationSuccessHandler)
                // 配置登录失败自定义处理类
                .failureHandler(secureAuthenticationFailureHandler)
                // 退出登录删除 session cookie缓存
                .and().logout().logoutUrl("/logout").addLogoutHandler(secureLogoutHandler)
                .deleteCookies("JSESSIONID").logoutSuccessHandler(secureLogoutSuccessHandler)
                //配置没有权限自定义处理类
                .and().exceptionHandling().accessDeniedHandler(securityAccessDeniedHandler)
                //session会话管理
                .and().sessionManagement().sessionFixation().migrateSession()
                // 在需要使用到session时才创建session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                // 同时登陆多个只保留一个
                .maximumSessions(securityProperty.getMaximum())
                // 踢出用户操作
                .expiredSessionStrategy(secureSessionExpiredHandler);
        // 用于统计在线
        //.sessionRegistry(sessionRegistry);

        // 取消跨站请求伪造防护
        http.csrf().disable();
        // 防止iframe 造成跨域
        http.headers().frameOptions().disable();
    }
}
