/**
 * 
 */
package com.tssa.cooperationBusiness.pojo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * @author gmc
 * @see 合作商实体
 */
@Entity
@Table(name="BUSINESS_COOPERATIONBUSINESS")
public class CooperationBusiness implements Serializable {

	private static final long serialVersionUID = 9029451649308884064L;
	
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String cooperId;
	
	/**
	 * 商户编码
	 */
	@Column(length=32, nullable=false)
	private String cooperCode;
	
	/**
	 * 商户名称
	 */
	@Column(length=50, nullable=false)
	private String cooperName;
	
	/**
	 * 行业类型
	 */
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "productTypeId")
	private CooperationProductType productTypeId;
	
	/**
	 * 地址关系
	 */
	@OneToOne(cascade = CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "referenceId", insertable=true, nullable=true)
	private CooperationBusinessLocationRefence locationReference;
	
	/**
	 * 商户地址
	 */
	@Column(length=200, nullable=false)
	private String address;
	
	/**
	 * 电话号码
	 */
	@Column(length=20, nullable=false)
	private String tel1;
	
	/**
	 * 电话号码2
	 */
	@Column(length=20, nullable=true)
	private String tel2;
	
	/**
	 * 营业执照编号
	 */
	@Column(length=32, nullable=false)
	private String businessLicense;
	
	/**
	 * 营业执照照片地址
	 */
	@Column(length=1000, nullable=true)
	private String pictureUrl;
	
	/**
	 * 加盟时间
	 */
	@Column(nullable=false)
	private Date allianceDate;
	
	/**
	 * 银行帐号
	 */
	@Column(length=50, nullable=false)
	private String account;
	
	/**
	 * 审核标志
	 */
	@Column(length=2, nullable=false)
	private String verificationState;
	
	/**
	 * 操作人
	 */
	@Column(length=32, nullable=false)
	private String operationUser;
	
//	/**
//	 * 产品
//	 */
//	@OneToMany(mappedBy = "businessProductId", fetch=FetchType.LAZY, cascade={CascadeType.REMOVE, CascadeType.REFRESH})
//	private Set<CooperationBuseinssProduct> products = new HashSet<CooperationBuseinssProduct>();
//	
	/**
	 * 显示日期字段
	 */
	private String allianceFormatDate;
	
	/**
	 * 商户logo
	 */
	@Column(length=1000, nullable=true)
	private String logoImageURL;
	
	public String getCooperId() {
		return cooperId;
	}
	public void setCooperId(String cooperId) {
		this.cooperId = cooperId;
	}
	public String getCooperCode() {
		return cooperCode;
	}
	public void setCooperCode(String cooperCode) {
		this.cooperCode = cooperCode;
	}
	public String getCooperName() {
		return cooperName;
	}
	public void setCooperName(String cooperName) {
		this.cooperName = cooperName;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}
	public void setBusinessLicense(String businessLicense) {
		this.businessLicense = businessLicense;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public Date getAllianceDate() {
		return allianceDate;
	}
	public void setAllianceDate(Date allianceDate) {
		this.allianceDate = allianceDate;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getAllianceFormatDate() {
		return allianceFormatDate;
	}
	public void setAllianceFormatDate(Date allianceFormatDate) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String formatDate = format.format(getAllianceDate());
		this.allianceFormatDate = formatDate;
	}
	public String getVerificationState() {
		return verificationState;
	}
	public void setVerificationState(String verificationState) {
		this.verificationState = verificationState;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getOperationUser() {
		return operationUser;
	}
	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}
//	public Set<CooperationBuseinssProduct> getProducts() {
//		return products;
//	}
//	public void setProducts(Set<CooperationBuseinssProduct> products) {
//		this.products = products;
//	}
	public CooperationProductType getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(CooperationProductType productTypeId) {
		this.productTypeId = productTypeId;
	}
	public CooperationBusinessLocationRefence getLocationReference() {
		return locationReference;
	}
	public void setLocationReference(
			CooperationBusinessLocationRefence locationReference) {
		this.locationReference = locationReference;
	}
	public String getLogoImageURL() {
		return logoImageURL;
	}
	public void setLogoImageURL(String logoImageURL) {
		this.logoImageURL = logoImageURL;
	}
	
}
