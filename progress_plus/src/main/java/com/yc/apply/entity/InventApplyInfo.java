package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

 /**
 * 专利申请详情;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
 @Data
@ApiModel(value = "专利申请详情",description = "")
@TableName("invent_apply_info")
public class InventApplyInfo extends BaseEntity {

    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "iaid", type = IdType.AUTO)
    private Integer iaid ;
    /** 专利申请号（授权号） */
    @ApiModelProperty(name = "专利授权号",notes = "")
    private String authorization ;
    /** 阶段（授权、转让） */
    @ApiModelProperty(name = "阶段（授权、转让）",notes = "")
    private String stage ;
    /** 专利名称 */
    @ApiModelProperty(name = "专利名称",notes = "")
    private String name ;
    /** 专利类型 */
    @ApiModelProperty(name = "专利类型",notes = "")
    private String type ;
    /** 转让金额 */
    @ApiModelProperty(name = "转让金额",notes = "")
    private Double money ;
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    private Integer gaid ;
    /** 计算依据 */
    @ApiModelProperty(name = "计算依据",notes = "")
    private String according ;
    /** 计分 */
    @ApiModelProperty(name = "计分",notes = "")
    private Double score ;
     /** 高校名称 */
     @ApiModelProperty(name = "高校名称",notes = "")
     private String school ;
     /** 年份 */
     @ApiModelProperty(name = "年份",notes = "")
     private String year ;
     /** 所在单位 */
     @ApiModelProperty(name = "所在单位",notes = "")
     private String locationUnit ;
     /** 授权日期 */
     @ApiModelProperty(name = "授权日期",notes = "")
     private String authorizeTime;
     /** 授权日期 */
     @ApiModelProperty(name = "申请日期",notes = "")
     private String applyTime;
     /** 合作类型 */
     @ApiModelProperty(name = "合作类型",notes = "")
     private String concurType;
     /** 学校署名 */
     @ApiModelProperty(name = "学校署名",notes = "")
     private String schoolSign;
     /** 专利范围 */
     @ApiModelProperty(name = "专利范围",notes = "")
     private String scope;
     /** 专利权状态 */
     @ApiModelProperty(name = "专利权状态",notes = "")
     private String patentStatus;
     /** 专利权人 */
     @ApiModelProperty(name = "专利权人",notes = "")
     private String powerMan;
     /** 专利申请号 */
     @ApiModelProperty(name = "专利申请号",notes = "")
     private String applyNumber;
}