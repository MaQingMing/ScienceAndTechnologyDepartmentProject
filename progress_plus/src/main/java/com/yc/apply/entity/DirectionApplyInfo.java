package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

 /**
 * 纵向申请详情;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
 @Data
@ApiModel(value = "纵向申请详情",description = "")
@TableName("direction_apply_info")
public class DirectionApplyInfo extends BaseEntity {
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "daiid", type = IdType.AUTO)
    private Integer daiid ;
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    private Integer gaid ;
    /** 项目名称 **/
    @ApiModelProperty(name = "项目名称", notes = "")
    private String name;
     /** 项目阶段 **/
     @ApiModelProperty(name = "项目阶段(申报,立项)", notes = "")
     private String stage;
    /** 项目性质 */
    @ApiModelProperty(name = "项目性质",notes = "")
    private String type ;
    /** 项目级别 */
    @ApiModelProperty(name = "项目级别id",notes = "")
    private String leid ;
    /** 文号 */
    @ApiModelProperty(name = "文号",notes = "")
    private String file ;
    /** 项目编号 */
    @ApiModelProperty(name = "项目编号",notes = "")
    private String identifier ;
    /** 批准立项部门 */
    @ApiModelProperty(name = "批准立项部门",notes = "")
    private String dept ;
    /** 计算依据 */
    @ApiModelProperty(name = "计算依据",notes = "")
    private String according ;
     /** 高校名称 **/
     @ApiModelProperty(name = "高校名称", notes = "")
     private String school;
     /** 学科门类 **/
     @ApiModelProperty(name = "学科门类", notes = "")
     private String subjectCategory;
     /** 立项年度 **/
     @ApiModelProperty(name = "立项年度", notes = "")
     private String startProjectYear;
     /** 所属单位 **/
     @ApiModelProperty(name = "所属单位", notes = "")
     private String belongUnit;
     /** 申报批准时间 **/
     @ApiModelProperty(name = "申报批准时间", notes = "")
     private String declareApproveTime;
     /** 开始时间 **/
     @ApiModelProperty(name = "开始时间", notes = "")
     private String startTime;
     /** 计划完成时间 **/
     @ApiModelProperty(name = "计划完成时间", notes = "")
     private String planFinishTime;
     /** 总经费 **/
     @ApiModelProperty(name = "总经费", notes = "")
     private Double totalFund;
     /** 到账总经费 **/
     @ApiModelProperty(name = "到账总经费", notes = "")
     private Double receiptTotalFund;
     /** 承担单位 **/
     @ApiModelProperty(name = "承担单位", notes = "")
     private String bearUnit;
     /** 项目状态 **/
     @ApiModelProperty(name = "项目状态", notes = "")
     private String projectStatus;
     /** 完成日期 **/
     @ApiModelProperty(name = "完成日期", notes = "")
     private String completeTime;
     /** 学科分类 **/
     @ApiModelProperty(name = "学科分类", notes = "")
     private String subjectType;
     /** 国民经济行业分类 **/
     @ApiModelProperty(name = "国民经济行业分类", notes = "")
     private String nationalEconomyCategory;
}