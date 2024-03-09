package com.yc.exception;

/**
 * excel 自定义异常
 * @author Administrator
 * @PACKAGE_NAME: com.yc.exception
 * @NAME: MyExcelException
 * @USER: Administrator
 * @DATE: 2023/2/13
 * @MONTH_NAME_SHORT: 2月
 * @MONTH_NAME_FULL: 二月
 **/
public class MyExcelException extends  RuntimeException{

    public MyExcelException(){

    }
    public MyExcelException(String s){
        super(s);
    }

}
