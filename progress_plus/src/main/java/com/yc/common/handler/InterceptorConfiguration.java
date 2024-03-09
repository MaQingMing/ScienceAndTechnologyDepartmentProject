package com.yc.common.handler;

import com.yc.common.property.IgnoredUrlsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @program: lore_quiz
 * @description: 全局拦截器属性配置
 * @author: 作者 huchaojie
 * @create: 2023-03-23 16:34
 */
//@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Resource
    private IgnoredUrlsProperties ignoredUrlsProperties;

    @Bean
    public WebInterceptor getWebInterceptor(){
        return new WebInterceptor();
    }

    /**
     * 重写addInterceptors()实现拦截器
     * 配置：要拦截的路径以及不拦截的路径
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册Interceptor拦截器(Interceptor这个类是我们自己写的拦截器类)
        InterceptorRegistration registration = registry.addInterceptor(getWebInterceptor());
        //所有路径都被拦截
        registration.addPathPatterns("/**");
        //excludePathPatterns()方法添加不拦截的路径
        //添加不拦截路径
        registration.excludePathPatterns(ignoredUrlsProperties.getUrls());
    }

}
