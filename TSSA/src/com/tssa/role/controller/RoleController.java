/**
 * 
 */
package com.tssa.role.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.menus.service.MenuService;
import com.tssa.options.service.OptionService;
import com.tssa.role.pojo.Role;
import com.tssa.role.service.RoleService;

/**
 * @author gmc
 *
 */

@Controller
@RequestMapping("/role")
@SessionAttributes(value={"user"}, types={Users.class})
public class RoleController {
	
	private static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private OptionService optionService;
	
	
	
	@RequestMapping("/toList")
	public String toPage(HttpServletRequest request){
		return "/system/roles";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public ModelMap list(HttpServletRequest request,  ModelMap map){
		PageMode<Role> pageMode = null;
		String roleCode = request.getParameter("roleCode");
		String start = request.getParameter("pageNumber");
		String limit = request.getParameter("pageSize");
		int pageNumber = 1;
		int pageSize = 0;
		
		DetachedCriteriaTS<Role> roleCriteria = new DetachedCriteriaTS<Role>(Role.class);
		if(roleCode != null && !"".equals(roleCode)){
			roleCriteria.add(Restrictions.like("roleCode", roleCode, MatchMode.ANYWHERE));
		}
		if(start != null && !"".equals(start) && !"0".equals(start)){
			pageNumber = Integer.parseInt(start);
		}
		if(limit != null && !"".equals(limit)){
			pageSize = Integer.parseInt(limit);
		}else{
			pageSize = 10;
		}
		
		pageMode = roleService.findForList(roleCriteria, pageNumber, pageSize);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false); 
		config.setExcludes(new String[]{
				"pid",
				"rid",
				"parentIds"
		});
		
		if(pageMode != null){
			map.addAttribute("pageMode", JSONObject.fromObject(pageMode, config));
			return map;
		}else{
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "产品新增失败");
			map.putAll(result);
			return map;
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public ModelMap save(HttpServletRequest request,  ModelMap map){
		
		Users user = (Users)request.getSession().getAttribute("user");
		
		try{
			String roleCode = request.getParameter("roleCode");
			String roleName = request.getParameter("roleName");
			String roleValueName = request.getParameter("roleTypeName");
			int roleValue = Integer.parseInt(request.getParameter("myRoleValue"));
			
			Role role = new Role(roleCode, roleName, roleValue, roleValueName, user.getUserCode());
			roleService.save(role);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "角色新增成功");
			map.putAll(result);
			return map;
		}catch(Exception e){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "角色新增失败");
			map.putAll(result);
			LOG.error("新增异常", e);
			return map;
		}
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public ModelMap update(HttpServletRequest request,  ModelMap map){
		try{
			String rid = request.getParameter("rid");
			String roleCode = request.getParameter("roleCode");
			String roleName = request.getParameter("roleName");
			String roleTypeName = request.getParameter("roleTypeName");
			int roleValue = Integer.parseInt(request.getParameter("myRoleValue"));
			
			//查询角色
			DetachedCriteriaTS<Role> roleCriteria = new DetachedCriteriaTS<Role>(Role.class);
			roleCriteria.add(Restrictions.eq("roleId", rid));
			Role role = roleService.find(roleCriteria);
			
			//更新角色属性
			role.setRoleCode(roleCode);
			role.setRoleName(roleName);
			role.setRoleValue(roleValue);
			role.setRoleValueName(roleTypeName);
			roleService.update(role);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "角色更新成功");
			map.putAll(result);
			return map;
		}catch(Exception e){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "角色更新失败");
			map.putAll(result);
			LOG.error("修改异常", e);
			return map;
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ModelMap delete(HttpServletRequest request,  ModelMap map){
		try{
			String rid = request.getParameter("rid");
			
			//查询角色
			DetachedCriteriaTS<Role> roleCriteria = new DetachedCriteriaTS<Role>(Role.class);
			roleCriteria.add(Restrictions.eq("roleId", rid));
			Role role = roleService.find(roleCriteria);
			
			roleService.delete(role);
			roleService.deleteRoleMenuOption(rid);//删除角色权限
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "角色删除成功");
			map.putAll(result);
			return map;
		}catch(Exception e){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "角色删除失败");
			map.putAll(result);
			LOG.error("修改异常", e);
			return map;
		}
	}
}
