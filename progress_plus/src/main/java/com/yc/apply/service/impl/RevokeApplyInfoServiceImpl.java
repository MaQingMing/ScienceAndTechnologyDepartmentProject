package com.yc.apply.service.impl;

import com.yc.apply.mapper.ApplylogMapper;
import com.yc.apply.mapper.BookApplyInfoMapper;
import com.yc.apply.mapper.GainApplyMapper;
import com.yc.apply.mapper.ScoreApplyInfoMapper;
import com.yc.apply.scoring.TechResultsContext;
import com.yc.apply.service.ApplylogService;
import com.yc.entity.ProveFile;
import com.yc.mapper.ProveFileMapper;
import com.yc.service.ProveFileService;
import com.yc.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * 撤销申请;服务实现类
 *
 * @author : zxy
 * @date : 2023-10-17
 */
@Service
public class RevokeApplyInfoServiceImpl {
    @Autowired
    private BookApplyInfoMapper bookApplyInfoMapper;
    @Autowired
    private GainApplyMapper gainApplyMapper;
    @Autowired
    private ProveFileService proveFileService;
    @Autowired
    private ApplylogMapper applylogMapper;
    @Autowired
    private ScoreApplyInfoMapper scoreApplyInfoMapper;
    @Resource
    private TechResultsContext techResultsContext;
    @Autowired
    private ProveFileMapper proveFileMapper;
    @Resource
    private ApplylogService applylogService;

    /**
     * 查询文件列表
     * @param gaid
     * @return
     */
    public Result queryfile(Integer gaid){
        List<ProveFile> filelist = proveFileService.queryByuseIdAndStatus(gaid,1);
        return Result.success(filelist);
    }

    @Transactional(rollbackFor = Exception.class)
    public Result deleteapplication(Integer gaid,String type) {
        Result result = new Result();
        try {
            // 删除主表数据
            gainApplyMapper.deleteapply(gaid);
            String tableName;
            if("纵向科研".equals(type) == true){
                tableName = "direction_apply_info";
                applylogService.deleteApply(tableName, gaid);
            }else if("横向科研".equals(type) == true){
                tableName = "transverse_apply_info";
                applylogService.deleteApply(tableName, gaid);
            }else if("科技成果".equals(type) == true){
                tableName = "achievement_apply_info";
                applylogService.deleteApply(tableName, gaid);
            }else if("学术论文".equals(type) == true){
                tableName = "paper_apply_info";
                applylogService.deleteApply(tableName, gaid);
            }else if("学术专著".equals(type) == true){
                // 删除细表数据
                bookApplyInfoMapper.deleteapply(gaid);
            }else if("发明专利".equals(type) == true){
                tableName = "invent_apply_info";
                applylogService.deleteApply(tableName, gaid);
            }else if("科研平台".equals(type) == true){
                tableName = "scientific_apply_info";
                applylogService.deleteApply(tableName, gaid);
            }else if("科技荣誉".equals(type) == true){
                tableName = "honor_apply_info";
                applylogService.deleteApply(tableName, gaid);
            }
            // 删除文件表数据
            proveFileService.deletByGaid(gaid);
            // 删除分数表数据
            scoreApplyInfoMapper.deletebygaid(gaid);
            // 删除申请审核表状态
            applylogMapper.deletebygaid(gaid);
            result = Result.success("撤销申请成功！");
        } catch (Exception e) {
            result = Result.error("撤销失败!");//+"错误原因："+e
            throw new RuntimeException("commit",e);
        }finally {
            return result;
        }
    }

}