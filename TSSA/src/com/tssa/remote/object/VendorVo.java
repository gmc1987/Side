/**
 * 
 */
package com.tssa.remote.object;

import java.io.Serializable;

/**
 * @author gmc
 * @see 商户手机端实体对象
 * @version 1.0
 */ 
@SuppressWarnings("serial")
public class VendorVo extends BaseVo implements Serializable {

	/**
	 * 商户id
	 */
	private String businessId;
	
	/**
	 * 商户编码
	 */
	private String businessCode;
	
	/**
	 * 商户名称
	 */
	private String businessName;
	
	/**
	 * 商户类型
	 */
	private String productTypeId;
	
	/**
	 * 行业类型名称
	 */
	private String productTypeName;
	
	/**
	 * 国家id
	 */
	private String countryId;
	
	/**
	 * 省份id
	 */
	private String provinceId;
	
	/**
	 * 城市id
	 */
	private String cityId;
	
	/**
	 * 详细地址
	 */
	private String detailAddress;
	
	/**
	 * 国家名称
	 */
	private String countryName;
	
	/**
	 * 省份名称
	 */
	private String provinceName;
	
	/**
	 * 城市名称
	 */
	private String cityName;
	
	/**
	 * 商户电话1
	 */
	private String tel1;
	
	/**
	 * 电话2
	 */
	private String tel2;
	
	/**
	 * 商户完整地址
	 */
	private String address;
	
	/**
	 * 商户logo路径
	 */
	private String logoImageURL;
	
	/**
	 * 经度
	 */
	private String longitude;
	
	/**
	 * 纬度
	 */
	private String latitude;
	
	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public String getProductTypeName() {
		return productTypeName;
	}

	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}

	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getDetailAddress() {
		return detailAddress;
	}

	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLogoImageURL() {
		return logoImageURL;
	}

	public void setLogoImageURL(String logoImageURL) {
		this.logoImageURL = logoImageURL;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

}
