/**
 * 
 */
package com.tssa.cooperationBusiness.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.base.controller.BaseController;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.cooperationBusiness.pojo.CooperationProductType;
import com.tssa.cooperationBusiness.service.CooperationProductTypeService;

/**
 * @author gmc
 *
 */

@Controller
@RequestMapping("/productTypes")
@SessionAttributes(value={"user","businessCustomer"}, types={Users.class, BusinessCustomer.class})
public class ProductTypeController extends BaseController{
	
	private static final Logger LOG = LoggerFactory.getLogger(ProductTypeController.class);
	
	@Autowired
	private CooperationProductTypeService cooperationProductTypeService;
	
	@RequestMapping("/toList")
	public String toPage(HttpServletRequest request){
		return "/cooperationBusiness/productManager";
	}

	@RequestMapping("/list")
	@ResponseBody
	public ModelMap list(HttpServletRequest request,  ModelMap map){

		int pageNumber = 1;
		int pageSize = 0;
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		String typeCode = request.getParameter("productTypeCode");
		String typeName = request.getParameter("productTypeName");
		String parentId = request.getParameter("parentId");//父产品类型id
		String isParent = request.getParameter("isParent");//父类、子类标识
		
		DetachedCriteriaTS<CooperationProductType> detachedCriteria = new DetachedCriteriaTS<CooperationProductType>(CooperationProductType.class);
		
		if(!isParent.isEmpty() && !"1".equals(isParent)){
			if(parentId != null && !"".equals(parentId)){
				CooperationProductType productType = cooperationProductTypeService.get(CooperationProductType.class, parentId);
				detachedCriteria.add(Restrictions.eq("pareTypes", productType));
			}else{
				Map<String, Object> result = new HashMap<String, Object>();
				result.put("success", false);
				result.put("msg", "需要选择父类产品");
				map.putAll(result);
				return map;
			}
		}else{
			detachedCriteria.add(Restrictions.isNull("pareTypes"));
		}
		
		if(typeCode != null && !"".equals(typeCode)){
			detachedCriteria.add(Restrictions.like("typeCode", typeCode, MatchMode.ANYWHERE));
		}	
		if(typeName != null && !"".equals(typeName)){
			detachedCriteria.add(Restrictions.like("typeName", typeName, MatchMode.ANYWHERE));
		}	
		
		if(start != null && !"".equals(start) && !"0".equals(start)){
			pageNumber = Integer.parseInt(start);
		}
		if(limit != null && !"".equals(limit)){
			pageSize = Integer.parseInt(limit);
		}else{
			pageSize = 10;
		}
		
		PageMode<CooperationProductType> pageMode = cooperationProductTypeService.findForList(detachedCriteria, pageNumber, pageSize);
		
		JsonConfig config = new JsonConfig();
		config.setIgnoreDefaultExcludes(false); 
		config.setExcludes(new String[]{
				"pareTypes",
				"subProductTypes"
		});
		
		if(pageMode != null){
			map.addAttribute("pageMode", JSONObject.fromObject(pageMode, config));
			return map;
		}else{
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("success", false);
			result.put("msg", "暂时没有相关产品类型");
			map.putAll(result);
			return map;
		}
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(HttpServletRequest request, ModelMap map){
		
		Users user = (Users)request.getSession().getAttribute("user");
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String parentId = request.getParameter("productTypeId");
			String typeCode = request.getParameter("typeCode");
			String typeName = request.getParameter("typeName");
			
			CooperationProductType parentType = null;
			CooperationProductType productType = new CooperationProductType();
			
			if(parentId != null &&!"".equals(parentId)){
				DetachedCriteriaTS<CooperationProductType> parentProductCriteria = new DetachedCriteriaTS<CooperationProductType>(CooperationProductType.class);
				parentProductCriteria.add(Restrictions.eq("productTypeId", parentId));
				parentType = cooperationProductTypeService.find(parentProductCriteria);
			}
			
			if(typeCode != null && !"".equals(typeCode)){
				productType.setTypeCode(typeCode);
			}
			if(typeName != null && !"".equals(typeName)){
				productType.setTypeName(typeName);
			}
			
			if(parentType != null){
				productType.setPareTypes(parentType);
			}
			
			productType.setCreateDate(new Date());
			productType.setOperationUser(user.getUserName());
			
			cooperationProductTypeService.save(productType);
			
			result.put("success", true);
			result.put("msg", "产品类型新增成功!");
			
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", "产品类型新增失败!");
			LOG.error("新增异常", e);
		}
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(HttpServletRequest request, ModelMap map){
		
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			String productId = request.getParameter("productTypeId");
			String typeCode = request.getParameter("typeCode");
			String typeName = request.getParameter("typeName");
			String parentTypeId = request.getParameter("parentTypeId");
			
			CooperationProductType parentType = null;
			CooperationProductType productType = null;
			
			if(productId != null &&!"".equals(productId)){
				DetachedCriteriaTS<CooperationProductType> productCriteria = new DetachedCriteriaTS<CooperationProductType>(CooperationProductType.class);
				productCriteria.add(Restrictions.eq("productTypeId", productId));
				productType = cooperationProductTypeService.find(productCriteria);
			}
			
			if(parentTypeId != null && !"该产品类型为产品大类".equals(parentTypeId)){
				DetachedCriteriaTS<CooperationProductType> parentProductCriteria = new DetachedCriteriaTS<CooperationProductType>(CooperationProductType.class);
				parentProductCriteria.add(Restrictions.eq("productTypeId", productId));
				parentType = cooperationProductTypeService.find(parentProductCriteria);
			}
			
			if(typeCode != null && !"".equals(typeCode)){
				productType.setTypeCode(typeCode);
			}
			if(typeName != null && !"".equals(typeName)){
				productType.setTypeName(typeName);
			}
			
			if(parentType != null){
				productType.setPareTypes(parentType);
			}
			
			cooperationProductTypeService.update(productType);
			
			result.put("success", true);
			result.put("msg", "产品类型修改成功!");
			
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", "产品类型修改失败!");
			LOG.error("新增异常", e);
		}
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(HttpServletRequest request, ModelMap map){
		
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			CooperationProductType productType = null;
			String productTypeId = request.getParameter("productTypeId");
			DetachedCriteriaTS<CooperationProductType> productCriteria = new DetachedCriteriaTS<CooperationProductType>(CooperationProductType.class);
			if(productTypeId != null && !"".equals(productTypeId)){
				productCriteria.add(Restrictions.eq("productTypeId", productTypeId));
				productType = cooperationProductTypeService.find(productCriteria);
			}
			
			if(productType != null){
				cooperationProductTypeService.delete(productType);
			}
			
			result.put("success", true);
			result.put("msg", "产品类型删除成功");
			
		}catch(Exception e){
			LOG.error("删除异常", e);
			result.put("success", false);
			result.put("msg", "产品类型删除失败");
		}
		return result;
	}
	
	@RequestMapping("/getProductType")
	@ResponseBody
	public Map<String, Object> getProductType(HttpServletRequest request,  ModelMap map){
		List<CooperationProductType> productTypes = null;
		Map<String, Object> result = new HashMap<String, Object>();
		DetachedCriteriaTS<CooperationProductType> detachedCriteria = new DetachedCriteriaTS<CooperationProductType>(CooperationProductType.class);
		detachedCriteria.add(Restrictions.isNull("pareTypes"));
		productTypes = cooperationProductTypeService.getParentTypes(detachedCriteria);
		//过滤字段对象
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{
				"pareTypes"
		});
		result.put("result", JSONArray.fromObject(productTypes,config));
		return result;
	}
	
	@RequestMapping("/getParentProductType")
	@ResponseBody
	public Map<String, Object> getParentProductType(HttpServletRequest request, ModelMap map){
		
		String productTypeId = request.getParameter("parentId");
		
		CooperationProductType productType = null;
		Map<String, Object> result = new HashMap<String, Object>();
		DetachedCriteriaTS<CooperationProductType> detachedCriteria = new DetachedCriteriaTS<CooperationProductType>(CooperationProductType.class);
		detachedCriteria.add(Restrictions.eq("productTypeId", productTypeId));
		productType = cooperationProductTypeService.find(detachedCriteria);
		
		if(productType.getPareTypes() != null){
			result.put("success", true);
			result.put("msg", productType.getPareTypes().getProductTypeId());
		}else{
			result.put("success", false);
			result.put("msg", "该产品类型为产品大类");
		}
		return result;
	}
}
