/**
 * 
 */
package com.tssa.menus.dao;

import java.util.List;

import com.tssa.common.dao.HibernateEntitryDao;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.menus.pojo.Menu;

/**
 * @author gmc
 * @see 菜单操作数据库接口
 */
public interface MenuDao extends HibernateEntitryDao {

	/**
	 * 查询菜单信息，提供给前台下拉列表使用
	 * @param detachedCriteria
	 * @return
	 */
	public List<Menu> findMenusInfo(DetachedCriteriaTS<Menu> detachedCriteria);
	
	/**
	 * 查询菜单信息
	 * @param sql
	 * @return
	 */
	public List<Menu> findMenuBySQL(String sql, List<String> params);
	
}
