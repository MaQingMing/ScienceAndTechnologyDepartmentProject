package com.yc.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yc.entity.TJob;
import com.yc.mapper.TJobMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class TJobBizImpl extends ServiceImpl<TJobMapper, TJob> {
    @Resource
    TJobMapper tJobMapper;

    public Set<String> selectAllTJob(){
        return tJobMapper.selectAllTJob();
    }
}
