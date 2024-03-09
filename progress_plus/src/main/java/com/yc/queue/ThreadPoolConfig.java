package com.yc.queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 */

@Configuration
public class ThreadPoolConfig {

    // 获取服务器的cpu个数 获取cpu个数
    //private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int COUR_SIZE = 5;
    private static final int MAX_COUR_SIZE = 10;

    /**
     * 接下来配置一个bean，配置线程池
     * @return
     */
    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        threadPoolTaskExecutor.setCorePoolSize(COUR_SIZE);
        // 配置最大线程数
        threadPoolTaskExecutor.setMaxPoolSize(MAX_COUR_SIZE);
        // 配置队列容量（这里设置成最大线程数的四倍）
        threadPoolTaskExecutor.setQueueCapacity(MAX_COUR_SIZE * 4);
        // 给线程池设置名称
        threadPoolTaskExecutor.setThreadNamePrefix("yc-queue-thread");
        // 设置任务的拒绝策略
        threadPoolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return threadPoolTaskExecutor;
    }
}
