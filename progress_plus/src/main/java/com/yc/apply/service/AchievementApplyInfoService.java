package com.yc.apply.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.AchievementApplyInfo;
import com.yc.vo.apply.ApplyVo;

/**
 * 科技成果奖申请详情;(achievement_apply_info)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface AchievementApplyInfoService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param aaiid 主键
     * @return 实例对象
     */
    AchievementApplyInfo queryById(Integer aaiid);
    
    /**
     * 分页查询
     *
     * @param achievementApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<AchievementApplyInfo> paginQuery(AchievementApplyInfo achievementApplyInfo, long current, long size);
    /** 
     * 新增数据
     *
     * @param achievementApplyInfo 实例对象
     * @return 实例对象
     */
    AchievementApplyInfo insert(AchievementApplyInfo achievementApplyInfo);
    /** 
     * 更新数据
     *
     * @param achievementApplyInfo 实例对象
     * @return 实例对象
     */
    AchievementApplyInfo update(AchievementApplyInfo achievementApplyInfo);
    /** 
     * 通过主键删除数据
     *
     * @param aaiid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer aaiid);

     /**
      * 添加科技成果申请
      * @param applyVo
      */
    int addAchievement(ApplyVo applyVo);
}