package com.example.fileupload.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileupload.service.UploadService;
import com.example.fileupload.utils.FrameGrabberKit;

@Transactional
@Service("UploadService")
public class UploadServiceImpl implements UploadService{

	
	/**
	 * 视频文件上传
	 */
	@Override
	public Map<String, Object> uploadVideo(MultipartFile file,HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		
		String basePath = request.getScheme() + "://" + request.getServerName()
        + ":" + request.getServerPort()+"/mimi/upload/video/";
		
		Long time = new Date().getTime();
		
		String fileName = file.getOriginalFilename();//文件原始名称
		String suffixName = fileName.substring(fileName.lastIndexOf("."));//从最后一个.开始截取。截取fileName的后缀名
		String newFileName = time+suffixName; //文件新名称
		//设置文件存储路径，可以存放在你想要指定的路径里面
		String rootPath="D:/mimi/"+File.separator+"upload/video/"; //上传视频存放位置
		
		String filePath = rootPath+newFileName;
		File newFile = new File(filePath);
		//判断目标文件所在目录是否存在
		if(!newFile.getParentFile().exists()){
			//如果目标文件所在的目录不存在，则创建父目录
			newFile.getParentFile().mkdirs();
		}
		
		//将内存中的数据写入磁盘
		file.transferTo(newFile);
		//视频上传保存url
		String videoUrl = basePath + newFileName;
		
		//视频封面图处理
		String newImgName = time+".jpg";
		String framefile = rootPath + newImgName;
		String imgUrlSave = basePath+newImgName;//图片最终位置路径
		//视频截取封面图
		String imgUrl=FrameGrabberKit.getVedioImg(videoUrl, framefile, imgUrlSave);  
		
		resultMap.put("videoUrl", videoUrl);
		resultMap.put("imgUrl", imgUrl);
		resultMap.put("returnCode", 200);
		//System.out.println("上传的文件名为："+fileName+",后缀名为："+newFileName);
		return resultMap;
	}

	/**
	 * 图片文件上传
	 */
	@Override
	public Map<String, Object> uploadImage(MultipartFile file, HttpServletRequest request) throws Exception {
		Map<String, Object> resultMap=new HashMap<String, Object>();
		
		String basePath = request.getScheme() + "://" + request.getServerName()
        + ":" + request.getServerPort()+"/mimi/upload/images/";
		
		Long time = new Date().getTime();
		
		String fileName = file.getOriginalFilename();//文件原始名称
		String suffixName = fileName.substring(fileName.lastIndexOf("."));//从最后一个.开始截取。截取fileName的后缀名
		String newFileName = time+suffixName; //文件新名称
		//设置文件存储路径，可以存放在你想要指定的路径里面
		String rootPath="D:/mimi/"+File.separator+"upload/images/"; //上传图片存放位置
		
		String filePath = rootPath+newFileName;
		File newFile = new File(filePath);
		//判断目标文件所在目录是否存在
		if(!newFile.getParentFile().exists()){
			//如果目标文件所在的目录不存在，则创建父目录
			newFile.getParentFile().mkdirs();
		}
		
		//将内存中的数据写入磁盘
		file.transferTo(newFile);
		//图片上传保存url
		String imgUrl = basePath + newFileName;
		
		resultMap.put("imgUrl", imgUrl);
		resultMap.put("returnCode", 200);
		return resultMap;
	}
	
	/**
	 * 项目目录下的图片文件上传
	 */
	@Override
	public Map<String, Object> getImageUrl(HttpServletRequest request) throws Exception {
		Map<String,Object> result = new HashMap<String,Object>();
		//获取图片在项目路径下的地址
		String basePath=ClassUtils.getDefaultClassLoader().getResource("").getPath();
		String oldPath=basePath+"/static/images/animate.png";
		
		Long res =new Date().getTime();
		//设置文件存储路径，可以存放在你想要指定的路径里面
		String rootPath="D:/mimi/"+File.separator+"upload/images/";
		// 新文件名
		String newFileName =res + oldPath.substring(oldPath.lastIndexOf("."));
			//新文件
		File newFile=new File(rootPath+File.separator+newFileName);
		//判断文件目录是否存在
		if(!newFile.getParentFile().exists()){
			//如果目标文件所在的目录不存在，则创建父目录
			newFile.getParentFile().mkdirs();
		}
        
		//-------把图片文件写入磁盘 start ----------------
		FileOutputStream fos = new FileOutputStream(newFile);
		//读取本地文件
		File localFile = new File(oldPath);
		//获取本地文件输入流
		InputStream stream=new FileInputStream(localFile);
		//写入目标文件
		byte[] buffer=new byte[1024*1024];
		int byteRead=0;
		//stream.read(buffer) 每次读到的数据存放在 buffer 数组中
		while((byteRead=stream.read(buffer))!=-1){
			//在 buffer 数组中 取出数据 写到 （输出流）磁盘上
			fos.write(buffer, 0, byteRead);
			fos.flush();
		}
		fos.close();
		stream.close();
		//-------把图片文件写入磁盘 end ----------------
        
		//服务器图片地址
		String baseURL = request.getScheme() + "://" + request.getServerName()
		+ ":" + request.getServerPort()+"/mimi/upload/images/";
		String imgUrl=baseURL+newFileName;

		result.put("imgUrl", imgUrl);
		return result;
	}
}
