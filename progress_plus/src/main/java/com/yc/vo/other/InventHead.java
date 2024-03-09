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
public class InventHead {
    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","序号"}, index = 0)
    @ColumnWidth(value = 10)
    private String id;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","所属二级学院"}, index = 1)
    @ColumnWidth(value = 20)
    private String userDept;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","高校名称"}, index = 2)
    @ColumnWidth(value = 20)
    private String school;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","年份"}, index = 3)
    @ColumnWidth(value = 10)
    private String year;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","专利名称"}, index = 4)
    @ColumnWidth(value = 20)
    private String name;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","专利类型"}, index = 5)
    @ColumnWidth(value = 20)
    private String type;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","专利申请号"}, index = 6)
    @ColumnWidth(value = 10)
    private String applyNumber;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","申请日期"}, index = 7)
    @ColumnWidth(value = 20)
    private String applyTime;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","所在单位"}, index = 8)
    @ColumnWidth(value = 20)
    private String locationUnit;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","姓名"}, index = 9)
    @ColumnWidth(value = 10)
    private String username;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","专利授权号"}, index = 10)
    @ColumnWidth(value = 20)
    private String authorization;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","授权日期"}, index = 11)
    @ColumnWidth(value = 20)
    private String authorizationTime;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","专利权人"}, index = 12)
    @ColumnWidth(value = 10)
    private String powerMan;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","合作类型"}, index = 13)
    @ColumnWidth(value = 20)
    private String concurType;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","学校署名"}, index = 14)
    @ColumnWidth(value = 20)
    private String schoolSign;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","专利范围"}, index = 15)
    @ColumnWidth(value = 10)
    private String scope;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","专利权状态"}, index = 16)
    @ColumnWidth(value = 20)
    private String patentStatus;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","备注"}, index = 17)
    @ColumnWidth(value = 20)
    private String remarks;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","授权/转让"}, index = 18)
    @ColumnWidth(value = 10)
    private String stage;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","转让金额"}, index = 19)
    @ColumnWidth(value = 20)
    private String money;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","计分"}, index = 20)
    @ColumnWidth(value = 20)
    private String score;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（专利）","计算依据"}, index = 21)
    @ColumnWidth(value = 10)
    private String according;
}
