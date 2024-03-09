package com.yc.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.HonorApplyInfo;
import com.yc.apply.mapper.HonorApplyInfoMapper;
import com.yc.apply.service.HonorApplyInfoService;
import com.yc.exception.CustomException;
import com.yc.vo.apply.ApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 /**
 * 科技类荣誉;(honor_apply_info)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class HonorApplyInfoServiceImpl implements HonorApplyInfoService{
    @Autowired
    private HonorApplyInfoMapper honorApplyInfoMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param haiid 主键
     * @return 实例对象
     */
    public HonorApplyInfo queryById(Integer haiid){
        return honorApplyInfoMapper.selectById(haiid);
    }
    
    /**
     * 分页查询
     *
     * @param honorApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<HonorApplyInfo> paginQuery(HonorApplyInfo honorApplyInfo, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<HonorApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(honorApplyInfo.getType())){
            queryWrapper.eq(HonorApplyInfo::getType, honorApplyInfo.getType());
        }
        if(StrUtil.isNotBlank(honorApplyInfo.getDept())){
            queryWrapper.eq(HonorApplyInfo::getDept, honorApplyInfo.getDept());
        }
        if(StrUtil.isNotBlank(honorApplyInfo.getAccording())){
            queryWrapper.eq(HonorApplyInfo::getAccording, honorApplyInfo.getAccording());
        }
        //2. 执行分页查询
        Page<HonorApplyInfo> pagin = new Page<>(current , size , true);
        IPage<HonorApplyInfo> selectResult = honorApplyInfoMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param honorApplyInfo 实例对象
     * @return 实例对象
     */
    public HonorApplyInfo insert(HonorApplyInfo honorApplyInfo){
        honorApplyInfoMapper.insert(honorApplyInfo);
        return honorApplyInfo;
    }
    
    /** 
     * 更新数据
     *
     * @param honorApplyInfo 实例对象
     * @return 实例对象
     */
    public HonorApplyInfo update(HonorApplyInfo honorApplyInfo){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<HonorApplyInfo> chainWrapper = new LambdaUpdateChainWrapper<HonorApplyInfo>(honorApplyInfoMapper);
        if(StrUtil.isNotBlank(honorApplyInfo.getType())){
            chainWrapper.eq(HonorApplyInfo::getType, honorApplyInfo.getType());
        }
        if(StrUtil.isNotBlank(honorApplyInfo.getDept())){
            chainWrapper.eq(HonorApplyInfo::getDept, honorApplyInfo.getDept());
        }
        if(StrUtil.isNotBlank(honorApplyInfo.getAccording())){
            chainWrapper.eq(HonorApplyInfo::getAccording, honorApplyInfo.getAccording());
        }
        //2. 设置主键，并更新
        chainWrapper.set(HonorApplyInfo::getHaiid, honorApplyInfo.getHaiid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(honorApplyInfo.getHaiid());
        }else{
            return honorApplyInfo;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param haiid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer haiid){
        int total = honorApplyInfoMapper.deleteById(haiid);
        return total > 0;
    }

     /**
      * 添加横向项目申请
      *
      * @param applyVo
      * @return
      */
     @Override
     public int addHonor(ApplyVo applyVo) {
         int row = honorApplyInfoMapper.addHonor(applyVo);
         if (row <= 0){
             throw new CustomException("0", "添加失败");
         }
         return row;
     }
 }