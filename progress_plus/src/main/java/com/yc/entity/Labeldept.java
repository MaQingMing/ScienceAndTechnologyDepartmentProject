
package com.yc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
import lombok.Data;
 /**
 * 部门表;
 * @author : http://www.chiner.pro
 * @date : 2023-11-6
 */
@Data
@ApiModel(value = "部门表",description = "")
@TableName("labeldept")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Labeldept extends BaseEntity {

    /** 部门id */
    @ApiModelProperty(name = "部门id",notes = "")
//    @TableId(value = "tid", type = IdType.AUTO)
//    private String tid ;
    @TableId(value = "tid", type = IdType.AUTO)
    private Long tid ;

    /** 部门名称 */
    @ApiModelProperty(name = "部门名称",notes = "")
    private String tname ;

    /** 用于表示该学院是(社科 1 |自科 2 ) */
    @ApiModelProperty(name = "用于表示该学院是(社科 1 |自科 2 )",notes = "")
    private Integer tscience ;
     /** 科技分认定部门 1 是  null 或 0 不是 */
    @ApiModelProperty(name = "科技分认定部门 1 是  null 或 0 不是",notes = "")
    private  Integer status;


}