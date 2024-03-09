package com.yc.standard.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.exception.CustomException;
import com.yc.standard.entity.HonorStandard;
import com.yc.standard.mapper.HonorStandardMapper;
import com.yc.standard.service.HonorStandardService;
import com.yc.vo.EditProject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 科技类荣誉(称号);(honor_standard)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class HonorStandardServiceImpl implements HonorStandardService{
    @Resource
    private HonorStandardMapper honorStandardMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param hsid 主键
     * @return 实例对象
     */
    public HonorStandard queryById(Integer hsid){
        return honorStandardMapper.selectById(hsid);
    }
    
    /**
     * 分页查询
     *
     * @param honorStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<HonorStandard> paginQuery(HonorStandard honorStandard, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<HonorStandard> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(honorStandard.getRemarks())){
            queryWrapper.eq(HonorStandard::getRemarks, honorStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(honorStandard.getStatus())){
            queryWrapper.eq(HonorStandard::getStatus, honorStandard.getStatus());
        }
        if(StrUtil.isNotBlank(honorStandard.getPosit())){
            queryWrapper.eq(HonorStandard::getPosit, honorStandard.getPosit());
        }
        //2. 执行分页查询
        Page<HonorStandard> pagin = new Page<>(current , size , true);
        IPage<HonorStandard> selectResult = honorStandardMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param honorStandard 实例对象
     * @return 实例对象
     */
    public HonorStandard insert(HonorStandard honorStandard){
        honorStandardMapper.insert(honorStandard);
        return honorStandard;
    }
    
    /** 
     * 更新数据
     *
     * @param honorStandard 实例对象
     * @return 实例对象
     */
    public HonorStandard update(HonorStandard honorStandard){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<HonorStandard> chainWrapper = new LambdaUpdateChainWrapper<HonorStandard>(honorStandardMapper);

        if(StrUtil.isNotBlank(honorStandard.getRemarks())){
            chainWrapper.eq(HonorStandard::getRemarks, honorStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(honorStandard.getStatus())){
            chainWrapper.eq(HonorStandard::getStatus, honorStandard.getStatus());
        }
        if(StrUtil.isNotBlank(honorStandard.getPosit())){
            chainWrapper.eq(HonorStandard::getPosit, honorStandard.getPosit());
        }
        //2. 设置主键，并更新
        chainWrapper.set(HonorStandard::getHsid, honorStandard.getHsid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(honorStandard.getHsid());
        }else{
            return honorStandard;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param hsid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer hsid){
        int total = honorStandardMapper.deleteById(hsid);
        return total > 0;
    }

     /**
      * 添加科技荣誉项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
     @Override
     public int addHonor(EditProject editProject){
         int row = honorStandardMapper.addHonor(editProject);
         if (row <= 0){
             throw new CustomException("0", "添加失败");
         }
         return row;
     }

    /**
     * 查询科技荣誉
     * @param pageNum 当前页码
     * @param pageSize 当前查询条数
     * @param leid    级别id
     * @return 分页查询结果
     */
    @Override
    public Page<EditProject> selectHonor(Integer pageNum, Integer pageSize, String leid){
         Page<EditProject> page = new Page<>(pageNum, pageSize);
         return honorStandardMapper.selectHonor(page, leid);
     }

    /**
     * 修改科技荣誉项目
     * @param editProject 添加的属性vo类
     * @return 0 失败 1 成功
     */
    @Override
    public int updateHonor(EditProject editProject){
        int row = honorStandardMapper.updateHonor(editProject);
        if (row <= 0){
            throw new CustomException("0", "修改失败");
        }
        return row;
    }

    /**
     * 查询荣誉级别对应的分数
     * @return
     */
    @Override
    public List<EditProject> selectLevel(){
        List<EditProject> results = honorStandardMapper.selectLevel();
        if (results.size() <= 0){
            throw new CustomException("0", "暂无信息");
        }
        return results;
    }
}