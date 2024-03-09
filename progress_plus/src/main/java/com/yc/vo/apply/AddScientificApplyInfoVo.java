package com.yc.vo.apply;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/11/24 21:16
 */
@Data
public class AddScientificApplyInfoVo {
    @ApiModelProperty(name = "项目名称",notes = "")
    private String name;
    @ApiModelProperty(name = "批准单位",notes = "")
    private String department;
    @ApiModelProperty(name = "备案ID",notes = "")
    private Integer recordId;
    @ApiModelProperty(name = "备注",notes = "")
    private String remark;
    @ApiModelProperty(name = "验收 立项",notes = "")
    private String xmlory;
    @ApiModelProperty(name = "具体分数分配" ,notes = "")
    private String xmpeople;
    @ApiModelProperty(name = "项目类型" ,notes = "")
    private String lname;
    @ApiModelProperty(name = "项目类型id" ,notes = "")
    private Integer leid;
    @ApiModelProperty(name = "申请时间",notes = "")
    private String date;
    @ApiModelProperty(name = "申请人id",notes = "")
    private Integer id;
    @ApiModelProperty(name = "是否可以换钱",notes = "")
    private Integer cash;
//    @ApiModelProperty(name = "当前用户",notes = "")
//    private String defaultUser;


}
