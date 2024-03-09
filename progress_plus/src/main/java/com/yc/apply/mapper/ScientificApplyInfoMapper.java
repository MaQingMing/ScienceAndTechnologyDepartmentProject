package com.yc.apply.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.vo.standard.ScientificHisPage;
import com.yc.apply.entity.ScientificApplyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 科技基地,学科建设申请详情;(scientific_apply_info)表数据库访问层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface ScientificApplyInfoMapper extends BaseMapper<ScientificApplyInfo> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<ScientificApplyInfo> selectByPage(IPage<ScientificApplyInfo> page, @Param(Constants.WRAPPER) Wrapper<ScientificApplyInfo> wrapper);

    /**
     * @Description 查询科技基地/学科建设所有的级别
     * @Return
     * @Author dm
     * @Date Created in 2023/10/25
     **/
    List<String> queryScientificTypes();

    /**
     * @Description 查询分页数据
     * @Return
     * @Author dm
     * @Date Created in 2023/10/25
     **/
    List<ScientificHisPage> queryPaperHisPage(@Param("begin") Long begin, @Param("size") Long size,
                                              @Param("status") String status, @Param("level") String level,
                                              @Param("commonLike") String commonLike, @Param("deptName") String deptName,
                                              @Param("dept") String dept, @Param("identifier") String identifier,
                                              @Param("userId") String userId, @Param("isDept") String isDept,
                                              @Param("query") String query, @Param("college") String college, @Param("years") String years);

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
                    @Param("query") String query, @Param("college") String college,
                    @Param("years")String years);

    /**
     * @Description 批量添加申请信息
     * @Return
     * @Author dm
     * @Date Created in 2023/11/10
     **/
    int insertBatch(@Param("list") List<ScientificApplyInfo> list,@Param("user")int user);

    /**
     * @Description 修改分数
     * @Return
     * @Author dm
     * @Date Created in 2023/11/10
     **/
    int changeScore(@Param("gaid") String gaid, @Param("score") String score,@Param("sid")String sid);

    /**
     * 查个人备案的名和id
     *
     * @param username
     * @return
     */

    List<Map<String, Object>> queryRecords(@Param("username") String username);

    /**
     * 查科研平台下的子项目类型
     *
     * @return
     */
    @Select("SELECT tsl.leid,tsl.lname,ss.ssid,ss.found_score as foundScore,ss.check_score as checkScore,ss.cash  from tech_results_level tsl \n" +
            "INNER JOIN scientific_standard ss ON tsl.leid = ss.leid  where tsl.trid = 7 and tsl.status =1  ")
    List<Map<String, Object>> queryLname();

    /**
     * 查询平台的细则
     *
     * @return
     */
    List<Map<String, Object>> queryAdditionalRules();
}