package com.yc.vo.other;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.Data;

import java.util.Objects;

/**
 * 导出 湖南工学院专业技术人员科技成果计分汇总表
 */

@Data
// 设置表头行高度
@HeadRowHeight(30)
public class AssExcel {

    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院二级学院科技成果计分汇总", "序号"}, index = 0)
    private Integer index;
    @ColumnWidth(30)
    @ExcelProperty(value = {"湖南工学院二级学院科技成果计分汇总", "所属二级学院"}, index = 1)
    private String tname;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院二级学院科技成果计分汇总", "纵向"}, index = 2)
    private Double direction;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院二级学院科技成果计分汇总", "横向"}, index = 3)
    private Double transverse;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院二级学院科技成果计分汇总", "成果奖"}, index = 4)
    private Double achievement;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院二级学院科技成果计分汇总", "论文"}, index = 5)
    private Double paper;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院二级学院科技成果计分汇总", "著作"}, index = 6)
    private Double book;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院二级学院科技成果计分汇总", "专利"}, index = 7)
    private Double invent;
    @ColumnWidth(15)
    @ExcelProperty(value = {"湖南工学院二级学院科技成果计分汇总", "科研基地"}, index = 8)
    private Double scientific;
    @ColumnWidth(15)
    @ExcelProperty(value = {"湖南工学院二级学院科技成果计分汇总", "科技荣誉"}, index = 9)
    private Double honor;

    public AssExcel() {

    }

    public AssExcel(Integer index, String tname, Double direction, Double transverse, Double achievement,
                    Double paper, Double book, Double invent, Double scientific, Double honor) {
        this.index = index;
        this.tname = tname;
        this.direction = direction;
        this.transverse = transverse;
        this.achievement = achievement;
        this.paper = paper;
        this.book = book;
        this.invent = invent;
        this.scientific = scientific;
        this.honor = honor;
    }
}
