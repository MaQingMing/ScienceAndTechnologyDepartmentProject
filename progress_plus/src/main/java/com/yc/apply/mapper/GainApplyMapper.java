package com.yc.apply.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.apply.entity.GainApply;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.apply.BookGainApplyVo;
import com.yc.vo.apply.PaperGainApplyVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 科技成果申请;(gain_apply)表数据库访问层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface GainApplyMapper extends BaseMapper<GainApply> {

    /**
     * 删除文件表数据
     *
     * @param gaid
     */
    @Delete("delete from prove_file where useid = #{gaid};")
    void deletefile(@Param("gaid") Integer gaid);

    /**
     * 撤销申请 删除主表数据
     *
     * @param gaid
     */
    @Delete("delete from gain_apply where gaid = #{gaid};")
    void deleteapply(@Param("gaid") Integer gaid);

    /**
     * 学术专著专用主表添加语句
     *
     * @param bookGainApplyVo
     * @return
     */
    int addbookgainapply(BookGainApplyVo bookGainApplyVo);

    /**
     * 学术论文专用主表添加语句
     * @param paperGainApplyVo
     * @return
     */
    int addpapergainapply(PaperGainApplyVo paperGainApplyVo);

    @Select("SELECT a.rejection FROM gain_apply a WHERE a.gaid = #{gaid}")
    String selectRejection(@Param("gaid") Integer gaid);

    /**
     * 学术专著专用主表添加语句
     *
     * @param sid
     * @param trtid
     * @param childid
     * @param according
     * @param remarks
     */
    @Insert(" INSERT INTO `gain_apply` ( `sid`, `status`, `rejection`, `trtid`,\n" +
            "\t\t`childid`, `team`, `recordid`, `date`, `according`, `batch`,\n" +
            "\t\t`remarks`, `create_by`, `create_time`, `update_by`, `update_time`)\n" +
            "VALUES ( #{sid}, '0', NULL, #{trtid}, #{childid}, '0', NULL, now(), #{according}," +
            " '0', #{remarks}, #{sid}, now(), #{sid}, now());")
    void insertbookgain(@Param("sid") String sid,// 申请人id
                        @Param("trtid") String trtid,// 总类型
                        @Param("childid") String childid,// 详细类别 leid
                        @Param("according") String according,// 计分依据
                        @Param("remarks") String remarks);

    /**
     * 查询gaid
     *
     * @param sid
     * @param trtid
     * @param childid
     * @param according
     * @param remarks
     * @return
     */
    @Select("SELECT a.gaid FROM gain_apply a WHERE a.sid=#{sid} and a.trtid=#{trtid} and a.childid=#{childid}" +
            " and according=#{according} and remarks = #{remarks}")
    String selectgaid(@Param("sid") String sid,// 申请人id
                      @Param("trtid") String trtid,// 总类型
                      @Param("childid") String childid,// 详细类别 leid
                      @Param("according") String according,// 计分依据
                      @Param("remarks") String remarks);

    /**
     * 查询用户id
     *
     * @param username
     * @return
     */
    @Select("SELECT a.id FROM systemuser a WHERE a.username = #{username}")
    String selectsid(@Param("username") String username);


    /**
     * 添加科技成果申请记录
     *
     * @param applyVo 科技成果申请vo
     * @return 1 成功 0 失败
     */
    int addGainApply(ApplyVo applyVo);

    /**
     * @Description 科技成果历史历史申请数据总数
     * @Return
     * @Author dm
     * @Date Created in 2023/10/22 11:27
     **/
    int queryHisPageTotal(@Param("status") String status, @Param("childid") int childid,
                          @Param("username") String username, @Param("type") String type);

    /**
     * @Description 驳回
     * @Return
     * @Author dm
     * @Date Created in 2023/10/29
     **/
    int reject(@Param("gaids") String gaids, @Param("rejectContent") String rejectContent, @Param("updateBy") String updateBy);

    /**
     * @Description 初审或者复审通过
     * @Return
     * @Author dm
     * @Date Created in 2023/11/3
     **/
    int pass(@Param("gaid") String gaid, @Param("isDept") String isDept);

    /**
     * @Description 批量插入申请信息
     * @Return
     * @Author dm
     * @Date Created in 2023/11/10
     **/
    int insertBatch(@Param("gainApplies") List<GainApply> gainApplies);

    /**
     * 根据不同的类型  查询  项目 和 成果 统计
     *
     * @param type
     * @return
     */
    List<Map<String, Object>> findTechResultsTypeCount(@Param("type") Integer type);

    /**
     * 查询最近三年的 每个类型的 申请量
     *
     * @return
     */
    List<Map<String, Object>> findRecentlyYearApplyCount();

    /**
     * @Description 查询所有申请所处的年份范围
     * @Return
     * @Author dm
     * @Date Created in 2023/12/22
     **/
    @Select("SELECT DISTINCT YEAR ( date ) FROM gain_apply ")
    List<String> queryYears();

    /**
     * 根据状态查询待审核的 部门和 老师
     * @param wrapper
     * @return
     */
    List<GainApply> findApplyCount(@Param("sid") String sid,@Param("tid") String tid,@Param("status") String status);


    /**
     * 根据状态查询待审核的 学院
     * @param wrapper
     * @return
     */
    @Select(" select count(*) as status,a.trtid from gain_apply a,systemuser s where  a.sid = s.id and s.tid =#{tid} and a.status = #{status}  GROUP BY a.trtid ")
    List<GainApply> findApplyByCollegeCount(@Param("status") Integer status,@Param("tid") Integer tid);
}