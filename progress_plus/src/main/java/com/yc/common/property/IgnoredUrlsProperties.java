package com.yc.common.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: yc-examination-dev
 * @description: url 忽略
 * @author: 作者 huchaojie
 * @create: 2022-06-22 10:05
 */
@Configuration
@ConfigurationProperties(prefix = "yc.ignored")
public class IgnoredUrlsProperties {

    private List<String> urls = new ArrayList<>(13);

    private Map<String,Integer> urlsMap = new HashMap<>(15);

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
        for(String url : urls){
            urlsMap.put(url,1);
        }
    }

    public Map<String, Integer> getUrlsMap() {
        return urlsMap;
    }
}
