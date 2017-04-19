/**
 * 
 */
package com.tssa.cooperationBusiness.service;

import java.util.List;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.TssaBaseException;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.cooperationBusiness.dao.CooperationDao;
import com.tssa.cooperationBusiness.dao.CooperationProductDao;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;

/**
 * @author gmc
 * @param <CooperationBusinessProduct>
 * 
 */
@Service
public class CooperationProductService extends
		BaseBusinessService<CooperationBusinessProduct> {

	@Autowired
	private CooperationProductDao cooperationProductDao;
	
	@Autowired
	private CooperationDao cooperationDao;

	/**
	 * 查询产品记录（不带分页）
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	public List<CooperationBusinessProduct> getProducts(
			DetachedCriteriaTS<CooperationBusinessProduct> detachedCriteria) {
		return cooperationProductDao.findProducts(detachedCriteria);
	}

	/**
	 * 根据关键字查询商户产品信息
	 * 
	 * @param businessId
	 * @param searchKey
	 * @param searchType
	 * @return
	 */
	public List<CooperationBusinessProduct> findCooperationBusinessProductBySearchKey(
			String businessId, String searchKey, String searchType, int pageNumber, int pageSize) {

		List<CooperationBusinessProduct> productList = null;
		CooperationBusiness cooperationInfo = null;
		DetachedCriteriaTS<CooperationBusinessProduct> detachedCriteria = new DetachedCriteriaTS<>(
				CooperationBusinessProduct.class);
		
		if(businessId != null && !"".equals(businessId)){
			cooperationInfo = cooperationDao.get(CooperationBusiness.class, businessId);
		}
		if(cooperationInfo != null){
			detachedCriteria.add(Restrictions.eq("cooperId", cooperationInfo));
		}
		if(searchKey != null && !"".equals(searchKey)){
			detachedCriteria.add(Restrictions.like("productName", searchKey, MatchMode.ANYWHERE));
		}
		if(searchType != null && !"".equals(searchType)){
			detachedCriteria.add(Restrictions.like("specialType", searchType, MatchMode.ANYWHERE));
		}
		
		productList = cooperationProductDao.find(detachedCriteria, pageNumber, pageSize);
		
		return productList;
	}
	
	/**
	 * 根据订单查出所有包含的产品
	 * @param orderId
	 * @return
	 */
	public List<CooperationBusinessProduct> findProductByOrderId(String orderId){
		
		try {
			return cooperationProductDao.getAllOrderProducts(orderId);
		} catch (TssaBaseException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
