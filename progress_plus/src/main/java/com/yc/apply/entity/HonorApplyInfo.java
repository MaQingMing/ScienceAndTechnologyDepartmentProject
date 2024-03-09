package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

 /**
 * 科技类荣誉;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
 @Data
@ApiModel(value = "科技类荣誉",description = "")
@TableName("honor_apply_info")
public class HonorApplyInfo extends BaseEntity {
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "haiid", type = IdType.AUTO)
    private Integer haiid ;
    /** 类别名称 */
    @ApiModelProperty(name = "类别名称",notes = "")
    private String type ;
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
    /** 名称 */
    @ApiModelProperty(name = "名称",notes = "")
    private String name ;
}