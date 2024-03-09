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
 * 科技成果奖申请详情;
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Data
@ApiModel(value = "科技成果奖申请详情", description = "")
@TableName("achievement_apply_info")
public class AchievementApplyInfo extends BaseEntity {
    /**
     * 主键Id
     */
    @ApiModelProperty(name = "主键Id", notes = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer aaiid;
    /**
     * 获奖形式及内容
     */
    @ApiModelProperty(name = "获奖形式及内容", notes = "")
    private String content;
    /**
     * 批准单位
     */
    @ApiModelProperty(name = "批准单位", notes = "")
    private String dept;
    /**
     * 获奖项目名称
     */
    @ApiModelProperty(name = "获奖项目名称", notes = "")
    private String name;
    /**
     * 获奖等级
     */
    @ApiModelProperty(name = "获奖等级", notes = "")
    private String level;
    /**
     * 计算依据(重复计算 国家级 省部级 市厅级)
     */
    @ApiModelProperty(name = "计算依据(重复计算 国家级 省部级 市厅级)", notes = "")
    private String according;
    /**
     * 主键Id
     */
    @ApiModelProperty(name = "主键Id", notes = "")
    private Integer gaid;

    @ApiModelProperty(name = "我校是否是第一申报单位", notes = "")
    private Integer firstSign;

    @ApiModelProperty(name = "我校排名", notes = "")
    private Integer schoolOrder;

    @ApiModelProperty(name = "教职工排名", notes = "")
    private Integer workersOrder;
}