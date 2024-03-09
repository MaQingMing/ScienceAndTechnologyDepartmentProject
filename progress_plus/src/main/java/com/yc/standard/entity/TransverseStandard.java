package com.yc.standard.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

 /**
 * 横向项目;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
 @Data
@ApiModel(value = "横向项目",description = "")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@TableName("transverse_standard")
public class TransverseStandard extends BaseEntity{
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "tsid", type = IdType.AUTO)
    private Integer tsid ;
    /** 到账经费 */
    @ApiModelProperty(name = "到账经费",notes = "")
    private Double finance ;
    /** 项目类型(自科|社科) */
    @ApiModelProperty(name = "项目类型(自科|社科)",notes = "")
    private String type ;
    /** 项目描述 */
    @ApiModelProperty(name = "项目描述",notes = "")
    private String context ;
    /** 科技分 */
    @ApiModelProperty(name = "科技分",notes = "")
    private Integer score ;
    /** 状态(1启用  0不启用) */
    @ApiModelProperty(name = "状态(1启用  0不启用)",notes = "")
    private String status ;
    /** 是否可以换现(1可以 0不可以) **/
    @ApiModelProperty(name = "是否可以换现(1可以 0不可以)",notes = "")
    private String cash;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remarks ;
    /** 认定部门 */
    @ApiModelProperty(name = "认定部门",notes = "")
    private String posit ;
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    private Integer trid ;
}