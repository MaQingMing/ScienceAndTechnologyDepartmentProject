package com.yc.apply.controller;

import com.yc.apply.service.impl.*;
import com.yc.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 撤销申请控制层
 *
 * @author : zxy
 * @date : 2023-10-17
 */
@Api(tags = "撤销申请功能接口")
@RestController
@RequestMapping("/revokeApply")
public class RevokeApplyController {
    @Autowired
    private RevokeApplyInfoServiceImpl revokeApplyInfoService;

    @ApiOperation("申请历史查看文件")
    @GetMapping("/queryfile")
    public Result queryfile(@Param("gaid") Integer gaid){
        return revokeApplyInfoService.queryfile(gaid);
    }


    /**
     * 申请历史撤销申请
     * @param gaid
     * @param type
     * @return
     */
    @ApiOperation("申请历史撤销申请")
    @GetMapping("/hisdelete")
    public Result selectRejection(@Param("gaid") Integer gaid,
                                  @Param("type") String type){
        Result result = revokeApplyInfoService.deleteapplication(gaid,type);
        return result;
    }

}