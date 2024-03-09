package com.yc.vo.standard;

import com.yc.apply.entity.PaperApplyInfo;
import lombok.Data;

/**
 * @author 邓弈2102
 * @version 1.0
 * @description: 分页数据中的学术论文数据
 * @date 2023/10/25
 */
@Data
public class PaperHisPage extends PaperApplyInfo {
    //申请人姓名
    private String username;
    //申请日期
    private String time;
    //备注
    private String remarks;
    //是否是团队申请
    private String team;
    //备案名称
    private String rname;
    //备案id
    private String recordid;
    //审核状态
    private String status;
    //主要负责人
    private String sid;
    //驳回缘由
    private String rejection;
    //是否批量加入
    private String batch;
    //项目级别
    private String level;
    //负责人所在部门
    private String userDept;
    //分数分配信息
    private String scoreInfo;
    //文件数量
    private int fileNum;
    //备案团队id
    private String teamId;
}
