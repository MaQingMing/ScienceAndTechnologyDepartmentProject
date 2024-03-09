package com.yc.common.utils;

import java.io.File;

/**
 * 系统常量类
 * @author yingzhang
 *
 */
public class YcConstants {

	/**
	 * SESSION的键用于存报名成功的学生信息
	 */
	public static final String ENROLLSTUDENT="enrollStudent";
	
	
	/**
	 * tomcat安装路径   :
	 *  /Applications/apache-tomcat-8.0.30/    System.getenv("CATALINA_HOME")   "/usr/tomcat/tomcat8"
	 */
	public static final String CATALINA_HOME = System.getenv("CATALINA_HOME") ;
	


	
	/**
	 * tomcat目录下的webapps路径 <br />
	 * 格式: $CATALINA_HOME/webapps <br />
	 * 
	 * @author yingzhang
	 */
	public static final String WEBAPPS = CATALINA_HOME + File.separator + "webapps" + File.separator;

	/**
	 * 因为安装了两个 tomcat  所以一直获取当前tomcat环境的变量(tw_web/file:/usr/tomcat/tw_web/tomcat8.5/webapps/uploadpic/)  改成  写死
	 */
	public static final String UPLOAD_PATH_MAC = "file:" + WEBAPPS;
}
