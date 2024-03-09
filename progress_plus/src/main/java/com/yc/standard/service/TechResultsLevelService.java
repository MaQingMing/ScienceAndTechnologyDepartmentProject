package com.yc.standard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.TechResultsLevel;
import com.yc.vo.TechResultsLevelAndTrname;

import java.util.List;

/**
* 科技成果分类;(tech_results_type)表服务接口
* @author : http://www.chiner.pro
* @date : 2023-10-17
*/
public interface TechResultsLevelService {

   /**
    * 通过ID查询单条数据
    *
    * @param leid 主键
    * @return 实例对象
    */
   TechResultsLevel queryById(Integer leid);

   /**
    * 分页查询
    *
    * @param techResultsLevel 筛选条件
    * @param current 当前页码
    * @param size  每页大小
    * @return
    */
   Page<TechResultsLevel> paginQuery(TechResultsLevel techResultsLevel, long current, long size);
   /**
    * 新增数据
    *
    * @param techResultsLevel 实例对象
    * @return 实例对象
    */
   TechResultsLevel insert(TechResultsLevel techResultsLevel);
   /**
    * 更新数据
    *
    * @param techResultsLevel 实例对象
    * @return 实例对象
    */
   TechResultsLevel update(TechResultsLevel techResultsLevel);
   /**
    * 通过主键删除数据
    *
    * @param trid 主键
    * @return 是否成功
    */
   boolean deleteById(Integer trid);

   /**
    * 添加项目级别
    * @param techResultsLevel
    * @return
    */
   int addTechResultsLevel(TechResultsLevel techResultsLevel);

   /**
    * 查询项目级别
    * @param trid    项目类别id
    * @param lname   项目级别名
    * @param currentPage 当前页
    * @param pageSize 查询条数
    * @return  分页查询结果
    */
   Page<TechResultsLevelAndTrname> selectTechResultsLevelByPage(String trid, String lname, Integer currentPage, Integer pageSize);

   /**
    * 修改项目级别
    * @param techResultsLevel
    * @return
    */
   int modifyTechResultsLevel(TechResultsLevel techResultsLevel);

   /**
    * 修改项目级别状态
    * @param tableName 表名
    * @param status 将被修改的状态
    * @param leid 被修改的级别id
    * @return 0 失败 1成功
    */
   int modifyStatus(String tableName, Integer status, Integer leid);

   /**
    * 根据类型id查询项目级别
    * @param trid 类型id
    * @return  根据id查询的项目级别
    */
   List<TechResultsLevel> selectLevelByTrid(Integer trid);

   /**
    * 修改项目级别是否可以换现
    *
    * @param tableName
    * @param cash      1 可以 0 不可以
    * @param idName id字段的名称
    * @param id        被修改的
    * @return 1 成功 0 失败
    */
   int modifyCash(String tableName, Integer cash, String idName, Integer id);

   /**
    * 修改项目状态
    *
    * @param tableName 表名
    * @param idName id字段的名称
    * @param status    将被修改的状态
    * @param id        被修改的项目id
    * @return 0 失败 1成功
    */
   int modifyProjectStatus(String tableName, Integer status, String idName, Integer id);
}