/**
 * 
 */
package com.tssa.cooperationBusiness.dao;

import java.util.List;

import com.tssa.common.dao.HibernateEntitryDao;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.TssaBaseException;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;

/**
 * @author gmc
 *
 */
public interface CooperationProductDao extends HibernateEntitryDao {

	public List<CooperationBusinessProduct> findProducts(DetachedCriteriaTS<CooperationBusinessProduct> detachedCriteria);
	
	public List<CooperationBusinessProduct> getAllOrderProducts(String orderId) throws TssaBaseException;
}
