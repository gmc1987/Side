/**
 * 
 */
package com.tssa.remote.vendor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.util.JsonDateValueProcessor;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.pojo.CooperationProductType;
import com.tssa.cooperationBusiness.service.CooperationProductTypeService;
import com.tssa.cooperationBusiness.service.CooperationService;
import com.tssa.remote.common.BaseController;
import com.tssa.remote.object.VendorVo;

/**
 * @author gmc
 * @see 商户查询接口,提供查询商户信息，商户列表功能
 */

@Controller
@RequestMapping("/vendor")
public class BusinessVendorController extends BaseController{

	private static final Logger logger = LoggerFactory
			.getLogger(BusinessVendorController.class);
	
	@Autowired
	private CooperationService cooperationService;
	
	@Autowired
	private CooperationProductTypeService cooperationProductTypeService;
	
	@RequestMapping("/businessList")
	public void getBusinessList(String requestData, HttpServletRequest request, HttpServletResponse response){
		
		try{
			List<CooperationBusiness> list = null;
			List<VendorVo> vendorlist = null;
//			String jsonData = request.getParameter(KEY_REQUEST_DATA);
//			if(jsonData == null || "".equals(jsonData)){
//				jsonData = (String)request.getAttribute(KEY_REQUEST_DATA);
//			}
			
			if(requestData != null && !"".equals(requestData)){
				JSONObject data = JSONObject.fromObject(requestData);
				String productTypeId = data.getString("productTypeId");
				int pageNumber = data.getInt("pageNumber");
				int pageSize = data.getInt("pageSize");
				
//				DetachedCriteriaTS<CooperationProductType> productTypecriteria = new DetachedCriteriaTS<CooperationProductType>(
//						CooperationProductType.class);
//				productTypecriteria.add(Restrictions.eq("typeCode", productTypeId));
//				CooperationProductType productType = cooperationProductTypeService
//						.find(productTypecriteria);
//				
//				DetachedCriteriaTS<CooperationBusiness> criteria = new DetachedCriteriaTS<CooperationBusiness>(
//						CooperationBusiness.class);
//				criteria.add(Restrictions.eq("productTypeId", productType));
				
//				list = cooperationService.find(criteria, pageSize);
				
				list = cooperationService.getListByProductTypeId(productTypeId, pageNumber, pageSize);
			}
			
			if(list != null && list.size() > 0){
				vendorlist = new ArrayList<VendorVo>();
				for(CooperationBusiness business : list){
					VendorVo vo = new VendorVo();
					vo.setBusinessId(business.getCooperId());
					vo.setBusinessCode(business.getCooperCode());
					vo.setBusinessName(business.getCooperName());
					vo.setCityId(String.valueOf(business.getLocationReference().getCityId()));
					vo.setProvinceId(String.valueOf(business.getLocationReference().getProvinceId()));
					vo.setCountryId(String.valueOf(business.getLocationReference().getCountryId()));
					vo.setProductTypeId(business.getProductTypeId().getPareTypes().getTypeCode());//取父级typeCode
					vo.setCityName(business.getLocationReference().getCityName());
					vo.setProvinceName(business.getLocationReference().getProvinceName());
					vo.setCountryName(business.getLocationReference().getCountryName());
					vo.setDetailAddress(business.getLocationReference().getDetailAddress());
					vo.setTel1(business.getTel1());
					vo.setTel2(business.getTel2());
					vo.setAddress(business.getAddress());
					vo.setLogoImageURL(business.getLogoImageURL());
					vo.setProductTypeName(business.getProductTypeId().getTypeName());
					vendorlist.add(vo);
				}
				
				if(vendorlist.size() > 0){
					JsonConfig jsonConfig = new JsonConfig();
					// 设置javabean中日期转换时的格式
					jsonConfig.registerJsonValueProcessor(Date.class,
							new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
					JSONArray array = JSONArray
							.fromObject(vendorlist, jsonConfig);

					responseOutStream(response, array.toString());
				}
			}
		}catch(Exception e){
			logger.error("异常信息", e);
			Map<String, String> result = new HashMap<String, String>(); 
			result.put("errorCode", "-1");
			result.put("errorMessage", "查询时发生错误，请稍后再试。");
			JSONObject jsonResult = JSONObject.fromObject(result);
			try{
				responseOutStream(response, jsonResult.toString());
			}catch(Exception ex){
				logger.error("返回时发生异常", ex);
			}
		}
	}
	
	@RequestMapping("/findBusinessInfo")
	@ResponseBody
	public VendorVo findBusinessInfo(String requestData){
		
		String businessId = null;
		CooperationBusiness business = null;
		VendorVo vo = null;
		if(requestData != null){
			businessId = JSONObject.fromObject(requestData).getString("businessId");
		}
		
		if(businessId != null){
			business = cooperationService.get(CooperationBusiness.class, businessId);
		}
		
		if(business != null){
			vo = new VendorVo();
			vo.setBusinessId(business.getCooperId());
			vo.setBusinessCode(business.getCooperCode());
			vo.setBusinessName(business.getCooperName());
			vo.setAddress(business.getAddress());
			vo.setProductTypeId(business.getProductTypeId().getPareTypes().getTypeCode());
			vo.setProductTypeName(business.getProductTypeId().getTypeName());
			vo.setLogoImageURL(business.getLogoImageURL());
			vo.setTel1(business.getTel1());
			vo.setTel2(business.getTel2());
			vo.setReturnCode(VendorVo.RETURN_SUCCESS);
			vo.setReturnDesc("获取商户信息成功");
		}
		
		return vo;
	}
	
}
