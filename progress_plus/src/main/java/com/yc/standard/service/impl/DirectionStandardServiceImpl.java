package com.yc.standard.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.exception.CustomException;
import com.yc.standard.entity.DirectionStandard;
import com.yc.standard.mapper.DirectionStandardMapper;
import com.yc.standard.service.DirectionStandardService;
import com.yc.vo.EditProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 纵向项目;(direction_standard)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class DirectionStandardServiceImpl implements DirectionStandardService{
    @Autowired
    private DirectionStandardMapper directionStandardMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param dsid 主键
     * @return 实例对象
     */
    public DirectionStandard queryById(Integer dsid){
        return directionStandardMapper.selectById(dsid);
    }
    
    /**
     * 分页查询
     *
     * @param directionStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<DirectionStandard> paginQuery(DirectionStandard directionStandard, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<DirectionStandard> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(directionStandard.getContext())){
            queryWrapper.eq(DirectionStandard::getContext, directionStandard.getContext());
        }
        if(StrUtil.isNotBlank(directionStandard.getRemarks())){
            queryWrapper.eq(DirectionStandard::getRemarks, directionStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(directionStandard.getStatus())){
            queryWrapper.eq(DirectionStandard::getStatus, directionStandard.getStatus());
        }
        if(StrUtil.isNotBlank(directionStandard.getPosit())){
            queryWrapper.eq(DirectionStandard::getPosit, directionStandard.getPosit());
        }
        //2. 执行分页查询
        Page<DirectionStandard> pagin = new Page<>(current , size , true);
        IPage<DirectionStandard> selectResult = directionStandardMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param directionStandard 实例对象
     * @return 实例对象
     */
    public DirectionStandard insert(DirectionStandard directionStandard){
        directionStandardMapper.insert(directionStandard);
        return directionStandard;
    }
    
    /** 
     * 更新数据
     *
     * @param directionStandard 实例对象
     * @return 实例对象
     */
    public DirectionStandard update(DirectionStandard directionStandard){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<DirectionStandard> chainWrapper = new LambdaUpdateChainWrapper<DirectionStandard>(directionStandardMapper);
        if(StrUtil.isNotBlank(directionStandard.getContext())){
            chainWrapper.eq(DirectionStandard::getContext, directionStandard.getContext());
        }
        if(StrUtil.isNotBlank(directionStandard.getRemarks())){
            chainWrapper.eq(DirectionStandard::getRemarks, directionStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(directionStandard.getStatus())){
            chainWrapper.eq(DirectionStandard::getStatus, directionStandard.getStatus());
        }
        if(StrUtil.isNotBlank(directionStandard.getPosit())){
            chainWrapper.eq(DirectionStandard::getPosit, directionStandard.getPosit());
        }
        //2. 设置主键，并更新
        chainWrapper.set(DirectionStandard::getDsid, directionStandard.getDsid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(directionStandard.getDsid());
        }else{
            return directionStandard;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param dsid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer dsid){
        int total = directionStandardMapper.deleteById(dsid);
        return total > 0;
    }

     /**
      * 添加纵向项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
     @Override
     public int addDirection(EditProject editProject){
         int row = directionStandardMapper.addDirection(editProject);
         if (row <= 0){
             throw new CustomException("0", "添加失败");
         }
         return row;
     }

     /**
      * 查询纵向项目
      * @param pageNum 当前页码
      * @param pageSize 当前查询条数
      * @param context 项目描述
      * @param leid 级别id
      * @return 分页查询结果
      */
     @Override
     public Page<EditProject> selectDirection(Integer pageNum, Integer pageSize, String context, String leid){
         Page<EditProject> page = new Page<>(pageNum, pageSize);
         return directionStandardMapper.selectDirection(page, context, leid);
     }

     /**
      * 修改纵向项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
     @Override
     public int updateDirection(EditProject editProject){
         int row = directionStandardMapper.updateDirection(editProject);
         if (row <= 0){
             throw new CustomException("0", "修改失败");
         }
         return row;
     }

    /**
     * 查询纵向项目级别对应的分数
     * @return
     */
    @Override
    public List<EditProject> selectLevel(){
        return directionStandardMapper.selectLevel();
    }
}