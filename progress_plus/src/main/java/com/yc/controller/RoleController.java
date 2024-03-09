package com.yc.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.Role;
import com.yc.service.RoleService;
import com.yc.vo.Result;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "权限控制器")
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Resource
    private RoleService roleService;


    @PostMapping("/save")
    public Result<?> save(@RequestBody Role role) {
        return Result.success(roleService.save(role));
    }

    @PostMapping("/update")
    public Result<?> update(@RequestBody Role role) {
        return Result.success(roleService.updateById(role));
    }

    @GetMapping("/del/{id}")
    public Result<?> delete(@PathVariable Long id) {
        Role role = roleService.getById(id);

        roleService.removeById(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Role> findById(@PathVariable Long id) {
        return Result.success(roleService.getById(id));
    }

    @GetMapping("/noOrdinary")
    public Result<List<Role>> findAll() {
        return Result.success(roleService.selectRole());
    }

    @GetMapping("/allrole")
    public Result<List<Role>> AllRole() {
        return Result.success(roleService.list());
    }


    @GetMapping("/page")
    public Result<IPage<Role>> findPage(@RequestParam(required = false, defaultValue = "") String name,
                                        @RequestParam(required = false, defaultValue = "1") Integer pageNum,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        //.ne(Role::getName,"普通用户")
        LambdaQueryWrapper wrapper = Wrappers.<Role>lambdaQuery().like(Role::getName, name);
        return Result.success(roleService.page(new Page<>(pageNum, pageSize), wrapper));
    }


}
