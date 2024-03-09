package com.yc.vo.standard;

import com.yc.apply.entity.DirectionApplyInfo;
import lombok.Data;

/**
 * @author 邓弈2102
 * @version 1.0
 * @description: TODO
 * @date 2023/10/26
 */
@Data
public class DirectionHisPage extends DirectionApplyInfo {
    //申请人姓名
    private String username;
    //申请日期
    private String time;
    //备注
    private String remarks;
    //是否是团队申请
    private String isteam;
    //团队成员
    private String team;
    //备案名称
    private String rname;
    //备案id
    private String recordid;
    //主要负责人
    private String sid;
    //是否能够换钱
    private String cash;
    //审核状态
    private String status;
    //项目级别
    private String level;
    //负责人所处部门
    private String userDept;
    //计分信息
    private String scoreInfo;
    //文件数量
    private int fileNum;
    //团队id
    private String teamId;
    //驳回理由
    private String rejection;
}
