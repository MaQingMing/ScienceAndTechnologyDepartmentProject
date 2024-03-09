package com.yc.apply.controller;

import com.yc.apply.entity.Applylog;
import com.yc.apply.service.ApplylogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 /**
 * 申请审核记录表;(applylog)表控制层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "申请审核记录表对象功能接口")
@RestController
@RequestMapping("/applylog")
public class ApplylogController{
    @Autowired
    private ApplylogService applylogService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public ResponseEntity<Applylog> queryById(Integer id){
        return ResponseEntity.ok(applylogService.queryById(id));
    }
    
    /** 
     * 新增数据
     *
     * @param applylog 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public ResponseEntity<Applylog> add(Applylog applylog){
        return ResponseEntity.ok(applylogService.insert(applylog));
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
        return ResponseEntity.ok(applylogService.deleteById(id));
    }
}