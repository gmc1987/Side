/**
 * 
 */
package com.tssa.myOrders.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.tssa.common.dao.HibernateEntitryDaoImpl;
import com.tssa.myOrders.pojo.Orders;

/**
 * @author gmc
 *
 */
public class OrderDaoImpl extends HibernateEntitryDaoImpl implements
		IOrderDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Orders> findAllOrders(String checkDate, int status) {
		// TODO Auto-generated method stub
		
		List<Orders> list = null;
		SQLQuery sqlQuery = null;
		Session session = null;
		
		StringBuilder sql = new StringBuilder("select ");
		sql.append("{o.*}");
		sql.append(" from CUST_ORDER o LEFT JOIN BUSINESS_COOPERATIONBUSINESS b ON o.vendorCode = b.cooperCode");
		sql.append(" WHERE appointmentTime <= ? AND orderStatus < ?");
		try{
			session = getSession();
			sqlQuery = session.createSQLQuery(sql.toString());
			sqlQuery.setParameter(0, checkDate);
			sqlQuery.setParameter(1, status);
			
			sqlQuery.addEntity("o", Orders.class);
			
			list = sqlQuery.list();
		} catch(Exception e){
			e.printStackTrace();
			list = null;
		} finally {
			session.close();
		}
		
		return list;
	}

}
