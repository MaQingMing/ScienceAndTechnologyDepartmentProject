package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

 /**
 * 横向申请详情;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
 @Data
@ApiModel(value = "横向申请详情",description = "")
@TableName("transverse_apply_info")
public class TransverseApplyInfo extends BaseEntity {

    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "taiid", type = IdType.AUTO)
    private String taiid ;
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    private Integer gaid ;
    /** 经费卡号 */
    @ApiModelProperty(name = "经费卡号",notes = "")
    private String card ;
    /** 科研项目名称 */
    @ApiModelProperty(name = "科研项目名称",notes = "")
    private String name ;
    /** 经费进账 */
    @ApiModelProperty(name = "经费进账",notes = "")
    private String money ;
    /** 计算依据 */
    @ApiModelProperty(name = "计算依据",notes = "")
    private String according ;
    /** 项目级别 */
    @ApiModelProperty(name = "项目级别",notes = "")
    private String leid ;
    /** 计分 */
    @ApiModelProperty(name = "计分",notes = "")
    private Double score ;
    /** 我校是否为第一署名单位 **/
    @ApiModelProperty(name = "我校是否为第一署名单位", notes = "")
    private Integer firstSign;

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
     //@ApiModelProperty(name = "到账总经费", notes = "")
     //private Integer receiptTotalFund;
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
     /** 合同编号 **/
     @ApiModelProperty(name = "合同编号", notes = "")
     private String contractNumber;
     /** 合同签订日期 **/
     @ApiModelProperty(name = "合同签订日期", notes = "")
     private String signContractTime;
     /** 项目编号 **/
     @ApiModelProperty(name = "项目编号", notes = "")
     private String identifier;
     /** 项目类别 **/
     @ApiModelProperty(name = "项目类别", notes = "")
     private String type;
     /** 项目来源单位 **/
     @ApiModelProperty(name = "项目来源单位", notes = "")
     private String sourceUnit;
}