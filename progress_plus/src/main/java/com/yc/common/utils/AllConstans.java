package com.yc.common.utils;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;

/**
 * 公共
 */
public class AllConstans {

    public static  final  String STU_PAHT ="D:"+ File.separator + "usr" + File.separator + "local"
            + File.separator + "jar" + File.separator;


    /**
     * 文件上传 文件夹名
     */
    public static final String UPLOAD_NAME = "uploadfile";

    /**
     * 服务器图片映射地址
     */
    public static  final  String STU_UPLOAD_PATH = "file:" + STU_PAHT + UPLOAD_NAME + File.separator;

    /**
     * 服务器数据备份映射地址
     */
    public static  final  String STU_BACKUP_PATH = "file:" + STU_PAHT + "backups" + File.separator;


    /**
     * 服务器数据备份映射地址
     */
    public static  final  String STU_BACKUP_SAVE_PATH = STU_PAHT + "backups";

    /**
     * 上传图片存取地址
     */
    public static  final  String STU_IMG_UPLOAD_PATH = STU_PAHT + UPLOAD_NAME ;


}
