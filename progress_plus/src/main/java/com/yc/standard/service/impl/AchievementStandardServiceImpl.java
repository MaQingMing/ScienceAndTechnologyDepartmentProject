package com.yc.standard.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.exception.CustomException;
import com.yc.standard.entity.AchievementStandard;
import com.yc.standard.mapper.AchievementStandardMapper;
import com.yc.standard.service.AchievementStandardService;
import com.yc.vo.EditProject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 科技成果奖;(achievement_standard)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class AchievementStandardServiceImpl implements AchievementStandardService{
    @Resource
    private AchievementStandardMapper achievementStandardMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param asid 主键
     * @return 实例对象
     */
    @Override
    public AchievementStandard queryById(Integer asid){
        return achievementStandardMapper.selectById(asid);
    }
    
    /**
     * 分页查询
     *
     * @param achievementStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @Override
    public Page<AchievementStandard> paginQuery(AchievementStandard achievementStandard, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<AchievementStandard> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(achievementStandard.getContext())){
            queryWrapper.eq(AchievementStandard::getContext, achievementStandard.getContext());
        }
        if(StrUtil.isNotBlank(achievementStandard.getRemarks())){
            queryWrapper.eq(AchievementStandard::getRemarks, achievementStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(achievementStandard.getStatus())){
            queryWrapper.eq(AchievementStandard::getStatus, achievementStandard.getStatus());
        }
        if(StrUtil.isNotBlank(achievementStandard.getPosit())){
            queryWrapper.eq(AchievementStandard::getPosit, achievementStandard.getPosit());
        }
        //2. 执行分页查询
        Page<AchievementStandard> pagin = new Page<>(current , size , true);
        IPage<AchievementStandard> selectResult = achievementStandardMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param achievementStandard 实例对象
     * @return 实例对象
     */
    @Override
    public AchievementStandard insert(AchievementStandard achievementStandard){
        achievementStandardMapper.insert(achievementStandard);
        return achievementStandard;
    }
    
    /** 
     * 更新数据
     *
     * @param achievementStandard 实例对象
     * @return 实例对象
     */
    @Override
    public AchievementStandard update(AchievementStandard achievementStandard){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<AchievementStandard> chainWrapper = new LambdaUpdateChainWrapper<AchievementStandard>(achievementStandardMapper);
        if(StrUtil.isNotBlank(achievementStandard.getContext())){
            chainWrapper.eq(AchievementStandard::getContext, achievementStandard.getContext());
        }
        if(StrUtil.isNotBlank(achievementStandard.getRemarks())){
            chainWrapper.eq(AchievementStandard::getRemarks, achievementStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(achievementStandard.getStatus())){
            chainWrapper.eq(AchievementStandard::getStatus, achievementStandard.getStatus());
        }
        if(StrUtil.isNotBlank(achievementStandard.getPosit())){
            chainWrapper.eq(AchievementStandard::getPosit, achievementStandard.getPosit());
        }
        //2. 设置主键，并更新
        chainWrapper.set(AchievementStandard::getAsid, achievementStandard.getAsid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(achievementStandard.getAsid());
        }else{
            return achievementStandard;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param asid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer asid){
        int total = achievementStandardMapper.deleteById(asid);
        return total > 0;
    }

    /**
     * 添加科技成果
     * @param editProject 添加的实体类
     * @return 0 失败 1 成功
     */
    @Override
    public int addAchievement(EditProject editProject){
        int row = achievementStandardMapper.addAchievement(editProject);

        if (row <= 0){
            throw new CustomException("0","添加失败,请核对信息");
        }
        return row;
    }

    /**
     * 查询科技成果
     *
     * @param pageNum 当前页码
     * @param pageSize 当前查询条数
     * @param context 项目描述
     * @param leid 级别id
     * @return 分页查询结果
     */
    @Override
    public Page<EditProject> selectAchievement(Integer pageNum, Integer pageSize, String context, String leid, Integer status){
        Page<EditProject> page = new Page<>(pageNum, pageSize);
        return achievementStandardMapper.selectAchievement(page, context, leid,status);
    }

    /**
     * 修改科技成果
     * @param editProject
     * @return
     */
    @Override
    public int updateAchievement(EditProject editProject){
        int row = achievementStandardMapper.updateAchievement(editProject);
        if (row <= 0){
            throw new CustomException("0", "修改失败，请检查内容");
        }
        return row;
    }
}