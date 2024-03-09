package com.yc.apply.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.apply.entity.AccountScore;
import com.yc.vo.AccountScoreVo;
import com.yc.vo.AccountSystemVo;
import com.yc.vo.AllTypeMoney;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 账户分数明细表;(account_score)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
@Mapper
public interface AccountScoreMapper  extends BaseMapper<AccountScore>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<AccountScore> selectByPage(IPage<AccountScore> page , @Param(Constants.WRAPPER) Wrapper<AccountScore> wrapper);

    /**
    *@Description 审核记录
    *@Return
    *@Author dm
    *@Date Created in 2023/11/17
    **/
    int insertBatch(@Param("list")List<AccountScore> list);

    /**
    *@Description 驳回审核记录
    *@Return
    *@Author dm
    *@Date Created in 2023/11/23
    **/
    int rejectBatch(@Param("ids")String ids);

    /**
    *@Description 查询所有申请者的总分数
    *@Return
    *@Author dm
    *@Date Created in 2023/12/5
    **/
    List<AllTypeMoney> queryAllScores();

    /**
     * 根据用户id查询总科技分，可换酬金的科技分，普通科技分
     * @param sysid
     * @return
     */
    AccountSystemVo findBySid(Integer sysid);

    /**
     * 近 x 月科技分账户详情
     * @param sysid 用户Id
     * @param month 近几月
     * @return
     */
    List<AccountScoreVo> findAccount(Integer sysid, Integer month, int start, int pagesize);

    /**
     * 查询当前月科技分账户详情
     * @param sysid
     * @return
     */
    List<AccountScoreVo> findCurrent(Integer sysid, int start, int pagesize);

    /**
     * 查找用户角色
     * @param sysid 用户Id
     * @return
     */
    List<String> findJob(Integer sysid);

    /**
     * 批量添加 账户扣分记录
     * @param details
     * @return
     */
    Integer addAccountScoreList(List<AccountScoreVo> details);

    /**
    *@Description 批量修改账户明细表中认可的记录
    *@Return
    *@Author dm
    *@Date Created in 2024/1/4
    **/
    int updateBatch(@Param("gaids")String gaids);
}