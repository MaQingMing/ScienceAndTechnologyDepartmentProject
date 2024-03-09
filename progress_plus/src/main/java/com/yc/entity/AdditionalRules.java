package com.yc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/11/19 9:07
 */
@Data
@ApiModel(value = "科技成果附则表",description = "")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@TableName(value = "additional_rules" ,autoResultMap = true)
@Component
public class AdditionalRules extends BaseEntity {

    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "rid", type = IdType.AUTO)
    private Integer rid ;
    /** 备案名称 */
    @ApiModelProperty(name = "备案名称",notes = "")
    private Integer trtid ;
    /** 备案状态( 1通过   0不通过) */
    @ApiModelProperty(name = "状态",notes = "")
    private Integer status ;
    /** 驳回理由 */
    @ApiModelProperty(name = "内容",notes = "")
    private String content ;
    /** 驳回理由 */
    @ApiModelProperty(name = "附则类型(基础分值0,百分比1,百分比加具体项2,其他3)",notes = "")
    private Integer type ;
    /** 备案日期 */
    @ApiModelProperty(name = "具体项目项 ",notes = "")
    private Integer childid ;
    /** 备案团队或个人ID( 1,2,3 ) */
    @ApiModelProperty(name = "百分比",notes = "")
    private String ratio ;
    /** 附件(图片/pdf) */
    @ApiModelProperty(name = "科技分",notes = "")
    private Integer score ;
    private String showcontent ;
}
