package com.yc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountScoreVo {

    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    private Integer asid ;
    /** 普通用户Id */
    private Integer sysid ;
    /** 申请applyId */
    private Integer applyid;
    /** 考核eId */
    private Integer eid;
    /** 分数 */
    private Double score ;
    /** 类型(  1加分 (记录 applyId )  2减分 (记录 eId ) ) */
    private Integer type ;
    /** 状态( 1 可换钱的分数 2不可换钱的分数) */
    private Integer status ;
    /** 创建时间 */
    private String createDate;
    /** 科技成果类别名称 */
    private String trname ;
    /** 科技成果级别名称 */
    private String lname ;
    /** 项目名称 */
    private String pname ;
    /** 备注 */
    private String remark ;
}
