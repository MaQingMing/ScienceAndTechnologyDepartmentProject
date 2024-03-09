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
 * 科技成果奖;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
 @Data
@ApiModel(value = "科技成果奖",description = "")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@TableName("achievement_standard")
public class AchievementStandard extends BaseEntity {
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "asid", type = IdType.AUTO)
    private Integer asid ;
    /** 项目级别 */
    @ApiModelProperty(name = "项目级别id",notes = "")
    private String leid ;
    /** 项目描述 */
    @ApiModelProperty(name = "项目描述",notes = "")
    private String context ;
    /** 科技分 */
    @ApiModelProperty(name = "科技分",notes = "")
    private Integer score ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remarks ;
    /** 状态(1启用  0不启用) */
    @ApiModelProperty(name = "状态(1启用  0不启用)",notes = "")
    private String status ;
    /** 是否可以换现(1可以 0不可以) **/
    @ApiModelProperty(name = "是否可以换现(1可以 0不可以)",notes = "")
    private String cash;
    /** 认定部门 */
    @ApiModelProperty(name = "认定部门",notes = "")
    private String posit ;
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    private Integer trid ;

}