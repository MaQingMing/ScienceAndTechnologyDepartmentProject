package com.yc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yc.entity.Notice;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface NoticeMapper extends BaseMapper<Notice> {

    /**
     * 查询最新的公告
     * @return
     */
    @Select(" select id,time,title,content FROM t_notice  where YEAR(time) = year(now())  ORDER BY time DESC limit 3 ")
    List<Notice> findNoticeYera();

}
