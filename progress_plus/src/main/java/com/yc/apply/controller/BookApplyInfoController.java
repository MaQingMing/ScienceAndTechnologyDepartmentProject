package com.yc.apply.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yc.apply.mapper.BookApplyInfoMapper;
import com.yc.vo.apply.BookApplyVo;
import com.yc.vo.Result;
import com.yc.apply.entity.BookApplyInfo;
import com.yc.apply.service.BookApplyInfoService;
import com.yc.apply.service.impl.BookApplyInfoServiceImpl;
import com.yc.common.utils.UploadFileUtil;
import com.yc.entity.ProveFile;
import com.yc.service.ProveFileService;
import com.yc.vo.apply.SearchApplyVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 学术专著申请详情;(book_apply_info)表控制层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "学术专著申请详情对象功能接口")
@RestController
@RequestMapping("/bookApplyInfo")
public class BookApplyInfoController {

    @Autowired
    private ProveFileService proveFileService;
    @Autowired
    private BookApplyInfoService bookApplyInfoService;
    @Resource
    private BookApplyInfoServiceImpl bookApplyInfoServiceImpl;
    @Resource
    private BookApplyInfoMapper bookApplyInfoMapper;

    /**
     * 提交申请
     *
     * @return
     */
    @ApiOperation("提交申请")
    @PostMapping("/commit")
    public Result commit(BookApplyVo bookApplyVo,
                         @RequestParam(value = "file",required = false) List<MultipartFile> file,
                         HttpServletRequest request){
        return bookApplyInfoServiceImpl.commit(bookApplyVo,file,request);
    }

    /**
     * 文件上传
     *
     * @param file    文件信息
     * @param request 请求
     * @return
     * @throws IOException
     */
    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public Result addRecordFile(@RequestParam("file") List<MultipartFile> file,
                                HttpServletRequest request) throws IOException {
        // 调用工具类将文件保存到本地 并获取返回值（Map类型 数据是文件信息
        Map<String, UploadFileUtil.UploadFile> stringUploadFileMap
                = UploadFileUtil.uploadFiles(request, file);
        String originalFilename = file.get(0).getOriginalFilename(); // 获取文件名（用于查找map集合中的新地址
        String newFilePath = stringUploadFileMap.get(originalFilename).getNewFileUrl(); // 获取map中的新地址
        ProveFile proveFile = new ProveFile(1, newFilePath, originalFilename, 1);
        proveFileService.insert(proveFile);
        return Result.success("上传成功", newFilePath);
    }

    /**
     * 查询申请人信息
     *
     * @param
     * @return
     */
    @ApiOperation("获取缓存返回前台（申请人姓名...)")
    @GetMapping("getuserinfoback")
    public Object getuserinfoback(HttpSession session) {
        Map<String,Object> attribute = (Map<String, Object>) session.getAttribute("systemuser");
        return attribute;
    }

    /**
     * 查询用于显示的著作类型和分数
     *
     * @return
     */
    @ApiOperation("查询著作类型与分数")
    @GetMapping("selectlxorfs")
    public List<Map<String, Object>> selectlxorfs() {
        List<Map<String, Object>> lxandfs = bookApplyInfoServiceImpl.selectlxorfs();
        return lxandfs;
    }

    /**
     * 通过ID查询单条数据
     *
     * @param baiid 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{baiid}")
    public ResponseEntity<BookApplyInfo> queryById(Integer baiid) {
        return ResponseEntity.ok(bookApplyInfoService.queryById(baiid));
    }

    /**
     * @Description 查询分页数据
     * @Return
     * @Author dm
     * @Date Created in 2023/10/22
     **/
    @ApiOperation("通过gaid查询数据")
    @PostMapping("/queryHisPage")
    public Result queryDetail(@RequestBody SearchApplyVo searchApplyVo) {
        Map<String, Object> map = new HashMap<>();
        map.put("list", bookApplyInfoMapper.
                queryPaperHisPage((searchApplyVo.getBegin() - 1) * searchApplyVo.getSize(), searchApplyVo.getSize(),
                        searchApplyVo.getStatus(), searchApplyVo.getLevel(),
                        searchApplyVo.getCommonLike(), searchApplyVo.getDeptName(),
                        searchApplyVo.getDept(), searchApplyVo.getIdentifier(),
                        searchApplyVo.getUserId(), searchApplyVo.getIsDept(),
                        searchApplyVo.getCollege(), searchApplyVo.getQuery(),
                        null));
        map.put("count", bookApplyInfoMapper.queryCount(searchApplyVo.getStatus(), searchApplyVo.getLevel(),
                searchApplyVo.getCommonLike(), searchApplyVo.getDeptName(),
                searchApplyVo.getDept(), searchApplyVo.getIdentifier(),
                searchApplyVo.getUserId(), searchApplyVo.getIsDept(),
                searchApplyVo.getCollege(), searchApplyVo.getQuery(),
                null));
        return Result.success(map);
    }

    /**
     * @return
     * @Description 修改分数
     * @Return
     * @Author dm
     * @Date Created in 2023/11/10
     */
    @ApiOperation("修改分数")
    @GetMapping("/changeScore")
    public Result changeScore(@RequestParam("gaid") String gaid,
                              @RequestParam("score") String score,
                              @RequestParam("sid")String sid) {
        int result = bookApplyInfoMapper.changeScore(gaid, score,sid);
        if (result < 0) {
            return Result.error("修改失败，请稍后再试");
        } else {
            return Result.success("修改成功");
        }
    }
}