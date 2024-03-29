package com.yc.common.utils;

import java.io.File;
import java.util.Calendar;

/**
 * @program: labour_teach
 * @description: 文件处理工具类
 * @author: 作者 huchaojie
 * @create: 2023-03-11 14:27
 */
public class FileUtils {

    /**
     * 如果一个文件夹下面保存超过1000个文件，就会影响文件访问性能，所以上传的文件要分散存储, 这里用年月日作为目录层级 * 获取当前日期字符串
     * * @param separator * @return "/2017/2/20/"
     */
    public static String getNowDateStr() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH) + 1;
        int day = now.get(Calendar.DATE);

        return File.separator + year + File.separator + month + File.separator + day + File.separator;
    }
}
