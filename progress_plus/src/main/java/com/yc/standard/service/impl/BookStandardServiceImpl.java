package com.yc.standard.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.exception.CustomException;
import com.yc.standard.entity.BookStandard;
import com.yc.standard.mapper.BookStandardMapper;
import com.yc.standard.service.BookStandardService;
import com.yc.vo.EditProject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 学术专著(含著/编著/译著);(book_standard)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class BookStandardServiceImpl implements BookStandardService{
    @Resource
    private BookStandardMapper bookStandardMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param bsid 主键
     * @return 实例对象
     */
    public BookStandard queryById(Integer bsid){
        return bookStandardMapper.selectById(bsid);
    }
    
    /**
     * 分页查询
     *
     * @param bookStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<BookStandard> paginQuery(BookStandard bookStandard, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<BookStandard> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(bookStandard.getRemarks())){
            queryWrapper.eq(BookStandard::getRemarks, bookStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(bookStandard.getStatus())){
            queryWrapper.eq(BookStandard::getStatus, bookStandard.getStatus());
        }
        if(StrUtil.isNotBlank(bookStandard.getPosit())){
            queryWrapper.eq(BookStandard::getPosit, bookStandard.getPosit());
        }
        //2. 执行分页查询
        Page<BookStandard> pagin = new Page<>(current , size , true);
        IPage<BookStandard> selectResult = bookStandardMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param bookStandard 实例对象
     * @return 实例对象
     */
    public BookStandard insert(BookStandard bookStandard){
        bookStandardMapper.insert(bookStandard);
        return bookStandard;
    }
    
    /** 
     * 更新数据
     *
     * @param bookStandard 实例对象
     * @return 实例对象
     */
    public BookStandard update(BookStandard bookStandard){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<BookStandard> chainWrapper = new LambdaUpdateChainWrapper<BookStandard>(bookStandardMapper);
        if(StrUtil.isNotBlank(bookStandard.getRemarks())){
            chainWrapper.eq(BookStandard::getRemarks, bookStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(bookStandard.getStatus())){
            chainWrapper.eq(BookStandard::getStatus, bookStandard.getStatus());
        }
        if(StrUtil.isNotBlank(bookStandard.getPosit())){
            chainWrapper.eq(BookStandard::getPosit, bookStandard.getPosit());
        }
        //2. 设置主键，并更新
        chainWrapper.set(BookStandard::getBsid, bookStandard.getBsid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(bookStandard.getBsid());
        }else{
            return bookStandard;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param bsid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer bsid){
        int total = bookStandardMapper.deleteById(bsid);
        return total > 0;
    }

     /**
      * 添加学术专著项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
     @Override
     public int addBook(EditProject editProject){
         int row = bookStandardMapper.addBook(editProject);
         if (row <= 0){
             throw new CustomException("0", "添加失败");
         }
         return row;
     }

    /**
     * 查询学术专著
     * @param pageNum 当前页码
     * @param pageSize 当前查询条数
     * @param leid    级别id
     * @return 分页查询结果
     */
    @Override
    public Page<EditProject> selectBook(Integer pageNum, Integer pageSize, String leid){
        Page<EditProject> page = new Page<>(pageNum, pageSize);
        return bookStandardMapper.selectBook(page, leid);
    }

    /**
     * 修改学术专著项目
     * @param editProject 编辑的实体类
     * @return 0 失败 1 成功
     */
    @Override
    public int updateBook(EditProject editProject){
        int row = bookStandardMapper.updateBook(editProject);
        if (row <= 0){
            throw new CustomException("0", "修改失败");
        }
        return row;
    }
}