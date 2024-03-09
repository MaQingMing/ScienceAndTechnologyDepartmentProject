package com.yc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.Labeldept;
import com.yc.exception.CustomException;
import com.yc.mapper.LabeldeptMapper;
import com.yc.service.LabeldeptService;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 部门表;(labeldept)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-11-6
 */
@Service
public class LabeldeptServiceImpl implements LabeldeptService{
    @Autowired
    private LabeldeptMapper labeldeptMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param tid 主键
     * @return 实例对象
     */
    @Override
    public Labeldept queryById(String tid){
        return labeldeptMapper.selectById(tid);
    }

    /**
     * 分页查询
     *
     * @param labeldept 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<Labeldept> paginQuery(Labeldept labeldept, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<Labeldept> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(labeldept.getTname())){
            queryWrapper.eq(Labeldept::getTname, labeldept.getTname());
        }
        //2. 执行分页查询
        Page<Labeldept> pagin = new Page<>(current , size , true);
        IPage<Labeldept> selectResult = labeldeptMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param labeldept 实例对象
     * @return 实例对象
     */
    @Override
    public Labeldept insert(Labeldept labeldept){
        labeldeptMapper.insert(labeldept);
        return labeldept;
    }

    @Override
    public int update(Labeldept labeldept) {
        return labeldeptMapper.updateById(labeldept);
    }

    /**

    /**
     * 通过主键删除数据
     *
     * @param tid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String tid){
        int total = labeldeptMapper.deleteById(tid);
        return total > 0;
    }

    /**
     * 查询所有的部门信息
     * @return  结果集
     */
    @Override
    public List<Labeldept> showAll(){
        List<Labeldept> labeldepts = labeldeptMapper.showAll();
        if (labeldepts.size() <= 0){
            throw new CustomException("0", "暂无信息");
        }
        return labeldepts;
    }

    /**
     * 查询不属于自科|社科的部门信息
     * @return  结果集
     */
    @Override
    public List<Labeldept> showNoScience(){
        List<Labeldept> labeldepts = labeldeptMapper.showNoScience();
        if (labeldepts.size() <= 0){
            throw new CustomException("0", "暂无信息");
        }
        return labeldepts;
    }
}
