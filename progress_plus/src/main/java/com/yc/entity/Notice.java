package com.yc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import lombok.Data;


@Data
@TableName("t_notice")
public class Notice extends BaseEntity {

    /**
      * 主键
      */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
      * 标题 
      */
    private String title;

    /**
      * 内容 
      */
    private String content;

    /**
      * 发布时间 
      */
    private String time;

    /**
     * 考核表id
     */
    private Integer eid;

}