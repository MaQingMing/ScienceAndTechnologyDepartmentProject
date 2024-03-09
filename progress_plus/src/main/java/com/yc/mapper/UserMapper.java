package com.yc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper extends BaseMapper<User> {


    /**
     * 修改系统用户信息
     *
     * @param username
     * @param nickname
     * @param phone
     */
    @Update("UPDATE systemuser set nickname = #{nickname} , phone=#{phone} where username=#{username} ")
    void updateInfo(String username, String nickname, String phone);

    /**
     * 批量插入
     *
     * @param list
     */
    void batchInsert(List<User> list);


    /**
     * 根据辅导员工号查询id
     *
     * @param username
     * @return
     */
    @Select("select id from systemuser where username=#{username}")
    Long selectIdByUsername(String username);

    /**
     * 查询所有系统用户信息（username）
     *
     * @param username
     * @return
     */
    @Select("select * from systemuser where username=#{username}")
    User selectAllByUsername(String username);

    /**
     * 更改密码
     *
     * @param password
     * @param username
     */
    @Select("update systemuser set  password = #{password} where username=#{username}")
    void updatePasswordByUsername(String password, String username);


    /**
     * 查该用户所在的学院名
     *
     * @return
     */
    @Select("select tname from labeldept where tid=#{tid}")
    List<String> selectDeptNameByUdid(Long tid);

    /**
     * 根据用户名查询用户信息和权限
     * @param username
     * @return
     */
    @Select(" select id,username,nickname,role,tid,(select concat(rolecode,',') from t_role where id in (REPLACE(REPLACE(REPLACE('[1,2]', '[', ''), ']', ''), ' ', ''))) as rolecode from systemuser where username = #{username} ")
    User selectUserInfoAndRole(@Param("username") String username);

}
