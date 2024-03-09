package com.yc.queue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yc.vo.other.DelayMessageVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/12/16 13:40
 */
@Slf4j
@Component
public class DelayQueueService {

    /**
     * key后面拼接当前机器的内网ip : 用于集群区分,解决集群出现的并发问题
     */
    public static final String KEY = "delay_queue:" + getHostAddress();

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 添加消息到延时队列中
     */
    public void put(DelayMessageVo message) {
        redisTemplate.opsForZSet().add(KEY, message, message.getExpireTime());
    }


    /**
     * 从延时队列中删除消息
     */
    public Long remove(DelayMessageVo message) {
        Long remove = redisTemplate.opsForZSet().remove(KEY, message);
        return remove;
    }

    /**
     * 获取延时队列中已到期的消息
     */
    public List<DelayMessageVo> getExpiredMessages() {
//        1 : 获取到开始时间
        long minScore = 0;
//        2 : 获取当前时间
        long maxScore = System.currentTimeMillis();
//        3 : 获取到指定范围区间的数据列表
        Set<Object> messages = redisTemplate.opsForZSet().rangeByScore(KEY, minScore, maxScore);
        if (messages == null || messages.isEmpty()) {
            return Collections.emptyList();
        }
//        4 : 把对象进行封装,返回
        List<DelayMessageVo> result = new ArrayList<>();
        for (Object message : messages) {
            // 将 DelayMessageVo 对象转换为 JSON 字符串
            String jsonMessage = JSON.toJSONString(message);
            DelayMessageVo delayMessage = JSONObject.parseObject(jsonMessage, DelayMessageVo.class);
            result.add(delayMessage);
        }
        return result;
    }

    /**
     * 获取地址(服务器的内网地址)(内网ip)
     *
     * @return
     */
    public static String getHostAddress() {
        InetAddress localHost = null;
        try {
            localHost = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            log.error(" getHostAddress ",e);
        }
        return localHost.getHostAddress();
    }
}