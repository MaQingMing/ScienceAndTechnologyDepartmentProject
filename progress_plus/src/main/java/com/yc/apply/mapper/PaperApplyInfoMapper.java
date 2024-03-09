package com.yc.apply.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.vo.standard.PaperHisPage;
import com.yc.apply.entity.PaperApplyInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 论文申请详情;(paper_apply_info)表数据库访问层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface PaperApplyInfoMapper extends BaseMapper<PaperApplyInfo> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<PaperApplyInfo> selectByPage(IPage<PaperApplyInfo> page, @Param(Constants.WRAPPER) Wrapper<PaperApplyInfo> wrapper);

    @Insert("INSERT INTO paper_apply_info\n" +
            " (`name`, `periodical_name`, `cnnum`, `order`, `institute`, `gaid`, `according`, `create_time`, `create_by`, `update_by`, `update_time`) \n" +
            " VALUES (#{name}, #{bookname}, #{booknum}, #{peopleorder}, #{yjy}, #{gaid}, #{according}, NOW(), #{userid}, #{userid}, NOW());")
    void addpaperapply(@Param("name") String name,
                       @Param("bookname") String bookname,
                       @Param("booknum") String booknum,
                       @Param("peopleorder") String peopleorder,
                       @Param("yjy") String yjy,
                       @Param("gaid") Integer gaid,
                       @Param("according") String according,
                       @Param("userid") Integer userid);

    /**
     * 通过leid 查询 类别分数 和 类别名 和 是否可换钱
     * @return
     */
    @Select("SELECT a.lname,b.score,b.cash,b.psid FROM tech_results_level a,paper_standard b \n" +
            "WHERE a.leid = #{leid} AND a.leid = b.leid AND a.`status` = 1")
    Map<String,Object> selectfs(@Param("leid") String leid);
    /**
     * 查询项目细则
     * @return
     */
    @Select("SELECT a.rid,a.content,a.ratio FROM additional_rules a WHERE a.trtid = 4 AND a.`status` = 1")
    List<Map<String,Object>> selectfz();

    /**
     * 查询附则的计算分数百分比和id
     * @return
     */
    @Select("SELECT a.rid,a.ratio FROM additional_rules a WHERE a.content = #{value} AND a.trtid = 4")
    Map<String,Object> selectfzid(@Param("value") String value);

    /**
     * 查询著作类型与相应分数
     *
     * @return 著作类型与分数
     */
    @Select("SELECT a.leid,a.lname,b.cash,b.score FROM tech_results_level a,paper_standard b WHERE a.trid = 4 AND a.leid = b.leid")
    List<Map<String, Object>> selectlxorfs();

    /**
     * @Description 查询分页数据
     * @Return
     * @Author dm
     * @Date Created in 2023/10/25
     **/
    List<PaperHisPage> queryPaperHisPage(@Param("begin") Long begin, @Param("size") Long size,
                                         @Param("status") String status, @Param("level") String level,
                                         @Param("commonLike") String commonLike, @Param("query") String query,
                                         @Param("dept") String dept, @Param("identifier") String identifier,
                                         @Param("userId") String userId, @Param("isDept") String isDept,
                                         @Param("college") String college, @Param("deptName") String deptName, @Param("years") String years);

    /**
     * @Description 查询总数
     * @Return
     * @Author dm
     * @Date Created in 2023/10/25
     **/
    Long queryCount(@Param("status") String status, @Param("level") String level,
                    @Param("commonLike") String commonLike, @Param("query") String query,
                    @Param("dept") String dept, @Param("identifier") String identifier,
                    @Param("userId") String userId, @Param("isDept") String isDept,
                    @Param("college") String college, @Param("deptName") String deptName,
                    @Param("years")String years);

    /**
     * @Description 批量添加申请信息
     * @Return
     * @Author dm
     * @Date Created in 2023/11/10
     **/
    int insertBatch(@Param("list") List<PaperApplyInfo> list,@Param("user")int user);

    /**
     * @Description 修改分数
     * @Return
     * @Author dm
     * @Date Created in 2023/11/10
     **/
    int changeScore(@Param("gaid") String gaid, @Param("score") String score,@Param("sid")String sid);
}