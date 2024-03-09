package com.yc.standard.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.exception.CustomException;
import com.yc.standard.service.TechResultsLevelService;
import com.yc.standard.entity.TechResultsLevel;
import com.yc.standard.mapper.TechResultsLevelMapper;
import com.yc.standard.service.TechResultsLevelService;
import com.yc.vo.TechResultsLevelAndTrname;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
* 科技成果分类;(tech_results_type)表服务实现类
* @author : http://www.chiner.pro
* @date : 2023-10-17
*/
@Service
public class TechResultsLevelServiceImpl implements TechResultsLevelService{
   @Resource
   private TechResultsLevelMapper techResultsLevelMapper;

    /**
     * 添加项目级别
     * @param techResultsLevel 实例对象
     * @return 1 成功 0 失败
     */
    @Override
    public int addTechResultsLevel(TechResultsLevel techResultsLevel){
        int row = techResultsLevelMapper.addTechResultsLevel(techResultsLevel);
        if (row <= 0){
            throw new CustomException("0", "添加失败!");
        }
        return row;
    }
   
   /** 
    * 通过ID查询单条数据 
    *
    * @param leid 主键
    * @return 实例对象
    */
   public TechResultsLevel queryById(Integer leid){
       return techResultsLevelMapper.selectById(leid);
   }
   
   /**
    * 分页查询
    *
    * @param techResultsLevel 筛选条件
    * @param current 当前页码
    * @param size  每页大小
    * @return
    */
   public Page<TechResultsLevel> paginQuery(TechResultsLevel techResultsLevel, long current, long size){
       //1. 构建动态查询条件
       LambdaQueryWrapper<TechResultsLevel> queryWrapper = new LambdaQueryWrapper<>();
       if(StrUtil.isNotBlank(techResultsLevel.getLname())){
           queryWrapper.eq(TechResultsLevel::getLname, techResultsLevel.getLname());
       }
       if(StrUtil.isNotBlank(techResultsLevel.getRemarks())){
           queryWrapper.eq(TechResultsLevel::getRemarks, techResultsLevel.getRemarks());
       }
       if(StrUtil.isNotBlank(techResultsLevel.getStatus())){
           queryWrapper.eq(TechResultsLevel::getStatus, techResultsLevel.getStatus());
       }
       //2. 执行分页查询
       Page<TechResultsLevel> pagin = new Page<>(current , size , true);
       IPage<TechResultsLevel> selectResult = techResultsLevelMapper.selectByPage(pagin , queryWrapper);
       pagin.setPages(selectResult.getPages());
       pagin.setTotal(selectResult.getTotal());
       pagin.setRecords(selectResult.getRecords());
       //3. 返回结果
       return pagin;
   }
   
   /** 
    * 新增数据
    *
    * @param techResultsLevel 实例对象
    * @return 实例对象
    */
   public TechResultsLevel insert(TechResultsLevel techResultsLevel){
       techResultsLevelMapper.insert(techResultsLevel);
       return techResultsLevel;
   }
   
   /** 
    * 更新数据
    *
    * @param techResultsLevel 实例对象
    * @return 实例对象
    */
   public TechResultsLevel update(TechResultsLevel techResultsLevel){
       //1. 根据条件动态更新
       LambdaUpdateChainWrapper<TechResultsLevel> chainWrapper = new LambdaUpdateChainWrapper<TechResultsLevel>(techResultsLevelMapper);
       if(StrUtil.isNotBlank(techResultsLevel.getLname())){
           chainWrapper.eq(TechResultsLevel::getLname, techResultsLevel.getLname());
       }
       if(StrUtil.isNotBlank(techResultsLevel.getRemarks())){
           chainWrapper.eq(TechResultsLevel::getRemarks, techResultsLevel.getRemarks());
       }
       if(StrUtil.isNotBlank(techResultsLevel.getStatus())){
           chainWrapper.eq(TechResultsLevel::getStatus, techResultsLevel.getStatus());
       }
       //2. 设置主键，并更新
       chainWrapper.set(TechResultsLevel::getLeid, techResultsLevel.getLeid());
       boolean ret = chainWrapper.update();
       //3. 更新成功了，查询最最对象返回
       if(ret){
           return queryById(techResultsLevel.getLeid());
       }else{
           return techResultsLevel;
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
       int total = techResultsLevelMapper.deleteById(trid);
       return total > 0;
   }

    /**
     * 查询项目级别
     * @param trid  项目类别id
     * @param lname   项目级别名
     * @param currentPage 当前页
     * @param pageSize 查询条数
     * @return  分页查询结果
     */
    @Override
    public Page<TechResultsLevelAndTrname> selectTechResultsLevelByPage(String trid, String lname, Integer currentPage, Integer pageSize){
        Page<TechResultsLevelAndTrname> page = new Page<>(currentPage, pageSize);
        return techResultsLevelMapper.selectTechResultsLevelByPage(page, trid, lname);
    }

    /**
     * 修改项目级别
     * @param techResultsLevel
     * @return 1 成功 0 失败
     */
    @Override
    public int modifyTechResultsLevel(TechResultsLevel techResultsLevel){
        int row = techResultsLevelMapper.modifyTechResultsLevel(techResultsLevel);
        if (row <= 0){
            throw new CustomException("0", "修改失败!");
        }
        return row;
    }

    /**
     * 修改项目级别状态
     * @param tableName 表名
     * @param status 将被修改的状态
     * @param leid 被修改的级别id
     * @return 0 失败 1成功
     */
    @Override
    public int modifyStatus(String tableName, Integer status, Integer leid){
        int row = techResultsLevelMapper.modifyStatus(tableName, status, leid);
        if (row <= 0){
            throw new CustomException("0", "修改失败");
        }
        return row;
    }

    /**
     * 根据类型id查询项目级别
     * @param trid 类型id
     * @return  根据id查询的项目级别
     */
    @Override
    public List<TechResultsLevel> selectLevelByTrid(Integer trid){
        List<TechResultsLevel> techResultsLevels = techResultsLevelMapper.selectLevelByTrid(trid);
        if (techResultsLevels.size() <= 0){
            throw new CustomException("0", "暂无信息");
        }
        return techResultsLevels;
    }

    /**
     * 修改项目级别是否可以换现
     *
     * @param tableName
     * @param cash      1 可以 0 不可以
     * @param idName id字段的名称
     * @param id        被修改的
     * @return 1 成功 0 失败
     */
    @Override
    public int modifyCash(String tableName, Integer cash, String idName, Integer id){
        int row = techResultsLevelMapper.modifyCash(tableName, cash, idName, id);
        if (row <= 0){
            throw new CustomException("0", "修改失败");
        }
        return row;
    }

    /**
     * 修改项目状态
     *
     * @param tableName 表名
     * @param idName id字段的名称
     * @param status    将被修改的状态
     * @param id        被修改的项目id
     * @return 0 失败 1成功
     */
    @Override
    public int modifyProjectStatus(String tableName, Integer status, String idName, Integer id){
        int row = techResultsLevelMapper.modifyProjectStatus(tableName, status, idName, id);
        if (row <= 0){
            throw new CustomException("0", "修改失败");
        }
        return row;
    }
}