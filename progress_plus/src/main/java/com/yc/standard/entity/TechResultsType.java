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
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Data
@ApiModel(value = "科技成果分类", description = "")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@TableName("tech_results_type")
public class TechResultsType extends BaseEntity {
    /**
     * 主键Id
     */
    @ApiModelProperty(name = "主键Id", notes = "")
    @TableId(value = "trid", type = IdType.AUTO)
    private Integer trid;
    /**
     * 科技成果类别名称
     */
    @ApiModelProperty(name = "科技成果类别名称", notes = "")
    private String trname;
    /**
     * 备注
     */
    @ApiModelProperty(name = "备注", notes = "")
    private String remarks;
    /**
     * 状态(1启用  0不启用)
     */
    @ApiModelProperty(name = "状态(1启用  0不启用)", notes = "")
    private String status;

    /**
     * 计分文件依据
     */
    @ApiModelProperty(name = "计分文件依据", notes = "")
    private String according;
}