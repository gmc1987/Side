/**
 * 
 */
package com.tssa.suit.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.suit.pojo.SuitType;
import com.tssa.suit.service.SuitTypeService;

/**
 * @author gmc
 *
 */

@Controller
@RequestMapping(value="/suitType")
public class SuitTypeController {

	@Autowired
	private SuitTypeService suitTypeService;
	
	@RequestMapping("/toList")
	public String toSuitTypePage(HttpServletRequest request, ModelMap map){
		return "/system/suitType";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public ModelMap list(Integer pageNumber, Integer pageSize, ModelMap map){
		DetachedCriteriaTS<SuitType> criteria = new DetachedCriteriaTS<SuitType>(SuitType.class);
		if(pageNumber == null || pageSize == null){
			pageNumber = 1;
			pageSize = 20;
		}
		
		PageMode<SuitType> pageMode = suitTypeService.findForList(criteria, pageNumber, pageSize);
		
		map.clear();
		map.put("pageMode", JSONObject.fromObject(pageMode));
		
		return map;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(SuitType suitType, HttpServletRequest request){
		Users user = (Users)request.getSession().getAttribute("user");
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(suitType != null){
			suitType.setCreateDate(new Date());
			suitType.setCreater(user.getUserName());
			
			suitTypeService.save(suitType);
			
			result.put("success", true);
			result.put("msg", "新增投诉类型成功");
		} else {
			
			result.put("success", false);
			result.put("msg", "新增投诉类型失败");
			
		}
		
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(SuitType suitType, HttpServletRequest request){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(suitType != null){
			SuitType type = suitTypeService.get(SuitType.class, suitType.getTypeId());
			type.setTypeName(suitType.getTypeName());
			suitTypeService.update(type);
			
			result.put("success", true);
			result.put("msg", "新增投诉类型成功");
		} else {
			
			result.put("success", false);
			result.put("msg", "新增投诉类型失败");
			
		}
		
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> delete(String id){
		Map<String, Object> result = new HashMap<String, Object>();
		if(!StringUtils.isEmpty(id)){
			SuitType type = suitTypeService.get(SuitType.class, id);
			if(type != null){
				suitTypeService.delete(type);
				result.put("success", true);
				result.put("msg", "删除投诉类型成功");
			}
		} else {
			result.put("success", false);
			result.put("msg", "删除投诉类型失败");
		}
		
		return result;
	}
}
