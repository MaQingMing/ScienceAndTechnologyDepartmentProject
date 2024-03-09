package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 /**
 * 论文申请详情;
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Data
@ApiModel(value = "论文申请详情", description = "")
@TableName("paper_apply_info")
public class PaperApplyInfo extends BaseEntity {
    /**
     * 主键Id
     */
    @ApiModelProperty(name = "主键Id", notes = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer paiid;
    /**
     * 论文名称
     */
    @ApiModelProperty(name = "论文名称", notes = "")
    private String name;
    /**
     * 发表刊物名
     */
    @ApiModelProperty(name = "发表刊物名", notes = "")
    private String periodicalName;
    /**
     * 期号
     */
    @ApiModelProperty(name = "期号", notes = "")
    private String cnnum;
    /**
     * 作者排序
     */
    @ApiModelProperty(name = "作者排序", notes = "")
    private String order;
    /**
     * 研究院
     */
    @ApiModelProperty(name = "研究院", notes = "")
    private String institute;
    /**
     * 主键Id
     */
    @ApiModelProperty(name = "主键Id", notes = "")
    private Integer gaid;
    /**
     * 计算依据
     */
    @ApiModelProperty(name = "计算依据", notes = "")
    private String according;
    /**
     * 计分
     */
    @ApiModelProperty(name = "计分", notes = "")
    private Double score;
}