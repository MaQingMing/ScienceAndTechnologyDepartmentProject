package com.yc.service;

import com.yc.entity.model.PageBean;
import com.yc.vo.GovernVo;

public interface GovernUserBiz {
    /**
     * 分页查询所有的governuser信息
     * @param start
     * @param pagesize
     * @return
     */
    PageBean<GovernVo> findAllGovern(int start, int pagesize);

    /**
     *根据工号或姓名和角色进行筛选
     * @param username
     * @param nickname
     * @param role
     * @param start
     * @param pagesize
     * @return
     */
    PageBean<GovernVo> find(String username, String nickname, String role, int start, int pagesize);

    /**
     *根据工号username或姓名nickname进行筛选
     * @param username
     * @param nickname
     * @param start
     * @param pagesize
     * @return
     */
    PageBean<GovernVo> findByName(String username, String nickname, int start, int pagesize);

    /**
     * 根据角色role筛选
     * @param role
     * @return
     */
    PageBean<GovernVo> findByRole(String role, int start, int pagesize);

    /**
     * 根据id修改密码
     * @param password
     * @param id
     */
    void updatePwd(String password, Integer id);

    /**
     * 根据id查找密码验证密码是否输入正确
     * @param id
     * @return
     */
    String findPwdById(Integer id);

    /**
     * 重置密码
     * 将密码置为工号
     * @param username
     */
    void resetPwd(String username);

    /**
     * 删除systemuser信息
     * @param id
     */
    void delete(String id);

    /**
     * 编辑信息
     * @param username
     * @param nickname
     * @param phone
     * @param updateBy
     */
    void updateInfo(String username, String nickname, String phone, Integer updateBy);

    /**
     * 添加管理员信息
     * @param governuser
     */
    void addGovern(GovernVo governuser);

    /**
     * 更新部门
     * @param username
     * @param tid
     * @param updateBy
     */
    void updateDept(String username, Integer tid, Integer updateBy);

    /**
     * 更新角色
     * @param governuser
     */
    void updateRole(GovernVo governuser);
}
