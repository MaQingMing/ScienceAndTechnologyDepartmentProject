package com.yc.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.DirectionApplyInfo;
import com.yc.apply.mapper.DirectionApplyInfoMapper;
import com.yc.apply.service.DirectionApplyInfoService;
import com.yc.exception.CustomException;
import com.yc.vo.apply.ApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 /**
 * 纵向申请详情;(direction_apply_info)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class DirectionApplyInfoServiceImpl implements DirectionApplyInfoService{
    @Autowired
    private DirectionApplyInfoMapper directionApplyInfoMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param daiid 主键
     * @return 实例对象
     */
    public DirectionApplyInfo queryById(Integer daiid){
        return directionApplyInfoMapper.selectById(daiid);
    }
    
    /**
     * 分页查询
     *
     * @param directionApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<DirectionApplyInfo> paginQuery(DirectionApplyInfo directionApplyInfo, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<DirectionApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(directionApplyInfo.getType())){
            queryWrapper.eq(DirectionApplyInfo::getType, directionApplyInfo.getType());
        }
        if(StrUtil.isNotBlank(directionApplyInfo.getLeid())){
            queryWrapper.eq(DirectionApplyInfo::getLeid, directionApplyInfo.getLeid());
        }
        if(StrUtil.isNotBlank(directionApplyInfo.getFile())){
            queryWrapper.eq(DirectionApplyInfo::getFile, directionApplyInfo.getFile());
        }
        if(StrUtil.isNotBlank(directionApplyInfo.getIdentifier())){
            queryWrapper.eq(DirectionApplyInfo::getIdentifier, directionApplyInfo.getIdentifier());
        }
        if(StrUtil.isNotBlank(directionApplyInfo.getDept())){
            queryWrapper.eq(DirectionApplyInfo::getDept, directionApplyInfo.getDept());
        }
        if(StrUtil.isNotBlank(directionApplyInfo.getAccording())){
            queryWrapper.eq(DirectionApplyInfo::getAccording, directionApplyInfo.getAccording());
        }
        //2. 执行分页查询
        Page<DirectionApplyInfo> pagin = new Page<>(current , size , true);
        IPage<DirectionApplyInfo> selectResult = directionApplyInfoMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param directionApplyInfo 实例对象
     * @return 实例对象
     */
    public DirectionApplyInfo insert(DirectionApplyInfo directionApplyInfo){
        directionApplyInfoMapper.insert(directionApplyInfo);
        return directionApplyInfo;
    }
    
    /** 
     * 更新数据
     *
     * @param directionApplyInfo 实例对象
     * @return 实例对象
     */
    public DirectionApplyInfo update(DirectionApplyInfo directionApplyInfo){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<DirectionApplyInfo> chainWrapper = new LambdaUpdateChainWrapper<DirectionApplyInfo>(directionApplyInfoMapper);
        if(StrUtil.isNotBlank(directionApplyInfo.getType())){
            chainWrapper.eq(DirectionApplyInfo::getType, directionApplyInfo.getType());
        }
        if(StrUtil.isNotBlank(directionApplyInfo.getLeid())){
            chainWrapper.eq(DirectionApplyInfo::getLeid, directionApplyInfo.getLeid());
        }
        if(StrUtil.isNotBlank(directionApplyInfo.getFile())){
            chainWrapper.eq(DirectionApplyInfo::getFile, directionApplyInfo.getFile());
        }
        if(StrUtil.isNotBlank(directionApplyInfo.getIdentifier())){
            chainWrapper.eq(DirectionApplyInfo::getIdentifier, directionApplyInfo.getIdentifier());
        }
        if(StrUtil.isNotBlank(directionApplyInfo.getDept())){
            chainWrapper.eq(DirectionApplyInfo::getDept, directionApplyInfo.getDept());
        }
        if(StrUtil.isNotBlank(directionApplyInfo.getAccording())){
            chainWrapper.eq(DirectionApplyInfo::getAccording, directionApplyInfo.getAccording());
        }
        //2. 设置主键，并更新
        chainWrapper.set(DirectionApplyInfo::getDaiid, directionApplyInfo.getDaiid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(directionApplyInfo.getDaiid());
        }else{
            return directionApplyInfo;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param daiid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer daiid){
        int total = directionApplyInfoMapper.deleteById(daiid);
        return total > 0;
    }

    /**
     * @Description 查询纵向科研项目种类
     * @Return
     * @Author dm
     * @Date Created in 2023/10/25
     **/
    @Override
    public Map<String, List<String>> queryInventTypesAndLevels() {
        Map<String,List<String>> map = new HashMap<>();
        List<String> types = directionApplyInfoMapper.queryInventTypes();
        map.put("types",types);
        return map;
    }

    /**
     * 添加纵向项目申请
     * @param applyVo 申请的vo
     * @return
     */
    @Override
    public int addDirection(ApplyVo applyVo){
        int row = directionApplyInfoMapper.addDirection(applyVo);
        if (row <= 0){
            throw new CustomException("0", "申请失败");
        }
        return row;
    }
}