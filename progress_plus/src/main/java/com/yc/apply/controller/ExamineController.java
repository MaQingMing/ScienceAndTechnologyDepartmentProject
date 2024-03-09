package com.yc.apply.controller;

import com.yc.apply.entity.Examine;
import com.yc.apply.mapper.ExamineMapper;
import com.yc.apply.service.ExamineService;
import com.yc.entity.Labeldept;
import com.yc.mapper.LabeldeptMapper;
import com.yc.vo.ExamineVo;
import com.yc.vo.Result;
import com.yc.vo.ToAssInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Api(tags = "年度考核控制器")
@RestController
@RequestMapping("/e")
public class ExamineController {

    @Autowired
    private ExamineService examineService;
    @Autowired
    private ExamineMapper examineMapper;
    @Autowired
    private LabeldeptMapper labeldeptMapper;

    @Value("${initial-examine.check}")
    private boolean checkExamine;

    @PostMapping("/saveExamine")
    @ApiModelProperty("保存考勤信息")
    public Result saveExamine(ExamineVo examineVo){
        boolean insertExamine = examineService.insertExamine(examineVo);
        if (insertExamine){
            return  Result.success("保存成功");
        }else {
            return Result.error(-1,"考核时间冲突");
        }
    }


    @RequestMapping("/queryExamine")
    @ApiOperation("查询考核")
    public Result queryExamine(@RequestParam("currentPage") int currentPage,@RequestParam("currentSize")int currentSize){
        List<Examine> examines = examineService.queryExamine(currentPage, currentSize);
        return Result.success(examines);
    }

    @RequestMapping("/queryTotal")
    @ApiOperation("查询考核总数")
    public Result queryTotal(){
        Integer integer = examineMapper.queryTotal();
        return Result.success(integer);
    }

    @RequestMapping("/finishExamine")
    @ApiOperation("手动结束考核")
    public Result finishExamine(@RequestParam("eid") Integer eid,@RequestParam("finishDate") String finishDate){
        examineService.finishExamine(eid,finishDate);
        return Result.success("修改成功");
    }


    @ApiOperation("科技成果计分汇总")
    @PostMapping("/findToAssInfo")
    public Result findToAssInfo(@RequestParam("eid") Integer eid,@RequestParam("currentSize") Integer currentSize,
                                @RequestParam("queryName") String queryName, @RequestParam("currentPage") Integer currentPage){
        Map<Integer, List<ToAssInfo>> toAssInfo = examineService.findToAssInfo(eid, currentSize, currentPage,queryName);
        return Result.success(toAssInfo);
    }

    @ApiOperation("科技成果计分汇总总数")
    @PostMapping("/findToAssInfoTotal")
    public Result findToAssInfoTotal(@RequestParam("eid") Integer eid, @RequestParam("queryName") String queryName){
        Integer toAssInfoTotal = examineMapper.findToAssInfoTotal(eid,queryName);
        return Result.success(toAssInfoTotal);
    }


    @RequestMapping("/beginExamine")
    @ApiOperation("手动开始考核")
    public Result beginExamine(@RequestParam("eid") Integer eid,@RequestParam("beginDate") String beginDate){
        examineService.updateBeginExamineStatus(eid,beginDate);
        return Result.success("修改成功");
    }

    @RequestMapping("/sendExamine")
    @ApiOperation("手动结束公示")
    public Result sendExamine(@RequestParam("eid") Integer eid,@RequestParam("publicityDate")String publicityDate){
        examineService.finishPublicityDate(eid, publicityDate);
        return Result.success("修改成功");
    }

    @PostMapping("/queryNoPeople")
    @ApiOperation("查询不参加的人员")
    public Result queryNoPeople(@RequestParam("ids")String ids,@RequestParam("eid") Integer eid){
        List<Map<String, Object>> maps = examineService.queryNoPeople(ids,eid);
        return Result.success(maps);
    }

    @RequestMapping("/queryTimeByRedis")
    @ApiOperation("查询要执行任务的时间")
    public Result queryTimeByRedis(){
        Map<String,Object> time = examineService.queryTimeByRedis();
        return Result.success(time);
    }

    @ApiOperation("查询当前时间是否有考核没结束,将不能申请")
    @GetMapping("/fiep")
    public Result findIfExaminePeriod(){
        List<Examine> list = examineService.findIfExaminePeriod();
        if(checkExamine){
            if(list!=null && list.size()>0){
                //不能申请
                return Result.success(list.get(0));
            }else {
                //能申请
                return Result.success("0");
            }
        }else{
            return Result.success("0");
        }
    }


    @ApiOperation("科技成果计分汇总")
    @PostMapping("/queryExamineTypeTotal")
    public Result queryExamineTypeTotal(@RequestParam("eid") Integer eid){
        List<Map<String, Object>> maps = examineService.queryExamineTypeTotal(eid);
        return Result.success(maps);
    }


    @ApiOperation("统计各部门 各科技项目 获得的科技分")
    @PostMapping("/fltpc")
    public Result findLabeldeptTypeProjectCount(@RequestParam("eid") Integer eid){
        if(eid < 0){
            return Result.error("暂无数据!");
        }
        List<Map<String, Object>> result = examineMapper.findLabeldeptTypeProjectCount(eid);
        if(null == result || result.isEmpty()){
            return Result.error("暂无数据!");
        }else{
            return Result.success(result);
        }
    }


    @PostMapping("/deleteExamineByEid")
    @ApiOperation("删除考核")
    public Result deleteExamineByEid( @RequestParam("eid") Integer eid, @RequestParam("beginDate") String beginDate,
                                      @RequestParam("finishDate") String finishDate,
                                      @RequestParam("publicityDate") String publicityDate){
        examineService.deleteExamineByEid(eid,beginDate,finishDate,publicityDate);
        return Result.success("删除成功");
    }

    @ApiOperation("查询所有年度考核信息")
    @GetMapping("/get")
    public Result selectExamine(){
        return Result.success(examineService.selectExamine());
    }
    
    @ApiOperation("通过昵称或工号查询用户所在学院的考核信息")
    @PostMapping("/find")
    public Result findToAssInfoByNicknameOrUsername(@RequestParam("eid") Integer eid,@RequestParam("currentSize") Integer currentSize,
                                                    @RequestParam("currentPage") Integer currentPage, @RequestParam("queryName") String queryName, 
                                                    @RequestParam("tid") Integer tid) {
        Map<Integer, List<ToAssInfo>> toAssInfo =null;
        //检查是否是 审核部门 审核部门都可以看
        if(Objects.nonNull(tid)){
            Labeldept labeldept = labeldeptMapper.selectById(tid);
            if(Objects.nonNull(labeldept.getStatus()) && labeldept.getStatus()==1){
                toAssInfo = examineService.findToAssInfo(eid, currentPage, currentSize, queryName);
            }else{
                toAssInfo = examineService.findToAssInfoByNicknameOrUsername(eid, currentPage, currentSize, queryName, tid);
            }
        }else{
            return Result.success("获取不到部门信息!");
        }
        return Result.success(toAssInfo);
    }

    @ApiOperation("根据学院ID科技分总数")
    @PostMapping("/findToAssInfoTotalByTid")
    public Result queryExamineTypeTotalByTid(@RequestParam("eid") Integer eid, @RequestParam("tid") Integer tid, @RequestParam("queryName") String queryName){
        Integer rows = 0;
        //检查是否是 审核部门 审核部门都可以看
        if(Objects.nonNull(tid)){
            Labeldept labeldept = labeldeptMapper.selectById(tid);
            if(Objects.nonNull(labeldept.getStatus()) && labeldept.getStatus()==1){
                rows = examineMapper.findToAssInfoTotal(eid,queryName);
            }else{
                rows = examineService.findToAssInfoTotalByTid(eid, tid, queryName);
            }
        }else{
            return Result.success("获取不到部门信息!");
        }
        return Result.success(rows);
    }


    @ApiOperation(value = "导出 已参加人员的科技成果计分 excel", httpMethod = "POST")
    @PostMapping("/exporttasExcel")
    public void exportTechAchievementScoringExcel(HttpServletResponse response, HttpSession session, @RequestParam("eid") Integer eid) throws IOException {
        examineService.exportTechAchievementScoringExcel(response,session,eid);
    }
    
    
}
