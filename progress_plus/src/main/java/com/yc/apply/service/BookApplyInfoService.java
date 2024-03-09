package com.yc.apply.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.BookApplyInfo;
import com.yc.vo.apply.BookApplyVo;
import com.yc.vo.Result;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学术专著申请详情;(book_apply_info)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface BookApplyInfoService{

    /**
     * 提交申请（处理请求参数
     * @param bookApplyVo
     * @param file
     * @param request
     * @return
     */
    public Result commit(BookApplyVo bookApplyVo,
                         List<MultipartFile> file,// 文件
                         HttpServletRequest request
    );
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param baiid 主键
     * @return 实例对象
     */
    BookApplyInfo queryById(Integer baiid);
    
    /**
     * 分页查询
     *
     * @param bookApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<BookApplyInfo> paginQuery(BookApplyInfo bookApplyInfo, long current, long size);
    /** 
     * 新增数据
     *
     * @param bookApplyInfo 实例对象
     * @return 实例对象
     */
    BookApplyInfo insert(BookApplyInfo bookApplyInfo);
    /** 
     * 更新数据
     *
     * @param bookApplyInfo 实例对象
     * @return 实例对象
     */
    BookApplyInfo update(BookApplyInfo bookApplyInfo);
    /** 
     * 通过主键删除数据
     *
     * @param baiid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer baiid);
}