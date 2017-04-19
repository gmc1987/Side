/**
 * 
 */
package com.tssa.customer.dao;

import java.util.List;

import com.tssa.common.dao.HibernateEntitryDao;
import com.tssa.customer.pojo.Customer;

/**
 * 类名 : CustomerDao.java
 * <p> 客户DAO </p>
 * @author lch
 * Date: 2013-10-6
 * @version 1.01
 */
public interface CustomerDao extends HibernateEntitryDao{

	List<Customer> findCustomerListByLoginname(String loginName);

}
