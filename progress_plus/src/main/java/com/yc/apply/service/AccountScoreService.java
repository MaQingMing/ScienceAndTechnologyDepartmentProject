package com.yc.apply.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.AccountScore;
import com.yc.apply.entity.ExamineDetail;
import com.yc.entity.model.PageBean;
import com.yc.vo.AccountScoreVo;
import com.yc.vo.AccountSystemVo;

import java.util.List;

/**
 * 账户分数明细表;(account_score)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
public interface AccountScoreService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param asid 主键
     * @return 实例对象
     */
    AccountScore queryById(Integer asid);
    
    /**
     * 分页查询
     *
     * @param accountScore 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<AccountScore> paginQuery(AccountScore accountScore, long current, long size);
    /** 
     * 新增数据
     *
     * @param accountScore 实例对象
     * @return 实例对象
     */
    AccountScore insert(AccountScore accountScore);
    /** 
     * 更新数据
     *
     * @param accountScore 实例对象
     * @return 实例对象
     */
    AccountScore update(AccountScore accountScore);
    /** 
     * 通过主键删除数据
     *
     * @param asid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer asid);

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
    PageBean<AccountScoreVo> findAccount(Integer sysid, Integer month, int start, int pagesize);

    /**
     * 查询当前月科技分账户详情
     * @param sysid
     * @return
     */
    PageBean<AccountScoreVo> findCurrent(Integer sysid, int start, int pagesize);

    /**
     * 查找用户角色
     * @param sysid 用户Id
     * @return
     */
    List<String> findJob(Integer sysid);

    /**
     * 批量添加 账户扣分记录
     * @param details
     */
    public Integer addAccountScoreList(List<AccountScoreVo> details);
}