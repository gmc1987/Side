/**
 * 
 */
package com.tssa.base.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * @author gmc
 * @see 基础控制器，支持上传文件功能
 */

@Controller
@RequestMapping("/base")
public class BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(BaseController.class);
	
	@RequestMapping("/upload")
	@ResponseBody
	public ModelMap saveFile(HttpServletRequest request, ModelMap map){
		String filePath = "";
		try{
			request.setCharacterEncoding("UTF-8");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
	        MultipartFile multiFile = multipartRequest.getFile("formfile");
//	        String viewUrl = request.getRealPath("/") + "WEB-INF/upload/";
	        String viewUrl = request.getServletContext().getContextPath()+ "/upload/";
	        String fileUrl = request.getServletContext().getRealPath("") + "/upload/";
	        
	        File file = new File(fileUrl);
	        if(!file.exists() || !file.isDirectory()){
	        	file.mkdirs();
	        }
	        
	        if(multiFile != null && (multiFile.getOriginalFilename() != null || !"".equals(multiFile.getOriginalFilename()))){
	        	//后缀名
	        	String suffixName = multiFile.getOriginalFilename().substring(multiFile.getOriginalFilename().indexOf("."));
	        	//文件名
	        	String fileName = System.currentTimeMillis() + suffixName;
	        	//文件路径
	        	String uploadFilePath = fileUrl + fileName;
	        	File uploadFile = new File(uploadFilePath);
	 	        multiFile.transferTo(uploadFile);
	 	        
	 	        filePath = viewUrl + fileName;
	 	        
	 	        Map<String, Object> result = new HashMap<String, Object>();
	 			result.put("success", true);
	 			result.put("msg", "上传成功");
	 			result.put("path", filePath);
	 			map.putAll(result);
	 			return map;
	        }else{
	        	Map<String, Object> result = new HashMap<String, Object>();
	 			result.put("success", false);
	 			result.put("msg", "上传失败");
	 			map.putAll(result);
	        	return map;
	        }
		}catch(IOException e){
			Map<String, Object> result = new HashMap<String, Object>();
 			result.put("success", false);
 			result.put("msg", "上传失败");
 			map.putAll(result);
 			LOG.error(e.getMessage(), e);
        	return map;
		}
	}
	
}
