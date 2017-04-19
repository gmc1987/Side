/**
 * 
 */
package com.tssa.role.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.role.dao.RoleDao;
import com.tssa.role.pojo.Role;

/**
 * @author gmc
 *
 */
@Service
public class RoleService extends BaseBusinessService<Role> {

	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 查询角色列表
	 * @param detachedCriteria
	 * @return
	 */
	public List<Role> getRoleList(DetachedCriteriaTS<Role> detachedCriteria){
		return roleDao.findRoles(detachedCriteria);
	}
	
	/**
	 * 更新角色菜单权限
	 * @param rid
	 * @param mid
	 * @param pid
	 */
	public void setRoleMenuOption(String rid, String mid, String pid){
		roleDao.updateRoleOption(rid, mid, pid);
	}
	
	/**
	 * 删除角色菜单权限
	 * @param rid
	 */
	public void deleteRoleMenuOption(String rid){
		roleDao.deleteRoleOption(rid);
	}
	
	/**
	 * 查询角色所有权限
	 * @param rid
	 * @return
	 */
	public List<Map<String, String>> findRoleOption(String rid){
		@SuppressWarnings("rawtypes")
		List result = roleDao.findRoleOption(rid);
		List<Map<String, String>> roleOptionList = new ArrayList<Map<String, String>>();
		if(result != null && result.size() > 0){
			for(int i = 0; i < result.size(); i++){
				Map<String,String> map = new HashMap<String,String>();
				Object[] obj = (Object[])result.get(i);
				map.put("rid", String.valueOf(obj[0]));
				map.put("mid", String.valueOf(obj[1]));
				map.put("pid", String.valueOf(obj[2]));
				roleOptionList.add(map);
			}
		}
		return roleOptionList;
	}
}
