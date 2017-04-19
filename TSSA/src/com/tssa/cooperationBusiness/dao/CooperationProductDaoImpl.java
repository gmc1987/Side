/**
 * 
 */
package com.tssa.cooperationBusiness.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hsqldb.lib.StringUtil;

import com.tssa.common.dao.HibernateEntitryDaoImpl;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.TssaBaseException;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;
import com.tssa.myOrders.pojo.Orders;

/**
 * @author gmc
 * @see 产品dao,主要负责增、删、改、查操作
 */
public class CooperationProductDaoImpl extends HibernateEntitryDaoImpl
		implements CooperationProductDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CooperationBusinessProduct> findProducts(DetachedCriteriaTS<CooperationBusinessProduct> detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		return criteria.list();
	}

	@Override
	public List<CooperationBusinessProduct> getAllOrderProducts(String orderId) throws TssaBaseException{
		
		List<CooperationBusinessProduct> products = null;
		Session session = null;
		
		if(StringUtil.isEmpty(orderId)){
			throw new TssaBaseException("参数["+ orderId +"]为空，参数不合法");
		}
		
		try{
			
			session = getSession();
			Orders order = (Orders)session.get(Orders.class, orderId);
			Set<CooperationBusinessProduct> productSet = null;
			
			if(order != null){
				productSet = order.getProducts();
				if(productSet != null && productSet.size() > 0){
					products = new ArrayList<CooperationBusinessProduct>();
					for(CooperationBusinessProduct product : productSet){
						products.add(product);
					}
				}
			}else{
				return null;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		
		return products;
	}
}
