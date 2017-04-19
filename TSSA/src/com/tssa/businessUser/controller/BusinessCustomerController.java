/**
 * @author gmc
 * @see control from the page request to service
 */
package com.tssa.businessUser.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.businessUser.service.BusinessCustomerService;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.common.util.TSSAUtil;
import com.tssa.common.util.UtilMD5;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.service.CooperationService;
import com.tssa.role.pojo.Role;
import com.tssa.role.service.RoleService;

@Controller
@RequestMapping("/businessCustomer")
@SessionAttributes(value={"businessCustomer"}, types={BusinessCustomer.class})
public class BusinessCustomerController {
	
	private static final Logger LOG = LoggerFactory.getLogger(BusinessCustomerController.class);
	@Autowired
	private BusinessCustomerService businessCustomerService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private CooperationService cooperationService;
	
	@RequestMapping("/toBusinessCustomerLogin")
	public String toLogin(HttpServletRequest request){
		return "businessCustomerLogin";
	}
	
	@RequestMapping("/toList")
	public String toPage(HttpServletRequest request){
		return "/businessCustomer/businessCustomerList";
	}
	
	@RequestMapping("/getRole")
	@ResponseBody
	public Map<String, Object> getRole(HttpServletRequest request, ModelMap map){
		List<Role> roles = null;
		Map<String, Object> result = new HashMap<String, Object>();
		DetachedCriteriaTS<Role> roleCriteria = new DetachedCriteriaTS<Role>(Role.class);
		roleCriteria.add(Restrictions.like("roleCode", "B", MatchMode.START));
		roles = roleService.getRoleList(roleCriteria);
		//过滤字段对象
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{
				"parentIds",
				"pid",
				"mid"
		});
		result.put("result", JSONArray.fromObject(roles,config));
		return result;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public ModelMap list(HttpServletRequest request, HttpServletResponse response){
		
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		
		ModelMap map = new ModelMap();
		PageMode<BusinessCustomer> pageModel = null;
		String businessCode = request.getParameter("businessCode");
		String businessName = request.getParameter("businessName");
		String businessStatus = request.getParameter("businessStatus");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int pageNumber = 1;
		int pageSize = 0;
		
		if(start != null && !"".equals(start) && !"0".equals(start)){
			pageNumber = Integer.parseInt(start);
		}
		if(limit != null && !"".equals(limit)){
			pageSize = Integer.parseInt(limit);
		}else{
			pageSize = 10;
		}
		
		DetachedCriteriaTS<BusinessCustomer> criteria = new DetachedCriteriaTS<BusinessCustomer>(BusinessCustomer.class);
		
		if(businessCustomer != null){
			criteria.add(Restrictions.eq("vendorCode", businessCustomer.getVendorCode()));
		}
		
		if(businessCode != null && !"".equals(businessCode)){
			criteria.add(Restrictions.eq("businessCustomerCode", businessCode));
		}
		
		if(businessName != null && !"".equals(businessName)){
			criteria.add(Restrictions.like("businessCustomerName", businessName, MatchMode.ANYWHERE));
		}
		
		if(businessStatus != null && !"".equals(businessStatus)){
			criteria.add(Restrictions.eq("status", businessStatus));
		}
		
		pageModel = businessCustomerService.findForList(criteria, pageNumber, pageSize);
		
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{
				"handler",
				"hibernateLazyInitializer",
				"pid",
				"mid"
		});
		
		map.addAttribute("pageMode", JSONObject.fromObject(pageModel, config));
		
		return map;
	}
	
	/**
	 * 管理员新增商户员工
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public ModelMap save(HttpServletRequest request, ModelMap map){
		
		Users user = (Users)request.getSession().getAttribute("user");
		BusinessCustomer sessionbusinessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		
		try{
			Role role = null;
			String businessVendorCode = request.getParameter("businessVendorCode");
			String businessCustomerName = request.getParameter("businessCustomerName");
			String businessCustomerStatus = request.getParameter("userStatus");
			String mobilePhone = request.getParameter("mobilePhone");
			String password = request.getParameter("password");
			String roleId = request.getParameter("myRoleValue");
			String vendorCode = null;
			String businessCustomerCode = null;
			
			if(sessionbusinessCustomer != null){
				businessCustomerCode = sessionbusinessCustomer.getVendorCode() + TSSAUtil.getBusinessCustomerUserCode("04", sessionbusinessCustomer.getVendorCode());
				vendorCode = sessionbusinessCustomer.getVendorCode();
			}else{
				if(businessVendorCode != null && !"".equals(businessVendorCode)){
					CooperationBusiness coopeartionBusiness = cooperationService.get(CooperationBusiness.class, businessVendorCode);
					if(coopeartionBusiness != null){
						businessCustomerCode = TSSAUtil.getBusinessCustomerUserCode("04", coopeartionBusiness.getCooperCode());
						vendorCode = coopeartionBusiness.getCooperCode();
					}
				}
			}
			
			if(roleId != null && !"".equals(roleId)){
				DetachedCriteriaTS<Role> roleCriteria = new DetachedCriteriaTS<Role>(Role.class);
				roleCriteria.add(Restrictions.eq("roleId", roleId));
				role = roleService.find(roleCriteria);
			}
			
			BusinessCustomer businessCustomer = new BusinessCustomer();
			businessCustomer.setBusinessCustomerCode(businessCustomerCode);
			businessCustomer.setBusinessCustomerName(businessCustomerName);
			businessCustomer.setMobilePhone(mobilePhone);
			if(businessCustomerStatus != null && !"".equals(businessCustomerStatus)){
				businessCustomer.setStatus(Integer.parseInt(businessCustomerStatus));
			}else{
				businessCustomer.setStatus(1);
			}
			businessCustomer.setVendorCode(vendorCode);
			businessCustomer.setCreateDate(new Date());
			if(sessionbusinessCustomer != null){
				businessCustomer.setCreateUser(sessionbusinessCustomer.getBusinessCustomerName());
			}else{
				businessCustomer.setCreateUser(user.getUserName());
			}
			businessCustomer.setPassword(UtilMD5.MD5(password));
			
			if(role != null){
				businessCustomer.setRid(role);
			}
			
			businessCustomerService.save(businessCustomer);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "商户用户新增成功");
			map.clear();
			map.putAll(result);
			
		}catch(Exception e){
			LOG.error("新增产品用户异常", e);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "商户用户新增失败");
			map.clear();
			map.putAll(result);
		}
		
		return map;
	}
	
	
	/**
	 * 管理员修改商户员工
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public ModelMap update(HttpServletRequest request, ModelMap map){
		
		try{
			String businessCustomerId = request.getParameter("uid");
			String businessVendorCode = request.getParameter("businessVendorCode");
			String businessCustomerName = request.getParameter("businessCustomerName");
			String businessCustomerStatus = request.getParameter("userStatus");
			String mobilePhone = request.getParameter("mobilePhone");
			String roleId = request.getParameter("myRoleValue");
			Role role = null;
			
			if(businessCustomerId != null && !"".equals(businessCustomerId)){
				BusinessCustomer businessCustomer = businessCustomerService.get(BusinessCustomer.class, businessCustomerId);
				if(businessCustomer != null){
					
					businessCustomer.setVendorCode(businessVendorCode);
					businessCustomer.setBusinessCustomerName(businessCustomerName);
					businessCustomer.setStatus(Integer.parseInt(businessCustomerStatus));
					businessCustomer.setMobilePhone(mobilePhone);
					if(roleId != null && !"".equals(roleId)){
						DetachedCriteriaTS<Role> roleCriteria = new DetachedCriteriaTS<Role>(Role.class);
						roleCriteria.add(Restrictions.eq("roleId", roleId));
						role = roleService.find(roleCriteria);
					}
					businessCustomer.setRid(role);
					
					businessCustomerService.update(businessCustomer);
					
					Map<String, Object> result = new HashMap<String, Object>();
					result.put("success", true);
					result.put("msg", "商户用户修改成功");
					map.clear();
					map.putAll(result);
				}
			}else{
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("success", false);
				result.put("msg", "修改失败，发现无效的用户");
				map.clear();
				map.putAll(result);
			}
			
		}catch(Exception e){
			LOG.error("修改商户员工信息失败，异常信息", e);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "商户用户修改失败");
			map.clear();
			map.putAll(result);
		}
		return map;
	}
	
	/**
	 * 商户用户删除
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ModelMap delete(HttpServletRequest request, ModelMap map){
		
		try{
			String businessCustomerId = request.getParameter("uid");
			
			if(businessCustomerId != null && !"".equals(businessCustomerId)){
				BusinessCustomer businessCustomer = businessCustomerService.get(BusinessCustomer.class, businessCustomerId);
				businessCustomerService.delete(businessCustomer);
				
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("success", true);
				result.put("msg", "商户用户删除成功");
				map.clear();
				map.putAll(result);
				
			}else{
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("success", false);
				result.put("msg", "商户用户修改失败、发现无效用户");
				map.clear();
				map.putAll(result);
			}
			
		}catch(Exception e){
			LOG.error("商户用户删除异常", e);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "商户用户修改失败");
			map.clear();
			map.putAll(result);
		}
		
		return map;
	}
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	@ResponseBody
	public ModelMap login(@RequestParam(value="loginID") String userCode, 
			@RequestParam(value="password")	String password, 
			HttpServletRequest request, ModelMap map){
		LOG.info("用户开始登录");
		BusinessCustomer user = businessCustomerService.businessCustomerLogin(userCode, password);
		if(user != null){
			request.getSession().setAttribute("businessCustomer", user);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "/businessCustomer/toIndex.do?isLogin=0");
			map.clear();
			map.putAll(result);
			return map;
		}else{
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "用户名或密码不正确");
			map.clear();
			map.putAll(result);
			return map;
		}
	}
	
	@RequestMapping("/toIndex")
	public String toIndex(HttpServletRequest request){
		BusinessCustomer user = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		if(user != null){
			return "/main/Index";
		}else{
			return "businessCustomerLogin";
		}
	}
	
}
