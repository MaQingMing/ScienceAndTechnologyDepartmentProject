package com.yc.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yc.entity.ProveFile;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 文件表;(prove_file)表数据库访问层
 * @author : http://www.chiner.pro
 * @date : 2023-11-17
 */
@Mapper
public interface ProveFileMapper  extends BaseMapper<ProveFile> {


    @Delete("delete from prove_file where useid = #{useid}")
    void deleteByuseId( @Param("useid") Integer useid);

    /**
     * 查询文件列表
     * @param
     * @return
     */
    @Select("SELECT a.file_name,a.file_size,a.file_type,a.`status`,a.path FROM prove_file a WHERE a.useid = #{useid} and a.status = #{status} ORDER BY CASE WHEN file_type IN ('jpg', 'jpeg', 'png', 'gif', 'bmp') THEN 1 ELSE 2 END, file_type")
    List<ProveFile> queryfile(@Param("useid") Integer useid,@Param("status") Integer status);

}
