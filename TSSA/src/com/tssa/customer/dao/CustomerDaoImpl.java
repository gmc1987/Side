/**
 * 
 */
package com.tssa.customer.dao;


import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.tssa.common.dao.HibernateEntitryDaoImpl;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.customer.pojo.Customer;


/**
 * 类名 : CustomerDaoImpl.java
 * <p> 客户Hibernate实现类 </p>
 * @author lch
 * Date: 2013-10-6
 * @version 1.01
 */
public class CustomerDaoImpl extends HibernateEntitryDaoImpl implements CustomerDao {

	
	/**
	 * <p>通过登录名称查询客户信息</p>
	 * @param loginName
	 * @return
	 */
	public List<Customer> findCustomerListByLoginname(String loginName){
		DetachedCriteriaTS<Customer> detachedCriteria = new DetachedCriteriaTS<Customer>(Customer.class);
		detachedCriteria.add(Restrictions.or(Restrictions.eq("uid", loginName),
				Restrictions.eq("phone", loginName)));
		return super.find(detachedCriteria, Integer.MAX_VALUE);
	}
	
}
