package com.yc.standard.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.exception.CustomException;
import com.yc.standard.entity.TransverseStandard;
import com.yc.standard.mapper.TransverseStandardMapper;
import com.yc.standard.service.TransverseStandardService;
import com.yc.vo.EditProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 横向项目;(transverse_standard)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class TransverseStandardServiceImpl implements TransverseStandardService{
    @Autowired
    private TransverseStandardMapper transverseStandardMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param tsid 主键
     * @return 实例对象
     */
    public TransverseStandard queryById(Integer tsid){
        return transverseStandardMapper.selectById(tsid);
    }
    
    /**
     * 分页查询
     *
     * @param transverseStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<TransverseStandard> paginQuery(TransverseStandard transverseStandard, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TransverseStandard> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(transverseStandard.getType())){
            queryWrapper.eq(TransverseStandard::getType, transverseStandard.getType());
        }
        if(StrUtil.isNotBlank(transverseStandard.getContext())){
            queryWrapper.eq(TransverseStandard::getContext, transverseStandard.getContext());
        }
        if(StrUtil.isNotBlank(transverseStandard.getStatus())){
            queryWrapper.eq(TransverseStandard::getStatus, transverseStandard.getStatus());
        }
        if(StrUtil.isNotBlank(transverseStandard.getRemarks())){
            queryWrapper.eq(TransverseStandard::getRemarks, transverseStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(transverseStandard.getPosit())){
            queryWrapper.eq(TransverseStandard::getPosit, transverseStandard.getPosit());
        }
        //2. 执行分页查询
        Page<TransverseStandard> pagin = new Page<>(current , size , true);
        IPage<TransverseStandard> selectResult = transverseStandardMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param transverseStandard 实例对象
     * @return 实例对象
     */
    public TransverseStandard insert(TransverseStandard transverseStandard){
        transverseStandardMapper.insert(transverseStandard);
        return transverseStandard;
    }
    
    /** 
     * 更新数据
     *
     * @param transverseStandard 实例对象
     * @return 实例对象
     */
    public TransverseStandard update(TransverseStandard transverseStandard){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TransverseStandard> chainWrapper = new LambdaUpdateChainWrapper<TransverseStandard>(transverseStandardMapper);
        if(StrUtil.isNotBlank(transverseStandard.getType())){
            chainWrapper.eq(TransverseStandard::getType, transverseStandard.getType());
        }
        if(StrUtil.isNotBlank(transverseStandard.getContext())){
            chainWrapper.eq(TransverseStandard::getContext, transverseStandard.getContext());
        }
        if(StrUtil.isNotBlank(transverseStandard.getStatus())){
            chainWrapper.eq(TransverseStandard::getStatus, transverseStandard.getStatus());
        }
        if(StrUtil.isNotBlank(transverseStandard.getRemarks())){
            chainWrapper.eq(TransverseStandard::getRemarks, transverseStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(transverseStandard.getPosit())){
            chainWrapper.eq(TransverseStandard::getPosit, transverseStandard.getPosit());
        }
        //2. 设置主键，并更新
        chainWrapper.set(TransverseStandard::getTsid, transverseStandard.getTsid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(transverseStandard.getTsid());
        }else{
            return transverseStandard;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param tsid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer tsid){
        int total = transverseStandardMapper.deleteById(tsid);
        return total > 0;
    }

     /**
      * 添加横向项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
     @Override
     public int addTransverse(EditProject editProject){
         int row = transverseStandardMapper.addTransverse(editProject);
         if (row <= 0){
             throw new CustomException("0", "添加失败");
         }
         return row;
     }

     /**
      * 查询横向项目
      * @param pageNum 当前页码
      * @param pageSize 当前查询条数
      * @param leid    级别id
      * @return 分页查询结果
      */
     @Override
     public Page<EditProject> selectTransverse(Integer pageNum, Integer pageSize, String context, String leid){
         Page<EditProject> page = new Page<>(pageNum, pageSize);
         return transverseStandardMapper.selectTransverse(page, context, leid);
     }

     /**
      * 修改横向项目
      * @param editProject 修改的属性vo类
      * @return 0 失败 1 成功
      */
     @Override
     public int updateTransverse(EditProject editProject){
         int row = transverseStandardMapper.updateTransverse(editProject);
         if (row <= 0){
             throw new CustomException("0", "修改失败");
         }
         return row;
     }

    /**
     * 查询横向项目级别对应的分数
     * @return
     */
    @Override
    public List<EditProject> selectLevel(){
        List<EditProject> results = transverseStandardMapper.selectLevel();
        if (results.size() <= 0){
            throw new CustomException("0", "暂无信息");
        }
        return results;
    }

    /**
     * 查询类型对应的分数
     * @param type 自科 | 社科
     * @return
     */
    @Override
    public List<EditProject> selectScore(String type){
        List<EditProject> results = transverseStandardMapper.selectScore(type);
        if (results.size() <= 0){
            throw new CustomException("0", "暂无信息");
        }
        return results;
    }
}