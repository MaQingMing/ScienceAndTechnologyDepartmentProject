package com.yc.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.TransverseApplyInfo;
import com.yc.apply.mapper.TransverseApplyInfoMapper;
import com.yc.apply.service.TransverseApplyInfoService;
import com.yc.exception.CustomException;
import com.yc.vo.apply.ApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 /**
 * 横向申请详情;(transverse_apply_info)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class TransverseApplyInfoServiceImpl implements TransverseApplyInfoService{
    @Autowired
    private TransverseApplyInfoMapper transverseApplyInfoMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param undefinedId 主键
     * @return 实例对象
     */
    public TransverseApplyInfo queryById(String undefinedId){
        return transverseApplyInfoMapper.selectById(undefinedId);
    }
    
    /**
     * 分页查询
     *
     * @param transverseApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<TransverseApplyInfo> paginQuery(TransverseApplyInfo transverseApplyInfo, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<TransverseApplyInfo> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(transverseApplyInfo.getTaiid())){
            queryWrapper.eq(TransverseApplyInfo::getTaiid, transverseApplyInfo.getTaiid());
        }
        if(StrUtil.isNotBlank(transverseApplyInfo.getCard())){
            queryWrapper.eq(TransverseApplyInfo::getCard, transverseApplyInfo.getCard());
        }
        if(StrUtil.isNotBlank(transverseApplyInfo.getName())){
            queryWrapper.eq(TransverseApplyInfo::getName, transverseApplyInfo.getName());
        }
        if(StrUtil.isNotBlank(transverseApplyInfo.getMoney())){
            queryWrapper.eq(TransverseApplyInfo::getMoney, transverseApplyInfo.getMoney());
        }
        if(StrUtil.isNotBlank(transverseApplyInfo.getAccording())){
            queryWrapper.eq(TransverseApplyInfo::getAccording, transverseApplyInfo.getAccording());
        }
        if(StrUtil.isNotBlank(transverseApplyInfo.getLeid())){
            queryWrapper.eq(TransverseApplyInfo::getLeid, transverseApplyInfo.getLeid());
        }
        //2. 执行分页查询
        Page<TransverseApplyInfo> pagin = new Page<>(current , size , true);
        IPage<TransverseApplyInfo> selectResult = transverseApplyInfoMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param transverseApplyInfo 实例对象
     * @return 实例对象
     */
    public TransverseApplyInfo insert(TransverseApplyInfo transverseApplyInfo){
        transverseApplyInfoMapper.insert(transverseApplyInfo);
        return transverseApplyInfo;
    }

     @Override
     public TransverseApplyInfo update(TransverseApplyInfo transverseApplyInfo) {
         return null;
     }
    
    /** 
     * 通过主键删除数据
     *
     * @param undefinedId 主键
     * @return 是否成功
     */
    public boolean deleteById(String undefinedId){
        int total = transverseApplyInfoMapper.deleteById(undefinedId);
        return total > 0;
    }

     /**
      * 添加横向项目申请
      *
      * @param applyVo
      * @return
      */
     @Override
     public int addTransverse(ApplyVo applyVo){
         int row = transverseApplyInfoMapper.addTransverse(applyVo);
         if (row <= 0){
             throw new CustomException("0", "添加失败");
         }
         return row;
     }
}