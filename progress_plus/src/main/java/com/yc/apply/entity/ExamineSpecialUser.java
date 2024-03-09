package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 不参加考核的人员;
 * @author : http://www.chiner.pro
 * @date : 2023-12-7
 */
@ApiModel(value = "不参加考核的人员",description = "")
@TableName("examine_special_user")
@Data
public class ExamineSpecialUser extends BaseEntity {

    /** 主键ID */
    @ApiModelProperty(name = "主键ID",notes = "")
    @TableId(value = "esuid", type = IdType.AUTO)
    private Integer esuid ;
    /** 考核表ID */
    @ApiModelProperty(name = "考核表ID",notes = "")
    private Integer eid ;
    /** 普通用户ID */
    @ApiModelProperty(name = "普通用户ID",notes = "")
    private Integer sysid ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remarks ;
}
