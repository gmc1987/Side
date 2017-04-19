/**
 * 
 */
package com.tssa.cooperationBusiness.pojo;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author gmc
 * @see 商户地址关系表
 */
@Entity
@Table(name="BUSINESS_COOPERATION_REFERENCE")
public class CooperationBusinessLocationRefence implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2848878349741370594L;

	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String referenceId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "cooperId", nullable = true)
	private CooperationBusiness cooperation;
	
	@Column(nullable=false)
	private Integer countryId;
	
	@Column(nullable=false)
	private Integer provinceId;
	
	@Column(nullable=false)
	private Integer cityId;
	
	@Column(length=500, nullable=false)
	private String detailAddress;
	
	@Column(length=100, nullable=false)
	private String countryName;
	
	@Column(length=100, nullable=false)
	private String provinceName;
	
	@Column(length=100, nullable=false)
	private String cityName;

	
	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public CooperationBusiness getCooperation() {
		return cooperation;
	}

	public void setCooperation(CooperationBusiness cooperation) {
		this.cooperation = cooperation;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
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
	
}
