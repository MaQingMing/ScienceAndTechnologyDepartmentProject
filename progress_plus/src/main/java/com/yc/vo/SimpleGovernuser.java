package com.yc.vo;

import lombok.Data;

/**
 * @author 邓弈2102
 * @version 1.0
 * @description: TODO
 * @date 2023/11/12
 */
@Data
public class SimpleGovernuser {
    //申请者id
    private String id;
    //申请者姓名
    private String username;
    //申请人所处学院或者部门
    private String dept;
    //部门还是学院
    private Integer status;
}
