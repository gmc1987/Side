/**
 * 
 */
package com.tssa.commissionSetting.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.commissionSetting.pojo.BusinessCommissionSetting;
import com.tssa.commissionSetting.service.BusinessCommissionSettingService;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.service.CooperationService;

/**
 * @author gmc
 *
 */
@Controller
@RequestMapping("/businessCommissionSetting")
public class BusinessCommissionSettingController {
	
	private static final Logger LOG = LoggerFactory.getLogger(BusinessCommissionSettingController.class);
	
	@Autowired
	private BusinessCommissionSettingService businessCommissionSettingService;
	
	@Autowired
	private CooperationService cooperationService;
	
	@RequestMapping("toList")
	public String toPage(HttpServletRequest request){
		return "/system/businessCommissionSetting";
	}
	
	/**
	 * 商户佣金设置列表
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("list")
	@ResponseBody
	public ModelMap list(HttpServletRequest request, ModelMap map){
		PageMode<BusinessCommissionSetting> pageMode = null;
		
		String cooperationCode = request.getParameter("cooperationCode");
		String cooperationName = request.getParameter("cooperationName");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int pageNumber = 1;
		int pageSize = 0;
		
		DetachedCriteriaTS<BusinessCommissionSetting> commissionCriteria = new DetachedCriteriaTS<BusinessCommissionSetting>(BusinessCommissionSetting.class);
		
		//如果查询条件存在商户编码或者商户名称、生成对应查询条件逻辑
		DetachedCriteriaTS<CooperationBusiness> cooperationCriteria = new DetachedCriteriaTS<>(CooperationBusiness.class);
		if(cooperationCode != null && !"".equals(cooperationCode)){
			cooperationCriteria.add(Restrictions.eq("cooperCode", cooperationCode));
		}
		if(cooperationName != null && !"".equals(cooperationName)){
			cooperationCriteria.add(Restrictions.eq("cooperName", cooperationName));
		}
		
		CooperationBusiness cooperation = cooperationService.find(cooperationCriteria);
		if(cooperation != null){
			commissionCriteria.add(Restrictions.eq("cooperationBusiness", cooperation));
		}
		if(start != null && !"".equals(start) && !"0".equals(start)){
			pageNumber = Integer.parseInt(start);
		}
		if(limit != null && !"".equals(limit)){
			pageSize = Integer.parseInt(limit);
		}else{
			pageSize = 10;
		}
		pageMode = businessCommissionSettingService.findForList(commissionCriteria, pageNumber, pageSize);
		
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{
				"productTypeId"
		});
		
		map.clear();
		
		if(pageMode != null){
			map.addAttribute("pageMode", JSONObject.fromObject(pageMode, config));
		}
		
		return map;
	}
	
	
	@RequestMapping("save")
	@ResponseBody
	public ModelMap commissionSave(HttpServletRequest request, ModelMap map){
		
		Users user = (Users)request.getSession().getAttribute("user");
		
		String cooperationId = request.getParameter("cooperId");
		String commissionPercentage = request.getParameter("percentage");
		String cycle = request.getParameter("settingCycle");
		String utin = request.getParameter("cycleUtil");
		CooperationBusiness cooperation = null;
		
		try{
			if(cooperationId != null && !"".equals(cooperationId)){
				cooperation = cooperationService.get(CooperationBusiness.class, cooperationId);
			}
			
			BusinessCommissionSetting setting = new BusinessCommissionSetting();
			if(cooperation != null){
				setting.setCooperationBusiness(cooperation);
			}
			setting.setCommissionPercentage(Double.parseDouble(commissionPercentage));
			setting.setCycle(Integer.parseInt(cycle));
			setting.setCreateDate(new Date());
			setting.setCreateUser(user.getUserName());
			setting.setCycleUnit(Integer.parseInt(utin));
			businessCommissionSettingService.save(setting);
			
			map.clear();
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "佣金设置新增成功");
			map.putAll(result);
			return map;
			
		}catch(Exception e){
			LOG.error("系统异常", e);
			map.clear();
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "佣金设置新增失败");
			map.putAll(result);
			return map;
		}
	}
	
	@RequestMapping("update")
	@ResponseBody
	public ModelMap update(HttpServletRequest request, ModelMap map){
		
		String settingId = request.getParameter("settingId");
		String commissionPercentage = request.getParameter("percentage");
		String cycle = request.getParameter("settingCycle");
		String utin = request.getParameter("cycleUtil");
		BusinessCommissionSetting setting = null;
		
		try{
			if(settingId != null && !"".equals(settingId)){
				setting = businessCommissionSettingService.get(BusinessCommissionSetting.class, settingId);
			}
			
			setting.setCommissionPercentage(Double.parseDouble(commissionPercentage));
			setting.setCycle(Integer.parseInt(cycle));
			setting.setCycleUnit(Integer.parseInt(utin));
			businessCommissionSettingService.update(setting);
			
			map.clear();
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "佣金设置修改成功");
			map.putAll(result);
			return map;
			
		}catch(Exception e){
			map.clear();
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "佣金设置修改失败");
			map.putAll(result);
			return map;
		}
	}
	
	@RequestMapping("delete")
	@ResponseBody
	public ModelMap delete(HttpServletRequest request, ModelMap map){
		
		String settingId = request.getParameter("settingId");
		BusinessCommissionSetting setting = null;
		
		try{
			if(settingId != null && !"".equals(settingId)){
				setting = businessCommissionSettingService.get(BusinessCommissionSetting.class, settingId);
			}
			businessCommissionSettingService.delete(setting);
			map.clear();
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "佣金设置删除成功");
			map.putAll(result);
			return map;
		}catch(Exception e){
			map.clear();
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "佣金设置修改失败");
			map.putAll(result);
			return map;
		}
	}
	
	/**
	 * 获取佣金设置参数
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("settingInfo")
	@ResponseBody
	public ModelMap getCommissionSettingInfo(HttpServletRequest request, ModelMap map){
		
		String cooperationId = request.getParameter("cooperId");
		CooperationBusiness cooperation = null;
		BusinessCommissionSetting businessCommissionSetting = null;
		
		if(cooperationId != null && !"".equals(cooperationId)){
			cooperation = cooperationService.get(CooperationBusiness.class, cooperationId);
		}
		
		if(cooperation != null){
			DetachedCriteriaTS<BusinessCommissionSetting> commissionCriteria = new DetachedCriteriaTS<BusinessCommissionSetting>(BusinessCommissionSetting.class);
			commissionCriteria.add(Restrictions.eq("cooperationBusiness", cooperation));
			businessCommissionSetting = businessCommissionSettingService.find(commissionCriteria);
		}
		
		if(businessCommissionSetting != null){
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[]{
					"cooperationBusiness"
			});
			map.clear();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("setting", JSONObject.fromObject(businessCommissionSetting, config));
			map.putAll(result);
		}else{
			map.clear();
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("setting", null);
			map.putAll(result);
		}
		
		return map;
		
	}
	
}
