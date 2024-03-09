package com.yc.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "管理员表",description = "")
@TableName(value = "governuser",autoResultMap = true)
public class GovernVo extends BaseEntity {
    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id ;
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
    /** 所属部门 */
    @ApiModelProperty(name = "所属部门",notes = "")
    private Integer tid ;
    /** 职位 */
    @ApiModelProperty(name = "职位",notes = "")
    private String job ;
    /** 角色 */
    @ApiModelProperty(name = "角色",notes = "")
    private String role ;


    /** 所属学院(部门) */
    @TableField(exist = false)
    private String deptname;
    /** 职位 */
    @TableField(exist = false)
    private String rolename ;

}
