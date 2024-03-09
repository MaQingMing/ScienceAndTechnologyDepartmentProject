package com.yc.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.yc.common.annotate.CreateTime;
import com.yc.common.annotate.UpdateTime;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @program: science_merit
 * @description: 提供 createBy createTime updateBy updateTime 公共字段
 * @author: 作者 huchaojie
 * @create: 2023-04-06 16:39
 */
@Data
public class BaseEntity implements Serializable {

    /**
     * 创建时间
     */
    @CreateTime
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 创建人
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 修改时间
     */
    @UpdateTime
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 修改人
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;

}
