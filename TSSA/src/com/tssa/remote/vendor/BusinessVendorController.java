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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.common.mode.PageMode;
import com.tssa.common.util.JsonDateValueProcessor;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.service.CooperationService;
import com.tssa.remote.common.BaseController;
import com.tssa.remote.object.VendorVo;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * @author gmc
 * @see 商户查询接口,提供查询商户信息，商户列表功能
 */

@Controller
@RequestMapping("/vendor")
public class BusinessVendorController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(BusinessVendorController.class);
	
	@Autowired
	private CooperationService cooperationService;
	
	@RequestMapping("/businessList")
	@ResponseBody
	public PageMode<VendorVo> getBusinessList(String requestData, HttpServletRequest request, HttpServletResponse response){
		PageMode<CooperationBusiness> list = null;
		PageMode<VendorVo> returnData = new PageMode<VendorVo>();;
		List<VendorVo> vendorlist = null;
		int pageNumber = 0;
		int pageSize = 0;
		String localId = null;
		try{
			
			if(requestData != null && !"".equals(requestData)){
				JSONObject data = JSONObject.fromObject(requestData);
				String productTypeId = data.getString("productTypeId");
				pageNumber = data.getInt("pageNumber");
				pageSize = data.getInt("pageSize");
				if(data.has("localId")) {
					localId = data.getString("localId");
				}
				list = cooperationService.getListByProductTypeId(productTypeId, localId, pageNumber, pageSize);
			}
			
			if(list != null && list.getRecords().size() > 0){
				vendorlist = new ArrayList<VendorVo>();
				for(CooperationBusiness business : list.getRecords()){
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
					vo.setLatitude(business.getLatitude());
					vo.setLongitude(business.getLongitude());
					vendorlist.add(vo);
				}
				
				if(vendorlist.size() > 0){
					
					returnData = new PageMode<VendorVo>(vendorlist, pageNumber, pageSize, list.getCount());
					
//					JsonConfig jsonConfig = new JsonConfig();
//					// 设置javabean中日期转换时的格式
//					jsonConfig.registerJsonValueProcessor(Date.class,
//							new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
//					JSONObject array = JSONObject
//							.fromObject(returnData, jsonConfig);
//
//					responseOutStream(response, array.toString());
				}
			}
		}catch(Exception e){
			logger.error("异常信息", e);
			returnData = new PageMode<VendorVo>();
//			Map<String, String> result = new HashMap<String, String>(); 
//			result.put("errorCode", "-1");
//			result.put("errorMessage", "查询时发生错误，请稍后再试。");
//			JSONObject jsonResult = JSONObject.fromObject(result);
//			try{
//				responseOutStream(response, jsonResult.toString());
//			}catch(Exception ex){
//				logger.error("返回时发生异常", ex);
//			}
		}
		
		return returnData;
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
