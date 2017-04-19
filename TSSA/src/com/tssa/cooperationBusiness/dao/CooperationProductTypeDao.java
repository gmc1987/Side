/**
 * 
 */
package com.tssa.cooperationBusiness.dao;

import java.util.List;

import com.tssa.common.dao.HibernateEntitryDao;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.cooperationBusiness.pojo.CooperationProductType;

/**
 * @author gmc
 *
 */
public interface CooperationProductTypeDao extends HibernateEntitryDao {

	public List<CooperationProductType> findProductTypes(DetachedCriteriaTS<CooperationProductType> detachedCriteria);
	
}
