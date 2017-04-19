/**
 * 
 */
package com.tssa.role.dao;

import java.util.List;

import com.tssa.common.dao.HibernateEntitryDao;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.role.pojo.Role;

/**
 * @author gmc
 * @see 处理角色数据库操作
 */
public interface RoleDao extends HibernateEntitryDao {

	/**
	 * 查询所有角色
	 * @param detachedCriteria
	 * @return
	 */
	public List<Role> findRoles(DetachedCriteriaTS<Role> detachedCriteria);
	
	/**
	 * 更新角色权限
	 * @param rid
	 * @param mid
	 * @param pid
	 */
	public void updateRoleOption(String rid, String mid, String pid);
	
	/**
	 * 删除角色权限
	 * @param rid
	 */
	public void deleteRoleOption(String rid);
	
	/**
	 * 查询角色所有权限
	 * @param rid
	 * @return
	 */
	public List<?> findRoleOption(String rid);
}
