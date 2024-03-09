package com.yc.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhupanlin
 * @version 1.0
 * @description: TODO
 * @date 2023/11/1 18:20
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ApiModel(value = "科技成果项目编辑类",description = "")
public class EditProject extends BaseEntity {

    /** 主键id **/
    @ApiModelProperty(name = "主键id",notes = "")
    private Integer id;

    /** 级别id **/
    @ApiModelProperty(name = "级别id",notes = "")
    private Integer leid;

    /** 级别名 **/
    @ApiModelProperty(name = "级别名",notes = "")
    private String lname;

    /* 项目描述 */
    @ApiModelProperty(name = "项目描述",notes = "")
    private String context;

    /* 科技分 */
    @ApiModelProperty(name = "科技分",notes = "")
    private Integer score;

    /* 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remarks;

    /* 到账经费 */
    @ApiModelProperty(name = "项目类型(自科|社科)",notes = "")
    private String type;

    /* 状态(1启用 0不启用) */
    @ApiModelProperty(name = "状态(1启用 0不启用)",notes = "")
    private Integer status;

    /* 	认定部门ID */
    @ApiModelProperty(name = "认定部门ID",notes = "")
    private String posit;

    /* 	认定部门ID */
    @ApiModelProperty(name = "认定部门名称",notes = "")
    private String tname;

    /* 类型id */
    @ApiModelProperty(name = "类型id",notes = "")
    private Integer trid;

    /** 申报科技分 */
    @ApiModelProperty(name = "申报科技分",notes = "")
    private Integer declareScore ;

    /* 立项科技分 */
    @ApiModelProperty(name = "立项科技分",notes = "")
    private Integer foundScore;

    /* 验收科技分 */
    @ApiModelProperty(name = "验收科技分",notes = "")
    private Integer checkScore;

    /* 项目阶段 */
    @ApiModelProperty(name = "项目阶段",notes = "")
    private String stage;

    /** 是否可以换现(1可以 0不可以) **/
    @ApiModelProperty(name = "是否可以换现(1可以 0不可以)",notes = "")
    private String cash;

    /** 最高分 **/
    @ApiModelProperty(name = "最高分", notes = "")
    private Integer maxScore;
}
