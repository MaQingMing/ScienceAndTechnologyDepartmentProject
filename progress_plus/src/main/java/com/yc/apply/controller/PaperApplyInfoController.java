package com.yc.apply.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yc.apply.mapper.PaperApplyInfoMapper;
import com.yc.apply.service.impl.PaperApplyInfoServiceImpl;
import com.yc.vo.*;
import com.yc.apply.entity.PaperApplyInfo;
import com.yc.apply.service.PaperApplyInfoService;
import com.yc.vo.apply.PaperApplyVo;
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

/**
 * 论文申请详情;(paper_apply_info)表控制层
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Api(tags = "论文申请详情对象功能接口")
@RestController
@RequestMapping("/paperApplyInfo")
public class PaperApplyInfoController {

    @Resource
    private PaperApplyInfoServiceImpl paperApplyInfoServiceimpl;
    @Resource
    private PaperApplyInfoMapper paperApplyInfoMapper;

    /**
     * 提交申请
     *
     * @return
     */
    @ApiOperation("提交申请")
    @PostMapping("/commit")
    public Result commit(PaperApplyVo paperApplyVo,
                         @RequestParam(value = "file",required = false) List<MultipartFile> file,
                         HttpServletRequest request){
        return paperApplyInfoServiceimpl.commit(paperApplyVo,file,request);
    }

    /**
     * 查询用于显示的著作类型和分数
     *
     * @return
     */
    @ApiOperation("查询论文类型与分数")
    @GetMapping("selectlxorfs")
    public List<Map<String, Object>> selectlxorfs() {
        List<Map<String, Object>> lxandfs = paperApplyInfoServiceimpl.selectlxorfs();
        return lxandfs;
    }

    /**
     * 查询论文的项目附则
     *
     * @return
     */
    @ApiOperation("查询论文的项目附则")
    @GetMapping("selectfz")
    public List<Map<String, Object>> selectfz() {
        List<Map<String, Object>> lxandfs = paperApplyInfoServiceimpl.selectfz();
        return lxandfs;
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
        map.put("list", paperApplyInfoMapper.
                queryPaperHisPage((searchApplyVo.getBegin() - 1) * searchApplyVo.getSize(), searchApplyVo.getSize(),
                        searchApplyVo.getStatus(), searchApplyVo.getLevel(),
                        searchApplyVo.getCommonLike(), searchApplyVo.getQuery(),
                        searchApplyVo.getDept(), searchApplyVo.getIdentifier(),
                        searchApplyVo.getUserId(), searchApplyVo.getIsDept(),
                        searchApplyVo.getCollege(), searchApplyVo.getDeptName(), null));
        map.put("count", paperApplyInfoMapper.
                queryCount(searchApplyVo.getStatus(), searchApplyVo.getLevel(),
                        searchApplyVo.getCommonLike(), searchApplyVo.getQuery(),
                        searchApplyVo.getDept(), searchApplyVo.getIdentifier(),
                        searchApplyVo.getUserId(), searchApplyVo.getIsDept(),
                        searchApplyVo.getCollege(), searchApplyVo.getDeptName(),
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
        int result = paperApplyInfoMapper.changeScore(gaid, score,sid);
        if (result < 0) {
            return Result.error("修改失败，请稍后再试");
        } else {
            return Result.success("修改成功");
        }
    }
}