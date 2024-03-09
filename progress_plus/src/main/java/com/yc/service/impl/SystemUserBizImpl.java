package com.yc.service.impl;

import com.alibaba.excel.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.yc.common.utils.AlyExcelUtil;
import com.yc.common.utils.Encrypt;
import com.yc.entity.Systemuser;
import com.yc.entity.model.PageBean;
import com.yc.mapper.SystemuserMapper;
import com.yc.service.SystemUserBiz;
import com.yc.vo.SystemUserVo;
import com.yc.vo.SystemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemUserBizImpl implements SystemUserBiz {
    @Autowired
    SystemuserMapper systemuserMapper;

    @Override
    public PageBean<SystemVo> findAllSystem(int pageno, int pagesize) {
        int start = (pageno - 1) * pagesize; // 分页的起始位置
        List<SystemVo> list = this.systemuserMapper.findAllSystem(start, pagesize);
        // 包装分页bean
        PageBean systemPage = new PageBean();
        systemPage.setPagesize(pagesize);
        systemPage.setPageno(pageno);
        systemPage.setDataset(list);
        QueryWrapper wrapper = new QueryWrapper();
        Integer total = Math.toIntExact(this.systemuserMapper.selectCount(wrapper));
        systemPage.setTotal(total);
        // 计算总页数 = 总记录数/pagesize
        int totalpages = total%pagesize==0 ? total/pagesize : total/pagesize+1;
        systemPage.setTotalpages(totalpages);
        // 上一页页号
        if(systemPage.getPageno() <= 1){
            systemPage.setPre(1);
        }else{
            systemPage.setPre(systemPage.getPageno() - 1);
        }
        // 下一页页号
        if(systemPage.getPageno() == totalpages){
            systemPage.setNext(totalpages);
        }else{
            systemPage.setNext(systemPage.getPageno() + 1);
        }
        return systemPage;
    }

    @Override
    public PageBean<SystemVo> findByName(String username, String nickname, int pageno, int pagesize) {
        int start = (pageno - 1) * pagesize; // 分页的起始位置
        List<SystemVo> list = this.systemuserMapper.findByName(username, nickname, start, pagesize);
        // 包装分页bean
        PageBean systemPage = new PageBean();
        systemPage.setPagesize(pagesize);
        systemPage.setPageno(pageno);
        systemPage.setDataset(list);
        QueryWrapper<Systemuser> wrapper = new QueryWrapper<>();
        wrapper.like("username", username).or().like("nickname", nickname);
        Integer total = Math.toIntExact(this.systemuserMapper.selectCount(wrapper));
        systemPage.setTotal(total);
        // 计算总页数 = 总记录数/pagesize
        int totalpages = total%pagesize==0 ? total/pagesize : total/pagesize+1;
        systemPage.setTotalpages(totalpages);
        // 上一页页号
        if(systemPage.getPageno() <= 1){
            systemPage.setPre(1);
        }else{
            systemPage.setPre(systemPage.getPageno() - 1);
        }
        // 下一页页号
        if(systemPage.getPageno() == totalpages){
            systemPage.setNext(totalpages);
        }else{
            systemPage.setNext(systemPage.getPageno() + 1);
        }
        return systemPage;
    }

    @Override
    public void addSystem(Systemuser systemuser) {
        this.systemuserMapper.addSystem(systemuser.getUsername(),Encrypt.fuc(systemuser.getUsername()),systemuser.getNickname(),systemuser.getPhone(),
                systemuser.getTid(), systemuser.getJob(), systemuser.getBaseScore(),systemuser.getCreateBy(),systemuser.getUpdateBy());
    }

    @Override
    public void updatePwd(String password, Integer id) {
        UpdateWrapper<Systemuser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("password", Encrypt.fuc(password));
        this.systemuserMapper.update(null, updateWrapper);
    }

    @Override
    public String findPwdById(Integer id) {
        Systemuser systemuser = this.systemuserMapper.selectById(id);
        return systemuser.getPassword();
    }

    @Override
    public void resetPwd(String username) {
        UpdateWrapper<Systemuser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", username)
                .set("password", Encrypt.fuc(username));
        this.systemuserMapper.update(null, updateWrapper);
    }

    @Override
    public void delete(String id) {
        this.systemuserMapper.deleteById(id);
    }

    @Override
    public void updateAcademy(String id, Integer academy) {
        UpdateWrapper<Systemuser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("academy", academy);
        this.systemuserMapper.update(null, updateWrapper);
    }

    @Override
    public void updateAcademyCare(String id, Integer academyCare) {
        UpdateWrapper<Systemuser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("academy", academyCare);
        this.systemuserMapper.update(null, updateWrapper);
    }

    @Override
    public void updateDept(String username, Integer tid, Integer updateBy) {
        this.systemuserMapper.updateDept(username, tid, updateBy);
    }

    @Override
    public void updateInfo(Systemuser systemuser) {
        this.systemuserMapper.updateInfo(systemuser.getUsername(), systemuser.getNickname(), systemuser.getPhone(),
                systemuser.getBaseScore(), systemuser.getJob(), systemuser.getUpdateBy());
    }

    @Override
    public void insertAll(List<SystemUserVo> allSystemuser) {
        this.systemuserMapper.insertAll(allSystemuser);
    }

    @Override
    public List<String> findAllUsername() {
        return this.systemuserMapper.findAllUsername();
    }

    /**
     * 批量添加扫描文件
     * @param file 扫描得文件
     * @param uid 操作者id
     * @return
     * @throws IOException
     */
    @Override
    public void processFile(MultipartFile file, String uid, Map<String, Object> errorMap) throws IOException {
        InputStream inputStream = file.getInputStream();
        List<String> allUsernames = this.systemuserMapper.findAllUsername();
        List<List<Map<String, Object>>> result = AlyExcelUtil.excelToShopIdList(inputStream);
        int flag = 1; // 记录行数
        List<SystemUserVo> userList = new ArrayList<>();
        for (List<Map<String, Object>> list : result) {
            for (Map<String, Object> map : list) {
                String id = (String) map.get("0");
                String username = (String) map.get("1");
                String nickname = (String) map.get("2");
                String phone = (String) map.get("3");
                Double baseScore = null;
                String bs = (String) map.get("4");
                String tname = (String) map.get("5");
                String job = (String) map.get("6");
                boolean flag1 = false, flag2 = false, flag3 = false, flag4 = false, flag5 = false;
                SystemUserVo user = new SystemUserVo();
                user.setUsername(username);
                user.setNickname(nickname);
                user.setPassword(Encrypt.fuc(username));
                user.setPhone(phone);
                user.setTname(tname);
                user.setContext(job);
                user.setBaseScore(baseScore);
                user.setCreateBy(uid);
                user.setUpdateBy(uid);
                if (StringUtils.isBlank(username) || StringUtils.isBlank(nickname) || StringUtils.isBlank(id) || StringUtils.isBlank(bs)) {
                    errorMap.put(String.valueOf(flag++), "序号" + id + "，序号或工号或名字或底分为空");
                    flag1 = true;
                }
                if (allUsernames.contains(username)) {
                    errorMap.put(String.valueOf(flag++), "序号" + id + "，该工号已存在");
                    flag2 = true;
                }
                if (!username.matches("^[0-9]{10}$")) {
                    errorMap.put(String.valueOf(flag++), "序号" + id + "，用户名不是10位数字");
                    flag3 = true;
                }
                if (phone != null && !phone.isEmpty() && !phone.matches("^[1][3,4,5,6,7,8,9][0-9]{9}$")) {
                    errorMap.put(String.valueOf(flag++), "序号" + id + "，手机号不是11位数字");
                    flag4 = true;
                }
                if (map.get("4") != null) {
                    try {
                        baseScore = Double.valueOf(map.get("4").toString());
                        if (baseScore <= 0) {
                            errorMap.put(String.valueOf(flag++), "序号" + id + "，底分需大于0");
                            flag5 = true;
                        }
                    } catch (NumberFormatException e) {
                        errorMap.put(String.valueOf(flag++), "序号" + id + "，底分格式错误");
                        flag5 = true;
                    }
                }
                if(flag1 || flag2 || flag3 || flag4 || flag5){
                    continue;
                }
                userList.add(user);
            }
        }
        if(userList.size() > 0){
            insertAll(userList); // 调用insertAll函数添加数据
        }
    }
}
