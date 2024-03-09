package com.yc.apply.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.vo.standard.BookHisPage;
import com.yc.apply.entity.BookApplyInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * /**
 * 学术专著申请详情;(book_apply_info)表数据库访问层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface BookApplyInfoMapper extends BaseMapper<BookApplyInfo> {

    @Delete("delete from book_apply_info where gaid = #{gaid};")
    void deleteapply(@Param("gaid") Integer gaid);

    /**
     * 查询bsid
     *
     * @param jbid
     * @return
     */
    @Select("SELECT a.bsid FROM book_standard a WHERE a.leid=#{jbid}")
    String selectbsid(@Param("jbid") String jbid);

    /**
     * 添加细表申请
     *
     * @param xmname
     * @param xmtype
     * @param xmdepartment
     * @param xmorder
     * @param xmwordnumber
     * @param gaid
     * @param yj
     * @param sid
     */
    @Insert("INSERT INTO book_apply_info ( `name`, press_name, academic_type, `order`, words_num, gaid, create_by,\n" +
            "        create_time, update_by, update_time )\n" +
            "        VALUES(#{xmname},#{xmdepartment},#{xmtype},#{xmorder},#{xmwordnumber},#{gaid},#{sid},now(),#{sid},now())")
    void insertbookapplication(@Param("xmname") String xmname,
                               @Param("xmtype") String xmtype,
                               @Param("xmdepartment") String xmdepartment,
                               @Param("xmorder") String xmorder,
                               @Param("xmwordnumber") String xmwordnumber,
                               @Param("gaid") int gaid,
                               @Param("yj") String yj,
                               @Param("sid") String sid);

    /**
     * 查询最高分与基础分
     *
     * @param text
     * @return
     */
    @Select("SELECT a.cash,a.score,a.max_score,b.lname,b.remarks,b.leid FROM book_standard a,tech_results_level b WHERE a.leid=b.leid AND a.leid=(SELECT a.leid FROM tech_results_level a WHERE a.trid = 5 AND a.lname LIKE concat('%',#{text},'%'))")
    Map<String, Object> selectmaxscore(@Param("text") String text);

    /**
     * 查询著作类型与相应分数
     *
     * @return 著作类型与分数
     */
    @Select("SELECT a.leid,a.lname,a.remarks,b.cash FROM tech_results_level a,book_standard b WHERE a.trid = 5 and b.`status` = 1 AND a.leid = b.leid")
    List<Map<String, Object>> selectlxorfs();


    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<BookApplyInfo> selectByPage(IPage<BookApplyInfo> page, @Param(Constants.WRAPPER) Wrapper<BookApplyInfo> wrapper);


    /**
     * @Description 查询分页数据
     * @Return
     * @Author dm
     * @Date Created in 2023/10/25
     **/
    List<BookHisPage> queryPaperHisPage(@Param("begin") Long begin, @Param("size") Long size,
                                        @Param("status") String status, @Param("level") String level,
                                        @Param("commonLike") String commonLike, @Param("deptName") String deptName,
                                        @Param("dept") String dept, @Param("identifier") String identifier,
                                        @Param("userId") String userId, @Param("isDept") String isDept,
                                        @Param("college") String college, @Param("query") String query, @Param("years") String years);

    /**
     * @Description 查询总数
     * @Return
     * @Author dm
     * @Date Created in 2023/10/25
     **/
    Long queryCount(@Param("status") String status, @Param("level") String level,
                    @Param("commonLike") String commonLike, @Param("deptName") String deptName,
                    @Param("dept") String dept, @Param("identifier") String identifier,
                    @Param("userId") String userId, @Param("isDept") String isDept,
                    @Param("college") String college, @Param("query") String query,
                    @Param("years")String years);

    /**
     * @Description 批量添加申请信息
     * @Return
     * @Author dm
     * @Date Created in 2023/11/10
     **/
    int insertBatch(@Param("list") List<BookApplyInfo> list,@Param("user")int user);

    /**
     * @Description 修改分数
     * @Return
     * @Author dm
     * @Date Created in 2023/11/10
     **/
    int changeScore(@Param("gaid") String gaid, @Param("score") String score,@Param("sid")String sid);

}