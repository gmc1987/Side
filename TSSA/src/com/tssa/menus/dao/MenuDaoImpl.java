/**
 * 
 */
package com.tssa.menus.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;

import com.tssa.common.dao.HibernateEntitryDaoImpl;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.menus.pojo.Menu;

/**
 * @author gmc
 *
 */
public class MenuDaoImpl extends HibernateEntitryDaoImpl implements MenuDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> findMenusInfo(DetachedCriteriaTS<Menu> detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		return criteria.list();
	}
	
	public List<Menu> findMenuBySQL(String sql, List<String> params){
		List<Menu> menus = new ArrayList<Menu>();
		Query query = null;
		query = this.getSession().createSQLQuery(sql);
		
		if(params != null && params.size() > 0 ){
			for(int i = 0; i < params.size(); i++){
				query.setString(i, params.get(i));	
			}
		}
		
		@SuppressWarnings("rawtypes")
		List menuList = query.list();
		
		if(menuList != null && menuList.size() > 0){
			for(int i = 0; i < menuList.size(); i++){
				Object[] obj = (Object[])menuList.get(i);
				Menu menu = new Menu();
				menu.setMid(String.valueOf(obj[0]));
				menu.setMenuName(String.valueOf(obj[1]));
				menu.setMenuUrl(String.valueOf(obj[2]));
				
				if(obj[3]!=null){
					Menu parent = new Menu();
					parent.setMid(String.valueOf(obj[3]));
					menu.setParentIds(parent);
				}
				
				menus.add(menu);
			}
		}
		
		return menus;
	}
}
