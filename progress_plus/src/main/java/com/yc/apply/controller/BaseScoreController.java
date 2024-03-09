package com.yc.apply.controller;

import com.yc.apply.entity.BaseScore;
import com.yc.apply.service.BaseScoreService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

 /**
 * 底分表;(base_score)表控制层
 * @author : http://www.chiner.pro
 * @date : 2023-10-29
 */
@Api(tags = "底分表对象功能接口")
@RestController
@RequestMapping("/baseScore")
public class BaseScoreController{
    @Autowired
    private BaseScoreService baseScoreService;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param bsid 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{bsid}")
    public ResponseEntity<BaseScore> queryById(Integer bsid){
        return ResponseEntity.ok(baseScoreService.queryById(bsid));
    }

}