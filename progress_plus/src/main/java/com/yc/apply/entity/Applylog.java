package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * 申请审核记录表;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
 @Data
@ApiModel(value = "申请审核记录表",description = "")
@TableName("applylog")
public class Applylog implements Serializable {
    /** 主键ID */
    @ApiModelProperty(name = "主键ID",notes = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id ;
    /** 审核状态 */
    @ApiModelProperty(name = "审核状态",notes = "")
    private Integer status ;
    /** 审核人ID */
    @ApiModelProperty(name = "审核人ID",notes = "")
    private Integer sysid ;
    /** 1 学院  2 职能部门(科技处/学科中心) */
    @ApiModelProperty(name = "1 学院  2 职能部门(科技处/学科中心)",notes = "")
    private Integer mark ;
    /** 申请ID */
    @ApiModelProperty(name = "申请ID",notes = "")
    private Integer applyid ;
    /** 是否作废 申请被删除 */
    @ApiModelProperty(name = "是否作废 申请被删除",notes = "")
    private String cancel ;
    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    public Applylog() {
    }

    public Applylog(Integer status, Integer applyid, LocalDateTime createTime) {
        this.status = status;
        this.applyid = applyid;
        this.createTime = createTime;
    }
}