package com.yc.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.entity.Labeldept;
import com.yc.entity.Role;
import com.yc.mapper.LabeldeptMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service
public class LabeldeptBizImpl extends ServiceImpl<LabeldeptMapper, Labeldept> {
    @Resource
    LabeldeptMapper labeldeptMapper;

    public Set<String> selectAllRole(){
        return labeldeptMapper.selectAllDept();
    }

    /**
     * 查询除普通用户的角色
     * @return
     */
    public List<Labeldept> selectDepts(){
        return labeldeptMapper.selectDepts();
    }
}
