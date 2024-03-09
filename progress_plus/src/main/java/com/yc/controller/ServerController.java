package com.yc.controller;

import com.baomidou.mybatisplus.generator.config.builder.Mapper;
import com.yc.apply.mapper.GainApplyMapper;
import com.yc.apply.service.GainApplyService;
import com.yc.common.utils.domain.Server;
import com.yc.vo.Result;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 服务器监控
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController {

    @Autowired
    private GainApplyService gainApplyService;
    @Autowired
    private GainApplyMapper gainApplyMapper;

    @ApiOperation("服务器监控")
    @GetMapping
    public Result getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return Result.success(server);
    }


    @ApiOperation(" 查询 不同 gainapply count")
    @GetMapping("acount")
    public Result getApplyCount(){
        Map<String,Object> count = new HashMap<>();
        //初审
        count.put("count1",gainApplyService.findGainApplyByStatus(0));
        //复核
        count.put("count2",gainApplyService.findGainApplyByStatus(1));
        //认可
        count.put("count3",gainApplyService.findGainApplyByStatus(2));
        return Result.success(count);
    }

    @ApiOperation(" 查询 横向 和 纵向 的 count")
    @GetMapping("pac")
    public Result getProjectApplyCount(){
        List<Map<String, Object>> techResultsTypeCount = gainApplyMapper.findTechResultsTypeCount(1);
        return Result.success(techResultsTypeCount);
    }

    @ApiOperation("查询 不是 横向和纵向 的 count")
    @GetMapping("gac")
    public Result getGainApplyCount(){
        List<Map<String, Object>> techResultsTypeCount = gainApplyMapper.findTechResultsTypeCount(2);
        return Result.success(techResultsTypeCount);
    }


    @ApiOperation("查询最近三年的 每个类型的 申请量")
    @GetMapping("ryac")
    public Result findRecentlyYearApplyCount(){
        List<Map<String, Object>> techResultsTypeCount = gainApplyMapper.findRecentlyYearApplyCount();
        return Result.success(techResultsTypeCount);
    }



    @ApiOperation(value = "下载模板", httpMethod = "POST")
    @PostMapping("/downLoadTemplate")
    public void downLoadTemplate(String fileName, HttpServletResponse response) throws IOException {
        downLoadTemplatePublic( fileName, response);
    }

    public void downLoadTemplatePublic( String fileName, HttpServletResponse response) throws IOException {
        ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resourcePatternResolver.getResources("classpath*:" + File.separator + "static" + File.separator + "file" + File.separator + fileName +".xls");
        Resource resource = resources[0];
        InputStream inputStream = null;
        OutputStream out = null;
        //根据文件在服务器的路径读取该文件转化为流
        inputStream = resource.getInputStream();
        //创建一个Buffer字符串
        byte[] buffer = new byte[1024];
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = simpleDateFormat.format(date);
        String fileNames = time + ".xls";
        //设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        //设置文件头：最后一个参数是设置下载文件名（设置编码格式防止下载的文件名乱码）
        response.setHeader("Content-Disposition", "attachment;fileName=" + new String(fileNames.getBytes("UTF-8"), "ISO8859-1"));
        out = response.getOutputStream();
        int b = 0;
        while (b != -1) {
            b = inputStream.read(buffer);
            //写到输出流(out)中
            out.write(buffer, 0, b);
        }
    }
}
