/**
 * 
 */
package com.tssa.cooperationBusiness.service;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.cooperationBusiness.dao.CooperationDao;
import com.tssa.cooperationBusiness.dao.CooperationProductTypeDao;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessLocationRefence;
import com.tssa.cooperationBusiness.pojo.CooperationProductType;

/**
 * @author gmc
 * 
 */
@Service
public class CooperationService extends
		BaseBusinessService<CooperationBusiness> {

	private static final Logger logger = LoggerFactory
			.getLogger(CooperationService.class);
	@Autowired
	private CooperationDao cooperationDao;
	
	@Autowired
	private CooperationProductTypeDao cooperationProductTypeDao;

	@Transactional
	public boolean cooperationSave(CooperationBusiness cooperation,
			String country, String province, String city, String countryText,
			String provinceText, String cityText, String detailAddress) {
		logger.info("新增商户...........................");
		try {
			if (cooperation != null) {
				CooperationBusinessLocationRefence locationReference = new CooperationBusinessLocationRefence();
				
				if ((country != null && !"".equals(country))
						&& (province != null && !"".equals(province))
						&& (city != null && !"".equals(city))) {
					int countryId = Integer.parseInt(country);
					int provinceId = Integer.parseInt(province);
					int cityId = Integer.parseInt(city);
					locationReference.setCityId(cityId);
					locationReference.setCountryId(countryId);
					locationReference.setProvinceId(provinceId);
				}
				
				locationReference.setCooperation(cooperation);
				locationReference.setCityName(cityText);
				locationReference.setCountryName(countryText);
				locationReference.setProvinceName(provinceText);
				locationReference.setDetailAddress(detailAddress);
				
				cooperation.setLocationReference(locationReference);
				
				cooperationDao.save(cooperation);
			}
			return true;
		} catch (Exception e) {
			logger.error("保存失败", e);
			return false;
		}
	}
	
	/**
	 * 根据行业子分类查询商户列表
	 * @param productTypeId
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public List<CooperationBusiness> getListByProductTypeId(String productTypeId, int pageNumber, int pageSize){
		List<CooperationBusiness> list = null;
		try{
			if(productTypeId != null && !"".equals(productTypeId)){
				DetachedCriteriaTS<CooperationProductType> productTypecriteria = new DetachedCriteriaTS<CooperationProductType>(
						CooperationProductType.class);
				productTypecriteria.add(Restrictions.eq("typeCode", productTypeId));
				CooperationProductType productType = cooperationProductTypeDao
						.find(productTypecriteria);
				
				DetachedCriteriaTS<CooperationBusiness> criteria = new DetachedCriteriaTS<CooperationBusiness>(
						CooperationBusiness.class);
				criteria.add(Restrictions.eq("productTypeId", productType));
				list = cooperationDao.find(criteria, pageNumber, pageSize);
			}else{
				logger.info("productTypeId 为空！查询条件错误");
			}
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return list;
	}
}
