package com.yc;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@MapperScans({@MapperScan("com.yc.mapper"),@MapperScan("com.yc.apply.mapper"),@MapperScan("com.yc.standard.mapper")})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@EnableTransactionManagement
public class ProgessPulseApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProgessPulseApplication.class, args);
    }
}
