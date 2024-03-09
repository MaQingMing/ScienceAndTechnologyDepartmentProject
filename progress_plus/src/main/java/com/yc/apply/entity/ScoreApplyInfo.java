package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 申请分数详情;
 * @author : http://www.chiner.pro
 * @date : 2023-12-2
 */
@Data
@ApiModel(value = "申请分数详情",description = "")
@TableName("score_apply_info")
public class ScoreApplyInfo  extends BaseEntity {

    /** 主键ID */
    @ApiModelProperty(name = "主键ID",notes = "")
    @TableId(value = "saiid", type = IdType.AUTO)
    private Integer saiid ;
    /** gain_apply主键ID */
    @ApiModelProperty(name = "gain_apply主键ID",notes = "")
    private Integer gaid ;
    /** 普通用户ID */
    @ApiModelProperty(name = "普通用户ID",notes = "")
    private String sysid ;
    /** 是否(1是  0否)主持人 */
    @ApiModelProperty(name = "是否(1是  0否)主持人",notes = "")
    private Integer host ;
    /** 能换钱的分 */
    @ApiModelProperty(name = "能换钱的分",notes = "")
    private Double canScore ;
    /** 不能换钱的分 */
    @ApiModelProperty(name = "不能换钱的分",notes = "")
    private Double cannotScore ;
    /** 备用字段 */
    @ApiModelProperty(name = "备用字段",notes = "")
    private String res1 ;
}
