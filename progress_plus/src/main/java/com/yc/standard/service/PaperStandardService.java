package com.yc.standard.service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.PaperStandard;
import com.yc.vo.EditProject;

/**
 * 学术论文(自科/社科);(paper_standard)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface PaperStandardService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param psid 主键
     * @return 实例对象
     */
    PaperStandard queryById(Integer psid);
    
    /**
     * 分页查询
     *
     * @param paperStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<PaperStandard> paginQuery(PaperStandard paperStandard, long current, long size);
    /** 
     * 新增数据
     *
     * @param paperStandard 实例对象
     * @return 实例对象
     */
    PaperStandard insert(PaperStandard paperStandard);
    /** 
     * 更新数据
     *
     * @param paperStandard 实例对象
     * @return 实例对象
     */
    PaperStandard update(PaperStandard paperStandard);
    /** 
     * 通过主键删除数据
     *
     * @param psid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer psid);

    /**
     * 添加学术论文项目
     * @param editProject 添加的属性vo类
     * @return 0 失败 1 成功
     */
    int addPaper(EditProject editProject);

    /**
     * 查询学术论文
     * @param pageNum 当前页码
     * @param pageSize 当前查询条数
     * @param leid    级别id
     * @return 分页查询结果
     */
    Page<EditProject> selectPaper(Integer pageNum, Integer pageSize, String leid);

    /**
     * 修改学术论文项目
     * @param editProject 修改的属性vo类
     * @return 0 失败 1 成功
     */
    int updatePaper(EditProject editProject);
}