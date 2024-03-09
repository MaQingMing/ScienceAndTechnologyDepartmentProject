package com.yc.common.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;

/**
 * @program: labour_teach
 * @description: 指定上传文件临时的路径
 * @author: 作者 huchaojie
 * @create: 2023-02-13 11:14
 */
@Configuration
public class MultipartConfig {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //指定临时文件路径
        factory.setLocation("/data/tmp");
        return factory.createMultipartConfig();
    }
}
