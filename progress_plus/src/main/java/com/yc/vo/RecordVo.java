package com.yc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/11/20 16:42
 */
@Data
public class RecordVo {

    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
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
    @ApiModelProperty(name = "附件(图片/pdf)",notes = "")
    private String filePath ;
    @ApiModelProperty(name = "上传文件类型",notes = "")
    private String fileType;
    /** 附件原文件名 */
    @ApiModelProperty(name = "附件原文件名",notes = "")
    private String fileName ;
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
    @ApiModelProperty(name = "团队成员姓名" ,notes="")
    private List<Map<String,Object>> teamNames;
    @ApiModelProperty(name = "查询总数" ,notes="")
    private Integer total;
    @ApiModelProperty(name = "大类型名称" ,notes="")
    private String trname;
    @ApiModelProperty(name = "小类型" ,notes="")
    private String lname;
    @ApiModelProperty(name = "项目类型及leid" ,notes = "")
    private List<Map<String,Object>> lnamesAndLeid;
}
