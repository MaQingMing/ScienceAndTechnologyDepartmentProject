package com.yc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 查询所有的角色
     * @return
     */
    @Select("select name from t_role")
    Set<String> selectAllRole();

    /**
     * 查询除普通用户的角色
     * @return
     */
    @Select("select id,name from t_role where name != '普通用户'")
    List<Role> selectRole();
}
