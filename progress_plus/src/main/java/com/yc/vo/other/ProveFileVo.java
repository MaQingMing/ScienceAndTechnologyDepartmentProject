package com.yc.vo.other;

import lombok.Data;

/**
 * 文件表Vo
 */

@Data
public class ProveFileVo {

    /** 主键 */
    private Integer pfid ;
    /** 1 申请  2 备案 */
    private Integer status ;

    /** 文件路径 */
    private String path ;
    /** 源文件名 */
    private String fileName ;
    /** 文件类型 */
    private String fileType ;
    /** 文件大小 */
    private String fileSize;
    /** 使用者Id */
    private Integer useid ;
}
