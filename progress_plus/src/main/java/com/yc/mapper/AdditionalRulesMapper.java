package com.yc.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.entity.AdditionalRules;
import com.yc.vo.other.AdditionalRulesVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author mqm
 * @version 1.0
 * @date 2023/11/19 9:15
 */
@Mapper
public interface AdditionalRulesMapper extends BaseMapper<AdditionalRules> {


    /**
     * 查询细则
     * @param context
     * @param currentPage
     * @param currentSize
     * @return
     */
    List<AdditionalRulesVo> queryRoles(@Param("context") String context, @Param("currentPage") int currentPage, @Param("currentSize") int currentSize);

    /**
     * 查出细则的total
     * @param content
     * @return
     */
    Integer queryRolesTotal(@Param("context") String content);

    /**
     * 根据trid查出项目类型
     * @param trid
     * @return
     */
    @Select("select leid,lname from tech_results_level WHERE trid = #{trid} ")
    List<Map<String,Object>> queryLeidByTrid(@Param("trid") Integer trid);

    /**
     * 修改细则状态
     * @param rid
     * @param status
     */
    @Update("update additional_rules set status = #{status} where rid = #{rid}")
    void updateStatus(@Param("rid") int rid, @Param("status") int status);

    /**
     * 修改细则的基础分
     * @param context
     * @param score
     * @param trid
     * @param id
     * @param type
     */
    @Update("update additional_rules set content = #{content},score = #{score}, trtid = #{trid}, childid = null," +
            "ratio = null , type = #{type} where rid = #{id}")
    void addRoles(@Param("content") String context,
                  @Param("score") int score,
                  @Param("trid") int trid,
                  @Param("id") int id,
                  @Param("type") int type);

    /**
     * 修改细则的百分比
     * @param context
     * @param score
     * @param trid
     * @param id
     * @param type
     */
    @Update("update additional_rules set content = #{content}, score = null, trtid = #{trid}, childid = null," +
            " ratio = #{score}  ,  type = #{type}  where rid = #{id}")
    void addRolesRatio(@Param("content") String context,
                  @Param("score") String score,
                  @Param("trid") int trid,
                  @Param("id") int id,
                  @Param("type") int type);

    /**
     * 修改细则子项目
     * @param context
     * @param score
     * @param trid
     * @param leid
     * @param id
     * @param type
     */
    @Update("update additional_rules set content = #{content}, score = null,trtid = #{trid}, childid = #{leid}," +
            " ratio = #{score} , type = #{type}  where rid = #{id}")
    void addRolesLeid( @Param("content") String context,
                       @Param("score") String score,
                       @Param("trid") int trid,
                       @Param("leid") int leid,
                       @Param("id") int id,
                       @Param("type") int type
    );

    /**
     * 修改其他
     * @param context
     * @param trid
     * @param id
     * @param type
     */
    @Update("update additional_rules set content = #{content}, score = null, trtid = #{trid}, childid = null," +
            " ratio = null , type = #{type}  where rid = #{id}")
    void addRulesOther( @Param("content") String context,
                       @Param("trid") int trid,
                       @Param("id") int id,
                        @Param("type") int type
    );




    /**
     * 通过项目类型id查询附则
     * @param trtid 项目类型id
     * @return 附则
     */
    List<AdditionalRules> queryAdditionalByTrtid(@Param("trtid") String trtid);
}
