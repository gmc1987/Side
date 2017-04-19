/**
 * 
 */
package com.tssa.cooperationBusiness.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.common.util.TSSAUtil;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessLocationRefence;
import com.tssa.cooperationBusiness.pojo.CooperationProductType;
import com.tssa.cooperationBusiness.service.CooperationProductTypeService;
import com.tssa.cooperationBusiness.service.CooperationService;

/**
 * @author gmc
 * 
 */

@Controller
@RequestMapping("/cooperation")
public class CooperationController {

	private static final Logger LOG = LoggerFactory
			.getLogger(CooperationController.class);

	@Autowired
	private CooperationService cooperationService;

	@Autowired
	private CooperationProductTypeService cooperationProductTypeService;

	@RequestMapping("/toList")
	public String toPage(HttpServletRequest request, ModelMap map) {
		Users user = (Users) request.getSession().getAttribute("user");
		BusinessCustomer businessCustomer = (BusinessCustomer) request
				.getSession().getAttribute("businessCustomer");
		if (user != null) {
			return "/cooperationBusiness/cooperationList";
		} else if (businessCustomer != null) {
			
			CooperationBusiness business = null;
			
			DetachedCriteriaTS<CooperationBusiness> detachedCriteria = new DetachedCriteriaTS<CooperationBusiness>(
					CooperationBusiness.class);
			detachedCriteria.add(Restrictions.eq("cooperCode", businessCustomer.getVendorCode()));
			
			business = cooperationService.find(detachedCriteria);
			
			if(business != null){
				map.addAttribute("type", business.getProductTypeId().getPareTypes().getTypeCode());
			}
			
			return "/cooperationBusiness/businessCustomerManager";
		} else {
			return "/cooperationBusiness/cooperationList";
		}
	}

	@RequestMapping("/toVendorList")
	public String toVenderPage(HttpServletRequest request) {
		return "/cooperationBusiness/vendorList";
	}

	@RequestMapping("/toVendorProductPage")
	public String toVenderProductPage(HttpServletRequest request) {
		return "/cooperationBusiness/vendorProduct";
	}

	@RequestMapping("/getProductType")
	@ResponseBody
	public Map<String, Object> getProductType(HttpServletRequest request,
			ModelMap map) {
		List<CooperationProductType> productTypes = null;
		Map<String, Object> result = new HashMap<String, Object>();
		DetachedCriteriaTS<CooperationProductType> detachedCriteria = new DetachedCriteriaTS<CooperationProductType>(
				CooperationProductType.class);
		detachedCriteria.add(Restrictions.isNull("pareTypes"));
		productTypes = cooperationProductTypeService
				.getParentTypes(detachedCriteria);
		// 过滤字段对象
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "pareTypes" });
		result.put("result", JSONArray.fromObject(productTypes, config));
		// map.putAll(result);
		return result;
	}

	@RequestMapping("/list")
	@ResponseBody
	public ModelMap list(HttpServletRequest request, ModelMap map) {
		PageMode<CooperationBusiness> page = null;
		String code = request.getParameter("businessCode");
		String name = request.getParameter("businessName");
		String type = request.getParameter("businessType");
		String childType = request.getParameter("businessChildType");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int pageNumber = 1;
		int pageSize = 0;

		DetachedCriteriaTS<CooperationBusiness> criteria = new DetachedCriteriaTS<CooperationBusiness>(
				CooperationBusiness.class);

		if (code != null && !"".equals(code)) {
			criteria.add(Restrictions.like("cooperCode", code,
					MatchMode.ANYWHERE));
		}
		if (name != null && !"".equals(name)) {
			criteria.add(Restrictions.like("cooperName", name,
					MatchMode.ANYWHERE));
		}
		if (type != null && !"".equals(type)) {
			/* 查询产品类型 */
			DetachedCriteriaTS<CooperationProductType> productTypecriteria = new DetachedCriteriaTS<CooperationProductType>(
					CooperationProductType.class);
			productTypecriteria.add(Restrictions.eq("typeCode", childType));
			CooperationProductType productType = cooperationProductTypeService
					.find(productTypecriteria);
			
//			Set<CooperationProductType> childs = productType.getSubProductTypes();
//			for(CooperationProductType child : childs){
//				criteria.add(Restrictions.or(Restrictions.eq("productTypeId", child), Restrictions.eq("productTypeId", child)));
//			}

			criteria.add(Restrictions.eq("productTypeId", productType));
		}
		if (start != null && !"".equals(start) && !"0".equals(start)) {
			pageNumber = Integer.parseInt(start);
		}
		if (limit != null && !"".equals(limit)) {
			pageSize = Integer.parseInt(limit);
		} else {
			pageSize = 10;
		}

		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "subProductTypes", "locationReference" });
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		PropertyFilter pf = new PropertyFilter(){

			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				// TODO Auto-generated method stub
				if(arg2 == null || String.valueOf(arg2).equals("")){
					return true;
				}
				return false;
			}
			
		};
		config.setJsonPropertyFilter(pf);

		page = cooperationService.findForList(criteria, pageNumber, pageSize);
		if (page != null) {
			map.addAttribute("pageMode", JSONObject.fromObject(page, config));
		}
		return map;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public ModelMap save(HttpServletRequest request, ModelMap map) {

		Users user = (Users) request.getSession().getAttribute("user");

		try {
			String cooperationCode = TSSAUtil.getCode("02", null);
			String cooperationName = request.getParameter("cooperationName");
			String tel1 = request.getParameter("tel1");
			String tel2 = request.getParameter("tel2");
			String cooperationType = request.getParameter("childTypeId");
			String address = request.getParameter("address");
			String businessLicense = request.getParameter("businessLicense");
			String account = request.getParameter("account");
			String pictureUrl = saveFile(request);
			String logoURL = saveLogoFile(request);

			String country = request.getParameter("country");
			String province = request.getParameter("province");
			String city = request.getParameter("city");

			String countryText = request.getParameter("countryText");
			String provinceText = request.getParameter("provinceText");
			String cityText = request.getParameter("cityText");
			String detailAddress = request.getParameter("detailAddressText");

			Date allianceDate = new Date();
			String operationUser = "";
			if (user != null) {
				operationUser = user.getUid();
			}
			
			// 查询当前变更产品类型
			DetachedCriteriaTS<CooperationProductType> cooperTypeCriteria = new DetachedCriteriaTS<CooperationProductType>(
					CooperationProductType.class);
			if (cooperationType != null && !"".equals(cooperationType)) {
				cooperTypeCriteria.add(Restrictions.eq("typeCode",
						cooperationType));
			}
			CooperationProductType productType = cooperationProductTypeService
					.find(cooperTypeCriteria);

			CooperationBusiness cooperation = new CooperationBusiness();
			cooperation.setCooperCode(cooperationCode);
			cooperation.setCooperName(cooperationName);
			cooperation.setTel1(tel1);
			cooperation.setTel2(tel2);
			cooperation.setProductTypeId(productType);
			cooperation.setAddress(address);
			cooperation.setBusinessLicense(businessLicense);
			cooperation.setAccount(account);
			cooperation.setPictureUrl(pictureUrl);
			cooperation.setAllianceDate(allianceDate);
			cooperation.setVerificationState("01");
			cooperation.setOperationUser(operationUser);
			cooperation.setLogoImageURL(logoURL);
			
			boolean resultFlag = cooperationService.cooperationSave(cooperation, country, province, city, countryText, provinceText, cityText, detailAddress);

			if(resultFlag){
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("success", true);
				result.put("msg", "商户新增成功");
				map.putAll(result);
			}else{
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("success", false);
				result.put("msg", "商户新增失败");
				map.putAll(result);
			}
			
			return map;

		} catch (Exception e) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "商户新增失败");
			map.putAll(result);
			LOG.error(e.getMessage(), e);
			return map;
		}
	}

	@RequestMapping("/update")
	@ResponseBody
	public ModelMap update(HttpServletRequest request, ModelMap map) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String cooperationId = request.getParameter("cooperationId");
			String cooperationName = request.getParameter("cooperationName");
			String tel1 = request.getParameter("tel1");
			String tel2 = request.getParameter("tel2");
			String cooperationType = request.getParameter("childTypeId");
			String address = request.getParameter("address");
			String businessLicense = request.getParameter("businessLicense");
			String account = request.getParameter("account");

			String country = request.getParameter("country");
			String province = request.getParameter("province");
			String city = request.getParameter("city");

			String countryText = request.getParameter("countryText");
			String provinceText = request.getParameter("provinceText");
			String cityText = request.getParameter("cityText");
			String detailAddress = request.getParameter("detailAddressText");

			String pictureUrl = saveFile(request);
			String logoURL = saveLogoFile(request);

			CooperationBusiness cooperationBusiness = null;

			// 查询当前被修改记录数据
			DetachedCriteriaTS<CooperationBusiness> criteria = new DetachedCriteriaTS<CooperationBusiness>(
					CooperationBusiness.class);
			if (cooperationId != null && !"".equals(cooperationId)) {
				criteria.add(Restrictions.like("cooperId", cooperationId,
						MatchMode.ANYWHERE));
			}
			cooperationBusiness = cooperationService.find(criteria);
			
			// 查询当前变更产品类型
			DetachedCriteriaTS<CooperationProductType> cooperTypeCriteria = new DetachedCriteriaTS<CooperationProductType>(
					CooperationProductType.class);
			if (cooperationType != null && !"".equals(cooperationType)) {
				cooperTypeCriteria.add(Restrictions.eq("typeCode",
						cooperationType));
			}
			CooperationProductType productType = cooperationProductTypeService
					.find(cooperTypeCriteria);

			if (cooperationBusiness != null) {
				
				CooperationBusinessLocationRefence locationReference = cooperationBusiness.getLocationReference();
				
				if ((country != null && !"".equals(country))
						&& (province != null && !"".equals(province))
						&& (city != null && !"".equals(city))) {
					int countryId = Integer.parseInt(country);
					int provinceId = Integer.parseInt(province);
					int cityId = Integer.parseInt(city);
					
					locationReference.setCityId(cityId);
					locationReference.setCountryId(countryId);
					locationReference.setProvinceId(provinceId);
					locationReference.setCountryName(countryText);
					locationReference.setProvinceName(provinceText);
					locationReference.setCityName(cityText);
					locationReference.setDetailAddress(detailAddress);
					cooperationBusiness.setLocationReference(locationReference);
				}
				
				cooperationBusiness.setCooperName(cooperationName);
				cooperationBusiness.setProductTypeId(productType);
				cooperationBusiness.setAddress(address);
				cooperationBusiness.setTel1(tel1);
				cooperationBusiness.setTel2(tel2);
				cooperationBusiness.setBusinessLicense(businessLicense);
				cooperationBusiness.setAccount(account);
				if(pictureUrl != null){
					cooperationBusiness.setPictureUrl(pictureUrl);
					
				}
				if(logoURL != null){
					cooperationBusiness.setLogoImageURL(logoURL);
				}

				cooperationService.update(cooperationBusiness);

				result.put("success", true);
				result.put("msg", "商户修改成功");
				map.putAll(result);
				return map;
			} else {
				result.put("success", false);
				result.put("msg", "没有找到商户信息");
				map.putAll(result);
				return map;
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "更新商户信息发生异常，请稍后再试");
			map.putAll(result);
			LOG.error(e.getMessage(), e);
			return map;
		}

	}

	@RequestMapping("/delete")
	@ResponseBody
	public ModelMap delete(HttpServletRequest request, ModelMap map) {
		String cooperId = request.getParameter("cooperId");
		CooperationBusiness cooperation = null;
		DetachedCriteriaTS<CooperationBusiness> criteria = new DetachedCriteriaTS<CooperationBusiness>(
				CooperationBusiness.class);

		if (cooperId != null && !"".equals(cooperId)) {
			criteria.add(Restrictions.like("cooperId", cooperId,
					MatchMode.ANYWHERE));
		}

		cooperation = cooperationService.find(criteria);
		cooperationService.delete(cooperation);

		map.clear();
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "商户删除成功");
		map.putAll(result);
		return map;
	}

	public String saveFile(HttpServletRequest request) {
		String filePath = "";
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multiFile = multipartRequest
					.getFile("businessLicensePic");
			String fileUrl =request.getServletContext().getRealPath("/") + "upload/";
			String viewUrl = request.getServletContext().getContextPath()+ "/upload/";
			// 测试使用
//			String fileUrl = "/Users/gmc/Works/eclipse/workspace/TSSA/WebContent/WEB-INF/upload/";
			
			File file = new File(fileUrl);
	        if(!file.exists()){
	        	file.mkdirs();
	        }

			if (multiFile != null
					&& (multiFile.getOriginalFilename() != null && !""
							.equals(multiFile.getOriginalFilename()))) {
				File uploadFile = new File(fileUrl
						+ multiFile.getOriginalFilename());
				multiFile.transferTo(uploadFile);
				
				filePath = viewUrl + multiFile.getOriginalFilename();

				return filePath;

			} else {
				return null;
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return filePath;
	}
	
	private String saveLogoFile(HttpServletRequest request) {
		String filePath = "";
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multiFile = multipartRequest
					.getFile("logoImageURL");
			String cooperationId = request.getParameter("cooperationId");
			String fileUrl =request.getServletContext().getRealPath("/") + "upload/";
			String viewUrl = request.getServletContext().getContextPath()+ "/upload/";
			// 测试使用
//			String fileUrl = "/Users/gmc/Works/eclipse/workspace/TSSA/WebContent/WEB-INF/upload/";

			File file = new File(fileUrl);
	        if(!file.exists()){
	        	file.mkdirs();
	        }
			if (multiFile != null
					&& (multiFile.getOriginalFilename() != null && !""
							.equals(multiFile.getOriginalFilename()))) {
				File uploadFile = new File(fileUrl
						+ cooperationId + multiFile.getOriginalFilename().substring(multiFile.getOriginalFilename().lastIndexOf(".")));
				multiFile.transferTo(uploadFile);

				filePath = viewUrl + cooperationId + multiFile.getOriginalFilename().substring(multiFile.getOriginalFilename().lastIndexOf("."));

				return filePath;

			} else {
				return null;
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}

}
