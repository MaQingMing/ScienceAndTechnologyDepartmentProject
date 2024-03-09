package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 科技基地,学科建设申请详情;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
 @Data
@ApiModel(value = "科技基地,学科建设申请详情",description = "")
@TableName("scientific_apply_info")

public class ScientificApplyInfo extends BaseEntity {
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "saiid", type = IdType.AUTO)
    private Integer saiid ;
    /** 类别名称 */
    @ApiModelProperty(name = "类别名称",notes = "")
    private String type ;
    /** 项目名称 */
    @ApiModelProperty(name = "项目名称",notes = "")
    private String name ;
    /** 批准单位 */
    @ApiModelProperty(name = "批准单位",notes = "")
    private String dept ;
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    private Integer gaid ;
    /** 计算依据 */
    @ApiModelProperty(name = "计算依据",notes = "")
    private String according ;
    /** 计分 */
    @ApiModelProperty(name = "计分",notes = "")
    private Double score ;
    /** 备案ID */
    @ApiModelProperty(name = "备案ID",notes = "")
    private Integer rid ;

}