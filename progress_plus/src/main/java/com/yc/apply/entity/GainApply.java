package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 /**
 * 科技成果申请;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Data
@ApiModel(value = "科技成果申请", description = "")
@TableName("gain_apply")
public class GainApply extends BaseEntity {
    /**
     * 主键Id
     */
    @ApiModelProperty(name = "主键Id", notes = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer gaid;
    /**
     * 申请人
     */
    @ApiModelProperty(name = "申请人", notes = "")
    private String sid;
    /**
     * 申请状态(0 学院初审  1 复核(科技处/学科中心)  2 通过  -1 驳回 )
     */
    @ApiModelProperty(name = "申请状态(0 学院初审  1 复核(科技处/学科中心)  2 通过  -1 驳回 )", notes = "")
    private String status;
    /**
     * 驳回理由
     */
    @ApiModelProperty(name = "驳回理由", notes = "")
    private String rejection;
    /**
     * 科技成果类别
     */
    @ApiModelProperty(name = "科技成果类别", notes = "")
    private String trtid;
    /**
     * 详细类别 (可能多条 重复计算)
     */
    @ApiModelProperty(name = "详细类别 (可能多条 重复计算)", notes = "")
    private String childid;
    /**
     * 是否是团队申请( 1 是  0 否 )
     */
    @ApiModelProperty(name = "是否是团队申请( 1 是  0 否 )", notes = "")
    private String team;
    /**
     * 备案信息ID
     */
    @ApiModelProperty(name = "备案信息ID", notes = "")
    private Integer recordid;
    /**
     * 申请日期
     */
    @ApiModelProperty(name = "申请日期", notes = "")
    private String date;
    /**
     * 备注
     */
    @ApiModelProperty(name = "备注", notes = "")
    private String remarks;

    @ApiModelProperty(name = "主类名称", notes = "")
    @TableField(exist = false)
    private String trname;

    @ApiModelProperty(name = "申请人姓名", notes = "")
    @TableField(exist = false)
    private String username;
    /** 计算依据 */
    @ApiModelProperty(name = "计算依据",notes = "")
    private String according ;

    @TableField(exist = false)
    @ApiModelProperty(name = "所得分",notes = "")
    private String score ;
}