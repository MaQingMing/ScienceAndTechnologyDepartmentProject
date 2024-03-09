package com.yc.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.entity.Role;
import com.yc.mapper.RoleMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


@Service
public class RoleService extends ServiceImpl<RoleMapper, Role> {

    @Resource
    private RoleMapper roleMapper;

    /**
     * 查询所有角色
     * @return
     */
    public Set<String> selectAllRole(){
        return roleMapper.selectAllRole();
    }

    /**
     * 查询除普通用户的角色
     * @return
     */
    public List<Role> selectRole(){
        return roleMapper.selectRole();
    }

}
