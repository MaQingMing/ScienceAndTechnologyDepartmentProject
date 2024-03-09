package com.yc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SystemUserVo {
    /** 用户名 */
    @ApiModelProperty(name = "用户名",notes = "")
    private String username ;
    /** 密码 */
    @ApiModelProperty(name = "密码",notes = "")
    private String password ;
    /** 昵称 */
    @ApiModelProperty(name = "昵称",notes = "")
    private String nickname ;
    /** 手机号 */
    @ApiModelProperty(name = "手机号",notes = "")
    private String phone ;
    /** 学院名称 */
    @ApiModelProperty(name = "学院名称",notes = "")
    private String tname ;
    /** 职位 */
    @ApiModelProperty(name = "职位",notes = "")
    private String context ;
    /** 底分 */
    @ApiModelProperty(name = "底分",notes = "")
    private Double baseScore ;
    /** 创建人 */
    @ApiModelProperty(name = "创建人",notes = "")
    private String createBy ;
    /** 修改人 */
    @ApiModelProperty(name = "修改人",notes = "")
    private String updateBy ;
}
