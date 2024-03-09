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
public class AssInfoExcel {

    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "序号"}, index = 0)
    private Integer index;
    @ColumnWidth(15)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "姓名"}, index = 1)
    private String nickname;
    @ColumnWidth(30)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "所属二级学院"}, index = 2)
    private String tname;
    @ColumnWidth(15)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "学历"}, index = 3)
    private String edu;
    @ColumnWidth(15)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "职称"}, index = 4)
    private String academic;
    @ColumnWidth(15)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "是否归属研究院"}, index = 5)
    private String academy;
    @ColumnWidth(17)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "是否承担研究院管理工作"}, index = 6)
    private String academyCare;
    @ColumnWidth(15)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "应完成科研任务标准"}, index = 7)
    private Double standard;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "纵向"}, index = 8)
    private Double direction;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "横向"}, index = 9)
    private Double transverse;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "成果奖"}, index = 10)
    private Double achievement;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "论文"}, index = 11)
    private Double paper;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "著作"}, index = 12)
    private Double book;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "专利"}, index = 13)
    private Double invent;
    @ColumnWidth(15)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "科研基地"}, index = 14)
    private Double scientific;
    @ColumnWidth(15)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "科技荣誉"}, index = 15)
    private Double honor;
    @ColumnWidth(15)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "合计总分"}, index = 16)
    private Double count;
    @ColumnWidth(10)
    @ExcelProperty(value = {"湖南工学院专业技术人员科技成果计分汇总", "备注"}, index = 17)
    private String remarks;

    public AssInfoExcel() {

    }



    public AssInfoExcel(Integer index, String nickname, String tname, String edu, String academic, Integer academy, Integer academyCare, Double standard,
                        Double direction, Double transverse, Double achievement, Double paper, Double book, Double invent, Double scientific, Double honor,
                        Double count, String remarks) {
        this.index = index;
        this.nickname = nickname;
        this.tname = tname;
        this.edu = edu;
        this.academic = academic;

        if(Objects.isNull(academy)){
            this.academy ="否";
        }else{
            if(academy == 1){
                this.academy ="是";
            }else{
                this.academy ="否";
            }
        }

        if(Objects.isNull(academyCare)){
            this.academyCare ="否";
        }else{
            if(academyCare == 1){
                this.academyCare ="是";
            }else{
                this.academyCare ="否";
            }
        }
        this.standard = standard;
        this.direction = direction;
        this.transverse = transverse;
        this.achievement = achievement;
        this.paper = paper;
        this.book = book;
        this.invent = invent;
        this.scientific = scientific;
        this.honor = honor;
        this.count = count;
        this.remarks = remarks;
    }
}
