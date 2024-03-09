package com.yc.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 备案表;
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
 @Data
@ApiModel(value = "备案表",description = "")
@TableName(value = "record" ,autoResultMap = true)
 @Component
public class Record extends BaseEntity {
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id ;
    /** 备案名称 */
    @ApiModelProperty(name = "备案名称",notes = "")
    private String name ;
    /** 备案状态( 1通过   0不通过) */
    @ApiModelProperty(name = "备案状态( 1通过   0不通过)",notes = "")
    private Integer status ;
    /** 驳回理由 */
    @ApiModelProperty(name = "驳回理由",notes = "")
    private String rejection ;
    /** 备案日期 */
    @ApiModelProperty(name = "备案日期",notes = "")
    private String date ;
    /** 备案团队或个人ID( 1,2,3 ) */
    @ApiModelProperty(name = "备案团队或个人ID( 1,2,3 )",notes = "")
    private String teamId ;
    /** 附件(图片/pdf) */
    private String fileType;
    /** 备案描述或备注信息 */
    @ApiModelProperty(name = "备案描述或备注信息",notes = "")
    private String description ;
    /** 备案是否被使用(applyId) */
    @ApiModelProperty(name = "备案是否被使用(applyId)",notes = "")
    private Integer consume ;
    @ApiModelProperty(name = "备案的项目类型ID",notes = "")
    private Integer standardType;
    /** 备案的成果类型ID */
    @ApiModelProperty(name = "备案的成果类型ID",notes = "")
    private Integer standardId ;

}