/**
 * 
 */
package com.tssa.location.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.location.dao.LocationDao;
import com.tssa.location.pojo.Location;
import com.tssa.remote.locations.vo.CityVO;
import com.tssa.remote.locations.vo.ContinentVO;
import com.tssa.remote.locations.vo.CountryVO;
import com.tssa.remote.locations.vo.ProvinceVO;

/**
 * @author gmc
 * @see 位置服务类
 */

@Service
public class LocationService extends BaseBusinessService<Location> {

	@Autowired
	private LocationDao locationDao;

	/**
	 * 查询所有的大洲
	 * 
	 * @param key
	 *            关键字
	 * @return
	 */
	public List<Location> findAllContinent(String key) {

		List<Location> continenies = null;

		DetachedCriteriaTS<Location> locationCriteriaTS = new DetachedCriteriaTS<Location>(
				Location.class);

		Location parentId = new Location();
		parentId.setId(0);

		locationCriteriaTS.add(Restrictions.eq("pareLocation", parentId));

		if (key != null && !"".equals(key)) {
			locationCriteriaTS.add(Restrictions.or(
					Restrictions.like("enName", key, MatchMode.ANYWHERE),
					Restrictions.like("name", key, MatchMode.ANYWHERE)));
			locationCriteriaTS.add(Restrictions.or(
					Restrictions.like("fullPinyin", key, MatchMode.ANYWHERE),
					Restrictions.like("pinyin", key, MatchMode.ANYWHERE)));
		}
		
		continenies = locationDao.find(locationCriteriaTS, 0);
		
		return continenies;
	}

	/**
	 * 查询所有国家
	 * 
	 * @param continentId
	 *            大洲id
	 * @param key
	 *            关键字
	 * @return
	 */
	public List<Location> findAllCountry(String continentId, String key) {
		
		List<Location> countries = new ArrayList<Location>();
		List<Location> continents = null;
		Location continent = null;
		
		DetachedCriteriaTS<Location> locationCriteriaTS = new DetachedCriteriaTS<Location>(
				Location.class);
		
		if(continentId != null && !"".equals(continentId)){
			continent = locationDao.get(Location.class, Integer.parseInt(continentId));
			locationCriteriaTS.add(Restrictions.eq("pareLocation", continent));
		}else{
			DetachedCriteriaTS<Location> continentCriteriaTS = new DetachedCriteriaTS<Location>(
					Location.class);
			continentCriteriaTS.add(Restrictions.eq("ltype", "1"));
			continents = locationDao.find(continentCriteriaTS, 0);
		}
		
		if (key != null && !"".equals(key)) {
			locationCriteriaTS.add(Restrictions.or(
					Restrictions.like("enName", key, MatchMode.ANYWHERE),
					Restrictions.like("name", key, MatchMode.ANYWHERE)));
			locationCriteriaTS.add(Restrictions.or(
					Restrictions.like("fullPinyin", key, MatchMode.ANYWHERE),
					Restrictions.like("pinyin", key, MatchMode.ANYWHERE)));
		}
		
		if(continents != null && continents.size() > 0){
			for(Location myContinent : continents){
				locationCriteriaTS.add(Restrictions.eq("pareLocation", myContinent));
				countries.addAll(locationDao.find(locationCriteriaTS, 0));
			}
		}else{
			countries = locationDao.find(locationCriteriaTS, 0);
		}
		
		return countries;
	}

	/**
	 * 查询所有省份
	 * 
	 * @param countryId
	 *            国家id
	 * @param provinceId
	 *            省份id
	 * @param key
	 *            关键字
	 * @return
	 */
	public List<Location> findAllProvince(String countryId, String provinceId,
			String key) {
		
		List<Location> provincies = null;
		Location continent = null;
		DetachedCriteriaTS<Location> locationCriteriaTS = new DetachedCriteriaTS<Location>(
				Location.class);
		
		if(countryId != null && !"".equals(provinceId)){
			
			continent = locationDao.get(Location.class, Integer.parseInt(countryId));
			
			locationCriteriaTS.add(Restrictions.eq("pareLocation", continent));
		}else{
			return null;
		}
		
		if(provinceId != null && !"".equals(provinceId)){
			locationCriteriaTS.add(Restrictions.eq("id", Integer.parseInt(provinceId)));
		}
		
		if (key != null && !"".equals(key)) {
			locationCriteriaTS.add(Restrictions.or(
					Restrictions.like("enName", key, MatchMode.ANYWHERE),
					Restrictions.like("name", key, MatchMode.ANYWHERE)));
			locationCriteriaTS.add(Restrictions.or(
					Restrictions.like("fullPinyin", key, MatchMode.ANYWHERE),
					Restrictions.like("pinyin", key, MatchMode.ANYWHERE)));
		}
		
		provincies = locationDao.find(locationCriteriaTS, 0);
		
		return provincies;
	}

	/**
	 * 查询所有城市
	 * 
	 * @param provinceId
	 *            省份id
	 * @param cityId
	 *            城市id
	 * @param key
	 *            关键字
	 * @return
	 */
	public List<Location> findAllCity(String provinceId, String cityId,
			String key) {
		
		List<Location> cities = null;
		
		Location province = null;
		DetachedCriteriaTS<Location> locationCriteriaTS = new DetachedCriteriaTS<Location>(
				Location.class);
		
		if(provinceId != null && !"".equals(provinceId)){
			
			province = locationDao.get(Location.class, Integer.parseInt(provinceId));
			
			locationCriteriaTS.add(Restrictions.eq("pareLocation", province));
		}else{
			return null;
		}
		
		if(cityId != null && !"".equals(cityId)){
			locationCriteriaTS.add(Restrictions.eq("id", Integer.parseInt(cityId)));
		}
		
		if (key != null && !"".equals(key)) {
			locationCriteriaTS.add(Restrictions.or(
					Restrictions.like("enName", key, MatchMode.ANYWHERE),
					Restrictions.like("name", key, MatchMode.ANYWHERE)));
			locationCriteriaTS.add(Restrictions.or(
					Restrictions.like("fullPinyin", key, MatchMode.ANYWHERE),
					Restrictions.like("pinyin", key, MatchMode.ANYWHERE)));
		}
		
		cities = locationDao.find(locationCriteriaTS, 0);

		return cities;
	}
	
	/*------------------------转换vo对象---------------------*/
	
	/**
	 * 查询所有的大洲
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public List<ContinentVO> findAllContinentToVO() throws Exception {

		List<ContinentVO> continenies = null;

		DetachedCriteriaTS<Location> locationCriteriaTS = new DetachedCriteriaTS<Location>(
				Location.class);
		locationCriteriaTS.add(Restrictions.eq("ltype", "1"));
		
		List<Location> locations = locationDao.findAll(locationCriteriaTS);
		
		if(locations != null && locations.size() > 0) {
			continenies = new ArrayList<ContinentVO>();
			for(Location location : locations) {
				ContinentVO continent = new ContinentVO();
				BeanUtils.copyProperties(continent, location);
				continenies.add(continent);
			}
		}
		return continenies;
	}

	/**
	 * 查询所有国家
	 * @return
	 */
	public List<CountryVO> findAllCountryToVO() throws Exception {
		
		List<CountryVO> contries = null;
		List<Location> myContries = new ArrayList<Location>();
		
		DetachedCriteriaTS<Location> locationCriteriaTS = new DetachedCriteriaTS<Location>(
				Location.class);
		locationCriteriaTS.add(Restrictions.eq("ltype", "2"));
		
		myContries = locationDao.findAll(locationCriteriaTS);
		
		if(myContries.size() > 0) {
			contries = new ArrayList<CountryVO>();
			for(Location myContry : myContries) {
				CountryVO contry = new CountryVO();
				BeanUtils.copyProperties(contry, myContry);
				contries.add(contry);
			}
		}
		
		return contries;
	}

	/**
	 * 查询所有省份
	 * @return
	 */
	public List<ProvinceVO> findAllProvinceToVO() throws Exception {
		
		List<ProvinceVO> provincies = null;
		List<Location> provincList = null;
		DetachedCriteriaTS<Location> locationCriteriaTS = new DetachedCriteriaTS<Location>(
				Location.class);
		locationCriteriaTS.add(Restrictions.eq("ltype", "3"));
		provincList = locationDao.findAll(locationCriteriaTS);
		
		if(provincList != null && provincList.size() > 0) {
			provincies = new ArrayList<ProvinceVO>();
			for(Location provincs : provincList) {
				ProvinceVO province = new ProvinceVO();
				BeanUtils.copyProperties(province, provincs);
				provincies.add(province);
			}
		}
		return provincies;
	}

	/**
	 * 查询所有城市
	 * @return
	 */
	public List<CityVO> findAllCityToVO() throws Exception {
		
		List<Location> cityList = null;
		List<CityVO> cities = null;
		
		DetachedCriteriaTS<Location> locationCriteriaTS = new DetachedCriteriaTS<Location>(
				Location.class);
		locationCriteriaTS.add(Restrictions.eq("ltype", "4"));
		cityList = locationDao.findAll(locationCriteriaTS);
		
		if(cityList != null && cityList.size() > 0) {
			cities = new ArrayList<CityVO>();
			for(Location city : cityList) {
				CityVO cityVO = new CityVO();
				BeanUtils.copyProperties(cityVO, city);
				cities.add(cityVO);
			}
		}
		return cities;
	}

}
