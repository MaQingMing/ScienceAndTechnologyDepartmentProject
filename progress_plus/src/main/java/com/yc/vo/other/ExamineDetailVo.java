package com.yc.vo.other;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.yc.vo.other
 * @NAME: ExamineDetailVo
 * @USER: Administrator
 * @DATE: 2023/12/22
 * @MONTH_NAME_SHORT: 12月
 * @MONTH_NAME_FULL: 十二月
 **/
@Data
public class ExamineDetailVo {

    /** 主键Id */
    private Integer edid ;
    /** 用户Id */
    private Integer sysid ;
    /** 任务标准 */
    private Double standard ;
    /** 纵向 */
    private Double direction ;
    /** 横向 */
    private Double transverse ;
    /** 科技成果奖 */
    private Double achievement ;
    /** 科技类荣誉(称号) */
    private Double honor ;
    /** 学术论文(自科/社科) */
    private Double paper ;
    /** 学术专著(含著/编著/译著) */
    private Double book ;
    /** 科研基地/科学建设 */
    private Double scientific ;
    /** 发明专利 */
    private Double invent ;
    /** 总分 */
    private Double count ;
    /** 是否合格 (1 0) */
    private Integer status ;
    /** 备注 */
    private String remark ;
    /** 考核主键Id */
    private Integer eid ;

    public ExamineDetailVo(){

    }

    public ExamineDetailVo(Integer sysid, Double standard, Double direction, Double transverse,  Double achievement, Double paper,
                         Double book, Double invent, Double scientific, Double honor,  Double count,
                         Integer status, Integer eid) {
        this.sysid = sysid;
        this.standard = standard;
        this.direction = direction;
        this.transverse = transverse;
        this.achievement = achievement;
        this.honor = honor;
        this.paper = paper;
        this.book = book;
        this.scientific = scientific;
        this.invent = invent;
        this.count = count;
        this.status = status;
        this.eid = eid;
    }
}