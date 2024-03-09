package com.yc.apply.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.apply.entity.Examine;
import com.yc.vo.ToAssInfo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


/**
 * 年度考核;(examine)表数据库访问层
 *
 * @date : 2023-10-17
 */
@Mapper
public interface ExamineMapper extends BaseMapper<Examine> {

    /**
     * 查询待考核的 人员
     *
     * @param eid
     * @return
     */
    public List<Map<String, Object>> findTreatSystemUser(@Param("eid") Integer eid);

    /**
     * 根据考核 时间段 查询 多个用户 的每个项目 的总科技分
     *
     * @param starttime
     * @param endtime
     * @return
     */
    public List<Map<String, Object>> findSystemUserTechResultsType(@Param("starttime") String starttime, @Param("endtime") String endtime);

    /**
     * 查询考核
     *
     * @param currentPage
     * @param currentSize
     * @return
     */
    List<Examine> queryExamine(@Param("currentPage") int currentPage, @Param("currentSize") int currentSize);

    /**
     * 查询总数
     *
     * @return
     */
    @Select("select count(*) from examine")
    Integer queryTotal();

    /**
     * 修改考核状态
     *
     * @param eid
     */
    @Update("update examine set status = 2 where eid = #{eid}")
    void finishExamine(@Param("eid") Integer eid);

    /**
     * 年度科技项目统计
     *
     * @param eid
     * @return
     */
    Map<String, Object> queryLnameTotal(@Param("eid") Integer eid);

    /**
     * 查年度科技项目总分数
     *
     * @param eid
     * @return
     */
    Map<String, Object> queryLnameScore(@Param("eid") Integer eid);

    /**
     * 科技成果计分汇总
     *
     * @param eid
     * @return
     */
    List<ToAssInfo> findToAssInfo(@Param("eid") Integer eid, @Param("currentPage") Integer currentPage,
                                  @Param("currentSize") Integer currentSize, @Param("queryName") String queryName);


    /**
     * 科技分总数
     * @param eid
     * @return
     */
    Integer findToAssInfoTotal(@Param("eid") Integer eid, @Param("queryName") String queryName);

    /**
     * 修改状态为开始
     *
     * @param eid
     */
    @Update("update examine set status = 1 where eid = #{eid}")
    void updateBeginExamineStatus(@Param("eid") Integer eid);

    /**
     * 修改状态为结束
     *
     * @param eid
     */
    @Update("update examine set status = 2 where eid = #{eid}")
    void updateFinishExamineStatus(@Param("eid") Integer eid);

    /**
     * 结束公告公示
     *
     * @param eid
     */
    @Update("update examine set status = 3 where eid = #{eid}")
    void updatePublicityExamineStatus(@Param("eid") Integer eid);

    /**
     * 查询没有参加的人员
     *
     * @param ids
     * @return
     */
    List<Map<String, Object>> queryNoPeople(@Param("ids") List<Integer> ids, @Param("eid") Integer eid);


    /**
     * 查询已考核人数，合格人数，不合格人数，合格率
     *
     * @param eid
     * @return
     */
    Map<String, Object> queryExamineDetailTotal(Integer eid);

    /**
     * 验证时间是否在考核区间内
     *
     * @param date
     * @return
     */
    int queryTimeBetween(@Param("date") String date);

    /**
     * 查询科技成果计分
     *
     * @param eid
     * @return
     */
    List<Map<String, Object>> queryExamineTypeTotal(@Param("eid") Integer eid);

    /**
     * 删除考核信息
     * @param eid
     */
    @Delete("delete from examine where eid = #{eid}")
    void deleteExamineByEid(@Param("eid") Integer eid);

    /**
     * 统计各部门 各科技项目 获得的科技分
     *
     * @param eid
     * @return
     */
    List<Map<String, Object>> findLabeldeptTypeProjectCount(@Param("eid") Integer eid);

    /**
     * 查询所有年度考核信息
     *
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
    List<ToAssInfo> findToAssInfoByNicknameOrUsername(@Param("eid") Integer eid, @Param("currentPage") Integer currentPage,
                                                      @Param("currentSize") Integer currentSize, @Param("queryName") String queryName,
                                                      @Param("tid") Integer ttid);


    /**
     * 根据学院ID科技分总数
     *
     * @param eid 考核id
     * @param tid 部门id
     * @param queryName 查询条件 昵称或工号
     * @return
     */
    Integer findToAssInfoTotalByTid(@Param("eid") Integer eid, @Param("tid") Integer tid, @Param("queryName") String queryName);
    /**
     * 删除公告根据考核id
     * @param eid
     */
    @Delete("delete from t_notice where eid = #{eid} ")
    void deleteNoticeByEid( @Param("eid") Integer eid);

}
