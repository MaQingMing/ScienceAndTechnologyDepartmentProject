package com.yc.apply.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.standard.InventHisPage;
import com.yc.apply.entity.InventApplyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 专利申请详情;(invent_apply_info)表数据库访问层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Mapper
public interface InventApplyInfoMapper extends BaseMapper<InventApplyInfo> {
    /**
     * 分页查询指定行数据
     *
     * @param page    分页参数
     * @param wrapper 动态查询条件
     * @return 分页对象列表
     */
    IPage<InventApplyInfo> selectByPage(IPage<InventApplyInfo> page, @Param(Constants.WRAPPER) Wrapper<InventApplyInfo> wrapper);

    /**
     * @Description 查询分页数据
     * @Return
     * @Author dm
     * @Date Created in 2023/10/25
     **/
    List<InventHisPage> queryPaperHisPage(@Param("begin") Long begin, @Param("size") Long size,
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
    int insertBatch(@Param("list") List<InventApplyInfo> list,@Param("user")int user);

    /**
     * @Description 修改分数
     * @Return
     * @Author dm
     * @Date Created in 2023/11/10
     **/
    int changeScore(@Param("gaid") String gaid, @Param("score") String score,@Param("sid")String sid);

    /**
     * 添加专利申请
     *
     * @param applyVo
     * @return
     */
    int addInvent(ApplyVo applyVo);
}