package com.yc.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.entity.Governuser;
import com.yc.vo.GovernVo;
import com.yc.vo.SimpleGovernuser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 管理员表;(governuser)表数据库访问层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface GovernuserMapper extends BaseMapper<Governuser> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<Governuser> selectByPage(IPage<Governuser> page, @Param(Constants.WRAPPER) Wrapper<Governuser> wrapper);

    /**
     * 查询用户的权限
     * @param username
     * @return
     */
    @Select(" SELECT id,username,nickname,tid,(SELECT GROUP_CONCAT( rolecode SEPARATOR ',' ) AS rolecode FROM t_role" +
            " WHERE instr( REPLACE ( REPLACE (REPLACE ( role, '[', '' ), ']', '' ), ' ', '' ), id )) as role " +
            "FROM governuser WHERE username = #{username} ")
    Governuser selectUserInfoAndRole(@Param("username") String username);

    /**
     * 根据用户密码查询用户信息
     * @param username
     * @param password
     */
    @Select(" select s.id,s.username,s.nickname,s.tid,s.role,l.status,l.tname FROM governuser s left join labeldept l  on s.tid = l.tid WHERE (s.username = #{username} AND s.password = #{password}) ")
    Map<String, Object>  login(@Param("username") String username,@Param("password") String password);

    /**
     * 查自科还是社科
     *
     * @param tid
     * @return
     */
    @Select("select status,tname from labeldept where tid =#{tid}")
    Map<String,Object> queryStatus(@Param("tid") int tid);

     /**
     * 查找全部管理员信息
     * @return
     */
    List<GovernVo> findAllGovern(int start, int pagesize);

    /**
     * 根据username,nickname,rolename分页查询governuser信息
     * @param username
     * @param nickname
     * @param role
     * @param start
     * @param pagesize
     * @return
     */
    List<GovernVo> find(@Param("username") String username, @Param("nickname") String nickname, @Param("role") String role, int start, int pagesize);

    /**
     * 根据username或nickname分页查询
     * @param username
     * @param nickname
     * @param start
     * @param pagesize
     * @return
     */
    List<GovernVo> findByName(@Param("username") String username, @Param("nickname") String nickname, int start, int pagesize);

    /**
     * 添加管理员
     * @param username
     * @param password
     * @param nickname
     * @param role
     * @param phone
     * @param tid
     * @param createBy
     * @param updateBy
     */
    void addGovern(String username, String password, String nickname, String role, String phone, Integer tid, String createBy, String updateBy);

    /**
     * 编辑信息
     * @param username
     * @param nickname
     * @param phone
     * @param updateBy
     */
    void updateInfo(String username, String nickname, String phone, Integer updateBy);

    /**
     * 查询所有的用户信息
     * @return
     */
    @Select("select g.nickname username,g.id id,l.tname dept from governuser g,labeldept l where g.tid = l.tid")
    List<SimpleGovernuser> queryUsers();

    /**
     * 修改部门
     * @param username
     * @param tid
     * @param updateBy
     */
    void updateDept(String username, Integer tid, Integer updateBy);

    /**
     * 修改角色
     * @param username
     * @param role
     * @param updateBy
     */
    void updateRole(String username, String role, String updateBy);
}