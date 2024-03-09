package com.yc.apply.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.yc.apply.entity.ScoreApplyInfo;
import com.yc.apply.mapper.ScoreApplyInfoMapper;
import com.yc.apply.service.ScoreApplyInfoService;
import com.yc.vo.apply.ApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 申请分数详情;(score_apply_info)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-12-2
 */
@Service
public class ScoreApplyInfoServiceImpl implements ScoreApplyInfoService {

    @Autowired
    private ScoreApplyInfoMapper scoreApplyInfoMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param saiid 主键
     * @return 实例对象
     */
    @Override
    public ScoreApplyInfo queryById(Integer saiid){
        return scoreApplyInfoMapper.selectById(saiid);
    }


    /**
     * 新增数据
     *
     * @param scoreApplyInfo 实例对象
     * @return 实例对象
     */
    @Override
    public ScoreApplyInfo insert(ScoreApplyInfo scoreApplyInfo){
        scoreApplyInfoMapper.insert(scoreApplyInfo);
        return scoreApplyInfo;
    }

    /**
     * 更新数据
     *
     * @param scoreApplyInfo 实例对象
     * @return 实例对象
     */
    @Override
    public ScoreApplyInfo update(ScoreApplyInfo scoreApplyInfo){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<ScoreApplyInfo> chainWrapper = new LambdaUpdateChainWrapper<ScoreApplyInfo>(scoreApplyInfoMapper);
        if(StrUtil.isNotBlank(scoreApplyInfo.getRes1())){
            chainWrapper.eq(ScoreApplyInfo::getRes1, scoreApplyInfo.getRes1());
        }
        //2. 设置主键，并更新
        chainWrapper.set(ScoreApplyInfo::getSaiid, scoreApplyInfo.getSaiid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(scoreApplyInfo.getSaiid());
        }else{
            return scoreApplyInfo;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param saiid 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer saiid){
        int total = scoreApplyInfoMapper.deleteById(saiid);
        return total > 0;
    }

    /**
     * 添加科技分分配详情
     * @param applyVo 申请详情
     */
    @Override
    public void addScoreInfo(ApplyVo applyVo){
        String[] scores = applyVo.getScoreInfo().split(";");
        // 1 可以换现 0 不可以
        Integer cash = applyVo.getCash();
        int len = scores.length;
        for (int i = 0; i < len; ++i) {
            String[] info = scores[i].split("::");
            ScoreApplyInfo sai = new ScoreApplyInfo();
            sai.setGaid(applyVo.getGaid());
            sai.setSysid(info[0]);
            sai.setCreateBy(applyVo.getCreateBy());
            sai.setUpdateBy(applyVo.getUpdateBy());
            // 第一个就为主持人
            if (i == 0){
                sai.setHost(1);
            }else {
                sai.setHost(0);
            }
            
            if (cash == 1){
                // 可以换现
                sai.setCanScore(Double.valueOf(info[1]));
            }else {
                // 不可以换现
                sai.setCannotScore(Double.valueOf(info[1]));
            }
            scoreApplyInfoMapper.addScoreInfo(sai);
        }
        
    }

}
