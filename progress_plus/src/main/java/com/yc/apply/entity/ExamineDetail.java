package com.yc.apply.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yc.entity.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 考核详情表;
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
@Data
@ApiModel(value = "考核详情表",description = "")
@TableName("examine_detail")
public class ExamineDetail extends BaseEntity {

    /** 主键Id */
    @ApiModelProperty(name = "主键Id",notes = "")
    @TableId(value = "edid", type = IdType.AUTO)
    private Integer edid ;
    /** 用户Id */
    @ApiModelProperty(name = "用户Id",notes = "")
    private Integer sysid ;
    /** 任务标准 */
    @ApiModelProperty(name = "任务标准",notes = "")
    private Double standard ;
    /** 纵向 */
    @ApiModelProperty(name = "纵向",notes = "")
    private Double direction ;
    /** 横向 */
    @ApiModelProperty(name = "横向",notes = "")
    private Double transverse ;
    /** 科技成果奖 */
    @ApiModelProperty(name = "科技成果奖",notes = "")
    private Double achievement ;
    /** 科技类荣誉(称号) */
    @ApiModelProperty(name = "科技类荣誉(称号)",notes = "")
    private Double honor ;
    /** 学术论文(自科/社科) */
    @ApiModelProperty(name = "学术论文(自科/社科)",notes = "")
    private Double paper ;
    /** 学术专著(含著/编著/译著) */
    @ApiModelProperty(name = "学术专著(含著/编著/译著)",notes = "")
    private Double book ;
    /** 科研基地/科学建设 */
    @ApiModelProperty(name = "科研基地/科学建设",notes = "")
    private Double scientific ;
    /** 发明专利 */
    @ApiModelProperty(name = "发明专利",notes = "")
    private Double invent ;
    /** 总分 */
    @ApiModelProperty(name = "总分",notes = "")
    private Double count ;
    /** 是否合格 (1 0) */
    @ApiModelProperty(name = "是否合格 (1 0)",notes = "")
    private Integer status ;
    /** 备注 */
    @ApiModelProperty(name = "备注",notes = "")
    private String remark ;
    /** 考核主键Id */
    @ApiModelProperty(name = "考核主键Id",notes = "")
    private Integer eid ;

    public ExamineDetail(){

    }

    public ExamineDetail(Integer sysid, Double standard, Double direction, Double transverse,  Double achievement, Double paper,
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
