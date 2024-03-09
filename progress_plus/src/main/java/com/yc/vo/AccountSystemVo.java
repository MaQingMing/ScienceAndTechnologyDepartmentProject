package com.yc.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AccountSystemVo {
    /** 总科技分 */
    @ApiModelProperty(name = "总科技分",notes = "")
    private Double total ;
    /** 可换酬金的科技分 */
    @ApiModelProperty(name = "可换酬金的科技分",notes = "")
    private Double exchange ;
    /** 普通科技分 */
    @ApiModelProperty(name = "普通科技分",notes = "")
    private Double ordinary ;
    /** 借贷科技分 */
    @ApiModelProperty(name = "借贷科技分",notes = "")
    private Double loan ;
    /** 是否归属研究院 */
    @ApiModelProperty(name = "是否归属研究院",notes = "")
    private Integer academy ;
    /** 是否承担研究院管理工作 */
    @ApiModelProperty(name = "是否承担研究院管理工作",notes = "")
    private Integer academyCare ;
    /** 底分 /基础分 */
    @ApiModelProperty(name = "底分",notes = "")
    private  Double baseScore;
    /** 职位 */
    @ApiModelProperty(name = "职位",notes = "")
    private String job ;
}
