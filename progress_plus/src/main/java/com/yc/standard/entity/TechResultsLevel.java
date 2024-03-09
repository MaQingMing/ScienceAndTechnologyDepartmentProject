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
* 科技成果分类;
* @author : http://www.chiner.pro
* @date : 2023-10-17
*/
@Data
@ApiModel(value = "科技成果分类",description = "")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@TableName("tech_results_level")
public class TechResultsLevel extends BaseEntity {
   /** 主键Id */
   @ApiModelProperty(name = "级别Id",notes = "")
   @TableId(value = "leid", type = IdType.AUTO)
   private Integer leid ;
   /** 科技成果类别名称 */
   @ApiModelProperty(name = "科技成果级别名称",notes = "")
   private String lname ;
   @ApiModelProperty(name = "类型id",notes = "")
   private Integer trid;
   /** 备注 */
   @ApiModelProperty(name = "备注",notes = "")
   private String remarks ;
   /** 状态(1启用  0不启用) */
   @ApiModelProperty(name = "级别状态(1启用 0不启用)",notes = "")
   private String status ;

}