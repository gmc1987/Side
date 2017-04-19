/**
 * 
 */
package com.tssa.adminUsers.service;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.adminUsers.dao.UserDao;
import com.tssa.adminUsers.pojo.Users;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;

/**
 * @author gmc
 * @see 用户服务类，用于基本用户登陆，权限控制，用户查询、修改
 */
@Service
public class UsersService extends BaseBusinessService<Users> {
	
	@Autowired
	private UserDao userDao;


	public Users login(String userId, String password){
		DetachedCriteriaTS<Users> criteriaUser = new DetachedCriteriaTS<Users>(Users.class);
		criteriaUser.add(Restrictions.eq("userCode", userId));
		criteriaUser.add(Restrictions.eq("password", password));
		Users user2 = userDao.find(criteriaUser);
		
		if(user2 != null){
			return user2;
		}else{
			return null;
		}
	}
}
