package com.yc.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.AchievementApplyInfo;
import com.yc.apply.mapper.AchievementApplyInfoMapper;
import com.yc.apply.service.AchievementApplyInfoService;
import com.yc.exception.CustomException;
import com.yc.vo.apply.ApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 /**
 * 科技成果奖申请详情;(achievement_apply_info)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class AchievementApplyInfoServiceImpl implements AchievementApplyInfoService{
    @Autowired
    private AchievementApplyInfoMapper achievementApplyInfoMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param aaiid 主键
     * @return 实例对象
     */
    @Override
    public AchievementApplyInfo queryById(Integer aaiid){
        return achievementApplyInfoMapper.selectById(aaiid);
    }
    
    /**
     * 分页查询
     *
     * @param achievementApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @Override
    public Page<AchievementApplyInfo> paginQuery(AchievementApplyInfo achievementApplyInfo, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<AchievementApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(achievementApplyInfo.getContent())){
            queryWrapper.eq(AchievementApplyInfo::getContent, achievementApplyInfo.getContent());
        }
        if(StrUtil.isNotBlank(achievementApplyInfo.getDept())){
            queryWrapper.eq(AchievementApplyInfo::getDept, achievementApplyInfo.getDept());
        }
        if(StrUtil.isNotBlank(achievementApplyInfo.getName())){
            queryWrapper.eq(AchievementApplyInfo::getName, achievementApplyInfo.getName());
        }
        if(StrUtil.isNotBlank(achievementApplyInfo.getLevel())){
            queryWrapper.eq(AchievementApplyInfo::getLevel, achievementApplyInfo.getLevel());
        }
        if(StrUtil.isNotBlank(achievementApplyInfo.getAccording())){
            queryWrapper.eq(AchievementApplyInfo::getAccording, achievementApplyInfo.getAccording());
        }
        //2. 执行分页查询
        Page<AchievementApplyInfo> pagin = new Page<>(current , size , true);
        IPage<AchievementApplyInfo> selectResult = achievementApplyInfoMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param achievementApplyInfo 实例对象
     * @return 实例对象
     */
    @Override
    public AchievementApplyInfo insert(AchievementApplyInfo achievementApplyInfo){
        achievementApplyInfoMapper.insert(achievementApplyInfo);
        return achievementApplyInfo;
    }
    
    /** 
     * 更新数据
     *
     * @param achievementApplyInfo 实例对象
     * @return 实例对象
     */
    @Override
    public AchievementApplyInfo update(AchievementApplyInfo achievementApplyInfo){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<AchievementApplyInfo> chainWrapper = new LambdaUpdateChainWrapper<AchievementApplyInfo>(achievementApplyInfoMapper);
        if(StrUtil.isNotBlank(achievementApplyInfo.getContent())){
            chainWrapper.eq(AchievementApplyInfo::getContent, achievementApplyInfo.getContent());
        }
        if(StrUtil.isNotBlank(achievementApplyInfo.getDept())){
            chainWrapper.eq(AchievementApplyInfo::getDept, achievementApplyInfo.getDept());
        }
        if(StrUtil.isNotBlank(achievementApplyInfo.getName())){
            chainWrapper.eq(AchievementApplyInfo::getName, achievementApplyInfo.getName());
        }
        if(StrUtil.isNotBlank(achievementApplyInfo.getLevel())){
            chainWrapper.eq(AchievementApplyInfo::getLevel, achievementApplyInfo.getLevel());
        }
        if(StrUtil.isNotBlank(achievementApplyInfo.getAccording())){
            chainWrapper.eq(AchievementApplyInfo::getAccording, achievementApplyInfo.getAccording());
        }
        //2. 设置主键，并更新
        chainWrapper.set(AchievementApplyInfo::getAaiid, achievementApplyInfo.getAaiid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(achievementApplyInfo.getAaiid());
        }else{
            return achievementApplyInfo;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param aaiid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer aaiid){
        int total = achievementApplyInfoMapper.deleteById(aaiid);
        return total > 0;
    }

     @Override
     public int addAchievement(ApplyVo applyVo) {
         int row = achievementApplyInfoMapper.addAchievement(applyVo);
         if (row <= 0){
             throw new CustomException("0", "添加失败");
         }
         return row;
     }
 }