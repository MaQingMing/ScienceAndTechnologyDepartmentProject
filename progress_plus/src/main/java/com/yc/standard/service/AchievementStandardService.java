package com.yc.standard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.AchievementStandard;
import com.yc.vo.EditProject;

/**
 * 科技成果奖;(achievement_standard)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface AchievementStandardService {

    /**
     * 通过ID查询单条数据
     *
     * @param asid 主键
     * @return 实例对象
     */
    AchievementStandard queryById(Integer asid);

    /**
     * 分页查询
     *
     * @param achievementStandard 筛选条件
     * @param current             当前页码
     * @param size                每页大小
     * @return
     */
    Page<AchievementStandard> paginQuery(AchievementStandard achievementStandard, long current, long size);

    /**
     * 新增数据
     *
     * @param achievementStandard 实例对象
     * @return 实例对象
     */
    AchievementStandard insert(AchievementStandard achievementStandard);

    /**
     * 更新数据
     *
     * @param achievementStandard 实例对象
     * @return 实例对象
     */
    AchievementStandard update(AchievementStandard achievementStandard);

    /**
     * 通过主键删除数据
     *
     * @param asid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer asid);

    /**
     * 添加科技成果
     *
     * @param editProject 添加的实体类
     * @return 0 失败 1 成功
     */
    int addAchievement(EditProject editProject);

    /**
     * 查询科技成果
     *
     * @param pageNum 当前页码
     * @param pageSize 当前查询条数
     * @param context 项目描述
     * @param leid 级别id
     * @param status  0 查询所有状态   1查询 1的状态
     * @return 分页查询结果
     */
    Page<EditProject> selectAchievement(Integer pageNum, Integer pageSize, String context, String leid, Integer status);

    /**
     * 修改科技成果
     * @param editProject
     * @return
     */
    int updateAchievement(EditProject editProject);
}