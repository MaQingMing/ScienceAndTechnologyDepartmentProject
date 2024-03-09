package com.yc.service.impl;

import com.yc.apply.mapper.ScientificApplyInfoMapper;
import com.yc.entity.AdditionalRules;
import com.yc.mapper.AdditionalRulesMapper;
import com.yc.service.AdditionalRulesService;
import com.yc.vo.other.AdditionalRulesVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/11/19 9:27
 */
@Service
public class AdditionalRulesServiceImpl implements AdditionalRulesService {

    @Autowired
    private AdditionalRulesMapper additionalRulesMapper;
    @Autowired
    private ScientificApplyInfoMapper scientificApplyInfoMapper;

    /**
     * 查询所有的细则
     * @param context
     * @param currrntPage
     * @param currentSize
     * @return
     */
    @Override
    public List<AdditionalRulesVo> queryRoles(String context, int currrntPage, int currentSize) {
        currrntPage=(currrntPage-1)*currentSize;
        List<AdditionalRulesVo> list = additionalRulesMapper.queryRoles(context, currrntPage, currentSize);
        for (int i = 0; i < list.size(); i++) {
            //拿出内容
            String content = list.get(i).getContent();
            list.get(i).setContent1(content);
            if (content.contains("#1")){
                //替换#1  基础分
                Integer ratio = list.get(i).getScore();
                content=content.replace("#1",String.valueOf(ratio));
            }
            if (content.contains("#2")){
                //替换#2  百分比
                String ratio = list.get(i).getRatio();
                content = content.replace("#2",ratio);
            }
            if (content.contains("#3")){
                //替换#3  替换子项目名称
                String lname = list.get(i).getLname();
                content = content.replace("#3",lname);
            }
            list.get(i).setContent(content);
        }
        return list;
    }

    /**
     * 查询细则总数
     * @param context
     * @return
     */
    @Override
    public Integer queryRolesTotal(String context){
        Integer integer = additionalRulesMapper.queryRolesTotal(context);
        return integer;
    }

    /**
     * 根据项目的trid查子项目
     * @param trid
     * @return
     */
    @Override
    public List<Map<String, Object>> queryLeidByTrid(Integer trid) {
        List<Map<String, Object>> maps = additionalRulesMapper.queryLeidByTrid(trid);
        return maps;
    }

    /**
     * 修改细则的状态
     * @param rid
     * @param status
     */
    @Override
    public void updateStatus(int rid, int status) {
        additionalRulesMapper.updateStatus(rid,status);
    }

    /**
     * 修改或者新增举出分值的方法
     * @param context
     * @param score
     * @param trid
     * @param updateStatus
     * @param id
     * @param type
     */
    @Override
    public void addRoles(String context, int score, int trid, boolean updateStatus, int id,int type) {
        //判断是修改还是新增
        if (updateStatus){
            //修改
            additionalRulesMapper.addRoles(context, score, trid, id,type);
        }else {
            //新增
            AdditionalRules additionalRules = new AdditionalRules();
            additionalRules.setContent(context);
            additionalRules.setScore(score);
            additionalRules.setTrtid(trid);
            additionalRules.setStatus(0);
            additionalRules.setType(type);
            additionalRulesMapper.insert(additionalRules);
        }
    }

    /**
     * 新增或者修改百分比的方法
     * @param context
     * @param score
     * @param trid
     * @param updateStatus
     * @param id
     * @param type
     */
    @Override
    public void addRolesRatio(String context, int score, int trid, boolean updateStatus, int id,int type) {
        if (updateStatus){
            additionalRulesMapper.addRolesRatio(context, score+"%", trid, id,type);
        }else {
            AdditionalRules additionalRules = new AdditionalRules();
            additionalRules.setContent(context);
            additionalRules.setRatio(score+"%");
            additionalRules.setTrtid(trid);
            additionalRules.setStatus(0);
            additionalRules.setType(type);
            additionalRulesMapper.insert(additionalRules);
        }
    }

    /**
     * 添加或者修改子项目的百分比
     * @param context
     * @param score
     * @param trid
     * @param leid
     * @param updateStatus
     * @param id
     * @param type
     */
    @Override
    public void addRolesLeid(String context, int score, int trid, int leid, boolean updateStatus, int id,int type) {
        if (updateStatus){
            additionalRulesMapper.addRolesLeid(context, score+"%", trid, leid, id,type);
        }else {
            AdditionalRules additionalRules = new AdditionalRules();
            additionalRules.setContent(context);
            additionalRules.setRatio(score+"%");
            additionalRules.setTrtid(trid);
            additionalRules.setStatus(0);
            additionalRules.setChildid(leid);
            additionalRules.setType(type);
            additionalRulesMapper.insert(additionalRules);
        }
    }

    /**
     * 添加或者修改其他
     * @param context
     * @param trid
     * @param updateStatus
     * @param id
     * @param type
     */
    @Override
    public void addRulesOther(String context, int trid, boolean updateStatus, int id,int type) {

        if (updateStatus){
            additionalRulesMapper.addRulesOther(context, trid, id,type);
        }else {
            AdditionalRules additionalRules = new AdditionalRules();
            additionalRules.setContent(context);
            additionalRules.setTrtid(trid);
            additionalRules.setStatus(0);
            additionalRules.setType(type);
            additionalRulesMapper.insert(additionalRules);
        }
    }

    /**
     * 删除细则
     * @param id
     */
    @Override
    public void deleteById(int id) {
        additionalRulesMapper.deleteById(id);
    }

    /**
     * 通过项目类型id查询附则
     * @param trtid 项目类型id
     * @return 附则
     */
    @Override
    public List<AdditionalRules> queryAdditionalByTrtid(String trtid){
        List<AdditionalRules> additionalRules = additionalRulesMapper.queryAdditionalByTrtid(trtid);
        //查询 科技平台的相关细则
        List<Map<String, Object>> maps = scientificApplyInfoMapper.queryAdditionalRules();
        Map<String, Map<String, Object>> stringMap =new HashMap<>();

        if(null == additionalRules || additionalRules.isEmpty() ){
            return Collections.emptyList();
        }

        if( maps !=null && !maps.isEmpty()){
            for (Map<String, Object> map : maps) {
                stringMap.put(String.valueOf(map.get("childid")),map);
            }
        }

        for (AdditionalRules additionalRule : additionalRules) {
            if (additionalRule.getType() < 3) {
                if (additionalRule.getType() == 0) {
                    additionalRule.setContent(additionalRule.getContent().replace("#1",String.valueOf(additionalRule.getScore())));
                }
                if (additionalRule.getType() == 1) {
                    additionalRule.setContent(additionalRule.getContent().replace("#2", additionalRule.getRatio()));
                }
                if (additionalRule.getType() == 2) {
                    additionalRule.setContent(additionalRule.getContent().replace("#3", String.valueOf(stringMap.get(String.valueOf(additionalRule.getChildid())).get("lname"))));
                    additionalRule.setContent(additionalRule.getContent().replace("#2", additionalRule.getRatio()));
                }
            }
            additionalRule.setShowcontent(additionalRule.getContent());
            if (additionalRule.getContent().length()>60){
                additionalRule.setContent(additionalRule.getContent().substring(0,60)+"...");
            }

        }
        return additionalRules;
    }
}
