package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


 /**
 * 账户分数明细表;
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
 @Data
@ApiModel(value = "账户分数明细表",description = "")
@TableName("account_score")
public class AccountScore extends BaseEntity {
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "asid", type = IdType.AUTO)
    private Integer asid ;
    /** 普通用户Id */
    @ApiModelProperty(name = "普通用户Id",notes = "")
    private String sysid ;
    /** 申请applyId */
    @ApiModelProperty(name = "申请Id",notes = "")
    private String applyid;
    /** 考核eId */
    @ApiModelProperty(name = "考核eId",notes = "")
    private Integer eid;
    /** 类型( 1加分 (记录 applyId )  2减分 (记录 eId ) -1 (记录被驳回) ) */
    @ApiModelProperty(name = "类型",notes = "")
    private Integer type;
    /** 状态( 1 可换钱的分数 2不可换钱的分数  3 考核时提前抵扣的分数(默认为不可换钱的分数)  ) */
    @ApiModelProperty(name = "状态",notes = "")
    private Integer status ;
    /** 分数 */
    @ApiModelProperty(name = "分数",notes = "")
    private Double score ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;


}