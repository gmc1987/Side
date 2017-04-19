/**
 * 
 */
package com.tssa.businessInterface.myInterface.dao;

import java.util.List;

import com.tssa.common.dao.HibernateEntitryDao;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;
import com.tssa.cooperationBusiness.pojo.CooperationProductType;

/**
 * @author gmc
 * 
 */
public interface MobileProductDao extends HibernateEntitryDao {

	/**
	 * 产品搜索
	 * 
	 * @param vendorCode
	 *            商户对象（包含商户编码与商户名称）
	 * @param productCode
	 *            产品编码
	 * @param productName
	 *            产品名称
	 * @param productType
	 *            产品类型
	 * @return
	 */
	public List<CooperationBusinessProduct> searchProduct(
			CooperationBusiness vendorCode, String productName, int pageSize,
			int pageNumber);

	/**
	 * 产品详细信息查询
	 * 
	 * @param productCode
	 *            产品编码
	 * @param productName
	 *            产品名称
	 * @param productType
	 *            产品类型
	 * @return
	 */
	public CooperationBusinessProduct findProductInfo(String productCode,
			String productName, CooperationProductType productType);
}
