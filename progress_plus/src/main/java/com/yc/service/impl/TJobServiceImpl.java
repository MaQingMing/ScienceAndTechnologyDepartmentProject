package com.yc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.TJob;
import com.yc.mapper.TJobMapper;
import com.yc.service.TJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 /**
 * 职位/学位;(t_job)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class TJobServiceImpl implements TJobService{
    @Autowired
    private TJobMapper tJobMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    public TJob queryById(Integer id){
        return tJobMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param tJob 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<TJob> paginQuery(TJob tJob, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TJob> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(tJob.getContext())){
            queryWrapper.eq(TJob::getContext, tJob.getContext());
        }
        //2. 执行分页查询
        Page<TJob> pagin = new Page<>(current , size , true);
        IPage<TJob> selectResult = tJobMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param tJob 实例对象
     * @return 实例对象
     */
    public TJob insert(TJob tJob){
        tJobMapper.insert(tJob);
        return tJob;
    }
    
    /** 
     * 更新数据
     *
     * @param tJob 实例对象
     * @return 实例对象
     */
    public TJob update(TJob tJob){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TJob> chainWrapper = new LambdaUpdateChainWrapper<TJob>(tJobMapper);
        if(StrUtil.isNotBlank(tJob.getContext())){
            chainWrapper.eq(TJob::getContext, tJob.getContext());
        }
        //2. 设置主键，并更新
        chainWrapper.set(TJob::getId, tJob.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(tJob.getId());
        }else{
            return tJob;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer id){
        int total = tJobMapper.deleteById(id);
        return total > 0;
    }
}