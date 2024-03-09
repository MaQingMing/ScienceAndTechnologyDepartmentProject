package com.yc.standard.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.TechResultsLevel;
import com.yc.vo.ChildLevel;
import com.yc.vo.TechResultsLevelAndTrname;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 科技成果分类;(tech_results_level)表数据库访问层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface TechResultsLevelMapper extends BaseMapper<TechResultsLevel> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<TechResultsLevel> selectByPage(IPage<TechResultsLevel> page, @Param(Constants.WRAPPER) Wrapper<TechResultsLevel> wrapper);

    /**
     * @Description 查询所有的科研项目种类
     * @Return
     * @Author dm
     * @Date Created in 2023/10/25
     **/
    List<String> queryAllType(@Param("tid") String tid, @Param("status") String status);

    /**
     * @Description 查询所有的学术论文等级
     * @Return
     * @Author dm
     * @Date Created in 2023/10/31
     **/
    List<ChildLevel> queryLevels();

    /**
     * 添加项目级别
     *
     * @param techResultsLevel
     * @return
     */
    int addTechResultsLevel(TechResultsLevel techResultsLevel);

    /**
     * 查询项目级别
     *
     * @param page  分页条件
     * @param trid  项目类别id
     * @param lname 项目级别名
     * @return 分页查询结果
     */
    Page<TechResultsLevelAndTrname> selectTechResultsLevelByPage(Page<TechResultsLevelAndTrname> page,
                                                                 @Param("trid") String trid, @Param("lname") String lname);

    /**
     * 修改项目级别
     *
     * @param techResultsLevel
     * @return
     */
    int modifyTechResultsLevel(TechResultsLevel techResultsLevel);

    /**
     * 修改项目级别状态
     *
     * @param tableName 表名
     * @param status    将被修改的状态
     * @param leid      被修改的级别id
     * @return 0 失败 1成功
     */
    int modifyStatus(@Param("tableName") String tableName, @Param("status") Integer status, @Param("leid") Integer leid);

    /**
     * 根据类型id查询项目级别
     *
     * @param trid 类型id
     * @return 根据id查询的项目级别
     */
    List<TechResultsLevel> selectLevelByTrid(@Param("trid") Integer trid);

    /**
     * 修改项目级别是否可以换现
     *
     * @param tableName
     * @param cash      1 可以 0 不可以
     * @param idName    id字段的名称
     * @param id        被修改的
     * @return 1 成功 0 失败
     */
    int modifyCash(@Param("tableName") String tableName, @Param("cash") Integer cash, @Param("idName") String idName, @Param("id") Integer id);

    /**
     * 修改项目状态
     *
     * @param tableName 表名
     * @param status    将被修改的状态
     * @param idName    id字段的名称
     * @param id        被修改的项目id
     * @return 0 失败 1成功
     */
    int modifyProjectStatus(@Param("tableName") String tableName, @Param("status") Integer status, @Param("idName") String idName, @Param("id") Integer id);

}