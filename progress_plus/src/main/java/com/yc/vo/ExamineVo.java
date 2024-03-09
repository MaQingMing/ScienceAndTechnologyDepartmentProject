package com.yc.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/12/12 10:25
 */
@Data
public class ExamineVo {
        /** 主键Id */
//        @ApiModelProperty(name = "主键Id",notes = "")
//        @TableId(value = "eid", type = IdType.AUTO)
//        private Integer eid ;
        /** 考核标题 */
        @ApiModelProperty(name = "考核标题",notes = "")
        private String title ;
//        /** 状态( 0 未开始  1 考核中  2 结束 ) */
//        @ApiModelProperty(name = "状态( 0 待开始  1 考核中  2 结束  3 公示  4 结束公示 )",notes = "")
//        private Integer status ;
        /** 是否可以预支 明年的科技分 */
        @ApiModelProperty(name = "是否可以预支 明年的科技分",notes = "")
        private String advance;
        /** 考核期间是否 可以申请科技成果 */
        @ApiModelProperty(name = "考核期间是否 可以申请科技成果",notes = "")
        private String confirm;
        /** 开始日期 */
        @ApiModelProperty(name = "考核时间段开始期日",notes = "")
        private String startDate ;
        /** 结束日期 */
        @ApiModelProperty(name = "考核时间段结束日期",notes = "")
        private String endDate ;
        /** 开始考核日期 */
        @ApiModelProperty(name = "开始考核日期",notes = "")
        private String beginDate;
        /** 结束考核日期 */
        @ApiModelProperty(name = "结束考核日期",notes = "")
        private String finishDate;
        /** 结束公示日期 */
        @ApiModelProperty(name = "结束公示日期",notes = "")
        private String publicityDate;
        /** 考核总人数 */
        @ApiModelProperty(name = "考核总人数",notes = "")
        private Integer total ;
//        /** 合格人数 */
//        @ApiModelProperty(name = "合格人数",notes = "")
//        private Integer qualified ;
//        /** 不合格人数 */
//        @ApiModelProperty(name = "不合格人数",notes = "")
//        private Integer unqualified ;
//        /** 合格率 */
//        @ApiModelProperty(name = "合格率",notes = "")
//        private String passrate ;
        /** 备注 */
        @ApiModelProperty(name = "备注", notes = "")
        private String remarks;
        /** 特殊情况不参加考核(1,2,3) */
        @ApiModelProperty(name = "特殊情况不参加考核(1,2,3)", notes = "")
        private String special;
        @ApiModelProperty(name = "不参加原因", notes = "")
        private String nonRemarks;
        @ApiModelProperty(name = "不参加人员", notes = "")
        private String noNtotal;
    }


