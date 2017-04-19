/**
 * 
 */
package com.tssa.cooperationBusiness.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.cooperationBusiness.dao.CooperationProductTypeDao;
import com.tssa.cooperationBusiness.pojo.CooperationProductType;
import com.tssa.remote.object.ProductTypeVo;

/**
 * @author gmc
 *
 */
@Service
public class CooperationProductTypeService extends BaseBusinessService<CooperationProductType> {

	private static final Logger logger = LoggerFactory.getLogger(CooperationProductTypeService.class);
	
	@Autowired
	private CooperationProductTypeDao cooperationProductTypeDao;
	
	/**
	 * 获取产品类型父级类型
	 * @param detachedCriteria
	 * @return
	 */
	public List<CooperationProductType> getParentTypes(DetachedCriteriaTS<CooperationProductType> detachedCriteria){
		return cooperationProductTypeDao.findProductTypes(detachedCriteria);
	}
	
	public List<CooperationProductType> getChildProductTypes(DetachedCriteriaTS<CooperationProductType> detachedCriteria){
		return cooperationProductTypeDao.findProductTypes(detachedCriteria);
	}
	
	/**
	 * 获取行业产品分类
	 * @param id
	 * @return
	 */
	public List<ProductTypeVo> findProductTypeByParentId(String id){
		List<ProductTypeVo> productTypies = null;
		if(id != null && !"".equals(id)){
			try{
				
				CooperationProductType parentType = cooperationProductTypeDao.get(CooperationProductType.class, id);
				
				if(parentType != null){
					
					DetachedCriteriaTS<CooperationProductType> detachedCriteria = new DetachedCriteriaTS<>(CooperationProductType.class);
					detachedCriteria.add(Restrictions.eq("pareTypes", parentType));
					List<CooperationProductType> list = cooperationProductTypeDao.findProductTypes(detachedCriteria);
					
					if(list != null && list.size() > 0){
						
						productTypies = new ArrayList<ProductTypeVo>();
						
						for(CooperationProductType productType : list){
							
							ProductTypeVo vo = new ProductTypeVo();
							
							vo.setParentId(productType.getPareTypes().getProductTypeId());
							vo.setCreateDate(productType.getCreateDate());
							vo.setOrderid(productType.getOrderid());
							vo.setProductTypeId(productType.getProductTypeId());
							vo.setTypeCode(productType.getTypeCode());
							vo.setTypeName(productType.getTypeName());
							vo.setReturnCode(ProductTypeVo.RETURN_SUCCESS);
							vo.setReturnDesc(ProductTypeVo.DATA_HAVE_FOUND_DESC);
							
							productTypies.add(vo);
							
						}
						
					}
					
				}
				
			}catch(Exception e){
				logger.error(e.getMessage(), e);
				return null;
			}
		}
		
		return productTypies;
	}
	
	public List<ProductTypeVo> findAllProfessionProduct(){
		
		List<ProductTypeVo> productTypies = null;
		
		DetachedCriteriaTS<CooperationProductType> detachedCriteria = new DetachedCriteriaTS<>(CooperationProductType.class);
		detachedCriteria.add(Restrictions.isNull("pareTypes"));
		
		List<CooperationProductType> list = cooperationProductTypeDao.findProductTypes(detachedCriteria);
		if(list != null && list.size() > 0){
			
			productTypies = new ArrayList<ProductTypeVo>();
			
			for(CooperationProductType productType : list){
				
				ProductTypeVo vo = new ProductTypeVo();
				vo.setCreateDate(productType.getCreateDate());
				vo.setOrderid(productType.getOrderid());
				vo.setProductTypeId(productType.getProductTypeId());
				vo.setReturnCode(ProductTypeVo.RETURN_SUCCESS);
				vo.setReturnDesc(ProductTypeVo.DATA_HAVE_FOUND_DESC);
				vo.setTypeCode(productType.getTypeCode());
				vo.setTypeName(productType.getTypeName());
				
				productTypies.add(vo);
				
			}
			
		}
		
		return productTypies;
	}
	
}
