package com.yc.vo.apply;

import lombok.Data;

/**
 * @author 邓弈2102
 * @version 1.0
 * @description: TODO
 * @date 2023/11/23
 */
@Data
public class SearchApplyVo {
    //开始页数
    private Long begin = 1l;
    //页面大小
    private Long size = 10l;
    //状态
    private String status = "0";
    //查询等级
    private String level;
    //共同点
    private String commonLike;
    //领导id
    private String userId;
    //身份
    private String identifier;
    //所属部门
    private String dept;
    //是否是查询历史
    private String query;
    //是否根据学院查询
    private String college;
    //是部门还是学院
    private String isDept;
    //部门或者学院名称
    private String deptName;
}
