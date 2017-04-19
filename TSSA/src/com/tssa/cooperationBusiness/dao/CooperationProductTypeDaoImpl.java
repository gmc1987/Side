/**
 * 
 */
package com.tssa.cooperationBusiness.dao;

import java.util.List;

import org.hibernate.Criteria;

import com.tssa.common.dao.HibernateEntitryDaoImpl;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.cooperationBusiness.pojo.CooperationProductType;

/**
 * @author gmc
 * @see 主要用于产品类型查询
 */
public class CooperationProductTypeDaoImpl extends HibernateEntitryDaoImpl implements CooperationProductTypeDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<CooperationProductType> findProductTypes(DetachedCriteriaTS<CooperationProductType> detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		return criteria.list();
	}
	

	

}
