package com.yc.controller;

import com.yc.entity.TJob;
import com.yc.service.TJobService;
import java.util.List;

import com.yc.entity.Labeldept;
import com.yc.service.TJobBizImpl;
import com.yc.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.annotation.Resource;

/**
 * 职位/学位;(t_job)表控制层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "职位/学位对象功能接口")
@RestController
@RequestMapping("/tJob")
public class TJobController{
    @Autowired
    private TJobService tJobService;

    @Resource
    private TJobBizImpl tJobBiz;

    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public ResponseEntity<TJob> queryById(Integer id){
        return ResponseEntity.ok(tJobService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param tJob 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping
    public ResponseEntity<PageImpl<TJob>> paginQuery(TJob tJob, PageRequest pageRequest){
        //1.分页参数
        long current = pageRequest.getPageNumber();
        long size = pageRequest.getPageSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<TJob> pageResult = tJobService.paginQuery(tJob, current,size);
        //3. 分页结果组装
        List<TJob> dataList = pageResult.getRecords();
        long total = pageResult.getTotal();
        PageImpl<TJob> retPage = new PageImpl<TJob>(dataList,pageRequest,total);
        return ResponseEntity.ok(retPage);
    }
    
    /** 
     * 新增数据
     *
     * @param tJob 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public ResponseEntity<TJob> add(TJob tJob){
        return ResponseEntity.ok(tJobService.insert(tJob));
    }
    
    /** 
     * 更新数据
     *
     * @param tJob 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseEntity<TJob> edit(TJob tJob){
        return ResponseEntity.ok(tJobService.update(tJob));
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @ApiOperation("通过主键删除数据")
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id){
        return ResponseEntity.ok(tJobService.deleteById(id));
    }

     @GetMapping("/allTJob")
     public Result<List<TJob>> AllTJob() {
         return Result.success(tJobBiz.list());
     }
}