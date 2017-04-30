/**
 * 
 */
package com.tssa.businessUser.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;

import com.tssa.role.pojo.Role;

/**
 * @author gmc
 * @see 商户用户实体
 */
@SuppressWarnings("serial")
@Entity
@Table(name="BUSINESS_CUSTOMER")
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BusinessCustomer implements Serializable {

	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String businessCustomerId;
	
	@Column(length=32, nullable=false)
	private String businessCustomerCode;
	
	@Column(length=50)
	private String businessCustomerName;
	
	@Column(length=32)
	private String vendorCode;
	
	@Column(length=32, nullable=false)
	private String password;
	
	@Column(length=32, nullable=false)
	private String mobilePhone;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="rid")
	private Role rid;
	
	@Column(nullable=false)
	private Date createDate;
	
	@Column(length=32)
	private String createUser;
	
	@Column(length=32)
	private String createUserCode;
	
	@Column
	private Integer status;

	public String getBusinessCustomerId() {
		return businessCustomerId;
	}

	public void setBusinessCustomerId(String businessCustomerId) {
		this.businessCustomerId = businessCustomerId;
	}

	public String getBusinessCustomerCode() {
		return businessCustomerCode;
	}

	public void setBusinessCustomerCode(String businessCustomerCode) {
		this.businessCustomerCode = businessCustomerCode;
	}

	public String getBusinessCustomerName() {
		return businessCustomerName;
	}

	public void setBusinessCustomerName(String businessCustomerName) {
		this.businessCustomerName = businessCustomerName;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Role getRid() {
		return rid;
	}

	public void setRid(Role rid) {
		this.rid = rid;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateUserCode() {
		return createUserCode;
	}

	public void setCreateUserCode(String createUserCode) {
		this.createUserCode = createUserCode;
	}
}
