/**
 * 
 */
package com.tssa.options.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.menus.pojo.Menu;
import com.tssa.menus.pojo.MenuOptionVO;
import com.tssa.menus.service.MenuService;
import com.tssa.options.pojo.Option;
import com.tssa.options.service.OptionService;
import com.tssa.role.pojo.Role;
import com.tssa.role.service.RoleService;

/**
 * @author gmc
 *
 */
@Controller
@RequestMapping("/options")
@SessionAttributes(value={"user"}, types={Users.class})
public class OptionController {

	private static final Logger LOG = LoggerFactory.getLogger(OptionController.class);

	@Autowired
	private RoleService roleService;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private OptionService optionService;
	
	@RequestMapping("/findMenuOption")
	@ResponseBody
	public Map<String, Object> findMenuOption(HttpServletRequest request){
		
		String rid = request.getParameter("rid");
		Role role = null;
		List<Map<String,String>> roleOptions = null;
		Map<String, Object> options = new HashMap<String, Object>();
		if(rid != null){
			DetachedCriteriaTS<Role> roleCriteria = new DetachedCriteriaTS<Role>(Role.class);
			roleCriteria.add(Restrictions.eq("roleId", rid));
			role = roleService.find(roleCriteria);
		}
		//获取角色菜单权限
		roleOptions = roleService.findRoleOption(rid);
		DetachedCriteriaTS<Option> optionCriteria = new DetachedCriteriaTS<Option>(Option.class);
		List<Option> optionList = optionService.getAllOption(optionCriteria);
		
		DetachedCriteriaTS<Menu> menuCriteria = new DetachedCriteriaTS<Menu>(Menu.class);
		menuCriteria.add(Restrictions.isNotNull("parentIds"));
		List<Map<String, Object>> menuList = menuService.getMenuTreeForCheckBox(menuCriteria);
		
		List<MenuOptionVO> list = new ArrayList<MenuOptionVO>();
		
		for(Map<String, Object> menu : menuList){
			MenuOptionVO vo = new MenuOptionVO();
			String hasSelectMid = null;
			vo.setMid((String)menu.get("id"));
			vo.setMenuName((String)menu.get("text"));
			if(role != null){
				for(Map<String,String> roleMenu : roleOptions){
					if(vo.getMid().equals(roleMenu.get("mid"))){
						vo.setMenuHasSelect(true);
						hasSelectMid = roleMenu.get("mid");
						break;
					}
				}
			}
			for(Option option : optionList){
				if(option.getOptionValue() == 1){
					vo.setFindOption(option);
					if(role != null && vo.getMid().equals(hasSelectMid)){
						for(Map<String,String> roleOption : roleOptions){
							if(vo.getFindOption().getPid().equals(roleOption.get("pid")) 
									&& vo.getMid().equals(roleOption.get("mid"))){
								vo.setFindOptionHasSelect(true);
								break;
							};
						}
					}
				}
				if(option.getOptionValue() == 2){
					vo.setAddOption(option);
					if(role != null && vo.getMid().equals(hasSelectMid)){
						for(Map<String,String> roleOption : roleOptions){
							if(vo.getAddOption().getPid().equals(roleOption.get("pid"))
									&& vo.getMid().equals(roleOption.get("mid"))){
								vo.setAddOptionHasSelect(true);
								break;
							};
						}
					}
				}
				if(option.getOptionValue() == 3){
					vo.setModOption(option);
					if(role != null && vo.getMid().equals(hasSelectMid)){
						for(Map<String,String> roleOption : roleOptions){
							if(vo.getModOption().getPid().equals(roleOption.get("pid"))
									&& vo.getMid().equals(roleOption.get("mid"))){
								vo.setModOptionHasSelect(true);
								break;
							};
						}
					}
				}
				if(option.getOptionValue() == 4){
					vo.setDelOption(option);
					if(role != null && vo.getMid().equals(hasSelectMid)){
						for(Map<String,String> roleOption : roleOptions){
							if(vo.getDelOption().getPid().equals(roleOption.get("pid"))
									&& vo.getMid().equals(roleOption.get("mid"))){
								vo.setDelOptionHasSelect(true);
								break;
							};
						}
					}
				}
				if(option.getOptionValue() == 5){
					vo.setInductionOption(option);
					if(role != null && vo.getMid().equals(hasSelectMid)){
						for(Map<String,String> roleOption : roleOptions){
							if(vo.getInductionOption().getPid().equals(roleOption.get("pid"))
									&& vo.getMid().equals(roleOption.get("mid"))){
								vo.setInductionOptionHasSelect(true);
								break;
							};
						}
					}
				}
				if(option.getOptionValue() == 6){
					vo.setVerificationOption(option);
					if(role != null && vo.getMid().equals(hasSelectMid)){
						for(Map<String,String> roleOption : roleOptions){
							if(vo.getVerificationOption().getPid().equals(roleOption.get("pid"))
									&& vo.getMid().equals(roleOption.get("mid"))){
								vo.setVerificationOptionHasSelect(true);
								break;
							};
						}
					}
				}
			}
			list.add(vo);
		}
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false); 
		config.setExcludes(new String[]{
				"rid",
		});
		options.put("result", JSONArray.fromObject(list, config));
		return options;
	}
	
	@RequestMapping("/setRoleOption")
	@ResponseBody
	public Map<String, Object> setRoleOption(HttpServletRequest request){
		try{
			Map<String,String[]> paramsMap = request.getParameterMap();
			Map<String, String> menus = new HashMap<String, String>();
			Map<String, String> options = new HashMap<String, String>();
			String rid = request.getParameter("rid");
			@SuppressWarnings("unused")
			Role role = null;
			if(rid != null){
				DetachedCriteriaTS<Role> roleCriteria = new DetachedCriteriaTS<Role>(Role.class);
				roleCriteria.add(Restrictions.eq("roleId", rid));
				role = roleService.find(roleCriteria);
				
			}
			Set<String> keys = paramsMap.keySet();
			for(String key : keys){
				if(key.indexOf("menu") != -1){
						menus.put(key, paramsMap.get(key)[0]);
				}
				if(key.indexOf("Option") != -1){
						options.put(key, paramsMap.get(key)[0]);
				}
			}
			//删除角色原来权限
			roleService.deleteRoleMenuOption(rid);
			for(String menuKey : menus.keySet()){
				String mid = menus.get(menuKey);
				String menuIndex = menuKey.substring(menuKey.indexOf("_"), menuKey.lastIndexOf("_"));
				for(String optionKey : options.keySet()){
					String pid = options.get(optionKey);
					String optionIndex = optionKey.substring(optionKey.indexOf("_"), optionKey.lastIndexOf("_"));
					if(menuIndex.equals(optionIndex)){
						//重新设置角色权限
						roleService.setRoleMenuOption(rid, mid, pid);
					}
				}
			}
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", true);
			result.put("msg", "角色权限设置成功");
			return result;
		}catch(Exception e){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "角色权限设置失败");
			LOG.error("设置异常", e);
			return result;
		}
	}
}
