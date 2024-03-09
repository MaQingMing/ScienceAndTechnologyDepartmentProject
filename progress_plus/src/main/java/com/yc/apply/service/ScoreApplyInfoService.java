package com.yc.apply.service;

import com.yc.apply.entity.ScoreApplyInfo;
import com.yc.vo.apply.ApplyVo;

/**
 * 申请分数详情;(score_apply_info)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-12-2
 */
public interface ScoreApplyInfoService {

    /**
     * 通过ID查询单条数据
     *
     * @param saiid 主键
     * @return 实例对象
     */
    ScoreApplyInfo queryById(Integer saiid);

    /**
     * 新增数据
     *
     * @param scoreApplyInfo 实例对象
     * @return 实例对象
     */
    ScoreApplyInfo insert(ScoreApplyInfo scoreApplyInfo);
    /**
     * 更新数据
     *
     * @param scoreApplyInfo 实例对象
     * @return 实例对象
     */
    ScoreApplyInfo update(ScoreApplyInfo scoreApplyInfo);
    /**
     * 通过主键删除数据
     *
     * @param saiid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer saiid);

    /**
     * 添加科技分分配详情
     * @param applyVo 申请详情
     */
    void addScoreInfo(ApplyVo applyVo);
}
