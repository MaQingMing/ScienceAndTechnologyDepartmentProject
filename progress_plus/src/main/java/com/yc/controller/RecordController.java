package com.yc.controller;

import java.io.*;
import java.util.List;

import com.yc.mapper.RecordMapper;
import com.yc.service.impl.RecordServiceImpl;
import com.yc.vo.AddRecord;
import com.yc.vo.RecordVo;
import com.yc.vo.Result;
import java.util.Map;

import com.yc.common.utils.UploadFileUtil;
import com.yc.entity.ProveFile;
import com.yc.entity.Record;
import com.yc.entity.Systemuser;
import com.yc.service.ProveFileService;
import com.yc.service.RecordService;
import com.yc.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 备案表;(record)表控制层
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "备案表对象功能接口")
@RestController
@RequestMapping("/record")
public class RecordController{
    @Autowired
    private RecordService recordService;
    @Resource
    private RecordMapper recordMapper;

    @Autowired
    private ProveFileService proveFileService;

    @Autowired
    private RecordServiceImpl recordServiceImpl;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param id 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{id}")
    public ResponseEntity<Record> queryById(Integer id){
        return ResponseEntity.ok(recordService.queryById(id));
    }
    
    /** 
     * 分页查询
     *
     * @param record 筛选条件
     * @param pageRequest 分页对象
     * @return 查询结果
     */
    @ApiOperation("分页查询")
    @GetMapping
    public ResponseEntity<PageImpl<Record>> paginQuery(Record record, PageRequest pageRequest){
        //1.分页参数
        long current = pageRequest.getPageNumber();
        long size = pageRequest.getPageSize();
        //2.分页查询
        /*把Mybatis的分页对象做封装转换，MP的分页对象上有一些SQL敏感信息，还是通过spring的分页模型来封装数据吧*/
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Record> pageResult = recordService.paginQuery(record, current,size);
        //3. 分页结果组装
        List<Record> dataList = pageResult.getRecords();
        long total = pageResult.getTotal();
        PageImpl<Record> retPage = new PageImpl<Record>(dataList,pageRequest,total);
        return ResponseEntity.ok(retPage);
    }
    
    /** 
     * 新增数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    @ApiOperation("新增数据")
    @PostMapping
    public ResponseEntity<Record> add(Record record){
        return ResponseEntity.ok(recordService.insert(record));
    }
    
    /** 
     * 更新数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    @ApiOperation("更新数据")
    @PutMapping
    public ResponseEntity<Record> edit(Record record){
        return ResponseEntity.ok(recordService.update(record));
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
        return ResponseEntity.ok(recordService.deleteById(id));
    }

    /**
     * 根据账号变化查询数据
     * @param username
     * @return
     */
    @ApiOperation("监听数据变化查账号")
    @GetMapping("/query_user")
    public Result query_user(String username ){
        List<Systemuser> systemusers = recordService.query_user(username);
        return Result.success(systemusers);
    }

    @ApiOperation("添加备案上传文件")
    @PostMapping("/addRecord/upload")
    public Result addRecordFile(@RequestParam("file") List<MultipartFile> files,AddRecord addRecord ,HttpServletRequest request
                                ) throws IOException {
        recordService.insertRecord(addRecord,request,files);
        return Result.success("上传成功");
    }


    @ApiOperation("查询提交的备案")
    @PostMapping("/queryRecord")
    public Result queryRecordByUsername(@RequestParam("username") String username,
                                        @RequestParam("name") String nameRecord,
                                        @RequestParam("radio") int radio,
                                        @RequestParam("pageSize")int pagesize,
                                        @RequestParam("pageNum")int pageNum){

        List<RecordVo> records = recordService.queryRecordByUsername(username,nameRecord,radio,pageNum,pagesize);
        return Result.success(records);
    }

    @ApiOperation("撤回备案")
    @PostMapping("/deleteRecord")
    public Result deleteRecord(@RequestParam("id") int id){
        recordService.deleteRecord(id);
        proveFileService.deleteById(id);
        return Result.success("撤回成功");
    }

    @ApiOperation("根据id查备案数据")
    @PostMapping("/queryRecordById")
    public Result queryRecordById(@RequestParam("id") int id){
        RecordVo record = recordService.queryRecordById(id);
        return Result.success(record);
    }

    @ApiOperation("修改备案")
    @PostMapping("/updateRecordUpdate")
    public Result updateRecordUpdate(@RequestParam("file") List<MultipartFile> files,
                                     HttpServletRequest request,
                                     AddRecord addRecord){
        recordService.updateRecordUpdate(files,request,addRecord);
        return Result.success("修改成功");
    }

    /**
     * 领导查询备案
     * @param username
     * @param radio
     * @param pagesize
     * @param pageNum
     * @return
     */
    @ApiOperation("领导查询备案")
    @PostMapping("/queryRecordByGovernuser")
    public Result queryRecordByGovernuser(@RequestParam("username") String username,
                                          @RequestParam("radio") String radio,
                                          @RequestParam("pageSize")int pagesize,
                                          @RequestParam("pageNum")int pageNum){
        List<RecordVo> records = recordService.queryRecordByGovernuser(username,radio, pageNum, pagesize);
        return Result.success(records);
    }

    @ApiOperation("查询图片和名")
    @PostMapping("/queryFilepath")
    public Result queryRecordFilepathAndName(int id){
        Map<String, Object> map = recordService.queryRecordFilepathAndName(id);
        return Result.success(map);
    }

    @ApiOperation("审核通过")
    @PostMapping("/updateStatus")
    public Result upateStatus( @RequestParam("id") int id){
        recordService.updateStatus(id);
        return Result.success("成功");
    }

    @ApiOperation("批量通过")
    @PostMapping("/saveRecord")
    public Result saveRecord(@RequestBody List<Integer> id){
        recordService.UpdateRecord(id);
        return Result.success("已处理");
    }

    @ApiOperation("查询类型")
    @PostMapping("/queryKind")
    public Result queryKind(){
        List<Map<String, Object>> maps = recordService.queryKind();
        return Result.success(maps);
    }

    @ApiOperation("查询第二个节点")
    @PostMapping("/queryNode2")
    public Result queryNode2( @RequestParam("id") int id ){
        List<Map<String, Object>> maps = recordService.queryNode2(id);
        return Result.success(maps);
    }

    @ApiOperation("下载压缩包")
    @PostMapping("/uploadZip")
    public void uploadZip(@RequestParam("filePath") List<String> filepath, @RequestParam("filename") String filename, HttpServletResponse response) throws IOException {
        recordService.uploadZip(filepath, filename, response);
    }

    @ApiOperation("驳回备案")
    @PostMapping("/updateBack")
    public Result updateBack(@RequestParam("id") int id,@RequestParam("reason") String reason){
        recordService.updateBack(id,reason);
        return Result.success("修改成功");
    }

    @ApiOperation("查第三级")
    @PostMapping("/queryLeid")
    public Result queryLeid(int leid){
        List<Map<String,Object>> list = recordService.querysmallTypeByleid(leid);
        return Result.success(list);
    }

    @ApiOperation("领导查询各状态的总数")
    @PostMapping("/queryTotalBygoveruname")
    public Result queryTotalBygoveruname(@RequestParam("username") String username){
        List<Map<String, Object>> maps = recordService.queryTotalBygoveruname(username);
        return Result.success(maps);
    }

    @ApiOperation("普通用户查自己的总数")
    @PostMapping("/queryIdByUsername")
    public Result queryIdByUsername(@RequestParam("username") String username,@RequestParam("name") String name){
        List<Map<String, Object>> maps = recordService.queryTotalByusername(username, name);
        return Result.success(maps);
    }

    @ApiOperation("通过项目类型id查询备案")
    @GetMapping("/type/{typeId}/{masterId}")
    public Result selectRecordByType(@ApiParam(name = "typeId", value = "项目类型的Id") @PathVariable("typeId") Integer typeId,
                                     @ApiParam(name = "typeId", value = "主持人Id") @PathVariable("masterId") Integer masterId){
        List<RecordVo> records = recordService.selectRecordByType(typeId, masterId);
        return Result.success(records);
    }

    /**
    *@Description 查询详情
    *@Return
    *@Author dm
    *@Date Created in 2023/11/2 18:43
    **/
    @ApiOperation("查询详情")
    @GetMapping("/queryDetail")
    public Result queryDetail(@RequestParam("id")String id){
        return recordServiceImpl.queryRecordHis(id);
    }


    @ApiOperation("根据trid查找小类型")
    @PostMapping("/queryLnameByTrid")
    public Result queryLnameByTrid(@RequestParam("trid") String  trid){
        List<Map<String, Object>> maps = recordMapper.queryLnameByTrid(trid);
        return Result.success(maps);
    }
}