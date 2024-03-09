package com.yc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.common.handler.ListHandler;
import com.yc.entity.base.BaseEntity;
import lombok.Data;

import java.util.List;

@Data
@TableName(value = "t_role", autoResultMap = true)
public class Role extends BaseEntity {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String description;

    private String rolecode;

    @TableField(typeHandler = ListHandler.class)
    private List<Long> permission;


}
