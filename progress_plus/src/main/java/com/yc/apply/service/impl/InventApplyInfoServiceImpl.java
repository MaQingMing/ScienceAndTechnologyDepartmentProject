package com.yc.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.InventApplyInfo;
import com.yc.apply.mapper.InventApplyInfoMapper;
import com.yc.apply.service.InventApplyInfoService;
import com.yc.exception.CustomException;
import com.yc.vo.apply.ApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 /**
 * 专利申请详情;(invent_apply_info)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class InventApplyInfoServiceImpl implements InventApplyInfoService{
    @Autowired
    private InventApplyInfoMapper inventApplyInfoMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param iaid 主键
     * @return 实例对象
     */
    public InventApplyInfo queryById(Integer iaid){
        return inventApplyInfoMapper.selectById(iaid);
    }
    
    /**
     * 分页查询
     *
     * @param inventApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<InventApplyInfo> paginQuery(InventApplyInfo inventApplyInfo, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<InventApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(inventApplyInfo.getAuthorization())){
            queryWrapper.eq(InventApplyInfo::getAuthorization, inventApplyInfo.getAuthorization());
        }
        if(StrUtil.isNotBlank(inventApplyInfo.getStage())){
            queryWrapper.eq(InventApplyInfo::getStage, inventApplyInfo.getStage());
        }
        if(StrUtil.isNotBlank(inventApplyInfo.getName())){
            queryWrapper.eq(InventApplyInfo::getName, inventApplyInfo.getName());
        }
        if(StrUtil.isNotBlank(inventApplyInfo.getType())){
            queryWrapper.eq(InventApplyInfo::getType, inventApplyInfo.getType());
        }
        if(StrUtil.isNotBlank(inventApplyInfo.getAccording())){
            queryWrapper.eq(InventApplyInfo::getAccording, inventApplyInfo.getAccording());
        }
        //2. 执行分页查询
        Page<InventApplyInfo> pagin = new Page<>(current , size , true);
        IPage<InventApplyInfo> selectResult = inventApplyInfoMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param inventApplyInfo 实例对象
     * @return 实例对象
     */
    public InventApplyInfo insert(InventApplyInfo inventApplyInfo){
        inventApplyInfoMapper.insert(inventApplyInfo);
        return inventApplyInfo;
    }
    
    /** 
     * 更新数据
     *
     * @param inventApplyInfo 实例对象
     * @return 实例对象
     */
    public InventApplyInfo update(InventApplyInfo inventApplyInfo){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<InventApplyInfo> chainWrapper = new LambdaUpdateChainWrapper<InventApplyInfo>(inventApplyInfoMapper);
        if(StrUtil.isNotBlank(inventApplyInfo.getAuthorization())){
            chainWrapper.eq(InventApplyInfo::getAuthorization, inventApplyInfo.getAuthorization());
        }
        if(StrUtil.isNotBlank(inventApplyInfo.getStage())){
            chainWrapper.eq(InventApplyInfo::getStage, inventApplyInfo.getStage());
        }
        if(StrUtil.isNotBlank(inventApplyInfo.getName())){
            chainWrapper.eq(InventApplyInfo::getName, inventApplyInfo.getName());
        }
        if(StrUtil.isNotBlank(inventApplyInfo.getType())){
            chainWrapper.eq(InventApplyInfo::getType, inventApplyInfo.getType());
        }
        if(StrUtil.isNotBlank(inventApplyInfo.getAccording())){
            chainWrapper.eq(InventApplyInfo::getAccording, inventApplyInfo.getAccording());
        }
        //2. 设置主键，并更新
        chainWrapper.set(InventApplyInfo::getIaid, inventApplyInfo.getIaid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(inventApplyInfo.getIaid());
        }else{
            return inventApplyInfo;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param iaid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer iaid){
        int total = inventApplyInfoMapper.deleteById(iaid);
        return total > 0;
    }

     /**
      * 添加专利项目申请
      *
      * @param applyVo
      * @return
      */
     @Override
     public int addInvent(ApplyVo applyVo) {
         int row = inventApplyInfoMapper.addInvent(applyVo);
         if (row <= 0){
             throw new CustomException("0", "添加失败");
         }
         return row;
     }
 }