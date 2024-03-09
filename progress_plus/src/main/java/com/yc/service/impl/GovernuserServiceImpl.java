package com.yc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.Governuser;
import com.yc.mapper.GovernuserMapper;
import com.yc.service.GovernuserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 /**
 * 管理员表;(governuser)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class GovernuserServiceImpl implements GovernuserService{
    @Autowired
    private GovernuserMapper governuserMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    public Governuser queryById(Integer id){
        return governuserMapper.selectById(id);
    }
    
    /**
     * 分页查询
     *
     * @param governuser 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<Governuser> paginQuery(Governuser governuser, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<Governuser> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(governuser.getUsername())){
            queryWrapper.eq(Governuser::getUsername, governuser.getUsername());
        }
        if(StrUtil.isNotBlank(governuser.getPassword())){
            queryWrapper.eq(Governuser::getPassword, governuser.getPassword());
        }
        if(StrUtil.isNotBlank(governuser.getNickname())){
            queryWrapper.eq(Governuser::getNickname, governuser.getNickname());
        }
        if(StrUtil.isNotBlank(governuser.getRolecode())){
            queryWrapper.eq(Governuser::getRole, governuser.getRole());
        }
        if(StrUtil.isNotBlank(governuser.getPhone())){
            queryWrapper.eq(Governuser::getPhone, governuser.getPhone());
        }
        //2. 执行分页查询
        Page<Governuser> pagin = new Page<>(current , size , true);
        IPage<Governuser> selectResult = governuserMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param governuser 实例对象
     * @return 实例对象
     */
    public Governuser insert(Governuser governuser){
        governuserMapper.insert(governuser);
        return governuser;
    }
    
    /** 
     * 更新数据
     *
     * @param governuser 实例对象
     * @return 实例对象
     */
    public Governuser update(Governuser governuser){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Governuser> chainWrapper = new LambdaUpdateChainWrapper<Governuser>(governuserMapper);
        if(StrUtil.isNotBlank(governuser.getUsername())){
            chainWrapper.eq(Governuser::getUsername, governuser.getUsername());
        }
        if(StrUtil.isNotBlank(governuser.getPassword())){
            chainWrapper.eq(Governuser::getPassword, governuser.getPassword());
        }
        if(StrUtil.isNotBlank(governuser.getNickname())){
            chainWrapper.eq(Governuser::getNickname, governuser.getNickname());
        }
        if(StrUtil.isNotBlank(governuser.getRolecode())){
            chainWrapper.eq(Governuser::getRole, governuser.getRole());
        }
        if(StrUtil.isNotBlank(governuser.getPhone())){
            chainWrapper.eq(Governuser::getPhone, governuser.getPhone());
        }
        //2. 设置主键，并更新
        chainWrapper.set(Governuser::getId, governuser.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(governuser.getId());
        }else{
            return governuser;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer id){
        int total = governuserMapper.deleteById(id);
        return total > 0;
    }
}