/**
 * 
 */
package com.tssa.menus.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
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
import com.tssa.base.pojo.ComboboxOrderVO;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.menus.pojo.Menu;
import com.tssa.menus.service.MenuService;
import com.tssa.options.service.OptionService;
import com.tssa.role.service.RoleService;



/**
 * @author gmc
 *
 */

@Controller
@RequestMapping("/menu")
@SessionAttributes(value={"user","businessCustomer"}, types={Users.class, BusinessCustomer.class})
public class MenuController {
	
	private static final Logger LOG = LoggerFactory.getLogger(MenuController.class);
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private OptionService optionService;
	
	@RequestMapping("/toList")
	public String toPage(HttpServletRequest request){
		return "system/menus";
	}
	
	@RequestMapping("/getParentMenu")
	@ResponseBody
	public Map<String, Object> getParentMenu(HttpServletRequest request){
		List<Menu> menus = null;
		Map<String, Object> result = new HashMap<String, Object>();
		DetachedCriteriaTS<Menu> menuCriteria = new DetachedCriteriaTS<Menu>(Menu.class);
		menuCriteria.add(Restrictions.isNull("parentIds"));
		menus = menuService.findMenusInfo(menuCriteria);
		//过滤字段对象
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{
				"parentIds",
				"rid"
		});
		result.put("parentMenus", JSONArray.fromObject(menus,config));
		return result;
	}
	
	@RequestMapping("/getParentMenus")
	@ResponseBody
	public Map<String, Object> getParentMenus(HttpServletRequest request){
		Menu menu = null;
		String mid = request.getParameter("mid");
		
		Map<String, Object> result = new HashMap<String, Object>();
		DetachedCriteriaTS<Menu> menuCriteria = new DetachedCriteriaTS<Menu>(Menu.class);
		menuCriteria.add(Restrictions.eq("mid", mid));
		menu = menuService.find(menuCriteria);
		if(menu.getParentIds() != null){
			result.put("success", true);
			result.put("msg", menu.getParentIds().getMid());
		}else{
			result.put("success", false);
			result.put("msg", "");
		}
		return result;
	}
	
	@RequestMapping("/getMenuOrder")
	@ResponseBody
	public Map<String, Object> getMenuOrder(HttpServletRequest request){
		@SuppressWarnings("rawtypes")
		List menus = null;
		Map<String, Object> result = new HashMap<String, Object>();
		DetachedCriteriaTS<Menu> menuCriteria = new DetachedCriteriaTS<Menu>(Menu.class);
		menuCriteria.addOrder(Order.asc("menuOrder"));
		menuCriteria.setProjection(Projections.groupProperty("menuOrder"));
		menus = menuService.findMenusInfo(menuCriteria);
		List<ComboboxOrderVO> orderVOList = new ArrayList<ComboboxOrderVO>();
		for(int i = 0; i < menus.size(); i++){
			int display = (Integer)menus.get(i);
			int orderValue = (Integer)menus.get(i);
			ComboboxOrderVO orderVO = new ComboboxOrderVO(display, orderValue);
			orderVOList.add(orderVO);
		}
		result.put("menuOrders", JSONArray.fromObject(orderVOList));
		return result;
	}
	
	@RequestMapping("/getMenus")
	@ResponseBody
	public List<Map<String, Object>> getMenus(HttpServletRequest request, ModelMap map){
		
		String parentId = request.getParameter("id");
		Users user = (Users)request.getSession().getAttribute("user");
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		
		DetachedCriteriaTS<Menu> menuCriteria = new DetachedCriteriaTS<Menu>(Menu.class);
		if("root".equals(parentId)){
			menuCriteria.add(Restrictions.isNull("parentIds"));
		}else{
			Menu parentMenu = null;
			DetachedCriteriaTS<Menu> parentCriteria = new DetachedCriteriaTS<Menu>(Menu.class);
			parentCriteria.add(Restrictions.eq("mid", parentId));
			parentMenu = menuService.find(parentCriteria);
			menuCriteria.add(Restrictions.eq("parentIds", parentMenu));
		}
		List<Map<String, Object>> menuTrees = menuService.getMenuTree(menuCriteria);
		List<Map<String,String>> roleOptions = null;
		if(user != null){
			roleOptions = roleService.findRoleOption(user.getRid().getRoleId());
		}
		if(businessCustomer != null){
			roleOptions = roleService.findRoleOption(businessCustomer.getRid().getRoleId());
		}
		List<Map<String, Object>> trees = new ArrayList<Map<String, Object>>();
		
		for(Map<String,Object> treeMenu : menuTrees){
			String mid = (String)treeMenu.get("id");//菜单树ID
			for(Map<String,String> roleTree : roleOptions){
				String treeId = roleTree.get("mid");//角色菜单ID
				DetachedCriteriaTS<Menu> treeCriteria = new DetachedCriteriaTS<Menu>(Menu.class);
				treeCriteria.add(Restrictions.eq("mid", treeId));
				Menu roleMenu = menuService.find(treeCriteria);
				if(mid.equals(roleMenu.getParentIds().getMid())){
					trees.add(treeMenu);
					break;
				}
				if(mid.equals(roleMenu.getMid())){
					trees.add(treeMenu);
					break;
				}
			}
		}
		return trees;
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public ModelMap list(HttpServletRequest request, ModelMap map){
		PageMode<Menu> pageMode = null;
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int pageNumber = 1;
		int pageSize = 0;
		
		DetachedCriteriaTS<Menu> menuCriteria = new DetachedCriteriaTS<Menu>(Menu.class);
		if(start != null && !"".equals(start) && !"0".equals(start)){
			pageNumber = Integer.parseInt(start);
		}
		if(limit != null && !"".equals(limit)){
			pageSize = Integer.parseInt(limit);
		}else{
			pageSize = 10;
		}
		
		pageMode = menuService.findForList(menuCriteria, pageNumber, pageSize);
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter(){
            @Override
            public boolean apply(Object source, String name, Object value) {
                return value == null;
            }
        });
		config.setIgnoreDefaultExcludes(false); 
		config.setExcludes(new String[]{
			"parentIds",
			"rid"
		});
		map.clear();
		map.addAttribute("pageMode", JSONObject.fromObject(pageMode,config));
		return map;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request, ModelMap map){
		Users user = (Users)request.getSession().getAttribute("user");
		try{
			String menuName = request.getParameter("menuName");
			String menuUrl = request.getParameter("menuUrl");
			String menuOrder = request.getParameter("menuOrder");
			String parentId = request.getParameter("parentMenuId");
			
			Menu parentMenu = null;
			if(parentId != null && !"".equals(parentId)){
				DetachedCriteriaTS<Menu> parentCriteria = new DetachedCriteriaTS<Menu>(Menu.class);
				parentCriteria.add(Restrictions.eq("mid", parentId));
				parentMenu = menuService.find(parentCriteria);
				menuOrder = request.getParameter("menuChildOrder");
			}
			
			Menu menu = new Menu();
			menu.setParentIds(parentMenu);
			menu.setMenuName(menuName);
			menu.setMenuUrl(menuUrl);
			menu.setMenuOrder(Integer.parseInt(menuOrder));
			menu.setMenuOrderDisplay(Integer.parseInt(menuOrder));
			menu.setCreateDate(new Date());
			menu.setCreateUser(user.getUserName());
			menuService.save(menu);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "菜单新增成功");
			return result;
		}catch(Exception e){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "菜单新增失败");
			LOG.error("新增异常", e);
			return result;
		}
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request, ModelMap map){
		@SuppressWarnings("unused")
		Users user = (Users)request.getSession().getAttribute("user");
		try{
			String mid = request.getParameter("mid");
			String menuName = request.getParameter("menuName");
			String menuUrl = request.getParameter("menuUrl");
			String menuOrder = request.getParameter("modmenuOrder");
			String parentId = request.getParameter("parentId");
			Menu menu = null;
			DetachedCriteriaTS<Menu> menuCriteria = new DetachedCriteriaTS<Menu>(Menu.class);
			menuCriteria.add(Restrictions.eq("mid", mid));
			menu = menuService.find(menuCriteria);
			
			Menu parentMenu = null;
			if(parentId != null && !"".equals(parentId)){
				DetachedCriteriaTS<Menu> parentCriteria = new DetachedCriteriaTS<Menu>(Menu.class);
				parentCriteria.add(Restrictions.eq("mid", parentId));
				parentMenu = menuService.find(parentCriteria);
			}
			
			menu.setParentIds(parentMenu);
			menu.setMenuName(menuName);
			menu.setMenuUrl(menuUrl);
			menu.setMenuOrder(Integer.parseInt(menuOrder));
			menu.setMenuOrderDisplay(Integer.parseInt(menuOrder));
			menuService.update(menu);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "菜单修改成功");
			return result;
		}catch(Exception e){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "菜单修改失败");
			LOG.error("修改异常", e);
			return result;
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, ModelMap map){
		@SuppressWarnings("unused")
		Users user = (Users)request.getSession().getAttribute("user");
		try{
			String mid = request.getParameter("mid");
			Menu menu = null;
			DetachedCriteriaTS<Menu> menuCriteria = new DetachedCriteriaTS<Menu>(Menu.class);
			menuCriteria.add(Restrictions.eq("mid", mid));
			menu = menuService.find(menuCriteria);
			
			if(menu != null){
				menuService.delete(menu);
			}
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "菜单删除成功");
			return result;
			
		}catch(Exception e){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "菜单删除失败");
			LOG.error("删除异常", e);
			return result;
		}
		
	}
	
}
