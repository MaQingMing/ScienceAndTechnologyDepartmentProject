package com.yc.standard.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.GainApply;
import com.yc.apply.mapper.GainApplyMapper;
import com.yc.entity.Systemuser;
import com.yc.exception.CustomException;
import com.yc.standard.entity.TechResultsType;
import com.yc.standard.mapper.TechResultsLevelMapper;
import com.yc.standard.mapper.TechResultsTypeMapper;
import com.yc.standard.service.TechResultsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * 科技成果分类;(tech_results_type)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class TechResultsTypeServiceImpl implements TechResultsTypeService{
    
    @Autowired
    private TechResultsTypeMapper techResultsTypeMapper;
    @Autowired
    private TechResultsLevelMapper techResultsLevelMapper;
    @Autowired
    private GainApplyMapper gainApplyMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param trid 主键
     * @return 实例对象
     */
    @Override
    public TechResultsType queryById(Integer trid){
        return techResultsTypeMapper.selectById(trid);
    }
    
    /**
     * 分页查询
     *
     * @param techResultsType 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @Override
    public Page<TechResultsType> paginQuery(TechResultsType techResultsType, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TechResultsType> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(techResultsType.getTrname())){
            queryWrapper.eq(TechResultsType::getTrname, techResultsType.getTrname());
        }
        if(StrUtil.isNotBlank(techResultsType.getRemarks())){
            queryWrapper.eq(TechResultsType::getRemarks, techResultsType.getRemarks());
        }
        if(StrUtil.isNotBlank(techResultsType.getStatus())){
            queryWrapper.eq(TechResultsType::getStatus, techResultsType.getStatus());
        }
        //2. 执行分页查询
        Page<TechResultsType> pagin = new Page<>(current , size , true);
        IPage<TechResultsType> selectResult = techResultsTypeMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param techResultsType 实例对象
     * @return 实例对象
     */
    @Override
    public TechResultsType insert(TechResultsType techResultsType){
        techResultsTypeMapper.insert(techResultsType);
        return techResultsType;
    }
    
    /** 
     * 更新数据
     *
     * @param techResultsType 实例对象
     * @return 实例对象
     */
    @Override
    public TechResultsType update(TechResultsType techResultsType){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<TechResultsType> chainWrapper = new LambdaUpdateChainWrapper<TechResultsType>(techResultsTypeMapper);
        if(StrUtil.isNotBlank(techResultsType.getTrname())){
            chainWrapper.eq(TechResultsType::getTrname, techResultsType.getTrname());
        }
        if(StrUtil.isNotBlank(techResultsType.getRemarks())){
            chainWrapper.eq(TechResultsType::getRemarks, techResultsType.getRemarks());
        }
        if(StrUtil.isNotBlank(techResultsType.getStatus())){
            chainWrapper.eq(TechResultsType::getStatus, techResultsType.getStatus());
        }
        //2. 设置主键，并更新
        chainWrapper.set(TechResultsType::getTrid, techResultsType.getTrid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(techResultsType.getTrid());
        }else{
            return techResultsType;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param trid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer trid){
        int total = techResultsTypeMapper.deleteById(trid);
        return total > 0;
    }

     /**
      * 添加科技项目类别
      * @param techResultsType 科技项目类别实例
      * @return
      */
     @Override
     public int addTechResultsType(TechResultsType techResultsType){
         if (techResultsType == null){
             throw new CustomException("0","添加失败!");
         }
         return techResultsTypeMapper.addTechResultsType(techResultsType);
     }

    /**
     * 查询科技项目类别
     * @param trname 科技项目类别名称
     * @return
     */
    @Override
    public List<TechResultsType> selectTechResultsType(String trname){
        return techResultsTypeMapper.selectTechResultsType(trname);
    }

    /**
     * 修改科技项目类别
     * @param techResultsType 科技项目类别名称
     * @return 1 成功 0 失败
     */
    @Override
    public int updateTechResultsType(TechResultsType techResultsType){
        if (techResultsType == null){
            throw new CustomException("0","修改失败!");
        }
        return techResultsTypeMapper.updateTechResultsType(techResultsType);
    }

    /**
     * 修改项目类别状态
     * @param status 将被修改的状态
     * @param trid 被修改的类别id
     * @return 0 失败 1成功
     */
    @Override
    public int modifyStatus(Integer status, Integer trid){
        int row = techResultsTypeMapper.modifyStatus(status, trid);
        if (row == 0){
            throw new CustomException("0", "修改失败!");
        }
        return row;
    }

    /**
     * 查询启用的项目类型名和id
     * @return 查询的项目类型名和id集合
     */
    @Override
    public List<TechResultsType> selectTrname(){
        List<TechResultsType> techResultsTypes = techResultsTypeMapper.selectTrname();
        if (techResultsTypes.size() <= 0){
            throw new CustomException("0","暂无信息");
        }
        return techResultsTypes;
    }

    /**
     * 查询计算依据
     * @param trid 项目id
     * @return 计算依据
     */
    @Override
    public String selectAccording(String trid){
         return techResultsTypeMapper.selectAccording(trid);
     }



    @Override
    public List<String> queryAllType(String tid, String status, HttpSession session) {
        List<String> stringList = techResultsLevelMapper.queryAllType(tid, status);

        Map<Integer, String> stringMap = new HashMap<>(10);
        for (String s : stringList) {
            String[] split = s.split(",");
            stringMap.put(Integer.valueOf(split[1]),s);
        }

        List<GainApply> applyCount = null ;
        LambdaQueryWrapper<GainApply> wrapper = Wrappers.<GainApply>lambdaQuery().groupBy(GainApply::getTrtid);
        if(Objects.nonNull(session) && Objects.nonNull(tid)){
            if((boolean) session.getAttribute("isAdmin")){
                if("1".equals(tid) || "2".equals(tid)){
                    applyCount = gainApplyMapper.findApplyCount(null,tid,status);
                }
            }
        }else{
            if(!(boolean) session.getAttribute("isAdmin")){
                Map<String, Object> systemuser = (Map<String, Object>) session.getAttribute("systemuser");
                applyCount = gainApplyMapper.findApplyCount(systemuser.get("id") + "",null,"0,1");
            }else{
                Map<String, Object> systemuser = (Map<String, Object>) session.getAttribute("governuser");
                applyCount = gainApplyMapper.findApplyByCollegeCount(0,Integer.valueOf(String.valueOf(systemuser.get("tid"))));
            }
        }

        List<String> result = new ArrayList<>(10);
        if(applyCount!=null && !applyCount.isEmpty()){
            for (GainApply gainApply : applyCount) {
                if (stringMap.get(Integer.valueOf(gainApply.getTrtid()))!=null) {
                    result.add(stringMap.get(Integer.valueOf(gainApply.getTrtid()))+","+gainApply.getStatus());
                }
            }
        }else{
            result.addAll(stringList);
        }

        return result;
    }
}