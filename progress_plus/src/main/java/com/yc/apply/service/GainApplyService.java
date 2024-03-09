package com.yc.apply.service;

import com.yc.apply.entity.GainApply;
import com.yc.vo.apply.ApplyVo;
import com.yc.vo.Result;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 科技成果申请;(gain_apply)表服务接口
 *
 * @author : http://www.chiner.pro
 * @date : 2023-10-17
 */
public interface GainApplyService {

    /**
     * 通过ID查询单条数据
     *
     * @param gaid 主键
     * @return 实例对象
     */
    GainApply queryById(Integer gaid);

    /**
     * 新增数据
     *
     * @param gainApply 实例对象
     * @return 实例对象
     */
    GainApply insert(GainApply gainApply);

    /**
     * 更新数据
     *
     * @param gainApply 实例对象
     * @return 实例对象
     */
    GainApply update(GainApply gainApply);

    /**
     * 通过主键删除数据
     *
     * @param gaid 主键
     * @return 是否成功
     */
    boolean deleteById(Integer gaid);

    /**
     * 添加科技成果申请记录
     *
     * @param applyVo 科技成果申请vo
     * @return 1 成功 0 失败
     */
    int addGainApply(ApplyVo applyVo);

    /**
     * 下载 批量导入模版
     * @param response
     * @return
     * @throws IOException
     */
    Result<?> downloadTemplate(HttpServletResponse response) throws IOException;


    /**
     * @Description 认可科技申请审核
     * @Return
     * @Author dm
     * @Date Created in 2023/12/5
     **/
    int pass(String id, String isDept, String uid);

    /**
     * @Description 不认可科技申请审核
     * @Return
     * @Author dm
     * @Date Created in 2023/12/5
     **/
    int reject(String ids, String rejectContent, String fuser, String isDept);

    /**
     * @Description 下载包含不合规的信息的文件
     * @Return
     * @Author dm
     * @Date Created in 2023/12/5
     **/
    void downloadError(HttpServletResponse response) throws UnsupportedEncodingException;

    /**
     * 根据状态 查询条数
     * @param status
     * @return
     */
    Long findGainApplyByStatus(Integer status);

    /**
    *@Description 批量上传申请信息
    *@Return
    *@Author dm
    *@Date Created in 2023/12/19
    **/
    String upload(InputStream inputStream, String fileName, int user);

    /**
     *@Description 查询所有申请所处的年份范围
     *@Return
     *@Author dm
     *@Date Created in 2023/12/22
     **/
    List<String> queryYears();

    /**
    *@Description 导出申请历史记录
    *@Return
    *@Author dm
    *@Date Created in 2023/12/22
    **/
    void exportHis(String years,String types,HttpServletResponse response) throws IOException;
}