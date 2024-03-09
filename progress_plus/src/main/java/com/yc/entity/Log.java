package com.yc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;


@Data
@TableName("t_log")
public class Log extends Model<Log> {

    private static final long serialVersionUID = -6052074032288756388L;

    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 操作内容
      */
    private String content;

    /**
      * 操作时间
      */
    private String time;

    /**
      * 操作人
      */
    private String user;

    /**
     * IP
     */
    private String ip;

    /**
     * 标记 log类型
     *  1 老师登录
     *  2 学生登录
     *  3 辅导员撤销申请
     */
    private int status;

}
