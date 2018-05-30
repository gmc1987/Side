/**
 * 
 */
package com.tssa.remote.locations.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.location.service.LocationService;
import com.tssa.remote.locations.vo.CityVO;
import com.tssa.remote.locations.vo.ContinentVO;
import com.tssa.remote.locations.vo.CountryVO;
import com.tssa.remote.locations.vo.ProvinceVO;

/**
 * @author gmc870223
 *
 */
@Controller
@RequestMapping("/locationRemote")
public class LocationRemoteController {

	protected final Logger logger = LoggerFactory.getLogger(LocationRemoteController.class);
	
	@Autowired
	private LocationService locationService;
	
	/**
	 * 获取洲数据
	 * @param request
	 * @return
	 */
	@RequestMapping("getContinent")
	@ResponseBody
	public List<ContinentVO> getAllContinent(){
		List<ContinentVO> continents = null;
		try {
			continents = locationService.findAllContinentToVO();
		} catch(Exception e) {
			logger.error("获取地区数据(洲)发生错误!", e);
		}
		return continents;
	}
	
	/**
	 * 获取国家数据
	 * @param request
	 * @return
	 */
	@RequestMapping("getCountry")
	@ResponseBody
	public List<CountryVO> getAllCountry(){
		List<CountryVO> countryList = null;
		try {
			countryList = locationService.findAllCountryToVO();
		} catch(Exception e) {
			logger.error("获取地区数据(国家)发生错误!", e);
		}
		return countryList;
	}
	
	/**
	 * 获取省份数据
	 * @param request
	 * @return
	 */
	@RequestMapping("getProvince")
	@ResponseBody
	public List<ProvinceVO> getAllProvince(){
		List<ProvinceVO> provinceList = null;
		try {
			provinceList = locationService.findAllProvinceToVO();
		} catch(Exception e) {
			logger.error("获取地区数据(省)发生错误!", e);
		}
		return provinceList;
	}
	
	/**
	 * 获取城市数据
	 * @param request
	 * @return
	 */
	@RequestMapping("getCity")
	@ResponseBody
	public List<CityVO> getAllCity(){
		List<CityVO> cityList = null;
		try {
			cityList = locationService.findAllCityToVO();
		} catch(Exception e) {
			logger.error("获取地区数据(省)发生错误!", e);
		}
		return cityList;
	}
}
