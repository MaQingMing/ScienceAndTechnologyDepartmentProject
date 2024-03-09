package com.yc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author zhupanlin
 * @version 1.0
 * @description: TODO
 * @date 2023/10/25 18:36
 */
@Data
@ApiModel(value = "科技成果级别和类别名",description = "")
public class TechResultsLevelAndTrname {

    /** 主键Id */
    @ApiModelProperty(name = "级别Id",notes = "")
    private Integer leid ;
    /** 科技成果级别名称 */
    @ApiModelProperty(name = "科技成果级别名称",notes = "")
    private String lname ;
    @ApiModelProperty(name = "类型id",notes = "")
    private Integer trid;
    /** 科技成果类别名称 */
    @ApiModelProperty(name = "类型名称", notes = "")
    private String trname;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remarks ;
    /** 是否可以换现(1可以 0不可以) **/
    @ApiModelProperty(name = "是否可以换现(1可以 0不可以)",notes = "")
    private String cash;
    /** 状态(1启用  0不启用) */
    @ApiModelProperty(name = "级别状态(1启用 0不启用)",notes = "")
    private String status ;

}
