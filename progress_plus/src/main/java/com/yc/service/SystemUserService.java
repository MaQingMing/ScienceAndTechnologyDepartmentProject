package com.yc.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.entity.Permission;
import com.yc.entity.Role;
import com.yc.entity.Systemuser;
import com.yc.exception.CustomException;
import com.yc.mapper.SystemuserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/10/21 17:28
 */
@Service
public class SystemUserService extends ServiceImpl<SystemuserMapper, Systemuser> {

    @Autowired
    private LogService logService;
    @Resource
    private RoleService roleService;
    @Resource
    private PermissionService permissionService;

    /**
     * 设置权限
     *
     * @param userId
     * @return
     */
    public List<Permission> getPermissions(Integer userId) {
        Systemuser byId = getById(userId);
        List<Permission> permissions = new ArrayList<>();
        List<Long> role = byId.getRole();
        if (role != null) {
            for (Object roleId : role) {
                Role realRole = roleService.getById((int) roleId);
                if (CollUtil.isNotEmpty(realRole.getPermission())) {
                    for (Object permissionId : realRole.getPermission()) {
                        Permission permission = permissionService.getById((int) permissionId);
                        if (permission != null && permissions.stream().noneMatch(p -> p.getPath().equals(permission.getPath()))) {
                            permissions.add(permission);
                        }
                    }
                }
            }
            byId.setPermission(permissions);
        }
        return permissions;
    }

}
