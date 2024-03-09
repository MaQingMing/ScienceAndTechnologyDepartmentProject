package com.yc.vo.other;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/12/16 13:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DelayMessageVo implements Serializable {

    /**
     * 切记实例化
     */
    private static final long serialVersionUID = -7671756385477179547L;

    /**
     * 消息 id
     */
    private Integer id;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息到期时间
     */
    private long expireTime;

}