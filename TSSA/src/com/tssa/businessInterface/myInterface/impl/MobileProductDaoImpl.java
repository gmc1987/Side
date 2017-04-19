/**
 * 
 */
package com.tssa.businessInterface.myInterface.impl;

import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.tssa.businessInterface.myInterface.dao.MobileProductDao;
import com.tssa.common.dao.HibernateEntitryDaoImpl;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;
import com.tssa.cooperationBusiness.pojo.CooperationProductType;

/**
 * @author gmc
 *
 */
public class MobileProductDaoImpl extends HibernateEntitryDaoImpl
		implements MobileProductDao {

	@Override
	public List<CooperationBusinessProduct> searchProduct(CooperationBusiness vendorCode, 
			String productName, int pageSize, int pageNumber) {
		// TODO Auto-generated method stub
		
		DetachedCriteriaTS<CooperationBusinessProduct> productCriteria = new DetachedCriteriaTS<CooperationBusinessProduct>(CooperationBusinessProduct.class);
		
		if(vendorCode != null){
			productCriteria.add(Restrictions.eq("cooperId", vendorCode));
		}
		if(productName != null && !"".equals(productName)){
			productCriteria.add(Restrictions.like("productName", productName, MatchMode.ANYWHERE));
		}
		return super.find(productCriteria, (pageNumber - 1) * pageSize, pageSize);
	}

	@Override
	public CooperationBusinessProduct findProductInfo(String productCode,
			String productName, CooperationProductType productType) {
		// TODO Auto-generated method stub
		
		DetachedCriteriaTS<CooperationBusinessProduct> productCriteria = new DetachedCriteriaTS<CooperationBusinessProduct>(CooperationBusinessProduct.class);
		
		if(productCode != null && !"".equals(productCode)){
			productCriteria.add(Restrictions.eq("productCode", productCode));
		}
		if(productName != null && !"".equals(productName)){
			productCriteria.add(Restrictions.eq("productName", productName));
		}
		if(productType != null){
			productCriteria.add(Restrictions.eq("productType", productType));
		}
		return super.find(productCriteria);
	}

}
