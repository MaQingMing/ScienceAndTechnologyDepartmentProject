package com.yc.standard.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.standard.entity.BookStandard;
import com.yc.vo.EditProject;

/**
 * 学术专著(含著/编著/译著);(book_standard)表服务接口
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface BookStandardService{
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param bsid 主键
     * @return 实例对象
     */
    BookStandard queryById(Integer bsid);
    
    /**
     * 分页查询
     *
     * @param bookStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    Page<BookStandard> paginQuery(BookStandard bookStandard, long current, long size);
    /** 
     * 新增数据
     *
     * @param bookStandard 实例对象
     * @return 实例对象
     */
    BookStandard insert(BookStandard bookStandard);
    /** 
     * 更新数据
     *
     * @param bookStandard 实例对象
     * @return 实例对象
     */
    BookStandard update(BookStandard bookStandard);
    /** 
     * 通过主键删除数据
     *
     * @param bsid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer bsid);

    /**
     * 添加学术专著项目
     * @param editProject 添加的属性vo类
     * @return 0 失败 1 成功
     */
    int addBook(EditProject editProject);

    /**
     * 查询学术专著
     * @param pageNum 当前页码
     * @param pageSize 当前查询条数
     * @param leid    级别id
     * @return 分页查询结果
     */
    Page<EditProject> selectBook(Integer pageNum, Integer pageSize, String leid);

    /**
     * 修改学术专著项目
     * @param editProject 编辑的实体类
     * @return 0 失败 1 成功
     */
    int updateBook(EditProject editProject);
}