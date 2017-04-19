/**
 * 
 */
package com.tssa.cooperationBusiness.controller;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
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

import com.tssa.base.controller.BaseController;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.common.util.TSSAUtil;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;
import com.tssa.cooperationBusiness.pojo.CooperationProductType;
import com.tssa.cooperationBusiness.service.CooperationProductService;
import com.tssa.cooperationBusiness.service.CooperationProductTypeService;
import com.tssa.cooperationBusiness.service.CooperationService;
import com.tssa.myOrders.service.MyOrderService;

/**
 * @author gmc
 *
 */

@Controller
@RequestMapping("/cooperationProduct")
public class CooperationProductController extends BaseController{
	
	private static final Logger LOG = LoggerFactory.getLogger(CooperationProductController.class);
	
	@Autowired
	private CooperationProductTypeService cooperationProductTypeService;
	
	@Autowired
	private CooperationProductService cooperationProductService;
	
	@Autowired
	private CooperationService cooperationService;
	
	@Autowired
	private MyOrderService orderService;

	@RequestMapping("/getProductType")
	@ResponseBody
	public Map<String, Object> getProductType(HttpServletRequest request,  ModelMap map){
		
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		List<CooperationProductType> productTypes = null;
		Map<String, Object> result = new HashMap<String, Object>();
		if(businessCustomer != null){
			productTypes = new ArrayList<CooperationProductType>();
			DetachedCriteriaTS<CooperationBusiness> cooperationBusinessCriteria = new DetachedCriteriaTS<CooperationBusiness>(CooperationBusiness.class);
			cooperationBusinessCriteria.add(Restrictions.eq("cooperCode", businessCustomer.getVendorCode()));
			CooperationBusiness cooperationBusiness = cooperationService.find(cooperationBusinessCriteria);
			productTypes.add(cooperationBusiness.getProductTypeId().getPareTypes());
		}else{
			DetachedCriteriaTS<CooperationProductType> detachedCriteria = new DetachedCriteriaTS<CooperationProductType>(CooperationProductType.class);
			detachedCriteria.add(Restrictions.isNull("pareTypes"));
			productTypes = cooperationProductTypeService.getParentTypes(detachedCriteria);
		}
		
		//过滤字段对象
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{
				"pareTypes"
		});
		result.put("result", JSONArray.fromObject(productTypes,config));
		
		return result;
	}
	
	@RequestMapping("/getChildProductType")
	@ResponseBody
	public Map<String, Object>getChildProductType(HttpServletRequest request,  ModelMap map){
		String parentCode = request.getParameter("parentType");
		List<CooperationProductType> productTypes = null;
		Map<String, Object> result = new HashMap<String, Object>();
		DetachedCriteriaTS<CooperationProductType> detachedCriteria = new DetachedCriteriaTS<CooperationProductType>(CooperationProductType.class);
		detachedCriteria.add(Restrictions.isNotNull("pareTypes"));
		if(parentCode != null && !"".equals(parentCode)){
			detachedCriteria.add(Restrictions.like("typeCode", parentCode, MatchMode.START));
			productTypes = cooperationProductTypeService.getChildProductTypes(detachedCriteria);
			//过滤字段对象
			JsonConfig config = new JsonConfig();
			config.setExcludes(new String[]{
					"pareTypes"
			});
			result.put("result", JSONArray.fromObject(productTypes,config));
			return result;
		}else{
			return null;
		}
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public ModelMap list(HttpServletRequest request,  ModelMap map){
		PageMode<CooperationBusinessProduct> page = null;
		String code = request.getParameter("productCode");
		String name = request.getParameter("productName");
		String type = request.getParameter("productType");
		String cooperCode = request.getParameter("vendorCode");
		String cooperName = request.getParameter("vendorName");
		String start = request.getParameter("pageNumber");
		String limit = request.getParameter("pageSize");
		int pageNumber = 1;
		int pageSize = 0;
		CooperationBusiness cooperationBusiness = null;
		
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		
		DetachedCriteriaTS<CooperationBusinessProduct> criteria = new DetachedCriteriaTS<CooperationBusinessProduct>(CooperationBusinessProduct.class);
//		criteria.add(Restrictions.isNull("pareTypes"));
		if(businessCustomer != null){
			DetachedCriteriaTS<CooperationBusiness> cooperationBusinessCriteria = new DetachedCriteriaTS<CooperationBusiness>(CooperationBusiness.class);
			cooperationBusinessCriteria.add(Restrictions.eq("cooperCode", businessCustomer.getVendorCode()));
			cooperationBusiness = cooperationService.find(cooperationBusinessCriteria);
			criteria.add(Restrictions.eq("cooperId", cooperationBusiness));
		}
		
		if(code != null && !"".equals(code)){
			criteria.add(Restrictions.like("productCode", code, MatchMode.ANYWHERE));
		}
		if(name != null && !"".equals(name)){
			criteria.add(Restrictions.like("productName", name, MatchMode.ANYWHERE));
		}
		if(type != null && !"".equals(type)){
			DetachedCriteriaTS<CooperationProductType> detachedCriteria = new DetachedCriteriaTS<CooperationProductType>(CooperationProductType.class);
			detachedCriteria.add(Restrictions.eq("typeCode", type));
			CooperationProductType productType = cooperationProductTypeService.find(detachedCriteria);
			criteria.add(Restrictions.eq("productType", productType));
		}
		if(cooperCode != null && !"".equals(cooperCode)){
			DetachedCriteriaTS<CooperationBusiness> detachedCriteria = new DetachedCriteriaTS<CooperationBusiness>(CooperationBusiness.class);
			detachedCriteria.add(Restrictions.like("cooperCode", cooperCode, MatchMode.ANYWHERE));
			CooperationBusiness cooperationCode = cooperationService.find(detachedCriteria);
			criteria.add(Restrictions.like("cooperId", cooperationCode));
		}
		if(cooperName != null && !"".equals(cooperName)){
			DetachedCriteriaTS<CooperationBusiness> detachedCriteria = new DetachedCriteriaTS<CooperationBusiness>(CooperationBusiness.class);
			detachedCriteria.add(Restrictions.like("cooperName", cooperName, MatchMode.ANYWHERE));
			CooperationBusiness cooperationName = cooperationService.find(detachedCriteria);
			criteria.add(Restrictions.like("cooperId", cooperationName));
		}
		if(start != null && !"".equals(start) && !"0".equals(start)){
			pageNumber = Integer.parseInt(start);
		}
		if(limit != null && !"".equals(limit)){
			pageSize = Integer.parseInt(limit);
		}else{
			pageSize = 10;
		}
		
		page = cooperationProductService.findForList(criteria, pageNumber, pageSize);
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false); 
		config.setExcludes(new String[]{
				"locationReference",
//				"productType",
				"order",
				"subProductTypes"
		});
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
		if(page != null){
			map.clear();
			map.addAttribute("pageMode", JSONObject.fromObject(page, config));
		}
		return map;
	}
	
	/**
	 * 订单信息产品列表
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/orderProductList")
	@ResponseBody
	public ModelMap orderProductList(HttpServletRequest request,  ModelMap map){
		
		List<CooperationBusinessProduct> list = null;
		Map<String, Object> result = new HashMap<String, Object>();
		
		String orderId = request.getParameter("orderId");
		
		if(orderId != null && !"".equals(orderId)){
			
			list = cooperationProductService.findProductByOrderId(orderId);
			
		}
		
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false); 
		config.setExcludes(new String[]{
				"pareTypes",
				"cooperId",
				"order",
				"productType"
		});
		if(list != null){
			result.put("result", JSONArray.fromObject(list,config));
		}
		map.putAll(result);
		return map;
	}
	
	
	@RequestMapping("/save")
	@ResponseBody
	public ModelMap save(HttpServletRequest request, ModelMap map){
		
		BusinessCustomer user = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		try{
			String productCode = TSSAUtil.getCode("03", null);
			String productName = request.getParameter("productName");
			String childTypeCode = request.getParameter("childTypeName");//子类
//			String vendorTypeName = request.getParameter("vendorTypeName");//父类
			String singlePrice = request.getParameter("singlePrice");
			String salePrice = request.getParameter("salePrice");
			String description = request.getParameter("description");
			String specialType = request.getParameter("specialType");
			String filePath = saveFile(request);
			Date createDate = new Date();
			String operationUser = "";
			String vendorId = null;
			if(user != null){
				operationUser = user.getBusinessCustomerId();
				vendorId = user.getVendorCode();
			}
			
			//查询商户资料
			DetachedCriteriaTS<CooperationBusiness> cooperationBusinessCriteria = new DetachedCriteriaTS<CooperationBusiness>(CooperationBusiness.class);
			cooperationBusinessCriteria.add(Restrictions.eq("cooperCode", vendorId));
			CooperationBusiness cooperationBusiness = cooperationService.find(cooperationBusinessCriteria);
			
			//查询产品类型
			DetachedCriteriaTS<CooperationProductType> cooperationProductTypeCriteria = new DetachedCriteriaTS<CooperationProductType>(CooperationProductType.class);
			cooperationProductTypeCriteria.add(Restrictions.eq("typeCode", childTypeCode));
			cooperationProductTypeCriteria.add(Restrictions.eq("pareTypes", cooperationBusiness.getProductTypeId().getPareTypes()));
			CooperationProductType cooperationProductType = cooperationProductTypeService.find(cooperationProductTypeCriteria);
			
			//保存产品数据
			CooperationBusinessProduct product = new CooperationBusinessProduct();
			product.setCooperId(cooperationBusiness);
			product.setProductType(cooperationProductType);
			product.setProductCode(productCode);
			product.setProductName(productName);
			product.setSpecialType(specialType);
			product.setSinglePrice(BigDecimal.valueOf(Long.parseLong(singlePrice)));
			product.setSalePrice(BigDecimal.valueOf(Long.parseLong(salePrice)));
			product.setDescription(description);
			product.setCreateDate(createDate);
			product.setOperationUser(operationUser);
			product.setImage(filePath);
			
			cooperationProductService.save(product);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", "true");
			result.put("msg", "产品新增成功");
			map.putAll(result);
			return map;
			
		}catch(Exception e){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "产品新增失败");
			map.putAll(result);
			LOG.error(e.getMessage(),e);
			return map;
		}
	}
	
	
	@RequestMapping("/update")
	@ResponseBody
	public ModelMap update(HttpServletRequest request, ModelMap map){
		
		BusinessCustomer user = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		try{
			String businessProductId = request.getParameter("businessProductId");
			String productName = request.getParameter("productName");
			String childTypeCode = request.getParameter("childTypeName");//子类
			String singlePrice = request.getParameter("singlePrice");
			String salePrice = request.getParameter("salePrice");
			String description = request.getParameter("description");
			String specialType = request.getParameter("specialType");
			String filePath = saveFile(request);
			String operationUser = "";
			String vendorId = null;
			if(user != null){
				operationUser = user.getBusinessCustomerId();
				vendorId = user.getVendorCode();
			}
			
			//查询商户资料
			DetachedCriteriaTS<CooperationBusiness> cooperationBusinessCriteria = new DetachedCriteriaTS<CooperationBusiness>(CooperationBusiness.class);
			cooperationBusinessCriteria.add(Restrictions.eq("cooperCode", vendorId));
			CooperationBusiness cooperationBusiness = cooperationService.find(cooperationBusinessCriteria);
			
			//查询产品类型
			DetachedCriteriaTS<CooperationProductType> cooperationProductTypeCriteria = new DetachedCriteriaTS<CooperationProductType>(CooperationProductType.class);
			cooperationProductTypeCriteria.add(Restrictions.eq("typeCode", childTypeCode));
			cooperationProductTypeCriteria.add(Restrictions.eq("pareTypes", cooperationBusiness.getProductTypeId().getPareTypes()));
			CooperationProductType cooperationProductType = cooperationProductTypeService.find(cooperationProductTypeCriteria);
			
			DetachedCriteriaTS<CooperationBusinessProduct> cooperationBuseinssProductCriteria = new DetachedCriteriaTS<CooperationBusinessProduct>(CooperationBusinessProduct.class);
			cooperationBuseinssProductCriteria.add(Restrictions.eq("businessProductId", businessProductId));
			CooperationBusinessProduct product = cooperationProductService.find(cooperationBuseinssProductCriteria);
			//更新产品数据
			product.setCooperId(cooperationBusiness);
			product.setProductType(cooperationProductType);
			product.setProductName(productName);
			product.setSinglePrice(BigDecimal.valueOf(Long.parseLong(singlePrice)));
			product.setSalePrice(BigDecimal.valueOf(Long.parseLong(salePrice)));
			product.setDescription(description);
			product.setOperationUser(operationUser);
			product.setSpecialType(specialType);
			if(filePath != null && !"".equals(filePath)){
				product.setImage(filePath);
			}
			cooperationProductService.update(product);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", "true");
			result.put("msg", "产品更新成功");
			map.putAll(result);
			return map;
		}catch(Exception e){
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "产品更新失败");
			map.putAll(result);
			LOG.error(e.getMessage(),e);
			return map;
		}
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public ModelMap delete(HttpServletRequest request, ModelMap map){
		
		String businessProductId = request.getParameter("businessProductId");
		CooperationBusinessProduct product = null;
		DetachedCriteriaTS<CooperationBusinessProduct> cooperationBuseinssProductCriteria = new DetachedCriteriaTS<CooperationBusinessProduct>(CooperationBusinessProduct.class);
		cooperationBuseinssProductCriteria.add(Restrictions.eq("businessProductId", businessProductId));
		
		product = cooperationProductService.find(cooperationBuseinssProductCriteria);
		cooperationProductService.delete(product);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("msg", "产品删除成功");
		map.putAll(result);
		return map;
	}
	
	@Override
	@RequestMapping("/upload")
	@ResponseBody
	public ModelMap saveFile(HttpServletRequest request, ModelMap map) {
		// TODO Auto-generated method stub
		return super.saveFile(request, map);
	}
	
	public String saveFile(HttpServletRequest request) {
		String filePath = "";
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multiFile = multipartRequest
					.getFile("picture");
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
				
				//后缀名
	        	String suffixName = multiFile.getOriginalFilename().substring(multiFile.getOriginalFilename().indexOf("."));
	        	//文件名
	        	String fileName = System.currentTimeMillis() + suffixName;
				
				File uploadFile = new File(fileUrl
						+ fileName);
				multiFile.transferTo(uploadFile);
				
				filePath = viewUrl + fileName;

				return filePath;

			} else {
				return null;
			}
		} catch (IOException e) {
			LOG.error(e.getMessage(), e);
		}
		return filePath;
	}
	
}
