package com.yc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.yc.common.utils.Encrypt;
import com.yc.entity.Governuser;
import com.yc.entity.User;
import com.yc.entity.model.PageBean;
import com.yc.mapper.GovernuserMapper;
import com.yc.service.GovernUserBiz;
import com.yc.vo.GovernVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GovernUserBizImpl implements GovernUserBiz {
    @Autowired
    GovernuserMapper governuserMapper;

    @Override
    public PageBean<GovernVo> findAllGovern(int pageno, int pagesize) {
        int start = (pageno - 1) * pagesize; // 分页的起始位置
        List<GovernVo> list = this.governuserMapper.findAllGovern(start, pagesize);
        // 包装分页bean
        PageBean governPage = new PageBean();
        governPage.setPagesize(pagesize);
        governPage.setPageno(pageno);
        governPage.setDataset(list);
        QueryWrapper wrapper = new QueryWrapper();
        Integer total = Math.toIntExact(this.governuserMapper.selectCount(wrapper));
        governPage.setTotal(total);
        // 计算总页数 = 总记录数/pagesize
        int totalpages = total%pagesize==0 ? total/pagesize : total/pagesize+1;
        governPage.setTotalpages(totalpages);
        // 上一页页号
        if(governPage.getPageno() <= 1){
            governPage.setPre(1);
        }else{
            governPage.setPre(governPage.getPageno() - 1);
        }
        // 下一页页号
        if(governPage.getPageno() == totalpages){
            governPage.setNext(totalpages);
        }else{
            governPage.setNext(governPage.getPageno() + 1);
        }
        return governPage;
    }

    @Override
    public PageBean<GovernVo> find(String username, String nickname, String role, int pageno, int pagesize) {
        int start = (pageno - 1) * pagesize; // 分页的起始位置
        List<GovernVo> list = this.governuserMapper.find(username, nickname, role,start, pagesize);
        // 包装分页bean
        PageBean governPage = new PageBean();
        governPage.setPagesize(pagesize);
        governPage.setPageno(pageno);
        governPage.setDataset(list);
        QueryWrapper<Governuser> wrapper = new QueryWrapper<>();
        wrapper.and(w -> w.like("username", username).or().like("nickname", nickname)).eq("role", "["+role+"]");
        Integer total = Math.toIntExact(this.governuserMapper.selectCount(wrapper));
        governPage.setTotal(total);
        // 计算总页数 = 总记录数/pagesize
        int totalpages = total%pagesize==0 ? total/pagesize : total/pagesize+1;
        governPage.setTotalpages(totalpages);
        // 上一页页号
        if(governPage.getPageno() <= 1){
            governPage.setPre(1);
        }else{
            governPage.setPre(governPage.getPageno() - 1);
        }
        // 下一页页号
        if(governPage.getPageno() == totalpages){
            governPage.setNext(totalpages);
        }else{
            governPage.setNext(governPage.getPageno() + 1);
        }
        return governPage;
    }

    @Override
    public PageBean<GovernVo> findByName(String username, String nickname, int pageno, int pagesize) {
        int start = (pageno - 1) * pagesize; // 分页的起始位置
        List<GovernVo> list = this.governuserMapper.findByName(username, nickname,start, pagesize);
        // 包装分页bean
        PageBean governPage = new PageBean();
        governPage.setPagesize(pagesize);
        governPage.setPageno(pageno);
        governPage.setDataset(list);
        QueryWrapper<Governuser> wrapper = new QueryWrapper<>();
        wrapper.and(w -> w.like("username", username).or().like("nickname", nickname));
        Integer total = Math.toIntExact(this.governuserMapper.selectCount(wrapper));
        governPage.setTotal(total);
        // 计算总页数 = 总记录数/pagesize
        int totalpages = total%pagesize==0 ? total/pagesize : total/pagesize+1;
        governPage.setTotalpages(totalpages);
        // 上一页页号
        if(governPage.getPageno() <= 1){
            governPage.setPre(1);
        }else{
            governPage.setPre(governPage.getPageno() - 1);
        }
        // 下一页页号
        if(governPage.getPageno() == totalpages){
            governPage.setNext(totalpages);
        }else{
            governPage.setNext(governPage.getPageno() + 1);
        }
        return governPage;
    }

    @Override
    public PageBean<GovernVo> findByRole(String role,int pageno, int pagesize) {
        return this.find("","",role,pageno,pagesize);
    }

    @Override
    public void updatePwd(String password, Integer id) {
        UpdateWrapper<Governuser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id)
                .set("password", Encrypt.fuc(password));
        this.governuserMapper.update(null, updateWrapper);
    }

    @Override
    public String findPwdById(Integer id) {
        Governuser governuser = this.governuserMapper.selectById(id);
        return governuser.getPassword();
    }

    @Override
    public void resetPwd(String username) {
        UpdateWrapper<Governuser> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("username", username)
                .set("password", Encrypt.fuc(username));
        this.governuserMapper.update(null, updateWrapper);
    }

    @Override
    public void delete(String id) {
        this.governuserMapper.deleteById(id);
    }

    @Override
    public void updateInfo(String username, String nickname, String phone, Integer updateBy) {
        this.governuserMapper.updateInfo(username, nickname, phone, updateBy);
    }

    @Override
    public void addGovern(GovernVo governuser) {
        this.governuserMapper.addGovern(governuser.getUsername(),Encrypt.fuc(governuser.getUsername()), governuser.getNickname(),
                governuser.getRole(), governuser.getPhone(), governuser.getTid(), governuser.getCreateBy(), governuser.getUpdateBy());
//        this.governuserMapper.addGovern(username, Encrypt.fuc(username) ,nickname, phone, tid, createBy, updateBy);
    }

    @Override
    public void updateDept(String username, Integer tid, Integer updateBy) {
        this.governuserMapper.updateDept(username, tid, updateBy);
    }

    @Override
    public void updateRole(GovernVo governUser) {
        this.governuserMapper.updateRole(governUser.getUsername(), governUser.getRole(), governUser.getUpdateBy());
    }


}
