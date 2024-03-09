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
public class AchievementHead {
    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","序号"}, index = 0)
    @ColumnWidth(value = 10)
    private String id;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","姓名"}, index = 1)
    @ColumnWidth(value = 20)
    private String username;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","获奖形式及内容"}, index = 2)
    @ColumnWidth(value = 20)
    private String content;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","批准单位"}, index = 3)
    @ColumnWidth(value = 10)
    private String dept;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","获奖项目名称"}, index = 4)
    @ColumnWidth(value = 20)
    private String name;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","获奖等级"}, index = 5)
    @ColumnWidth(value = 20)
    private String level;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","计算依据"}, index = 6)
    @ColumnWidth(value = 10)
    private String according;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","计分"}, index = 7)
    @ColumnWidth(value = 20)
    private String score;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","所属二级学院"}, index = 8)
    @ColumnWidth(value = 20)
    private String userDept;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","备注"}, index = 9)
    @ColumnWidth(value = 10)
    private String remarkds;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","获奖级别"}, index = 10)
    @ColumnWidth(value = 20)
    private String grade;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","是否是第一申报单位"}, index = 11)
    @ColumnWidth(value = 20)
    private String firstSign;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","我校排名"}, index = 12)
    @ColumnWidth(value = 20)
    private String schoolOrder;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计分表（成果奖）","教职工排名"}, index = 13)
    @ColumnWidth(value = 20)
    private String worksOrder;
}
