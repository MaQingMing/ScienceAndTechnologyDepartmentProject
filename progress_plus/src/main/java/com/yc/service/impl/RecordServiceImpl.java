package com.yc.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.yc.common.utils.AllConstans;
import com.yc.common.utils.FileUtils;
import com.yc.common.utils.UploadFileUtil;
import com.yc.common.utils.ZipUtils;
import com.yc.common.utils.domain.server.Sys;
import com.yc.entity.ProveFile;
import com.yc.entity.Systemuser;
import com.yc.exception.CustomException;
import com.yc.mapper.ProveFileMapper;
import com.yc.mapper.SystemuserMapper;
import com.yc.vo.AddRecord;
import com.yc.vo.RecordVo;
import com.yc.vo.Result;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.yc.entity.Record;
import com.yc.entity.Systemuser;
import com.yc.mapper.RecordMapper;
import com.yc.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.*;

/**
 * 备案表;(record)表服务实现类
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
@Service
public class RecordServiceImpl implements RecordService {
    @Autowired
    private RecordMapper recordMapper;
    @Resource
    private SystemuserMapper systemuserMapper;
    @Resource
    private ProveFileServiceImpl proveFileService;
    @Resource
    private ProveFileMapper proveFileMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Record queryById(Integer id){
        return recordMapper.selectById(id);
    }

    /**
     * 申请历史查询备案
     * @param id
     * @return
     */
    public Result queryRecordHis(String id){
        Integer userid = Integer.valueOf(id);
        Result result = new Result();
        List<Object> data = new ArrayList<>();
        Map<String,Object> record = recordMapper.selectrecordbyid(id);
        String user = (String) record.get("team_id");
        String nickname = "";
        Map<String,Object> usermap = new HashMap<>();
        String[] numberStrings = user.split(",");
        for (int i = 0; i < numberStrings.length; i++) {
            if (nickname == ""){
                usermap = systemuserMapper.selectnickname(Integer.parseInt(numberStrings[i]));
                nickname = usermap.get("nickname")+" "+usermap.get("username");
            }else {
                usermap = systemuserMapper.selectnickname(Integer.parseInt(numberStrings[i]));
                nickname = nickname + ","+ usermap.get("nickname")+" "+usermap.get("username");
            }
        }
        data.add(record);
        data.add(proveFileMapper.queryfile(userid,2));
        data.add(nickname);
        result.setData(data);
        result.setCode(1);
        return result;
    }

    /**
     * 分页查询
     *
     * @param record 筛选条件
     * @param current 当前页码
     * @param size  每页大小
     * @return
     */
    @Override
    public Page<Record> paginQuery(Record record, long current, long size){
        //1. 构建动态查询条件
        LambdaQueryWrapper<Record> queryWrapper = new LambdaQueryWrapper<>();
        if (StrUtil.isNotBlank(record.getName())) {
            queryWrapper.eq(Record::getName, record.getName());
        }
        if (StrUtil.isNotBlank(record.getRejection())) {
            queryWrapper.eq(Record::getRejection, record.getRejection());
        }
        if (StrUtil.isNotBlank(record.getDate())) {
            queryWrapper.eq(Record::getDate, record.getDate());
        }
        if (StrUtil.isNotBlank(record.getTeamId())) {
            queryWrapper.eq(Record::getTeamId, record.getTeamId());
        }
        if (StrUtil.isNotBlank(record.getDescription())) {
            queryWrapper.eq(Record::getDescription, record.getDescription());
        }
        //2. 执行分页查询
        Page<Record> pagin = new Page<>(current, size, true);
        IPage<Record> selectResult = recordMapper.selectByPage(pagin, queryWrapper);
        pagin.setPages(selectResult.getPages());
        pagin.setTotal(selectResult.getTotal());
        pagin.setRecords(selectResult.getRecords());
        //3. 返回结果
        return pagin;
    }

    /**
     * 新增数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    @Override
    public Record insert(Record record){
        recordMapper.insert(record);
        return record;
    }

    /**
     * 更新数据
     *
     * @param record 实例对象
     * @return 实例对象
     */
    @Override
    public Record update(Record record){
        //1. 根据条件动态更新
        LambdaUpdateChainWrapper<Record> chainWrapper = new LambdaUpdateChainWrapper<Record>(recordMapper);
        if (StrUtil.isNotBlank(record.getName())) {
            chainWrapper.eq(Record::getName, record.getName());
        }
        if (StrUtil.isNotBlank(record.getRejection())) {
            chainWrapper.eq(Record::getRejection, record.getRejection());
        }
        if (StrUtil.isNotBlank(record.getDate())) {
            chainWrapper.eq(Record::getDate, record.getDate());
        }
        if (StrUtil.isNotBlank(record.getTeamId())) {
            chainWrapper.eq(Record::getTeamId, record.getTeamId());
        }
        if (StrUtil.isNotBlank(record.getDescription())) {
            chainWrapper.eq(Record::getDescription, record.getDescription());
        }
        //2. 设置主键，并更新
        chainWrapper.set(Record::getId, record.getId());
        boolean ret = chainWrapper.update();
        //3. 更新成功了，查询最最对象返回
        if (ret) {
            return queryById(record.getId());
        } else {
            return record;
        }
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id){
        int total = recordMapper.deleteById(id);
        return total > 0;
    }

    /**
     * 查询普通用户
     * @param username
     * @return
     */
    @Override
    public List<Systemuser> query_user(String username) {
        List<Systemuser> systemusers = recordMapper.query_user(username);
        return systemusers;
    }

    @Override
    public void insertRecord(AddRecord addRecord,HttpServletRequest request,List<MultipartFile> file) {
        Integer leid = addRecord.getLeid();
        String teamPeople = addRecord.getTeamPeople();
        String username = addRecord.getUsername();
        String name = addRecord.getName();
        String textarea = addRecord.getTextarea();
        String date = addRecord.getDate();
        Integer trid = addRecord.getTrid();
        // 调用工具类将文件保存到本地 并获取返回值（Map类型 数据是文件信息
        Map<String, UploadFileUtil.UploadFile> stringUploadFileMap
                = UploadFileUtil.uploadFiles(request, file);
        String[] split = teamPeople.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            //判断账号是否是当前用户,如果是就加在集合中的第一个元素，永远是集合当中第一个，如不是，正常添加
            if (split[i].equals(username)) {
                list.add(0, split[i]);
            } else {
                list.add(split[i]);
            }
        }
        String ids = systemuserMapper.queryIdByusername(list);
        Record record = new Record();
        record.setName(name);
        record.setStatus(0);
        record.setTeamId(ids);
        record.setDescription(textarea);
        record.setDate(date);
        record.setConsume(0);
        record.setStandardId(trid);
        record.setStandardType(leid);
        recordMapper.insert(record);
        Integer integer = record.getId();
        for (MultipartFile multipartFile : file) {
            String filename = multipartFile.getOriginalFilename();
            String newFileUrl = stringUploadFileMap.get(filename).getNewFileUrl();
            ProveFile proveFile = new ProveFile(2, newFileUrl, filename, integer);
            proveFileService.insert(proveFile);
        }
    }

    /**
     * 查询备案
     * @param username
     * @param nameRecord
     * @param radio
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<RecordVo> queryRecordByUsername(String username,String nameRecord,int radio,int pageNum,int pageSize) {
        int currentPage = (pageNum - 1  )*pageSize;
        int id = systemuserMapper.queryIdByUsername(username);
        List<RecordVo> records = recordMapper.queryRecordByuser(nameRecord, pageSize, currentPage, radio, id);
        if (records.isEmpty()||records.get(0).getId()==null){
            return null;
        }else {
            Integer total = records.get(0).getTotal();
            List<RecordVo> resultRecords = new ArrayList<>();
            for (RecordVo record : records) {
                RecordVo newRecord = new RecordVo();
                newRecord.setId(record.getId());
                newRecord.setDate(record.getDate());
                newRecord.setDescription(record.getDescription());
                newRecord.setFileName(record.getFileName());
                newRecord.setFilePath(record.getFilePath());
                newRecord.setName(record.getName());
                newRecord.setStatus(record.getStatus());
                newRecord.setRejection(record.getRejection());
                newRecord.setStandardId(record.getStandardId());
                newRecord.setTeamId(record.getTeamId());
                newRecord.setTotal(total);
                newRecord.setTrname(record.getTrname());
                newRecord.setLname(record.getLname());
                String teamId = record.getTeamId();
                String[] split = teamId.split(",");
                List<String> teamNames = new ArrayList<>(Arrays.asList(split));
                List<Map<String, Object>> maps = recordMapper.queryNameByUsername(teamNames);
                newRecord.setTeamNames(maps);
                resultRecords.add(newRecord);
            }
            return resultRecords;
        }

    }

    /**
     * 删除备案
     * @param id
     */
    @Override
    public void deleteRecord(int id) {
        recordMapper.deleteRecord(id);
    }

    /**
     * 根据id查询备案信息
     * @param id
     * @return
     */
    @Override
    public RecordVo queryRecordById(int id) {
        RecordVo record = recordMapper.queryRecordById(id);
        Integer standardId = record.getStandardId();
        List<Map<String, Object>> maps = recordMapper.queryLnameByTrid(String.valueOf(standardId));
        String teamId = record.getTeamId();
        String[] ids = teamId.split(",");
        List<String> IDS = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            IDS.add(ids[i]);
        }
        record.setLnamesAndLeid(maps);
        List<Map<String,Object>> list = recordMapper.queryNicknameById(IDS);
        record.setTeamNames(list);
        return record;
    }

    /**
     *
     * @param files
     * @param request
     */
    @Override
    public void updateRecordUpdate(List<MultipartFile> files,HttpServletRequest request,AddRecord addRecord) {
        Integer trid = addRecord.getTrid();
        String date = addRecord.getDate();
        String values = addRecord.getValues();
        String name = addRecord.getName();
        String textarea = addRecord.getTextarea();
        String username = addRecord.getUsername();
        Integer leid = addRecord.getLeid();
        String teamPeople = addRecord.getTeamPeople();
        String filename = addRecord.getFilename();
        String filepath = addRecord.getFilePath();
        Integer id = addRecord.getId();
        // 调用工具类将文件保存到本地 并获取返回值（Map类型 数据是文件信息
        Map<String, UploadFileUtil.UploadFile> stringUploadFileMap
                = UploadFileUtil.uploadFiles(request, files);
        String[] usernames = teamPeople.split(",");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < usernames.length; i++) {
            list.add(usernames[i]);
        }
        String ids = systemuserMapper.queryIdByusername(list);
        String[] split = filepath.split(",");
        String[] split1 = filename.split(",");
        List<String> paths = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            paths.add(split[i]);
            names.add(split1[i]);
        }
        for (MultipartFile multipartFile : files) {
            String filenames = multipartFile.getOriginalFilename();
            String newFileUrl = stringUploadFileMap.get(filenames).getNewFileUrl();
            paths.add(newFileUrl);
            names.add(filenames);
        }
        proveFileService.deleteByuseId(id);
        for (int i = 0; i < paths.size(); i++) {
            ProveFile proveFile = new ProveFile(2, paths.get(i), names.get(i), id);
            proveFileService.insert(proveFile);
        }
        recordMapper.updateRecordUpdate(id, name, values, ids, textarea, date, trid, leid);
    }

    /**
     * 领导查询
     * @param username
     * @param radio
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public List<RecordVo> queryRecordByGovernuser(String username, String radio, int pageNum, int pageSize) {
        int currentPage = (pageNum - 1) * pageSize;
        List<RecordVo> records = recordMapper.queryRecordByGovernuser(username, radio, currentPage, pageSize);
        int total = recordMapper.queryRecordtotal(username, radio, currentPage, pageSize);
        List<RecordVo> resultRecords = new ArrayList<>();
        for (RecordVo record : records) {
            String teamId = record.getTeamId();
            String[] split = teamId.split(",");
            List<String> teamNames = new ArrayList<>();
            for (String s : split) {
                teamNames.add(s);
            }
            List<Map<String, Object>> maps = recordMapper.queryNameByUsername(teamNames);
            RecordVo newRecord = new RecordVo();
            newRecord.setId(record.getId());
            newRecord.setDate(record.getDate());
            newRecord.setDescription(record.getDescription());
            newRecord.setFileName(record.getFileName());
            newRecord.setFilePath(record.getFilePath());
            newRecord.setName(record.getName());
            newRecord.setStatus(record.getStatus());
            newRecord.setRejection(record.getRejection());
            newRecord.setStandardId(record.getStandardId());
            newRecord.setTeamId(record.getTeamId());
            newRecord.setTotal(total);
            newRecord.setTeamNames(maps);
            newRecord.setTrname(record.getTrname());
            newRecord.setLname(record.getLname());
            resultRecords.add(newRecord);
        }
        return resultRecords; // 返回包含团队成员姓名的记录列表
    }

    /**
     * 查询备案文件的名和路径
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> queryRecordFilepathAndName(int id) {
        Map<String, Object> map = recordMapper.queryRecordFilepathAndName(id);
        return map;
    }

    /**
     * 修改状态
     * @param id
     */
    @Override
    public void updateStatus(int id) {
        recordMapper.updateStatus(id);
    }

    @Override
    public void UpdateRecord(List<Integer> ids) {
        recordMapper.UpdateRecord(ids);
    }

    /**
     * 查找类型
     * @return
     */
    @Override
    public List<Map<String, Object>> queryKind() {
        List<Map<String, Object>> maps = recordMapper.queryKind();
        return maps;
    }

    /**
     * 驳回备案
     * @param id
     * @param reason
     */
    @Override
    public void updateBack(int id, String reason) {
        recordMapper.updateBack(id,reason);
    }

    @Override
    public void uploadZip(List<String> fileList, String filename, HttpServletResponse response){
        String fileName = filename + ".zip";
        try {
            //设置文件ContentType类型，这样设置，会自动判断下载文件类型
            response.setContentType("application/octet-stream;charset=UTF-8");
            //设置文件头：最后一个参数是设置下载文件名（设置编码格式防止下载的文件名乱码）
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
            List<File> list = new ArrayList<>();
            for (String s : fileList) {
                // 文件的格式为 uploadfile\2023\12\19\5d8adf87212a43c29e86e40f8ca9e180.docx
                String finalPath = AllConstans.STU_IMG_UPLOAD_PATH + File.separator + s.substring(s.lastIndexOf("uploadfile") + "uploadfile".length() + 1);
                File fileFinal = new File(finalPath);
                list.add(fileFinal);
            }
            ZipUtils.toZip(list, response.getOutputStream());
        } catch (IOException e) {
            throw new CustomException("0", "文件压缩失败");
        }
    }

    @Override
    public List<Map<String, Object>> queryNode2(int id) {
        List<Map<String, Object>> maps = recordMapper.queryNode2(id);
        return maps;
    }

    /**
     * 查找小类型
     * @param leid
     * @return
     */
    @Override
    public List<Map<String,Object>> querysmallTypeByleid(int leid) {
        List<Map<String,Object>> list = recordMapper.querysmallTypeByleid(leid);
        return list;
    }

    /**
     * 查询备案总数
     * @param username
     * @return
     */
    @Override
    public List<Map<String, Object>> queryTotalBygoveruname(String username) {
        List<Map<String, Object>> maps = recordMapper.queryTotalBygoveruname(username);
        return maps;
    }

    @Override
    public List<Map<String, Object>> queryTotalByusername(String username, String name) {
        int id = systemuserMapper.queryIdByUsername(username);
        List<Map<String, Object>> maps = recordMapper.queryTotalByusername(id, name);
        return maps;
    }

    /**
     * 通过项目类型id查询备案
     * @param typeId 项目类型的ID
     * @param masterId 主持人id
     * @return
     */
    @Override
    public List<RecordVo> selectRecordByType(Integer typeId, Integer masterId){
        List<RecordVo> records = recordMapper.selectRecordByType(typeId,masterId);
        return records;
    }

    /**
     * 申请成功后修改备案已被使用
     * @param gaid 申请表id
     * @param recordid 备案id
     * @return
     */
    @Override
    public void updateConsume(Integer gaid, Integer recordid){
        recordMapper.updateConsume(gaid, recordid);
    }

    @Override
    public List<Record> findRecord(String standardType, Integer standardId, String createBy) {
        return recordMapper.findRecord(standardType, standardId, createBy);
    }

    @Override
    public int queryCousumeByRid(Integer rid) {
        return recordMapper.queryCousumeByRid(rid);
    }
}