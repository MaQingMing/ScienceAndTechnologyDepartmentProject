package com.yc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

 /**
 * 职位/学位;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
 @Data
@ApiModel(value = "学历/职称",description = "")
@TableName("t_job")
public class TJob extends BaseEntity{
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id ;
    /** 职位名称 */
    @ApiModelProperty(name = "职位名称",notes = "")
    private String context ;
    /** 学历-1,职称-2 */
    @ApiModelProperty(name = "学历-1,职称-2",notes = "")
    private Integer status;
    /** 分数 */
    @ApiModelProperty(name = "分数",notes = "")
    private Integer score ;

}