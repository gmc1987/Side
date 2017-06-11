/**
 * 
 */
package com.tssa.menus.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.menus.dao.MenuDao;
import com.tssa.menus.pojo.Menu;

/**
 * @author gmc
 *
 */
@Service
public class MenuService extends BaseBusinessService<Menu> {

	@Autowired
	private MenuDao menuDao;
	
	/**
	 * 查询菜单信息
	 * @param detachedCriteria
	 * @return
	 */
	public List<Menu> findMenusInfo(DetachedCriteriaTS<Menu> detachedCriteria){
		return menuDao.findMenusInfo(detachedCriteria);
	}
	
	/**
	 * 加载菜单树
	 * @param detachedCriteria
	 * @return
	 */
	public List<Map<String, Object>> getMenuTree(String menuId, String roleId){
		List<Map<String, Object>> menus = new ArrayList<Map<String, Object>>();
		List<String> params = new ArrayList<String>();
		params.add(roleId);
		String selSql = "select a.mid, a.menuName, a.menuUrl, a.parentId from SYSTEM_MENU a";
		if("root".equals(menuId)){
			selSql += " left join SYSTEM_MENU c on a.mid=c.parentId";
		}else{
			selSql += " inner join SYSTEM_MENU c on a.parentId=c.mid";
		}
		selSql += " left join MANAGER_ROLE_MENU_OPTION b";
		if("root".equals(menuId)){
			selSql += " on c.mid=b.mid where b.rid=? group by a.mid, a.menuName, a.menuUrl, a.parentId";
		}else{
			params.add(menuId);
			selSql += " on a.mid = b.mid where b.rid=? and a.parentId=? group by a.mid, a.menuName, a.menuUrl";
		}
		selSql += " order by a.menuOrder asc";
		
		
		List<Menu> list = menuDao.findMenuBySQL(selSql, params);
		for(Menu menu : list){
			Map<String, Object> myMenu = new HashMap<String, Object>();
			myMenu.put("id", menu.getMid());
			myMenu.put("text", menu.getMenuName());
			myMenu.put("url", menu.getMenuUrl());
//			myMenu.put("checked", false);
			if(menu.getParentIds() != null){
				myMenu.put("parentId", menu.getParentIds().getMid());
			}else{
				myMenu.put("parentId", "");
			}
			if(menu.getParentIds() != null || (menu.getMenuUrl() != null && !"".equals(menu.getMenuUrl()))){
				myMenu.put("leaf", true);
				myMenu.put("cls", "file");
			}else{
				myMenu.put("leaf", false);
				myMenu.put("cls", "folder");
			}
			menus.add(myMenu);
		}
		return menus;
	}
	
	/**
	 * 加载带有复选框的菜单树
	 * @param detachedCriteria
	 * @return
	 */
	public List<Map<String, Object>> getMenuTreeForCheckBox(DetachedCriteriaTS<Menu> detachedCriteria){
		List<Map<String, Object>> menus = new ArrayList<Map<String, Object>>();
		
		List<Menu> list = menuDao.findMenusInfo(detachedCriteria);
		for(Menu menu : list){
			Map<String, Object> myMenu = new HashMap<String, Object>();
			myMenu.put("id", menu.getMid());
			myMenu.put("text", menu.getMenuName());
			menus.add(myMenu);
		}
		return menus;
	}
}
