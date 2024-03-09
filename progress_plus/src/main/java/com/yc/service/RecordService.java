package com.yc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.Record;
import com.yc.entity.Systemuser;
import com.yc.vo.AddRecord;
import com.yc.vo.RecordVo;
import com.yc.vo.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 备案表;(record)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface RecordService{


    /**
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    Record queryById(Integer id);

    /**
     * 分页查询
     *
     * @param record 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<Record> paginQuery(Record record, long current, long size);
    /**
     * 新增数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    Record insert(Record record);
    /**
     * 更新数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    Record update(Record record);
    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 监听前端输入值的变化查询用户
     * @param username
     * @return
     */
    List<Systemuser> query_user(String username);

    /**
     * 添加备案

     */
    void insertRecord(AddRecord addRecord,HttpServletRequest request,List<MultipartFile> files);

    /**
     * 组合条件查询
     * @param username
     * @param nameRecord
     * @param radio
     * @return
     */
    List<RecordVo> queryRecordByUsername(String username,String nameRecord,int radio,int pageNum,int pageSize);

    /**
     * 撤回备案
     * @param id
     */
    void deleteRecord(int id);

    /**
     * 根据id查询备案
     */
    RecordVo queryRecordById(int id);

    /**
     * 编辑备案

     */
    void updateRecordUpdate(List<MultipartFile> files ,HttpServletRequest request,AddRecord addRecord);

    List<RecordVo> queryRecordByGovernuser(String username, String radio, int pageNum, int pageSize);

    /**
     * 查询备案问价的路径和名字
     * @param id
     * @return
     */
    Map<String,Object> queryRecordFilepathAndName(int id);

    /**
     * 修改状态
     * @param id
     */
    void updateStatus(int id);

    /**
     * 修改状态
     * @param ids
     */
    void UpdateRecord(List<Integer> ids);

    /**
     * 查询类型
     * @return
     */
    List<Map<String,Object>> queryKind();

    /**
     * 驳回审核理由
     * @param id
     * @param reason
     */
    void updateBack(int id,String reason);

    void uploadZip(List<String> fileList, String filename, HttpServletResponse response) throws IOException;

    /**
     * 查询第二个节点
     * @param id
     * @return
     */
    List<Map<String,Object>> queryNode2(int id);

    /**
     * 查项目类型
     * @param leid
     * @return
     */
    List<Map<String,Object>> querysmallTypeByleid(int leid);

    /**
     * 查备案总数
     * @param username
     * @return
     */
    List<Map<String,Object>> queryTotalBygoveruname(String username);

    List<Map<String,Object>> queryTotalByusername(String username,String name);

    /**
     * 通过项目类型id查询备案
     * @param typeId 项目类型的ID
     * @param masterId 主持人id
     * @return
     */
    List<RecordVo> selectRecordByType(Integer typeId, Integer masterId);

    /**
     * 申请成功后修改备案已被使用
     * @param gaid 申请表id
     * @param recordid 备案id
     * @return
     */
    void updateConsume(Integer gaid, Integer recordid);

    /**
     * 选择项目备案
     * @param standardType
     * @param standardId
     * @return
     */
    List<Record> findRecord(String standardType, Integer standardId, String createBy);

    /**
     * 查备案是否已经被使用
     * @param rid
     * @return
     */
    int queryCousumeByRid(@Param("rid") Integer rid);

}