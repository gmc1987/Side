/**
 * 
 */
package com.tssa.location.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.location.pojo.Location;
import com.tssa.location.service.LocationService;

/**
 * @author gmc
 * 
 */
@Controller
@RequestMapping("/location")
public class LocationController {

	@Autowired
	private LocationService locationService;
	
	/**
	 * 获取洲数据
	 * @param request
	 * @return
	 */
	@RequestMapping("getContinent")
	@ResponseBody
	public ModelMap getAllContinent(HttpServletRequest request, ModelMap map){
		
		List<Location> continents = null;
		
		String key = request.getParameter("key");
		
		continents = locationService.findAllContinent(key);
		
		map.clear();
		
		JsonConfig config = new JsonConfig();
		// 设置javabean中日期转换时的格式
		config.setIgnoreDefaultExcludes(false); 
		config.setExcludes(new String[]{
			"pareLocation",
			"subLocations"
		});
		
		JSONArray jsonArray = JSONArray.fromObject(continents, config);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("continents", jsonArray);
		
		map.putAll(result);
		
		return map;
	}
	
	/**
	 * 获取国家数据
	 * @param request
	 * @return
	 */
	@RequestMapping("getCountry")
	@ResponseBody
	public ModelMap getAllCountry(HttpServletRequest request, ModelMap map){
		
		List<Location> contries = null;
		
		String continentId = request.getParameter("continentId");
		String key = request.getParameter("key");
		
		contries = locationService.findAllCountry(continentId, key);
		
		map.clear();
		
		JsonConfig config = new JsonConfig();
		// 设置javabean中日期转换时的格式
		config.setIgnoreDefaultExcludes(false); 
		config.setExcludes(new String[]{
			"pareLocation",
			"subLocations"
		});
		
		JSONArray jsonArray = JSONArray.fromObject(contries, config);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("contries", jsonArray);
		
		map.putAll(result);
		
		return map;
	}
	
	/**
	 * 获取省份数据
	 * @param request
	 * @return
	 */
	@RequestMapping("getProvince")
	@ResponseBody
	public ModelMap getAllProvince(HttpServletRequest request, ModelMap map){
		
		List<Location> provincies = null;
		
		String countryId = request.getParameter("countryId");
		String key = request.getParameter("key");
		String provinceId = request.getParameter("provinceId");
		
		provincies = locationService.findAllProvince(countryId, provinceId, key);
		
		map.clear();
		
		JsonConfig config = new JsonConfig();
		// 设置javabean中日期转换时的格式
		config.setIgnoreDefaultExcludes(false); 
		config.setExcludes(new String[]{
			"pareLocation",
			"subLocations"
		});
		
		JSONArray jsonArray = JSONArray.fromObject(provincies, config);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("provincies", jsonArray);
		
		map.putAll(result);
		
		return map;
	}
	
	/**
	 * 获取城市数据
	 * @param request
	 * @return
	 */
	@RequestMapping("getCity")
	@ResponseBody
	public ModelMap getAllCity(HttpServletRequest request, ModelMap map){
		
		List<Location> cities = null;
		
		String cityId = request.getParameter("cityId");
		String provinceId = request.getParameter("provinceId");
		String key = request.getParameter("key");
		
		cities = locationService.findAllCity(provinceId, cityId, key);
		
		map.clear();
		
		JsonConfig config = new JsonConfig();
		// 设置javabean中日期转换时的格式
		config.setIgnoreDefaultExcludes(false); 
		config.setExcludes(new String[]{
			"pareLocation",
			"subLocations"
		});
		
		JSONArray jsonArray = JSONArray.fromObject(cities, config);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("success", true);
		result.put("cities", jsonArray);
		
		map.putAll(result);
		
		return map;
	}
	
}
