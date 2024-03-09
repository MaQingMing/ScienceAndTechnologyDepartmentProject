package com.yc.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.entity.Record;
import com.yc.entity.Systemuser;
import com.yc.vo.RecordVo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * 备案表;(record)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface RecordMapper  extends BaseMapper<Record>{
    /** 
     * 分页查询指定行数据
     *
     * @param page 分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<Record> selectByPage(IPage<Record> page , @Param(Constants.WRAPPER) Wrapper<Record> wrapper);

    @Select("select id,username,nickname from systemuser where username LIKE CONCAT (#{username},'%') OR nickname LIKE CONCAT ('%',#{username},'%')")
    List<Systemuser> query_user( @Param("username") String username);

    /**
     * 添加备案
     * @param name
     * @param teamPeople
     * @param values
     * @param filePath
     * @param textarea
     * @param fileName
     * @param date
     */
    @Insert("insert into record values (default,#{name},0,null,#{date},#{teamPeople},#{filePath},#{fileName},#{textarea},0,0,#{createTime},#{createBy},#{updateBy},#{updateTime})")
    void insertRecord(@Param("name") String name, @Param("teamPeople") String teamPeople
            ,@Param("values") String values, @Param("filePath") String filePath
            ,@Param("textarea") String textarea,
                      @Param("fileName") String fileName,@Param("date") String date);

    /**
     * 查询备案
     * @param username
     * @param name
     * @param radio
     * @return
     */
    List<RecordVo> queryRecordByUsername(@Param("username") String username, @Param("name")String name, @Param("radio") int radio);

    /**
     * 申请历史查询备案
     * @return
     */
    @Select("SELECT * FROM record WHERE id = #{id}")
    Map<String,Object> selectrecordbyid(@Param("id") String id);

    /**
     *根据id查用户名和账号
     * @param usernames
     * @return
     */
    List<Map<String,Object>> queryNameByUsername(@Param("usernames") List<String> usernames);

    /**
     * 删除备案
     * @param id
     */
    @Delete("delete from record where id = #{id}")
    void deleteRecord(@Param("id")int id);

    /**
     * 查询备案
     * @param id
     * @return
     */
    @Select("select DISTINCT r.*,GROUP_CONCAT(pf.path) as file_path,GROUP_CONCAT(pf.file_name) as file_name ,\n" +
            " GROUP_CONCAT(pf.file_size) as file_size, GROUP_CONCAT(pf.file_type) as file_type from record r inner join prove_file pf on r.id = pf.useid " +
            "where r.id = #{id} GROUP BY r.id")
    RecordVo queryRecordById(@Param("id") int id);

    /**
     * 根据ids查询用户名
     * @param ids
     * @return
     */
    List<Map<String,Object>> queryNicknameById(@Param("ids") List<String> ids);

    /**
     * 修改备案
     * @param id
     * @param name
     * @param values
     * @param teamPeople
     * @param textarea
     * @param date
     * @param trid
     * @param leid
     */
    @Update("update record set name = #{name} , status = 0 , date = #{date} , team_id = #{teamPeople}," +
            "description = #{textarea}" +
            ",standard_id = #{trid} ,standard_type = #{leid} where id= #{id} ")
    void updateRecordUpdate(@Param("id") int id,@Param("name") String name, @Param("standard_id") String values,
                            @Param("teamPeople")String teamPeople,
                            @Param("textarea") String textarea, @Param("date") String date,
                            @Param("trid") int trid,@Param("leid")int leid);

    /**
     * 管理员查询备案
     * @param name
     * @param radio
     * @param pageNum
     * @param pageSize
     * @return
     */
    List<RecordVo> queryRecordByGovernuser(@Param("username")String name
            ,@Param("radio") String radio ,@Param("currentPage") int pageNum,@Param("currentSize") int pageSize);

    /**
     * 查文件名和路径
     * @param id
     * @return
     */
    @Select("select GROUP_CONCAT(pf.path) as file_path , GROUP_CONCAT(file_name) AS file_name ,GROUP_CONCAT(file_type) as file_type\n" +
            ",GROUP_CONCAT(file_size) as file_size from record r INNER JOIN prove_file pf ON r.id = pf.useid\n" +
            "WHERE r.id = #{id}")
    Map<String,Object> queryRecordFilepathAndName(@Param("id") int id);

    /**
     * 通过备案
     * @param id
     */
    @Update("update record set status = 1 ,  rejection = null  where id = #{id}")
    void updateStatus( @Param("id") int id);

    /**
     * 批量通过
     * @param ids
     */
    void UpdateRecord(@Param("ids") List<Integer> ids);

    /**
     * 查类型
     * @return
     */
    List<Map<String,Object>> queryKind();

    /**
     * 驳回备案
     * @param id
     * @param reason
     */
    @Update("update record set status = 2, rejection = #{reason} where id = #{id}")
    void updateBack(@Param("id") int id,@Param("reason") String reason);

    @Select("select leid,lname from tech_results_level where trid = #{id}")
    List<Map<String,Object>> queryNode2(@Param("id") int id);

    @Select("select asid,context from achievement_standard where leid = #{leid}")
    List<Map<String,Object>> querysmallTypeByleid(@Param("leid") int leid);

    /**
     * 用户查备案
     * @param name
     * @param pageSize
     * @param pageNum
     * @param radio
     * @param id
     * @return
     */
    List<RecordVo> queryRecordByuser(@Param("name") String name,@Param("currentSize") int pageSize
            ,@Param("currentPage") int pageNum,@Param("radio") int radio,@Param("id") int id);

    /**
     * 查总数
     * @param name
     * @param radio
     * @param pageNum
     * @param pageSize
     * @return
     */
    int queryRecordtotal(@Param("username")String name
            ,@Param("radio") String radio ,@Param("currentPage") int pageNum,@Param("currentSize") int pageSize);

    List<Map<String,Object>> queryTotalBygoveruname(@Param("username") String username);

    List<Map<String,Object>> queryTotalByusername(@Param("id") int id,@Param("name") String name);

   /**
    * 通过项目类型id查询备案
    * @param typeId 项目类型的ID
    * @param masterId 主持人id
    * @return
    */
   List<RecordVo> selectRecordByType(@Param("typeId") Integer typeId, @Param("masterId") Integer masterId);

    /**
     * 查询详情
     * @return
     */
   List<Record> queryDetail(@Param("id") String id);

   List<Map<String,Object>> queryLnameByTrid(@Param("trid") String  trid);

    /**
     * 申请成功后修改备案已被使用
     * @param gaid 申请表id
     * @param recordid 备案id
     * @return
     */
    int updateConsume(@Param("gaid") Integer gaid, @Param("recordid") Integer recordid);

    /**
     * 查备案是否已经被使用
     * @param rid
     * @return
     */
    @Select("select consume from record where id = #{rid}")
    int queryCousumeByRid(@Param("rid") Integer rid);
    /**
     * 选择项目备案
     * @param standardType
     * @param standardId
     * @return
     */
    List<Record> findRecord(String standardType, Integer standardId, String createBy);

}