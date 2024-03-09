package com.yc.apply.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.DirectionApplyInfo;
import com.yc.vo.apply.ApplyVo;

import java.util.List;
import java.util.Map;

/**
 * 纵向申请详情;(direction_apply_info)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface DirectionApplyInfoService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param daiid 主键
     * @return 实例对象
     */
    DirectionApplyInfo queryById(Integer daiid);
    
    /**
     * 分页查询
     *
     * @param directionApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<DirectionApplyInfo> paginQuery(DirectionApplyInfo directionApplyInfo, long current, long size);
    /** 
     * 新增数据
     *
     * @param directionApplyInfo 实例对象
     * @return 实例对象
     */
    DirectionApplyInfo insert(DirectionApplyInfo directionApplyInfo);
    /** 
     * 更新数据
     *
     * @param directionApplyInfo 实例对象
     * @return 实例对象
     */
    DirectionApplyInfo update(DirectionApplyInfo directionApplyInfo);
    /** 
     * 通过主键删除数据
     *
     * @param daiid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer daiid);

     /**
      * @Description 查询纵向科研项目种类
      * @Return
      * @Author dm
      * @Date Created in 2023/10/25
      **/
     Map<String, List<String>> queryInventTypesAndLevels();

    /**
     * 添加纵向项目申请
     * @param applyVo 申请的vo
     * @return
     */
    int addDirection(ApplyVo applyVo);
}