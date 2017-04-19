/**
 * 
 */
package com.tssa.role.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;

import com.tssa.common.dao.HibernateEntitryDaoImpl;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.role.pojo.Role;

/**
 * @author gmc
 *
 */
public class RoleDaoImpl extends HibernateEntitryDaoImpl implements RoleDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findRoles(DetachedCriteriaTS<Role> detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		return criteria.list();
	}

	@Override
	public void updateRoleOption(String rid, String mid, String pid) {
		// TODO Auto-generated method stub
		Query query = null;
		query = this.getSession().createSQLQuery("insert into MANAGER_ROLE_MENU_OPTION (rid, mid, pid) values(?,?,?)");
		query.setString(0, rid);
		query.setString(1, mid);
		query.setString(2, pid);
		query.executeUpdate();
	}

	@Override
	public void deleteRoleOption(String rid) {
		// TODO Auto-generated method stub
		Query query = null;
		query = this.getSession().createSQLQuery("delete from MANAGER_ROLE_MENU_OPTION where rid=?");
		query.setString(0, rid);
		query.executeUpdate();
	}

	@Override
	public List<?> findRoleOption(String rid) {
		Query query = null;
		query = this.getSession().createSQLQuery("select rid,mid,pid from MANAGER_ROLE_MENU_OPTION where rid=?");
		query.setString(0, rid);
		return query.list();
	}

}
