package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 底分表;
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
@Data
@ApiModel(value = "底分表",description = "")
@TableName("base_score")
public class BaseScore extends BaseEntity {

    /** 主键Id */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(name = "主键Id",notes = "")
    private Integer bsid ;
    /** 普通用户Id */
    @ApiModelProperty(name = "普通用户Id",notes = "")
    private Integer sysid ;
    /** 底分 */
    @ApiModelProperty(name = "底分",notes = "")
    private Double score ;
    /** 1启用 0废弃 */
    @ApiModelProperty(name = "1启用 0废弃",notes = "")
    private Integer status ;
    /** 职位和学位  [1,2,3] */
    @ApiModelProperty(name = "职位和学位  [1,2,3]",notes = "")
    private String jobs;

}