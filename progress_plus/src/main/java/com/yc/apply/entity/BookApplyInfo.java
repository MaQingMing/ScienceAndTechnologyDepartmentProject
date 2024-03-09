package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


 /**
 * 学术专著申请详情;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
 @Data
@ApiModel(value = "学术专著申请详情",description = "")
@TableName("book_apply_info")
public class BookApplyInfo extends BaseEntity {

    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "baiid", type = IdType.AUTO)
    private Integer baiid ;
    /** 著作名称 */
    @ApiModelProperty(name = "著作名称",notes = "")
    private String name ;
    /** 出版单位 */
    @ApiModelProperty(name = "出版单位",notes = "")
    private String pressName ;
    /** 著作类型 */
    @ApiModelProperty(name = "著作类型",notes = "")
    private String academicType ;
    /** 字数(千字) */
    @ApiModelProperty(name = "字数(千字)",notes = "")
    private String wordsNum ;
    /** 作者排序      第几、编著、教材、专著 */
    @ApiModelProperty(name = "作者排序      第几、编著、教材、专著",notes = "")
    private String order ;
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    private Integer gaid ;
    /** 计算依据 */
    @ApiModelProperty(name = "计算依据",notes = "")
    private String according ;
    /** 计分 */
    @ApiModelProperty(name = "计分",notes = "")
    private Double score ;

}