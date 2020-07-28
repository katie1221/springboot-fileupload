package com.example.fileupload.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileupload.service.UploadService;

/**
 * 文件上传
 */
@RestController
@RequestMapping("/upload")
public class UploadController {

	@Autowired
	private UploadService uploadService;
	/**
	 * 视频文件上传
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value ="/uploadVideo",method=RequestMethod.POST)
	public Map<String, Object> uploadVideo(MultipartFile file,HttpServletRequest request) throws Exception{
		return uploadService.uploadVideo(file, request);
	}
	
	/**
	 * 图片文件上传
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value ="/uploadImage",method=RequestMethod.POST)
	public Map<String, Object> uploadImage(MultipartFile file,HttpServletRequest request) throws Exception{
		return uploadService.uploadImage(file, request);
	}
}
