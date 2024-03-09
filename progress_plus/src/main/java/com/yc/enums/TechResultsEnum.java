package com.yc.enums;

/**
 * 科技成果类型 枚举
 * @author 1943
 */
public enum TechResultsEnum {

    Direction(1),//纵向项目
    Transverse(2), //横向项目
    Achievement(3),//科技成果奖
    Paper(4), //学术论文(自科/社科)
    Book(5),//学术专著(含著/编著/译著)
    Invent(6), //发明专利
    Scientific(7), //科研基地/科学建设
    Honor(8) //科技类荣誉(称号)
    ;

    /**
     *  科技成果类型顺序
     */
    private Integer status;

    TechResultsEnum(Integer status) {
        this.status = status;
    }
}
