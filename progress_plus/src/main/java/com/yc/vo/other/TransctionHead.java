package com.yc.vo.other;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Builder;
import lombok.Data;

/**
 * @author 邓弈2102
 * @version 1.0
 * @description: TODO
 * @date 2023/12/29
 */
@Data
@Builder
@HeadRowHeight(value = 20)
public class TransctionHead {
    @ExcelProperty(value = {"湖南工学院专业技术人员科技工作计分表（横向项目）","序号"}, index = 0)
    @ColumnWidth(value = 10)
    private String id;

    @ExcelProperty(value = {"湖南工学院专业技术人员科技工作计分表（横向项目）","所属二级学院"}, index = 1)
    @ColumnWidth(value = 20)
    private String userDept;

    @ExcelProperty(value = {"湖南工学院专业技术人员科技工作计分表（横向项目）","高校名称"}, index = 2)
    @ColumnWidth(value = 20)
    private String school;

    @ExcelProperty(value = {"湖南工学院专业技术人员科技工作计分表（横向项目）","自然/社科"}, index = 3)
    @ColumnWidth(value = 10)
    private String detailLevel;

    @ExcelProperty(value = {"湖南工学院专业技术人员科技工作计分表（横向项目）","学科门类"}, index = 4)
    @ColumnWidth(value = 20)
    private String subjectCategory;

    @ExcelProperty(value = {"湖南工学院专业技术人员科技工作计分表（横向项目）","立项年度"}, index = 5)
    @ColumnWidth(value = 20)
    private String year;

    @ExcelProperty(value = {"湖南工学院专业技术人员科技工作计分表（横向项目）","所属单位"}, index = 6)
    @ColumnWidth(value = 10)
    private String belongUnit;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","科研项目名称"}, index = 7)
    @ColumnWidth(value = 20)
    private String name;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","项目来源单位"}, index = 8)
    @ColumnWidth(value = 20)
    private String sourceUnit;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","承担单位"}, index = 9)
    @ColumnWidth(value = 10)
    private String bearUnit;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","项目类别"}, index = 10)
    @ColumnWidth(value = 20)
    private String type;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","主持人"}, index = 11)
    @ColumnWidth(value = 20)
    private String username;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","合同签订时间"}, index = 12)
    @ColumnWidth(value = 10)
    private String signContractTime;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","开始时间"}, index = 13)
    @ColumnWidth(value = 20)
    private String startTime;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","计划完成时间"}, index = 14)
    @ColumnWidth(value = 20)
    private String planFinishTime;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","项目编号"}, index = 15)
    @ColumnWidth(value = 10)
    private String identifier;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","总经费"}, index = 16)
    @ColumnWidth(value = 20)
    private String totalFund;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","到账经费"}, index = 17)
    @ColumnWidth(value = 20)
    private String receiptTotalFund;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","经费卡号"}, index = 18)
    @ColumnWidth(value = 10)
    private String card;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","合同编号"}, index = 19)
    @ColumnWidth(value = 10)
    private String contractNumber;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","项目状态"}, index = 20)
    @ColumnWidth(value = 20)
    private String projectStatus;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","完成日期"}, index = 21)
    @ColumnWidth(value = 20)
    private String completeTime;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","学科分类"}, index = 22)
    @ColumnWidth(value = 10)
    private String subjectType;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","国名经济行业分类"}, index = 23)
    @ColumnWidth(value = 20)
    private String nationalEconomyCategory;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","我校是否为第一署名单位"}, index = 24)
    @ColumnWidth(value = 20)
    private String firstSign;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","计算依据"}, index = 25)
    @ColumnWidth(value = 20)
    private String according;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","计分"}, index = 26)
    @ColumnWidth(value = 10)
    private String score;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（横向项目）","备注"}, index = 27)
    @ColumnWidth(value = 20)
    private String remarks;
}
