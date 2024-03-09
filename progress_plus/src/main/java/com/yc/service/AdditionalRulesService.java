package com.yc.service;

import com.yc.entity.AdditionalRules;
import com.yc.vo.other.AdditionalRulesVo;

import java.util.List;
import java.util.Map;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/11/19 9:27
 */
public interface AdditionalRulesService {


    /**
     * 查询所有细则
     * @param context
     * @param currrntPage
     * @param currentSize
     * @return
     */
    List<AdditionalRulesVo> queryRoles(String context, int currrntPage, int currentSize);

    /**
     * 查询总数
     * @param context
     * @return
     */
    Integer queryRolesTotal(String context);

    /**
     * 根据大类新的trid查下面的项目类型leid
     * @param trid
     * @return
     */
    List<Map<String,Object>> queryLeidByTrid(Integer trid);

    /**
     * 修改细则状态
     * @param rid
     * @param status
     */
    void updateStatus(int rid,int status);

    /**
     * 添加或者修改细则基础分
     * @param context
     * @param score
     * @param trid
     * @param updateStatus
     * @param id
     * @param type
     */
    void addRoles( String context,
                  int score,
                  int trid,
                  boolean updateStatus,
                  int id,
                  int type);

    /**
     * 添加或者修改细则百分比
     * @param context
     * @param score
     * @param trid
     * @param updateStatus
     * @param id
     * @param type
     */
    void addRolesRatio(String context,
                 int score,
                 int trid,
                 boolean updateStatus,
                 int id,
                 int type);

    /**
     * 添加或者修改细则子项目和百分比
     * @param context
     * @param score
     * @param trid
     * @param leid
     * @param updateStatus
     * @param id
     * @param type
     */
    void addRolesLeid(String context,
                      int score,
                      int trid,
                      int leid,
                      boolean updateStatus,
                      int id,
                      int type);

    /**
     * 添加或者修改细则其他
     * @param context
     * @param trid
     * @param updateStatus
     * @param id
     * @param type
     */
    void addRulesOther( String context,
                       int trid,
                       boolean updateStatus,
                       int id, int type);

    /**
     * 根据细则id删除细则
     * @param id
     */
    void deleteById(int id);

    /**
     * 通过项目类型id查询附则
     * @param trtid 项目类型id
     * @return 附则
     */
    List<AdditionalRules> queryAdditionalByTrtid(String trtid);
}
