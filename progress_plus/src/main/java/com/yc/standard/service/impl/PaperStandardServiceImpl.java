package com.yc.standard.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yc.exception.CustomException;
import com.yc.standard.entity.PaperStandard;
import com.yc.standard.mapper.PaperStandardMapper;
import com.yc.standard.service.PaperStandardService;
import com.yc.vo.EditProject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

 /**
 * 学术论文(自科/社科);(paper_standard)表服务实现类
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class PaperStandardServiceImpl implements PaperStandardService{
    @Resource
    private PaperStandardMapper paperStandardMapper;
    
    /** 
     * 通过ID查询单条数据 
     *
     * @param psid 主键
     * @return 实例对象
     */
    public PaperStandard queryById(Integer psid){
        return paperStandardMapper.selectById(psid);
    }
    
    /**
     * 分页查询
     *
     * @param paperStandard 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    public Page<PaperStandard> paginQuery(PaperStandard paperStandard, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<PaperStandard> queryWrapper = new LambdaQueryWrapper<>();
        if(StrUtil.isNotBlank(paperStandard.getRemarks())){
            queryWrapper.eq(PaperStandard::getRemarks, paperStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(paperStandard.getStatus())){
            queryWrapper.eq(PaperStandard::getStatus, paperStandard.getStatus());
        }
        if(StrUtil.isNotBlank(paperStandard.getPosit())){
            queryWrapper.eq(PaperStandard::getPosit, paperStandard.getPosit());
        }
        //2. 执行分页查询
        Page<PaperStandard> pagin = new Page<>(current , size , true);
        IPage<PaperStandard> selectResult = paperStandardMapper.selectByPage(pagin , queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }
    
    /** 
     * 新增数据
     *
     * @param paperStandard 实例对象
     * @return 实例对象
     */
    public PaperStandard insert(PaperStandard paperStandard){
        paperStandardMapper.insert(paperStandard);
        return paperStandard;
    }
    
    /** 
     * 更新数据
     *
     * @param paperStandard 实例对象
     * @return 实例对象
     */
    public PaperStandard update(PaperStandard paperStandard){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<PaperStandard> chainWrapper = new LambdaUpdateChainWrapper<PaperStandard>(paperStandardMapper);
        if(StrUtil.isNotBlank(paperStandard.getRemarks())){
            chainWrapper.eq(PaperStandard::getRemarks, paperStandard.getRemarks());
        }
        if(StrUtil.isNotBlank(paperStandard.getStatus())){
            chainWrapper.eq(PaperStandard::getStatus, paperStandard.getStatus());
        }
        if(StrUtil.isNotBlank(paperStandard.getPosit())){
            chainWrapper.eq(PaperStandard::getPosit, paperStandard.getPosit());
        }
        //2. 设置主键，并更新
        chainWrapper.set(PaperStandard::getPsid, paperStandard.getPsid());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if(ret){
            return queryById(paperStandard.getPsid());
        }else{
            return paperStandard;
        }
    }
    
    /** 
     * 通过主键删除数据
     *
     * @param psid 主键
     * @return 是否成功
     */
    public boolean deleteById(Integer psid){
        int total = paperStandardMapper.deleteById(psid);
        return total > 0;
    }

     /**
      * 添加学术论文项目
      * @param editProject 添加的属性vo类
      * @return 0 失败 1 成功
      */
     @Override
     public int addPaper(EditProject editProject){
         int row = paperStandardMapper.addPaper(editProject);
         if (row <= 0){
             throw new CustomException("0", "添加失败");
         }
         return row;
     }

     /**
      * 查询学术论文
      * @param pageNum 当前页码
      * @param pageSize 当前查询条数
      * @param leid    级别id
      * @return 分页查询结果
      */
     @Override
     public Page<EditProject> selectPaper(Integer pageNum, Integer pageSize, String leid){
         Page<EditProject> page = new Page<>(pageNum, pageSize);
         return paperStandardMapper.selectPaper(page, leid);
     }

    /**
     * 修改学术论文项目
     * @param editProject 修改的属性vo类
     * @return 0 失败 1 成功
     */
    @Override
    public int updatePaper(EditProject editProject){
        int row = paperStandardMapper.updatePaper(editProject);
        if (row <= 0){
            throw new CustomException("0", "修改失败");
        }
        return row;
    }
}