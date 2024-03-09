package com.yc.standard.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.exception.CustomException;
import com.yc.standard.entity.ScientificStandard;
import com.yc.standard.mapper.ScientificStandardMapper;
import com.yc.standard.service.ScientificStandardService;
import com.yc.vo.EditProject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 科研基地/科学建设;(scientific_standard)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class ScientificStandardServiceImpl implements ScientificStandardService{
    @Resource
    private ScientificStandardMapper scientificStandardMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param ssid 主键
     * @return 实例对象
     */
    public ScientificStandard queryById(Integer ssid){
        return scientificStandardMapper.selectById(ssid);
    }
    
    /**
     * 分页查询
     *
     * @param scientificStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<ScientificStandard> paginQuery(ScientificStandard scientificStandard, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<ScientificStandard> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(scientificStandard.getRemarks())){
            queryWrapper.eq(ScientificStandard::getRemarks, scientificStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(scientificStandard.getStatus())){
            queryWrapper.eq(ScientificStandard::getStatus, scientificStandard.getStatus());
        }
        if(StrUtil.isNotBlank(scientificStandard.getPosit())){
            queryWrapper.eq(ScientificStandard::getPosit, scientificStandard.getPosit());
        }
        //2. 执行分页查询
        Page<ScientificStandard> pagin = new Page<>(current , size , true);
        IPage<ScientificStandard> selectResult = scientificStandardMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param scientificStandard 实例对象
     * @return 实例对象
     */
    public ScientificStandard insert(ScientificStandard scientificStandard){
        scientificStandardMapper.insert(scientificStandard);
        return scientificStandard;
    }
    
    /** 
     * 更新数据
     *
     * @param scientificStandard 实例对象
     * @return 实例对象
     */
    public ScientificStandard update(ScientificStandard scientificStandard){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<ScientificStandard> chainWrapper = new LambdaUpdateChainWrapper<ScientificStandard>(scientificStandardMapper);
        if(StrUtil.isNotBlank(scientificStandard.getRemarks())){
            chainWrapper.eq(ScientificStandard::getRemarks, scientificStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(scientificStandard.getStatus())){
            chainWrapper.eq(ScientificStandard::getStatus, scientificStandard.getStatus());
        }
        if(StrUtil.isNotBlank(scientificStandard.getPosit())){
            chainWrapper.eq(ScientificStandard::getPosit, scientificStandard.getPosit());
        }
        //2. 设置主键，并更新
        chainWrapper.set(ScientificStandard::getSsid, scientificStandard.getSsid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(scientificStandard.getSsid());
        }else{
            return scientificStandard;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param ssid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer ssid){
        int total = scientificStandardMapper.deleteById(ssid);
        return total > 0;
    }

     /**
      * 添加科研平台项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
     @Override
     public int addScientific(EditProject editProject){
         int row = scientificStandardMapper.addScientific(editProject);
         if (row <= 0){
             throw new CustomException("0", "添加失败");
         }
         return row;
     }

    /**
     * 查询科研平台
     * @param pageNum 当前页码
     * @param pageSize 当前查询条数
     * @param leid    级别id
     * @return 分页查询结果
     */
    @Override
    public Page<EditProject> selectScientific(Integer pageNum, Integer pageSize, String leid){
         Page<EditProject> page = new Page<>(pageNum, pageSize);
         return scientificStandardMapper.selectScientific(page, leid);
     }

    /**
     * 修改科研平台项目
     * @param editProject 修改的属性vo类
     * @return 0 失败 1 成功
     */
    @Override
    public int updateScientific(EditProject editProject){
        int row = scientificStandardMapper.updateScientific(editProject);
        if (row <= 0){
            throw new CustomException("0", "修改失败");
        }
        return row;
    }
}