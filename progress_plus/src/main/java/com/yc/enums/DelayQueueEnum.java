package com.yc.enums;

/**
 * 考核阶段 状态
 */
public enum DelayQueueEnum {

    //开始考核
    TASKSTART,
    //开始考核扣分 和 统计结果
    EXAMINESTART,
    //结束考核 开始公示
    MISSIONEND,
    // 结束公示
    PUBLICITYEND;
}
