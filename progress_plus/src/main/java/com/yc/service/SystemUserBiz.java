package com.yc.service;

import com.yc.entity.Systemuser;
import com.yc.entity.model.PageBean;
import com.yc.vo.SystemUserVo;
import com.yc.vo.SystemVo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SystemUserBiz {
    /**
     * 查询所有的systemuser信息
     * @param pageno
     * @param pagesize
     * @return
     */
    PageBean<SystemVo> findAllSystem(int pageno, int pagesize);

    /**
     * 根据username或者nickname查询systemuser信息
     * @param username
     * @param nickname
     * @param start
     * @param pagesize
     * @return
     */
    PageBean<SystemVo> findByName(String username, String nickname, int start, int pagesize);

    /**
     * 添加普通用户信息
     * @param systemuser
     */
    void addSystem(Systemuser systemuser);

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
     * 是否属于研究院
     * @param id
     * @param academy
     */
    void updateAcademy(String id, Integer academy);

    /**
     * 是否承担研究院管理工作
     * @param id
     * @param academyCare
     */
    void updateAcademyCare(String id, Integer academyCare);

    /**
     * 更新部门
     * @param username
     * @param tid
     * @param updateBy
     */
    void updateDept(String username, Integer tid, Integer updateBy);

    /**
     * 编辑信息
     * @param systemuser
     */
    void updateInfo(Systemuser systemuser);

    /**
     * 批量增加
     * @param allSystemuser
     */
    void insertAll(List<SystemUserVo> allSystemuser);
//    void insertAll(String username, String nickname, String phone, String tname, String context, Double baseScore, String createBy, String updateBy);

    /**
     * 查询表中所有的username
     * @return
     */
    List<String> findAllUsername();

    /**
     * 批量添加扫描文件
     * @param file 扫描得文件
     * @param uid 操作者id
     * @return
     * @throws IOException
     */
    void processFile(MultipartFile file, String uid,Map<String, Object> errorMap) throws IOException;
}
