package com.yc.apply.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.yc.apply.mapper.GainApplyMapper;
import com.yc.apply.service.impl.GainApplyServiceImpl;
import com.yc.vo.Result;
import com.yc.apply.entity.GainApply;
import com.yc.apply.service.GainApplyService;
import com.yc.mapper.SystemuserMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;


/**
 * 科技成果申请;(gain_apply)表控制层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "科技成果申请对象功能接口")
@RestController
@RequestMapping("/gainApply")
public class GainApplyController {
    @Autowired
    private GainApplyService gainApplyService;

    @Autowired
    private GainApplyServiceImpl gainApplyServiceImpl;

    @Resource
    private SystemuserMapper systemuserMapper;

    @Resource
    private GainApplyMapper gainApplyMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param gaid 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{gaid}")
    public ResponseEntity<GainApply> queryById(Integer gaid) {
        return ResponseEntity.ok(gainApplyService.queryById(gaid));
    }

    @ApiOperation("查询驳回原因")
    @GetMapping("/selectrejection")
    public Map<String,Object> selectRejection(@Param("gaid") Integer gaid) {
        return gainApplyServiceImpl.selectRejection(gaid);
    }


    /**
     * @Description 驳回
     * @Return
     * @Author dm
     * @Date Created in 2023/10/29
     **/
    @ApiOperation("驳回")
    @PostMapping("/reject")
    public Result reject(@RequestParam("ids") String ids,
                         @RequestParam("rejectContent") String rejectContent,
                         @RequestParam("fuser") String fuser,
                         @RequestParam("isDept") String isDept) {
        int reject = gainApplyService.reject(ids, rejectContent, fuser, isDept);
        if (reject > 0) {
            return Result.success("批量驳回成功");
        } else {
            return Result.error("批量驳回失败");
        }
    }

    /**
     * @Description 初审通过
     * @Return
     * @Author dm
     * @Date Created in 2023/11/3
     **/
    @ApiOperation("初审通过")
    @PostMapping("/pass")
    public Result pass(@RequestParam(value = "gaids") String gaids,
                       @RequestParam(value = "isDept") String isDept,
                       @RequestParam(value = "uid") String uid) {
        int pass = gainApplyService.pass(gaids, isDept, uid);
        if (pass > 0) {
            return Result.success("操作成功");
        } else {
            return Result.error("系统错误，请联系管理员");
        }

    }

    /**
     * @Description 下载批量审核申请表模板
     * @Return
     * @Author dm
     * @Date Created in 2023/11/8
     **/
    @ApiOperation("下载模板文件")
    @GetMapping("/downLoadTemplate")
    public void downLoadTemplate(HttpServletResponse response) {
        try {
            gainApplyService.downloadTemplate(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 上传文件
     *
     * @param file 上传的文件对象
     * @return 实例对象
     */
    @ApiOperation("上传已经编辑好的excel信息表格")
    @PostMapping("/upload")
    public Result upload(MultipartFile file,
                         @Param("user") int user) throws IOException {
        Result<String> result = new Result<>();
        InputStream inputStream = file.getInputStream();
        String fileName = file.getOriginalFilename().split("_")[1].split("\\.")[0];
        //判断文件名称是否符合规定
        if (fileName.length() != 32) {
            result.setMsg("上传的文件或者衍生文件名称不符合规定，请查看是否修改文件名称");
            result.setCode(0);
            return result;
        } else {
            String upload = gainApplyService.upload(inputStream, fileName, user);
            result.setMsg(upload);
            if (upload.contains("!")) {
                result.setCode(1);
                return result;
            } else {
                result.setCode(99);
                return result;
            }
        }
    }


    @ApiOperation("下载错误信息excel文件")
    @GetMapping("/downloadError")
    public void downloadError(HttpServletResponse response) throws UnsupportedEncodingException {
        try {
            gainApplyService.downloadError(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("根据姓名或者工号查询用户")
    @GetMapping("/user")
    public Result queryUserBySearch(@RequestParam(value = "username", required = false) String username,
                                    @RequestParam(value = "nickname", required = false) String nickname) {
        return Result.success(systemuserMapper.queryUserBySearch(username, nickname));
    }

    /**
     * @Description 查询所有申请所处的年份范围
     * @Return
     * @Author dm
     * @Date Created in 2023/12/22
     **/
    @ApiOperation("查询所有申请所处的年份范围")
    @GetMapping("/queryYears")
    public Result queryYears() {
        return Result.success(gainApplyService.queryYears());
    }

    /**
     * @Description 导出申请历史记录
     * @Return
     * @Author dm
     * @Date Created in 2023/12/22
     **/
    @ApiOperation("导出申请历史记录")
    @GetMapping("/exportHis")
    public void exportHis(String years, String types, HttpServletResponse response) {
        //对类别进行处理
        if (types.contains("0")) {
            types = "1,2,3,4,5,6,7,8";
        }
        try {
            gainApplyService.exportHis(years, types, response);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}