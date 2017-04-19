/**
 * 
 */
package com.tssa.menus.dao;

import java.util.List;

import org.hibernate.Criteria;

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
}
