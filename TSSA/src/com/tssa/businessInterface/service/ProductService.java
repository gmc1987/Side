/**
 * 
 */
package com.tssa.businessInterface.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.businessInterface.myInterface.dao.MobileProductDao;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.cooperationBusiness.dao.CooperationDao;
import com.tssa.cooperationBusiness.dao.CooperationProductTypeDao;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;
import com.tssa.cooperationBusiness.pojo.CooperationProductType;
import com.tssa.remote.object.ProductVo;

/**
 * @author gmc
 * @see 产品查询服务类，用于查询产品列表或者产品信息
 */
@Service
public class ProductService extends
		BaseBusinessService<CooperationBusinessProduct> {

	@Autowired
	private MobileProductDao mobileProductDao;

	@Autowired
	private CooperationDao cooperationDao;

	@Autowired
	private CooperationProductTypeDao cooperationProductTypeDao;

	/**
	 * @see <P>
	 *      产品列表通用查询方法
	 *      </P>
	 * @param vendorCode
	 *            商户ID
	 * @param vendorName
	 *            商户名称
	 * @param productCode
	 *            产品编码
	 * @param productName
	 *            产品名称
	 * @param productTypeId
	 *            产品类型ID
	 * @param pageSize
	 *            页大小
	 * @param pageNumber
	 *            页码
	 * @return
	 */
	public List<ProductVo> findProduct(CooperationBusiness vendor,
			String productName, int pageSize, int pageNumber) {

		List<ProductVo> list = new ArrayList<ProductVo>();

		/* 根据查询条件查询产品信息 */
		List<CooperationBusinessProduct> productList = mobileProductDao
				.searchProduct(vendor, productName, pageSize, pageNumber);

		if (productList != null && productList.size() > 0) {
			for (CooperationBusinessProduct products : productList) {
				// 转换到手机实体
				ProductVo vo = new ProductVo(products.getBusinessProductId(),
						products.getProductCode(), products.getProductName(),
						products.getImage(), products.getProductType()
								.getProductTypeId(), products.getProductType()
								.getTypeName(), products.getCooperId()
								.getCooperId(), products.getCooperId()
								.getCooperName(), products.getTotalNum(),
						products.getResidualNum(), String.valueOf(products
								.getSalePrice()), String.valueOf(products
								.getSinglePrice()), products.getDescription(),
						products.getSpecialType());
				list.add(vo);
			}
		} else {
			ProductVo vo = new ProductVo();
			vo.setReturnCode(ProductVo.DATA_NOT_FOUND);
			vo.setReturnDesc(ProductVo.DATA_NOT_FOUND_DESC);
			list.add(vo);
		}
		return list;
	}

	/**
	 * @see <P>
	 *      查询产品详细信息
	 *      </P>
	 * @param productCode
	 *            产品编码
	 * @param productName
	 *            产品名称
	 * @param productType
	 *            产品类型
	 * @return
	 */
	public ProductVo findProductInfo(String productCode, String productName,
			String productTypeId) {

		DetachedCriteriaTS<CooperationProductType> typeCriteria = new DetachedCriteriaTS<CooperationProductType>(
				CooperationProductType.class);

		/* 查询产品类型 */
		if (productTypeId != null && !"".equals(productTypeId)) {
			typeCriteria.add(Restrictions.eq("productTypeId", productTypeId));
		}
		CooperationProductType productType = cooperationProductTypeDao
				.find(typeCriteria);

		// 查询产品详细信息
		CooperationBusinessProduct productInfo = mobileProductDao
				.findProductInfo(productCode, productName, productType);
		if (productInfo != null) {
			ProductVo vo = new ProductVo(productInfo.getBusinessProductId(),
					productInfo.getProductCode(), productInfo.getProductName(),
					productInfo.getImage(), productInfo.getProductType()
							.getProductTypeId(), productInfo.getProductType()
							.getTypeName(), productInfo.getCooperId()
							.getCooperId(), productInfo.getCooperId()
							.getCooperName(), productInfo.getTotalNum(),
					productInfo.getResidualNum(), String.valueOf(productInfo
							.getSalePrice()), String.valueOf(productInfo
							.getSinglePrice()), productInfo.getDescription(),
					productInfo.getSpecialType());
			vo.setReturnCode(ProductVo.DATA_HAVE_FOUND);
			vo.setReturnDesc(ProductVo.DATA_HAVE_FOUND_DESC);
			return vo;
		} else {
			ProductVo vo = new ProductVo();
			vo.setReturnCode(ProductVo.DATA_NOT_FOUND);
			vo.setReturnDesc(ProductVo.DATA_NOT_FOUND_DESC);
			return vo;
		}
	}

}
