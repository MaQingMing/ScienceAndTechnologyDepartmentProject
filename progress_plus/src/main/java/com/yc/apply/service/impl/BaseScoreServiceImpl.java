package com.yc.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.BaseScore;
import com.yc.apply.mapper.BaseScoreMapper;
import com.yc.apply.service.BaseScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 /**
 * 底分表;(base_score)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
@Service
public class BaseScoreServiceImpl implements BaseScoreService {
    @Autowired
    private BaseScoreMapper baseScoreMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param bsid 主键
     * @return 实例对象
     */
    public BaseScore queryById(Integer bsid){
        return baseScoreMapper.selectById(bsid);
    }
    
    /**
     * 分页查询
     *
     * @param baseScore 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<BaseScore> paginQuery(BaseScore baseScore, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<BaseScore> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(baseScore.getJobs())){
            queryWrapper.eq(BaseScore::getJobs, baseScore.getJobs());
        }
        //2. 执行分页查询
        Page<BaseScore> pagin = new Page<>(current , size , true);
        IPage<BaseScore> selectResult = baseScoreMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param baseScore 实例对象
     * @return 实例对象
     */
    public BaseScore insert(BaseScore baseScore){
        baseScoreMapper.insert(baseScore);
        return baseScore;
    }
    
    /** 
     * 更新数据
     *
     * @param baseScore 实例对象
     * @return 实例对象
     */
    public BaseScore update(BaseScore baseScore){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<BaseScore> chainWrapper = new LambdaUpdateChainWrapper<BaseScore>(baseScoreMapper);
        if(StrUtil.isNotBlank(baseScore.getJobs())){
            chainWrapper.eq(BaseScore::getJobs, baseScore.getJobs());
        }
        //2. 设置主键，并更新
        chainWrapper.set(BaseScore::getBsid, baseScore.getBsid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(baseScore.getBsid());
        }else{
            return baseScore;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param bsid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer bsid){
        int total = baseScoreMapper.deleteById(bsid);
        return total > 0;
    }
}