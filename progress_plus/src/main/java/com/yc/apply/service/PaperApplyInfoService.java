package com.yc.apply.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.apply.entity.PaperApplyInfo;
import com.yc.vo.apply.PaperApplyVo;
import com.yc.vo.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 论文申请详情;(paper_apply_info)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface PaperApplyInfoService{

    @Transactional
    Result commit(PaperApplyVo paperApplyVo, List<MultipartFile> file, HttpServletRequest request);

    /**
     * 通过ID查询单条数据 
     *
     * @param paiid 主键
     * @return 实例对象
     */
    PaperApplyInfo queryById(Integer paiid);
    
    /**
     * 分页查询
     *
     * @param paperApplyInfo 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<PaperApplyInfo> paginQuery(PaperApplyInfo paperApplyInfo, long current, long size);
    /** 
     * 新增数据
     *
     * @param paperApplyInfo 实例对象
     * @return 实例对象
     */
    PaperApplyInfo insert(PaperApplyInfo paperApplyInfo);
    /** 
     * 更新数据
     *
     * @param paperApplyInfo 实例对象
     * @return 实例对象
     */
    PaperApplyInfo update(PaperApplyInfo paperApplyInfo);
    /** 
     * 通过主键删除数据
     *
     * @param paiid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer paiid);
}