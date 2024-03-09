package com.yc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.entity.Systemuser;
import com.yc.vo.AllTypeMoney;
import com.yc.vo.SimpleGovernuser;
import com.yc.vo.SystemUserVo;
import com.yc.vo.SystemVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/10/20 21:20
 */
@Mapper
public interface SystemuserMapper extends BaseMapper<Systemuser> {


    /**
     * 查询姓名通过id
     * @param id
     * @return
     */
    @Select("SELECT nickname,username FROM systemuser WHERE id = #{id}")
    Map<String,Object> selectnickname(@Param("id") Integer id);

    @Select(" SELECT id,username,role,nickname,tid,(SELECT GROUP_CONCAT( rolecode SEPARATOR ',' ) AS rolecode FROM t_role WHERE instr( REPLACE ( REPLACE (REPLACE ( role, '[', '' ), ']', '' ), ' ', '' ), id )) as rolecode FROM systemuser WHERE username = #{username} ")
    Systemuser selectUserInfoAndRole(@Param("username") String username);

    /**
     * 根据用户密码查询用户信息
     * @param username
     * @param password
     */
    @Select(" select s.id,s.username,s.nickname,s.tid,s.role,l.status,l.tname FROM systemuser s left join labeldept l  on s.tid = l.tid WHERE (s.username = #{username} AND s.password = #{password}) ")
    Map<String, Object>  login(@Param("username") String username,@Param("password") String password);

    /**
     * 查自科还是社科
     *
     * @param tid
     * @return
     */
    @Select("select status,tname from labeldept where tid =#{tid}")
    Map<String, Object> queryStatus(@Param("tid") int tid);

    /**
     * 根据工号或姓名分页查询
     * @param username
     * @param nickname
     * @param start
     * @param pagesize
     * @return
     */
    List<SystemVo> findByName(@Param("username") String username, @Param("nickname") String nickname, int start, int pagesize);

    /**
     * 分页查询全部
     * @param start
     * @param pagesize
     * @return
     */
    List<SystemVo> findAllSystem(int start, int pagesize);

    /**
     * 根据账号查id可能多个
     * @param usernames
     * @return
     */
    String queryIdByusername(@Param("usernames") List<String> usernames);

    /**
     * 查询用户的id
     * @param username
     * @return
     */
    @Select("select id from systemuser where username = #{username}")
    int queryIdByUsername(@Param("username") String username);

    /**
     * 修改用户 科技分
     * @param allCashMonies
     * @return
     */
    int updateUserScore(@Param("list") List<AllTypeMoney> allCashMonies);

    /**
     * @Description 将所有申请者的分数全部置为0，感觉基本不会用到
     * @Return
     * @Author dm
     * @Date Created in 2023/11/24
     **/
    int updateAllUserScore();

    /**
     * 根据姓名或者工号查询用户
     *
     * @param username 用户名
     * @param nickname 昵称
     * @return 查询的用户结果
     */
    List<Systemuser> queryUserBySearch(@Param("username") String username, @Param("nickname") String nickname);

    /**
     * 查询所有用户名 id  部门名称
     * @return
     */
    @Select("SELECT s.nickname username,s.id id,l.tname dept,l.`status` FROM systemuser s, labeldept l WHERE s.tid = l.tid")
    List<SimpleGovernuser> queryUsers();

    /**
     * 添加普通用户
     * @param username
     * @param password
     * @param nickname
     * @param phone
     */
    void addSystem(String username, String password, String nickname, String phone, Integer tid, String job, Double baseScore, String createBy, String updateBy);

    /**
     * 修改部门
     * @param username
     * @param tid
     * @param updateBy
     */
    void updateDept(String username, Integer tid, Integer updateBy);

    /**
     * 编辑信息
     * @param username
     * @param nickname
     * @param phone
     * @param baseScore
     * @param job
     * @param updateBy
     */
    void updateInfo(String username, String nickname, String phone, Double baseScore, String job, String updateBy);

    /**
     * 批量增加
     * @param allSystemuser
     */
    void insertAll(@Param("list") List<SystemUserVo> allSystemuser);

    /**
     * 查询所有用户的工号
     * @return
     */
    @Select(" select username from systemuser ")
    List<String> findAllUsername();


    /**
     * 修改用户分数信息
     * @param scoreBalance
     * @param nonScoreBalance
     * @param loanScore
     * @param sysId
     * @return
     */
    @Update(" update systemuser set score_balance=#{scoreBalance},non_score_balance=#{nonScoreBalance},loan_score=#{loanScore},update_by=1,update_time=now() where id=#{sysId}  ")
    Integer updateSystemUserScoreInfo(@Param("scoreBalance") Double scoreBalance, @Param("nonScoreBalance") Double nonScoreBalance, @Param("loanScore") Double loanScore, @Param("sysId") Integer sysId);

    /**
    *@Description 根据主键查询具体用户
    *@Return
    *@Author dm
    *@Date Created in 2024/1/3
    **/

}
