package com.yc.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.apply.entity.AccountScore;
import com.yc.apply.mapper.AccountScoreMapper;
import com.yc.apply.service.AccountScoreService;
import com.yc.entity.Systemuser;
import com.yc.entity.model.PageBean;
import com.yc.vo.AccountScoreVo;
import com.yc.vo.AccountSystemVo;
import com.yc.vo.SystemVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 账户分数明细表;(account_score)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
@Service
public class AccountScoreServiceImpl implements AccountScoreService {
    @Autowired
    private AccountScoreMapper accountScoreMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param asid 主键
     * @return 实例对象
     */
    @Override
    public AccountScore queryById(Integer asid){
//        return accountScoreMapper.selectById(asid);
        return null;
    }
    
    /**
     * 分页查询
     *
     * @param accountScore 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @Override
    public Page<AccountScore> paginQuery(AccountScore accountScore, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<AccountScore> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(accountScore.getRemark())){
            queryWrapper.eq(AccountScore::getRemark, accountScore.getRemark());
        }
        //2. 执行分页查询
        Page<AccountScore> pagin = new Page<>(current , size , true);
        IPage<AccountScore> selectResult = accountScoreMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param accountScore 实例对象
     * @return 实例对象
     */
    @Override
    public AccountScore insert(AccountScore accountScore){
        accountScoreMapper.insert(accountScore);
        return accountScore;
    }
    
    /** 
     * 更新数据
     *
     * @param accountScore 实例对象
     * @return 实例对象
     */
    @Override
    public AccountScore update(AccountScore accountScore){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<AccountScore> chainWrapper = new LambdaUpdateChainWrapper<AccountScore>(accountScoreMapper);
        if(StrUtil.isNotBlank(accountScore.getRemark())){
            chainWrapper.eq(AccountScore::getRemark, accountScore.getRemark());
        }
        //2. 设置主键，并更新
        chainWrapper.set(AccountScore::getAsid, accountScore.getAsid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(accountScore.getAsid());
        }else{
            return accountScore;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param asid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer asid){
        int total = accountScoreMapper.deleteById(asid);
        return total > 0;
    }

     @Override
     public AccountSystemVo findBySid(Integer sysid) {
         return accountScoreMapper.findBySid(sysid);
     }

    @Override
    public PageBean<AccountScoreVo> findAccount(Integer sysid, Integer month, int pageno, int pagesize) {
        int start = (pageno - 1) * pagesize; // 分页的起始位置
        List<AccountScoreVo> list = this.accountScoreMapper.findAccount(sysid, month, start, pagesize);
        // 包装分页bean
        PageBean systemPage = new PageBean();
        systemPage.setPagesize(pagesize);
        systemPage.setPageno(pageno);
        systemPage.setDataset(list);
        return systemPage;
    }

    @Override
    public PageBean<AccountScoreVo> findCurrent(Integer sysid, int pageno, int pagesize) {
        int start = (pageno - 1) * pagesize; // 分页的起始位置
        List<AccountScoreVo> list = this.accountScoreMapper.findCurrent(sysid, start, pagesize);
        // 包装分页bean
        PageBean systemPage = new PageBean();
        systemPage.setPagesize(pagesize);
        systemPage.setPageno(pageno);
        systemPage.setDataset(list);
//        QueryWrapper<Systemuser> wrapper = new QueryWrapper<>();
//        wrapper.like("username", username).or().like("nickname", nickname);
//        Integer total = Math.toIntExact(this.systemuserMapper.selectCount(wrapper));
//        systemPage.setTotal(total);
//        // 计算总页数 = 总记录数/pagesize
//        int totalpages = total%pagesize==0 ? total/pagesize : total/pagesize+1;
//        systemPage.setTotalpages(totalpages);
//        // 上一页页号
//        if(systemPage.getPageno() <= 1){
//            systemPage.setPre(1);
//        }else{
//            systemPage.setPre(systemPage.getPageno() - 1);
//        }
//        // 下一页页号
//        if(systemPage.getPageno() == totalpages){
//            systemPage.setNext(totalpages);
//        }else{
//            systemPage.setNext(systemPage.getPageno() + 1);
//        }
        return systemPage;
    }

    @Override
    public List<String> findJob(Integer sysid) {
        return accountScoreMapper.findJob(sysid);
    }

    @Override
    public Integer addAccountScoreList(List<AccountScoreVo> details) {
        return accountScoreMapper.addAccountScoreList(details);
    }
}