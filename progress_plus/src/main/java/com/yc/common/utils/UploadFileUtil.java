package com.yc.common.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * 文件上传工具类
 */
@Slf4j
public class UploadFileUtil {


	/**
	 * 
	 * @param request   http请求
	 * @param files		要保存的文件
	 * @return
	*/
	public static Map<String, UploadFile> uploadFiles(HttpServletRequest request, List<MultipartFile> files) {
		Map<String, UploadFile> map = new HashMap<String, UploadFile>();
		if (files != null && files.size() > 0) {
			//图片保存的服务器位置
			File picFile=new File(AllConstans.STU_PAHT, AllConstans.UPLOAD_NAME);
			//构建图片服务器的 http url地址  http://localhost:8080/uploadPdfs
			String picBasePath = AllConstans.UPLOAD_NAME;
			for (MultipartFile multipartFile : files) {
				try {
					String originalFilename = multipartFile.getOriginalFilename();
					
					if(  multipartFile.isEmpty() ){
						continue;
					}
					// 生成新文件名,与时间相关
					String newfilename = getUniqueFileName() + originalFilename.substring(originalFilename.lastIndexOf("."));
					//D:\\Tomcat\\apache-tomcat-7.0.30-windows-x86\\apache-tomcat-7.0.30\\webapps\\uploadPdfs\\/2017/2/26
					String saveDir=picFile.getAbsolutePath() + getNowDateStr();
					//D:\\Tomcat\\apache-tomcat-7.0.30-windows-x86\\apache-tomcat-7.0.30\\webapps\\uploadPdfs\\/2017/2/26/xxx.pdf
					String newFilePath=saveDir+newfilename;
					//http://localhost:8080/uploadPdfs/2017/2/26//xxx.pdf
					String newFileUrl= picBasePath+getNowDateStr()+newfilename;
					
					File saveDirFile=new File( saveDir);
					if (!saveDirFile.exists()) {
						saveDirFile.mkdirs();
					}
					File imageFile = new File(newFilePath);
					UploadFile uploadFile = new UploadFile();
					uploadFile.contentType = multipartFile.getContentType();
					uploadFile.size = multipartFile.getSize();
					uploadFile.originalFileName = originalFilename;
					uploadFile.newFileName = newfilename;
					uploadFile.newFilePath=newFilePath;
					uploadFile.newFileUrl=newFileUrl;
					map.put(originalFilename, uploadFile);
					multipartFile.transferTo(imageFile);
				} catch (Exception e) {
					log.error(" uploadFiles Exception ",e);
				}
			}
		}
		return map;
	}
	

	/**
	 * 如果一个文件夹下面保存超过1000个文件，就会影响文件访问性能，所以上传的文件要分散存储, 这里用年月日作为目录层级 * 获取当前日期字符串
	 * * @param separator * @return "2017/2/20"
9	 */
	public static String getNowDateStr() {
		Calendar now = Calendar.getInstance();
		int year = now.get(Calendar.YEAR);
		int month = now.get(Calendar.MONTH) + 1;
		int day = now.get(Calendar.DATE);
		return File.separator+year + File.separator + month + File.separator + day+File.separator;
	}

	/**
	 *  生成唯一的文件名
	 * @return
	 */
	public static String getUniqueFileName() {
		String str = UUID.randomUUID().toString();
		return str.replace("-", "");
	}

	@Data
	public static class UploadFile {
		String originalFileName;	//原始文件名
		String newFileName;			//新文件名
		String newFilePath;			//新文件在服务器的保存路径
		String newFileUrl;			//新文件的访问路径	
		String OutImgPath;
		long size;					//文件大小
		String contentType;			//文件类型
	}
}
