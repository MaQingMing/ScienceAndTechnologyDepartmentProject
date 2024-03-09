package com.yc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;

/**
 * 文件表;
 * @author : http://www.chiner.pro
 * @date : 2023-11-17
 */
@Data
@ApiModel(value = "文件表",description = "")
@TableName("prove_file")
public class ProveFile extends BaseEntity {

    /** 主键 */
    @ApiModelProperty(name = "主键",notes = "")
    @TableId(value = "pfid", type = IdType.AUTO)
    private Integer pfid ;

    /** 1 申请  2 备案 */
    @ApiModelProperty(name = "1 申请  2 备案",notes = "")
    private Integer status ;

    /** 文件路径 */
    @ApiModelProperty(name = "文件路径",notes = "")
    private String path ;
    /** 源文件名 */
    @ApiModelProperty(name = "源文件名",notes = "")
    private String fileName ;
    /** 文件类型 */
    @ApiModelProperty(name = "文件类型",notes = "")
    private String fileType ;
    /** 文件大小 */
    @ApiModelProperty(name = "文件大小",notes = "")
    private Double fileSize ;
    /** 使用者Id */
    @ApiModelProperty(name = "使用者Id",notes = "")
    private Integer useid ;

    /** 是否可以直接显示 1-可以  0-不行 */
    @TableField(exist = false)
    private Integer isShow;

    public ProveFile() {
    }

    public ProveFile(Integer status, String path, String fileName, Integer useid) {
        this.status = status;
        this.path = path;
        this.fileName = fileName;
        this.useid = useid;
    }

    public ProveFile(Integer status, String path, String fileName, String fileType, Double fileSize, Integer useid) {
        this.status = status;
        this.path = path;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.useid = useid;
    }
}