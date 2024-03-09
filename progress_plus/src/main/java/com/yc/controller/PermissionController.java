package com.yc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.Governuser;
import com.yc.entity.Permission;
import com.yc.entity.Role;
import com.yc.entity.Systemuser;
import com.yc.service.PermissionService;
import com.yc.service.SystemUserService;
import com.yc.service.UserService;
import com.yc.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Api(tags = "角色控制器")
@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    @Resource
    private PermissionService permissionService;
    @Autowired
    private UserService userService;
    @Resource
    private SystemUserService systemUserServices;

    @PostMapping("/save")
    public Result<?> save(@RequestBody Permission permission) {
        return Result.success(permissionService.save(permission));
    }

    @PostMapping("/update")
    public Result<?> update(@RequestBody Permission permission) {
        return Result.success(permissionService.updateById(permission));
    }

    @GetMapping("/del/{id}")
    public Result<?> delete(@PathVariable Long id) {
        Permission permission = permissionService.getById(id);
        permissionService.delete(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Permission> findById(@PathVariable Long id) {
        return Result.success(permissionService.getById(id));
    }


    @GetMapping
    public Result<List<Permission>> findAll() {
        return Result.success(permissionService.list());
    }

    @GetMapping("/page")
    public Result<IPage<Permission>> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                              @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                              @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return Result.success(permissionService.page(new Page<>(pageNum, pageSize), Wrappers.<Permission>lambdaQuery().like(Permission::getName, name)));
    }

    @PostMapping("/getByRoles")
    public Result<List<Permission>> getByRoles(@RequestBody List<Role> roles) {
        return Result.success(permissionService.getByRoles(roles));
    }

    @GetMapping("/getUserRoles")
    public Result<List<Permission>> getUserRoles(HttpSession session) {
        if (session.getAttribute("governuser")!=null||session.getAttribute("systemuser")!=null){
            boolean isAdmin = (boolean)session.getAttribute("isAdmin");
            if (isAdmin){
                Map<String,Object> user =(Map<String,Object>) session.getAttribute("governuser");
                List<Permission> permissions = userService.getPermissions(Integer.valueOf(String.valueOf(user.get("id"))));
                return Result.success(permissions);
            }else {
                Map<String,Object> systemuser = (Map<String, Object>) session.getAttribute("systemuser");
                List<Permission> permissions = systemUserServices.getPermissions(Integer.valueOf(String.valueOf(systemuser.get("id"))));
                return Result.success(permissions);
            }
        }else{
            return Result.error("请先登录后,在访问!");
        }
    }

}
