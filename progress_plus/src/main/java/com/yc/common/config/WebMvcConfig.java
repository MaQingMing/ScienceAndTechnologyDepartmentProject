package com.yc.common.config;

import com.yc.common.utils.AllConstans;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 **/
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 资源映射
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/META-INF/resources/")
                .addResourceLocations("classpath:/resources/")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/public/");
        /*解释:
        /files/**: 相当于 别名的意思
        file:D:/图片/: 本地文件的路径*/
        registry.addResourceHandler("/uploadfile/**","/backups/**")
                //TODO:修改为图片在服务器的存放地址
                .addResourceLocations(AllConstans.STU_UPLOAD_PATH,AllConstans.STU_BACKUP_PATH);
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }

}
