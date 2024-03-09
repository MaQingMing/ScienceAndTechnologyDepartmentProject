package com.yc.entity.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页的实体类
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageBean<T> {
    // 以下两个属性是界面给的已知参数
    private int pageno = 1; // 当前第几页
    private int pagesize = 5; // 每页多少条
    private String sortby; // 排序列的列名
    private String sort; // 取值为：asc/desc

    // 以下两个属性是数据库表查询得到的结果
    private long total; // 总记录数
    private List<T> dataset;
    // 需要在业务层中计算
    private int totalpages; // 总共多少条
    private int pre; // 上一页的页数
    private int next; // 下一页的页数
}
