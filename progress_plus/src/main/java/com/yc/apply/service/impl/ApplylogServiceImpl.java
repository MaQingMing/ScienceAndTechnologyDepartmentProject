package com.yc.apply.service.impl;

import com.yc.apply.entity.Applylog;
import com.yc.apply.mapper.ApplylogMapper;
import com.yc.apply.service.ApplylogService;
import com.yc.vo.apply.ApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 申请审核记录表;(applylog)表服务实现类
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class ApplylogServiceImpl implements ApplylogService {

    @Autowired
    private ApplylogMapper applylogMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Applylog queryById(Integer id) {
        return applylogMapper.selectById(id);
    }


    /**
     * 新增数据
     *
     * @param applylog 实例对象
     * @return 实例对象
     */
    @Override
    public Applylog insert(Applylog applylog) {
        applylogMapper.insert(applylog);
        return applylog;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        int total = applylogMapper.deleteById(id);
        return total > 0;
    }

    @Override
    public int addApplylog(ApplyVo applyVo) {

        return 0;
    }

    /**
     * 删除申请细表记录
     * @param tableName 表名
     * @param gaid 申请大表id
     * @return
     */
    @Override
    public int deleteApply(String tableName, Integer gaid){
        return applylogMapper.deleteApply(tableName, gaid);
    }
}