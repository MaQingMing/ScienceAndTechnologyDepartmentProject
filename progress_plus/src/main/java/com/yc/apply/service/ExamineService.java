package com.yc.apply.service;

import com.yc.apply.entity.Examine;
import com.yc.vo.ExamineVo;
import com.yc.vo.ToAssInfo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ExamineService {

    /**
     *添加考核
     * @param examineVo
     */
    boolean insertExamine(ExamineVo examineVo);
    /**
     * 查询考核
     * @return
     */
    List<Examine> queryExamine(int currentPage,int currentSize);

    /**
     * 年度科技项目统计
     * @param eid
     * @return
     */
    Map<String,Object> queryLnameTotal(Integer eid);

    /**
     * 查科技分
     * @param eid
     * @param currentSize
     * @param currentPage
     * @return
     */
    Map<Integer, List<ToAssInfo>>  findToAssInfo( Integer eid,  Integer currentSize, Integer currentPage,String queryName);

    /**
     * 查年度总分
     * @param eid
     * @return
     */
    Map<String,Object> queryLnameScore(Integer eid);

    /**
     * 查询不参加的人
     * @param ids
     * @return
     */
    List<Map<String,Object>> queryNoPeople(String ids, Integer eid);

    /**
     * 手动开始考勤
     * @param eid
     * @return
     */
    void updateBeginExamineStatus(Integer eid,String beginDate);

    /**
     * 查redis里面的时间
     * @return
     */
    Map<String,Object> queryTimeByRedis();

    /**
     * 查考核总数
     * @param eid
     * @return
     */
    List<Map<String,Object>> queryExamineTypeTotal(Integer eid);

    /**
     * 查询当前时间是否有考核没结束,将不能申请
     * @return
     */
    List<Examine> findIfExaminePeriod();

    /**
     * 手动结束考核
     * @param eid
     */
     void  finishExamine(Integer eid,String finish);

    /**
     * 结束公示
     * @param eid
     * @param publicityDate
     */
     void finishPublicityDate(Integer eid,String publicityDate);

    /**
     * 删除考核
     * @param eid
     */
    void deleteExamineByEid(Integer eid, String beginDate,String finishDate,String publicityDate );

    /**
     * 查询所有年度考核信息
     * @return
     */
    List<Examine> selectExamine();

    /**
     * 通过昵称或工号查询用户所在学院的考核信息
     *
     * @param eid 考核id
     * @param currentPage 当前页码 
     * @param currentSize 当前查询条数
     * @param queryName 查询条件 昵称或工号
     * @param ttid 部门id
     * @return
     */
    Map<Integer, List<ToAssInfo>> findToAssInfoByNicknameOrUsername(Integer eid, Integer currentPage,
                                                      Integer currentSize, String queryName, Integer ttid);

    /**
     * 根据学院ID科技分总数
     *
     * @param eid 考核id
     * @param tid 部门id
     * @param queryName 查询条件 昵称或工号
     * @return
     */
    Integer findToAssInfoTotalByTid(Integer eid, Integer tid, String queryName);

    /**
     * 导出 已参加人员的科技成果计分 excel
     * @param response
     * @param eid
     */
    void exportTechAchievementScoringExcel(HttpServletResponse response, HttpSession session, Integer eid) throws IOException;
}
