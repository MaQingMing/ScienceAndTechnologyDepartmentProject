package com.yc.controller;

import com.yc.common.handler.MysqlBackupHandler;
import com.yc.entity.ProveFile;
import com.yc.vo.Result;
import com.yc.vo.other.ProveFileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;

@Slf4j
@Api(tags = "数据库备份控制层")
@RestController
@RequestMapping("/mb")
public class MysqlBackupController {

    @Autowired
    private MysqlBackupHandler mysqlBackupHandler;

    @ApiOperation("新建数据备份")
    @GetMapping("/nb")
    public Result NewMysqlBackup() {
        try {
            Path backup = mysqlBackupHandler.backup();
            return Result.success("备份成功!");
        } catch (Exception e) {
            log.error("数据库备份失败!",e);
            return Result.error("备份失败");
        }
    }

    @ApiOperation("查询备份列表")
    @GetMapping("/nblist")
    public Result mysqlBackupList() {
        List<ProveFileVo> proveFiles = mysqlBackupHandler.listFiles();
        if(null == proveFiles || proveFiles.isEmpty()){
            return Result.error("暂无备份数据!");
        }
        return Result.success(proveFiles);
    }
}
