package com.yc.vo.apply;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.yc.apply.entity.AchievementApplyInfo;
import com.yc.entity.base.BaseEntity;
import com.yc.vo.apply.PaperApplyVo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 申请的 vo类
 */
@Data
public class ApplyVo extends BaseEntity {

    /**
     * 学术论文的参数
     */
    PaperApplyVo paperApplyVo;
    /**
     * 科技成果的参数
     */
    private String awardContent;
    private String level;

    /**
     * 计算需要用到的参数
     */
    Map<String, Object> userdata;
    /**
     * 成果类型 具体看 TechResultsEnum
     */
    private Integer techResults;

    List<Map<String, Object>> scientificData;

    /**
     * 我校排名次序
     */
    private String xmschoolorder;

    /**
     * 教职工排名次序
     */
    private String xmworkersorder;

    /**
     * 科技成果申请主键Id
     */
    @ApiModelProperty(name = "科技成果申请主键Id", notes = "")
    @TableId(value = "gaid", type = IdType.AUTO)
    private Integer gaid;
    /**
     * 申请人
     */
    @ApiModelProperty(name = "申请人", notes = "")
    private String sid;

    /**
     * 科技成果类别
     */
    @ApiModelProperty(name = "科技成果类别", notes = "")
    private String trtid;

    /**
     * 是否是团队申请( 1 是  0 否 )
     */
    @ApiModelProperty(name = "是否是团队申请( 1 是  0 否 )", notes = "")
    private String team;
    /**
     * 是否是团队申请( 1 是  0 否 )
     */
    @ApiModelProperty(name = "是否是团队申请( 1 联合申请  0 独立申请 )", notes = "")
    private String concurType;

    /**
     * 备案信息ID
     */
    @ApiModelProperty(name = "备案信息ID", notes = "")
    private Integer recordid;

    /**
     * 备注
     */
    @ApiModelProperty(name = "备注", notes = "")
    private String remarks;

    /**
     * 经费卡号
     */
    @ApiModelProperty(name = "经费卡号", notes = "")
    private String card;

    /**
     * 科研项目名称
     */
    @ApiModelProperty(name = "科研项目名称", notes = "")
    private String name;

    /**
     * 详细类别 (可能多条 重复计算)
     */
    @ApiModelProperty(name = "详细类别 (可能多条 重复计算)", notes = "")
    private String childid;

    /**
     * 科研项目类别
     */
    @ApiModelProperty(name = "科研项目类别", notes = "")
    private String type;

    /**
     * 经费进账
     */
    @ApiModelProperty(name = "经费进账", notes = "")
    private String money;

    /**
     * 计算依据
     */
    @ApiModelProperty(name = "计算依据", notes = "")
    private String according;

    /**
     * 项目级别
     */
    @ApiModelProperty(name = "项目级别", notes = "")
    private String leid;

    /**
     * 计分
     */
    @ApiModelProperty(name = "计分", notes = "")
    private Double score;

    /**
     * 我校是否为第一署名单位
     **/
    @ApiModelProperty(name = "我校是否为第一署名单位", notes = "")
    private String firstSign;

    /**
     * 项目阶段
     */
    @ApiModelProperty(name = "项目阶段")
    private String stage;

    /**
     * 附则内容
     */
    @ApiModelProperty(name = "附则内容", notes = "")
    private String content;
    /**
     * 附则内容
     */
    @ApiModelProperty(name = "附则内容用来显示", notes = "")
    private String content1;

    /**
     * 计算比率 (10%等)
     */
    @ApiModelProperty(name = "计算比率 (10%等)", notes = "")
    private String ratio;

    /**
     * 附则加分
     */
    @ApiModelProperty(name = "附则加分", notes = "")
    private String addScore;

    private String lname;

    /**
     * 附则类型(基础分值0,百分比1,百分比加具体项2,其他3)
     */
    @ApiModelProperty(name = "附则类型(基础分值0,百分比1,百分比加具体项2,其他3)", notes = "")
    private Integer scoreType;

    /**
     * 申报分数
     */
    @ApiModelProperty(name = "申报分数", notes = "")
    private Integer declareScore;

    /**
     * 立项分数
     */
    @ApiModelProperty(name = "立项分数", notes = "")
    private Integer foundScore;

    @ApiModelProperty(name = "文号", notes = "")
    private String file;
    /**
     * 项目编号
     */
    @ApiModelProperty(name = "项目编号", notes = "")
    private String identifier;
    /**
     * 批准立项部门
     */
    @ApiModelProperty(name = "批准立项部门", notes = "")
    private String dept;
    /**
     * 具体分数分配(用户id::分数;用户id::分数;)
     */
    @ApiModelProperty(name = "具体分数分配(用户id::分数;用户id::分数;)", notes = "")
    private String scoreInfo;
    /**
     * 是否可以换现(1可以 0不可以)
     **/
    @ApiModelProperty(name = "是否可以换现(1可以 0不可以)", notes = "")
    private Integer cash;

    /**
     * 学校
     **/
    @ApiModelProperty(name = "学校", notes = "")
    private String school;

    /**
     * 项目授权号
     **/
    @ApiModelProperty(name = "专利授权号", notes = "")
    private String authorization;

    /**
     * 项目申请号
     **/
    @ApiModelProperty(name = "专利申请号", notes = "")
    private String applyNum;

    /**
     * 科研范围
     **/
    @ApiModelProperty(name = "科研范文", notes = "")
    private String scope;

    /**
     * 专利权状态
     **/
    @ApiModelProperty(name = "专利权状态", notes = "")
    private String patentStatus;

    /**
     * 专利权人
     **/
    @ApiModelProperty(name = "专利权人", notes = "")
    private String pid;

    /**
     * 所在单位
     **/
    @ApiModelProperty(name = "所在单位", notes = "")
    private String locationUnit;

    /**
     * 授权日期
     **/
    @ApiModelProperty(name = "授权日期", notes = "")
    private String authorizeTime;
    /**
     * 申请日期
     **/
    @ApiModelProperty(name = "申请日期", notes = "")
    private String applyTime;
    /**
     * 学科门类
     */
    @ApiModelProperty(name = "学科门类", notes = "")
    private String subjectCategory;
    /**
     * 立项年度
     */
    @ApiModelProperty(name = "立项年度", notes = "")
    private String startProjectYear;
    /**
     * 所属单位
     */
    @ApiModelProperty(name = "所属单位", notes = "")
    private String belongUnit;
    /**
     * 申报批准时间
     */
    @ApiModelProperty(name = "申报批准时间", notes = "")
    private String declareApproveTime;
    /**
     * 开始时间
     */
    @ApiModelProperty(name = "开始时间", notes = "")
    private String startTime;
    /**
     * 计划完成时间
     */
    @ApiModelProperty(name = "计划完成时间", notes = "")
    private String planFinishTime;
    /**
     * 总经费
     */
    @ApiModelProperty(name = "总经费", notes = "")
    private String totalFund;
    /**
     * 到账总经费
     */
    @ApiModelProperty(name = "到账总经费", notes = "")
    private String receiptTotalFund;
    /**
     * 承担单位
     */
    @ApiModelProperty(name = "承担单位", notes = "")
    private String bearUnit;
    /**
     * 项目状态
     */
    @ApiModelProperty(name = "项目状态", notes = "")
    private String projectStatus;
    /**
     * 完成日期
     */
    @ApiModelProperty(name = "完成日期", notes = "")
    private String completeTime;
    /**
     * 学科分类
     */
    @ApiModelProperty(name = "学科分类", notes = "")
    private String subjectType;
    /**
     * 国民经济行业分类
     */
    @ApiModelProperty(name = "国民经济行业分类", notes = "")
    private String nationalEconomyCategory;
    /**
     * 合同签订日期
     */
    @ApiModelProperty(name = "合同签订日期", notes = "")
    private String signContractTime;
    /**
     * 合同编号
     */
    @ApiModelProperty(name = "合同编号", notes = "")
    private String contractNumber;
    /**
     * 项目来源单位
     **/
    @ApiModelProperty(name = "项目来源单位", notes = "")
    private String sourceUnit;
}
