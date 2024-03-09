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
public class HonourHead {
    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计量表（科技类荣誉）","序号"}, index = 0)
    @ColumnWidth(value = 10)
    private String id;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计量表（科技类荣誉）","负责人"}, index = 1)
    @ColumnWidth(value = 20)
    private String username;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计量表（科技类荣誉）","类别"}, index = 2)
    @ColumnWidth(value = 20)
    private String type;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计量表（科技类荣誉）","批准单位"}, index = 3)
    @ColumnWidth(value = 10)
    private String dept;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计量表（科技类荣誉）","荣誉名称"}, index = 4)
    @ColumnWidth(value = 20)
    private String name;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计量表（科技类荣誉）","计算依据"}, index = 5)
    @ColumnWidth(value = 20)
    private String according;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计量表（科技类荣誉）","计分"}, index = 6)
    @ColumnWidth(value = 10)
    private String score;

    @ExcelProperty(value = {"2022年湖南工学院专业技术人员科技工作计量表（科技类荣誉）","所属二级学院"}, index = 7)
    @ColumnWidth(value = 20)
    private String userDept;
}
