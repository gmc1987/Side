/**
 * 
 */
package com.tssa.myOrders.dao;

import java.util.List;

import com.tssa.common.dao.HibernateEntitryDao;
import com.tssa.myOrders.pojo.Orders;

/**
 * @author gmc
 *
 */
public interface IOrderDao extends HibernateEntitryDao {

	public List<Orders> findAllOrders(String checkDate, int status);
	
}
