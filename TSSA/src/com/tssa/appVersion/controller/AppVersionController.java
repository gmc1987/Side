/**
 * 
 */
package com.tssa.appVersion.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.appVersion.pojo.AppVersion;
import com.tssa.appVersion.service.AppVersionService;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.common.util.JsonDateValueProcessor;

/**
 * @author gmc
 *
 */
@Controller
@RequestMapping("/app_version")
@SessionAttributes(value={"user","businessCustomer"}, types={Users.class, BusinessCustomer.class})
public class AppVersionController {
	
	private static final Logger LOG = LoggerFactory.getLogger(AppVersionController.class);

	@Autowired
	private AppVersionService appVersionService;
	
	@RequestMapping("toList")
	public String toList(HttpServletRequest request){
		return "/appVersion/appVersionManager";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public ModelMap versionlist(HttpServletRequest request, ModelMap map){
		
		String platform = request.getParameter("platform");
		String version = request.getParameter("version");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int pageNumber = 1;
		int pageSize = 0;
		
		if (start != null && !"".equals(start) && !"0".equals(start)) {
			pageNumber = Integer.parseInt(start);
		}
		if (limit != null && !"".equals(limit)) {
			pageSize = Integer.parseInt(limit);
		} else {
			pageSize = 10;
		}
		
		DetachedCriteriaTS<AppVersion> appVersionCriteriaTs = new DetachedCriteriaTS<>(AppVersion.class);
		try{
			if(platform != null && !"".equals(platform)){
				
				appVersionCriteriaTs.add(Restrictions.eq("platform", platform));
			}
			
			if(version != null && !"".equals(version)){
				appVersionCriteriaTs.add(Restrictions.eq("version", version));
			}
			PageMode<AppVersion> page = appVersionService.findForList(appVersionCriteriaTs, pageNumber, pageSize);
			
			JsonConfig config = new JsonConfig();
//			config.setIgnoreDefaultExcludes(false); 
//			config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
//			PropertyFilter pf = new PropertyFilter(){
//
//				@Override
//				public boolean apply(Object arg0, String arg1, Object arg2) {
//					if(arg2 == null || String.valueOf(arg2).equals("")){
//						return true;
//					}
//					return false;
//				}
//				
//			};
//			config.setJsonPropertyFilter(pf);
			// 设置javabean中日期转换时的格式
			config.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessor("yyyy-MM-dd"));
			map.clear();
//			map.put("pageMode", JSONObject.fromObject(page, config));
			map.addAttribute("pageMode", JSONObject.fromObject(page, config));
			
			return map;
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
			return null;
		}
	}
	
	/**
	 * 新增版本
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value="save")
	@ResponseBody
	public Map<String, Object> save(AppVersion appVersion, HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			Users user = (Users)request.getSession().getAttribute("user");
			appVersion.setPublisher(user.getUserName());
			appVersion.setIsPublished(0);
			
			//获取上传文件
			appUpload(appVersion, request);
			
			appVersionService.save(appVersion);
			
			result.put("success", true);
			result.put("msg", "版本新增成功。");
//			response.setContentType("text/html;charset=utf-8");
//			response.getWriter().write(JSONObject.fromObject(result).toString());
			
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", "新增版本异常。请联系管理员");
			LOG.error(e.getMessage(), e);
		}
		
		return result;
	}
	
	/**
	 * 修改版本信息
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping(value="update")
	@ResponseBody
	public Map<String, Object> update(AppVersion appVersion, HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try{
			
			AppVersion oldAppVersion = appVersionService.get(AppVersion.class, appVersion.getId());
			boolean uploadFlag = appUpload(appVersion, request);
			
			oldAppVersion.setAppName(appVersion.getAppName());
			oldAppVersion.setIsPublished(appVersion.getIsPublished());
			oldAppVersion.setVersion(appVersion.getVersion());
			
			if(uploadFlag){
				oldAppVersion.setDownLoadPath(appVersion.getDownLoadPath());
			}
			
			appVersionService.update(oldAppVersion);
			
			result.put("success", true);
			result.put("msg", "更新成功");
			
//			response.setContentType("application/json;charset=utf-8");
			
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", "更新失败");
		}
		
		return result;
	}
	
	/**
	 * 删除应用版本
	 * @param appId
	 * @return
	 */
	@RequestMapping("delete")
	@ResponseBody
	public Map<String, Object> delete(String appId){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try{
			
			AppVersion oldAppVersion = appVersionService.get(AppVersion.class, appId);
			
			if(oldAppVersion != null){
				appVersionService.delete(oldAppVersion);
			}
			
			result.put("success", true);
			result.put("msg", "版本删除成功");
			
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", "版本删除失败");
		}
		
		return result;
	}
	
	/**
	 * 删除应用版本
	 * @param appId
	 * @return
	 */
	@RequestMapping("publish")
	@ResponseBody
	public Map<String, Object> publish(String appId){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		try{
			
			AppVersion oldAppVersion = appVersionService.get(AppVersion.class, appId);
			
			if(oldAppVersion != null){
				oldAppVersion.setIsPublished(1);
				oldAppVersion.setPublishDate(new Date());
				appVersionService.update(oldAppVersion);
			}
			
			result.put("success", true);
			result.put("msg", "版本发布成功");
			
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", "版本发布失败");
		}
		
		return result;
	}
	
	/**
	 * 应用版本上传
	 * @param app
	 * @param request
	 * @return
	 */
	private boolean appUpload(AppVersion app, HttpServletRequest request){
		
		boolean result = false;
		
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multiFile = multipartRequest
					.getFile("appFile");
			String fileUrl =request.getServletContext().getRealPath("/") + "appPack/";
			String downLoadUrl = request.getServletContext().getContextPath()+ "/appPack/";
			// 测试使用
//			String fileUrl = "/Users/gmc/Works/eclipse/workspace/TSSA/WebContent/WEB-INF/upload/";
			
			File file = new File(fileUrl);
	        if(!file.exists()){
	        	file.mkdirs();
	        }

			if (multiFile != null
					&& (multiFile.getOriginalFilename() != null && !""
							.equals(multiFile.getOriginalFilename()))) {
				File uploadFile = new File(fileUrl
						+ multiFile.getOriginalFilename());
				multiFile.transferTo(uploadFile);
				
				String filePath = downLoadUrl + multiFile.getOriginalFilename();
				app.setDownLoadPath(filePath);

				result = true;
				
				return result;

			} else {
				return result;
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			return result;
		}
	}
}
