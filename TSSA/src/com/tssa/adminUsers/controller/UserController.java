/**
 * 
 */
package com.tssa.adminUsers.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

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
import com.tssa.adminUsers.service.UsersService;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.role.pojo.Role;
import com.tssa.role.service.RoleService;


/**
 * @author gmc
 *
 */
@Controller
@RequestMapping("/user")
@SessionAttributes(value={"user"}, types={Users.class})
public class UserController {

	private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
	private String INIT_PASSWORD = "20131202";
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping("/toLogin")
	public String toLogin(){
		return "login";
	}
	
	@RequestMapping("/toList")
	public String toPage(HttpServletRequest request){
		return "/system/users";
	}
	
	
	@RequestMapping("/getRole")
	@ResponseBody
	public Map<String, Object> getRole(HttpServletRequest request, ModelMap map){
		List<Role> roles = null;
		Map<String, Object> result = new HashMap<String, Object>();
		DetachedCriteriaTS<Role> roleCriteria = new DetachedCriteriaTS<Role>(Role.class);
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
	
	@RequestMapping(value="/login",method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> login(@RequestParam(value="userId") String userCode, 
			@RequestParam(value="pwd")	String password, 
			HttpServletRequest request, HttpSession session, ModelMap map){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		LOG.info("用户开始登录");
		Users user = usersService.login(userCode, password);
		if(user != null){
			result.put("success", true);
			session.setAttribute("user", user);
			return result; //"/main/Index";
		} else {
			result.put("success", false);
		}
		return result;//"login";
	}
	
	@RequestMapping("/toIndex")
	public String toIndexPage(HttpServletRequest request, HttpSession session){
		Users user = (Users)request.getSession().getAttribute("user");
		if(user != null){
			return "/main/Index";
//			return "/main/index_new";
		} else {
			return "login";
		}
	}
	
	@RequestMapping("/loginOut")
	public String loginOut(HttpServletRequest request, HttpSession session){
		Users user = (Users)request.getSession().getAttribute("user");
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		if(user == null && businessCustomer == null){
			//清空session
			session.removeAttribute("user");
			return "login";
		}else if(user != null){
			session.removeAttribute("user");
			return "login";
		} else {
			session.removeAttribute("businessCustomer");
			return "businessCustomerLogin";
		}
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public ModelMap list(HttpServletRequest request, ModelMap map){
		PageMode<Users> pageMode = null;
		String userCode = request.getParameter("userCode");
		String userName = request.getParameter("userName");
		String rid = request.getParameter("role");
		String start = request.getParameter("pageNumber");
		String limit = request.getParameter("pageSize");
		int pageNumber = 1;
		int pageSize = 0;
		
		DetachedCriteriaTS<Users> userCriteria = new DetachedCriteriaTS<Users>(Users.class);
		if(userCode != null && !"".equals(userCode)){
			userCriteria.add(Restrictions.like("userCode", userCode, MatchMode.ANYWHERE));
		}
		if(userName != null && !"".equals(userName)){
			userCriteria.add(Restrictions.like("userName", userName, MatchMode.ANYWHERE));
		}
		if(rid != null && !"".equals(rid)){
			DetachedCriteriaTS<Role> roleCriteria = new DetachedCriteriaTS<Role>(Role.class);
			roleCriteria.add(Restrictions.eq("roleId", rid));
			Role role = roleService.find(roleCriteria);
			if(role != null){
				userCriteria.add(Restrictions.eq("rid", role));
			}
		}
		if(start != null && !"".equals(start) && !"0".equals(start)){
			pageNumber = Integer.parseInt(start);
		}
		if(limit != null && !"".equals(limit)){
			pageSize = Integer.parseInt(limit);
		}else{
			pageSize = 10;
		}
		
		pageMode = usersService.findForList(userCriteria, pageNumber, pageSize);
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter(){
            @Override
            public boolean apply(Object source, String name, Object value) {
                return value == null;
            }
        });
		config.setIgnoreDefaultExcludes(false); 
		config.setExcludes(new String[]{
				"handler",
				"hibernateLazyInitializer",
				"pid",
				"subProductTypes",
				"pareTypes",
				"mid"
		});
		map.clear();
		map.addAttribute("pageMode", JSONObject.fromObject(pageMode, config));
		return map;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public ModelMap save(HttpServletRequest request, ModelMap map){
		
		Users user = (Users)request.getSession().getAttribute("user");
		try{
			String userCode = request.getParameter("userCode");
			String userName = request.getParameter("userName");
			String roleId = request.getParameter("myRoleValue");
			Role role = null;
			Users newUser = new Users();
			if(roleId != null && !"".equals(roleId)){
				DetachedCriteriaTS<Role> roleCriteria = new DetachedCriteriaTS<Role>(Role.class);
				roleCriteria.add(Restrictions.eq("roleId", roleId));
				role = roleService.find(roleCriteria);
			}
			
			newUser.setUserCode(userCode);
			newUser.setUserName(userName);
			newUser.setPassword(INIT_PASSWORD);
			newUser.setRid(role);
			newUser.setCreateUser(user.getUserName());
			newUser.setCreateDate(new Date());
			
			usersService.save(newUser);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "用户新增成功");
			map.clear();
			map.putAll(result);
			return map;
			
		}catch(Exception e){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "用户新增失败");
			map.clear();
			map.putAll(result);
			LOG.error("新增异常", e);
			return map;
		}
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ModelMap update(HttpServletRequest request, ModelMap map){
		
		Users user = (Users)request.getSession().getAttribute("user");
		try{
			String userName = request.getParameter("userName");
			String roleId = request.getParameter("myRoleValue");
			String uid = request.getParameter("uid");
			
			DetachedCriteriaTS<Users> userCriteria = new DetachedCriteriaTS<Users>(Users.class);
			userCriteria.add(Restrictions.eq("uid", uid));
			Users updateUser = usersService.find(userCriteria);
			
			Role role = null;
			
			if(roleId != null && !"".equals(roleId)){
				DetachedCriteriaTS<Role> roleCriteria = new DetachedCriteriaTS<Role>(Role.class);
				roleCriteria.add(Restrictions.eq("roleId", roleId));
				role = roleService.find(roleCriteria);
			}
			
			updateUser.setUserName(userName);
			updateUser.setRid(role);
			updateUser.setCreateUser(user.getUserName());
			
			usersService.update(updateUser);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "用户更新成功");
			map.clear();
			map.putAll(result);
			return map;
			
		}catch(Exception e){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "用户更新失败");
			map.clear();
			map.putAll(result);
			LOG.error("更新异常", e);
			return map;
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ModelMap delete(HttpServletRequest request,  ModelMap map){
		try{
			String uid = request.getParameter("uid");
			
			//查询角色
			DetachedCriteriaTS<Users> userCriteria = new DetachedCriteriaTS<Users>(Users.class);
			userCriteria.add(Restrictions.eq("uid", uid));
			Users user = usersService.find(userCriteria);
			
			usersService.delete(user);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "用户删除成功");
			map.putAll(result);
			return map;
		}catch(Exception e){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "用户删除失败");
			map.putAll(result);
			LOG.error("修改异常", e);
			return map;
		}
	}
}
