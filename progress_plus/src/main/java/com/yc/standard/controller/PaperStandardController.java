package com.yc.standard.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.entity.Systemuser;
import com.yc.standard.entity.PaperStandard;
import com.yc.standard.mapper.PaperStandardMapper;
import com.yc.standard.service.PaperStandardService;
import com.yc.standard.service.TechResultsLevelService;
import com.yc.vo.EditProject;
import com.yc.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学术论文(自科/社科);(paper_standard)表控制层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "学术论文(自科/社科)对象功能接口")
@RestController
@RequestMapping("/paperStandard")
public class PaperStandardController {
    @Autowired
    private PaperStandardService paperStandardService;

    @Resource
    private TechResultsLevelService techResultsLevelService;
    @Resource
    private PaperStandardMapper paperStandardMapper;

    // 表名 主键名
    private String tableName = "paper_standard";
    private String idName = "psid";

    /**
     * 通过ID查询单条数据
     *
     * @param psid 主键
     * @return 实例对象
     */
    @ApiOperation("通过ID查询单条数据")
    @GetMapping("{psid}")
    public ResponseEntity<PaperStandard> queryById(Integer psid) {
        return ResponseEntity.ok(paperStandardService.queryById(psid));
    }

    @ApiOperation("添加学术论文项目")
    @PostMapping("/add")
    public Result addPaper(@RequestBody EditProject editProject, @SessionAttribute("governuser") Map governuser) {
        editProject.setCreateBy(governuser.get("id") + "");
        editProject.setUpdateBy(governuser.get("id") + "");
        int result = paperStandardService.addPaper(editProject);
        return Result.success("添加成功!");
    }

    @ApiOperation("查询学术论文")
    @GetMapping("/get/{pageNum}/{pageSize}")
    public Result selectPaper(@ApiParam(name = "pageNum", value = "当前页码")
                              @PathVariable("pageNum") Integer pageNum,
                              @ApiParam(name = "pageSize", value = "当前查询条数")
                              @PathVariable("pageSize") Integer pageSize,
                              @RequestParam(value = "leid", required = false) String leid) {
        Page<EditProject> page = paperStandardService.selectPaper(pageNum, pageSize, leid);
        List<EditProject> records = page.getRecords();
        long total = page.getTotal();
        if (total <= 0) {
            return Result.error("暂无信息");
        }
        Map<String, Object> map = new HashMap<>();
        map.put("data", records);
        map.put("total", total);
        return Result.success(map);
    }

    @ApiOperation("修改学术论文项目")
    @PostMapping("/update")
    public Result updatePaper(@RequestBody EditProject editProject, @SessionAttribute("governuser") Map governuser) {
        editProject.setCreateBy(governuser.get("id") + "");
        editProject.setUpdateBy(governuser.get("id") + "");
        int result = paperStandardService.updatePaper(editProject);
        return Result.success("修改成功!");
    }

    @ApiOperation("修改项目是否能换现")
    @PostMapping("/cash/{id}/{cash}")
    public Result modifyCash(@ApiParam(name = "id", value = "项目id")
                             @PathVariable("id") Integer id,
                             @ApiParam(name = "cash", value = "是否能换现")
                             @PathVariable("cash") Integer cash) {
        int row = techResultsLevelService.modifyCash(tableName, cash, idName, id);
        return Result.success("修改成功!");
    }

    @ApiOperation("修改项目状态")
    @PostMapping("/project/{id}/{status}")
    public Result modifyProjectStatus(@ApiParam(name = "id", value = "项目id")
                                      @PathVariable("id") Integer id,
                                      @ApiParam(name = "status", value = "项目级别状态")
                                      @PathVariable("status") Integer status){
        int row = techResultsLevelService.modifyProjectStatus(tableName, status, idName,id);
        return Result.success("修改成功!");
    }

    @ApiOperation("查询所有等级")
    @GetMapping("/queryLevels")
    public Result queryLevels(){
        return Result.success(paperStandardMapper.queryLevels());
    }
}