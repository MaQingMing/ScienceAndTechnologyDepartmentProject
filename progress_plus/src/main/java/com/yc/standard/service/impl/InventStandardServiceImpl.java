package com.yc.standard.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.exception.CustomException;
import com.yc.standard.entity.InventStandard;
import com.yc.standard.mapper.InventStandardMapper;
import com.yc.standard.service.InventStandardService;
import com.yc.vo.EditProject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 发明专利;(invent_standard)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class InventStandardServiceImpl implements InventStandardService{
    @Resource
    private InventStandardMapper inventStandardMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param iid 主键
     * @return 实例对象
     */
    public InventStandard queryById(Integer iid){
        return inventStandardMapper.selectById(iid);
    }
    
    /**
     * 分页查询
     *
     * @param inventStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<InventStandard> paginQuery(InventStandard inventStandard, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<InventStandard> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(inventStandard.getStage())){
            queryWrapper.eq(InventStandard::getStage, inventStandard.getStage());
        }
        if(StrUtil.isNotBlank(inventStandard.getRemarks())){
            queryWrapper.eq(InventStandard::getRemarks, inventStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(inventStandard.getStatus())){
            queryWrapper.eq(InventStandard::getStatus, inventStandard.getStatus());
        }
        if(StrUtil.isNotBlank(inventStandard.getPosit())){
            queryWrapper.eq(InventStandard::getPosit, inventStandard.getPosit());
        }
        //2. 执行分页查询
        Page<InventStandard> pagin = new Page<>(current , size , true);
        IPage<InventStandard> selectResult = inventStandardMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param inventStandard 实例对象
     * @return 实例对象
     */
    public InventStandard insert(InventStandard inventStandard){
        inventStandardMapper.insert(inventStandard);
        return inventStandard;
    }
    
    /** 
     * 更新数据
     *
     * @param inventStandard 实例对象
     * @return 实例对象
     */
    public InventStandard update(InventStandard inventStandard){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<InventStandard> chainWrapper = new LambdaUpdateChainWrapper<InventStandard>(inventStandardMapper);
        if(StrUtil.isNotBlank(inventStandard.getStage())){
            chainWrapper.eq(InventStandard::getStage, inventStandard.getStage());
        }
        if(StrUtil.isNotBlank(inventStandard.getRemarks())){
            chainWrapper.eq(InventStandard::getRemarks, inventStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(inventStandard.getStatus())){
            chainWrapper.eq(InventStandard::getStatus, inventStandard.getStatus());
        }
        if(StrUtil.isNotBlank(inventStandard.getPosit())){
            chainWrapper.eq(InventStandard::getPosit, inventStandard.getPosit());
        }
        //2. 设置主键，并更新
        chainWrapper.set(InventStandard::getIid, inventStandard.getIid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(inventStandard.getIid());
        }else{
            return inventStandard;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param iid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer iid){
        int total = inventStandardMapper.deleteById(iid);
        return total > 0;
    }

     /**
      * 添加发明专利项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
     @Override
     public int addInvent(EditProject editProject){
         int row = inventStandardMapper.addInvent(editProject);
         if (row <= 0){
             throw new CustomException("0", "添加失败");
         }
         return row;
     }

     /**
      * 查询发明专利
      * @param pageNum 当前页码
      * @param pageSize 当前查询条数
      * @param leid    级别id
      * @return 分页查询结果
      */
     @Override
     public Page<EditProject> selectInvent(Integer pageNum, Integer pageSize, String leid){
         Page<EditProject> page = new Page<>(pageNum, pageSize);
         return inventStandardMapper.selectInvent(page, leid);
     }

    /**
     * 修改发明专利项目
     * @param editProject 修改的属性vo类
     * @return 0 失败 1 成功
     */
    @Override
    public int updateInvent(EditProject editProject){
        int row = inventStandardMapper.updateInvent(editProject);
        if (row <= 0){
            throw new CustomException("0", "修改失败");
        }
        return row;
    }

    /**
     * 查询专利级别对应的分数
     * @return
     */
    @Override
    public List<EditProject> selectLevel(){
        List<EditProject> results = inventStandardMapper.selectLevel();
        if (results.size() <= 0){
            throw new CustomException("0", "暂无信息");
        }
        return results;
    }
}