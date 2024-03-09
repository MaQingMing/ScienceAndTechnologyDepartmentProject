package com.yc.apply.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.apply.entity.ScoreApplyInfo;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 申请分数详情;(score_apply_info)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-12-2
 */
@Mapper
public interface ScoreApplyInfoMapper extends BaseMapper<ScoreApplyInfo> {

    /**
     * 删除数据 通过gaid
     * @param gaid
     */
    @Delete("delete from score_apply_info where gaid = #{gaid};")
    void deletebygaid(@Param("gaid") Integer gaid);

    /**
     * 学术专著专用分数添加语句
     * @param scoreApplyInfo
     * @return
     */
    int BookApplyScore(ScoreApplyInfo scoreApplyInfo);

    /**
     * 添加科技分分配详情
     * @param scoreApplyInfo 科技分分配实体类
     * @return 1成功 0失败
     */
    int addScoreInfo(ScoreApplyInfo scoreApplyInfo);

    /**
    *@Description 批量添加神奇分数详情
    *@Return
    *@Author dm
    *@Date Created in 2023/12/6
    **/
     int insertBatch(List<ScoreApplyInfo> list,int user);

     /**
     *@Description 查询具体的分数详情
     *@Return
     *@Author dm
     *@Date Created in 2024/1/3
     **/
     List<ScoreApplyInfo> queryDetails(@Param("gaid") String gaid);
}
