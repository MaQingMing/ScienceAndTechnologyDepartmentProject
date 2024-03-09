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
public class BookHead {
    @ExcelProperty(value = {"2021年湖南工学院专业技术人员科技工作计分表（著作）", "序号"}, index = 0)
    @ColumnWidth(value = 10)
    private String id;

    @ExcelProperty(value = {"2021年湖南工学院专业技术人员科技工作计分表（著作）", "著作人"}, index = 1)
    @ColumnWidth(value = 10)
    private String username;

    @ExcelProperty(value = {"2021年湖南工学院专业技术人员科技工作计分表（著作）", "著作名称"}, index = 2)
    @ColumnWidth(value = 10)
    private String name;

    @ExcelProperty(value = {"2021年湖南工学院专业技术人员科技工作计分表（著作）", "出版单位"}, index = 3)
    @ColumnWidth(value = 10)
    private String pressName;

    @ExcelProperty(value = {"2021年湖南工学院专业技术人员科技工作计分表（著作）", "著作类型"}, index = 4)
    @ColumnWidth(value = 10)
    private String type;

    @ExcelProperty(value = {"2021年湖南工学院专业技术人员科技工作计分表（著作）", "字数(千字)"}, index = 5)
    @ColumnWidth(value = 10)
    private String wordsNum;

    @ExcelProperty(value = {"2021年湖南工学院专业技术人员科技工作计分表（著作）", "作者排序"}, index = 6)
    @ColumnWidth(value = 10)
    private String order;

    @ExcelProperty(value = {"2021年湖南工学院专业技术人员科技工作计分表（著作）", "计算依据"}, index = 7)
    @ColumnWidth(value = 10)
    private String according;

    @ExcelProperty(value = {"2021年湖南工学院专业技术人员科技工作计分表（著作）", "所属二级学院"}, index = 8)
    @ColumnWidth(value = 10)
    private String dept;

    @ExcelProperty(value = {"2021年湖南工学院专业技术人员科技工作计分表（著作）", "计分"}, index = 9)
    @ColumnWidth(value = 10)
    private String score;

    @ExcelProperty(value = {"2021年湖南工学院专业技术人员科技工作计分表（著作）", "备注"}, index = 10)
    @ColumnWidth(value = 10)
    private String remarks;
}
