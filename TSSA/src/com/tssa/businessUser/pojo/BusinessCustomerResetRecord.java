/**
 * 
 */
package com.tssa.businessUser.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author gmc
 * @see 重置密码记录对象
 */
@SuppressWarnings("serial")
@Entity
@Table(name="BUSINESS_CUSTOMER_RESET")
public class BusinessCustomerResetRecord implements Serializable {

	/**
	 * 记录id
	 */
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String recordId;
	
	/**
	 * 申请重置用户编码
	 */
	@Column(name="reset_user", length=32, nullable=false)
	private String resetCustomerCode;
	
	/**
	 * 申请重置用户手机
	 */
	@Column(name="mobilePhone", length=20, nullable=false)
	private String mobilePhone;
	
	/**
	 * 处理用户编码
	 */
	@Column(name="audit_user", length=32, nullable=true)
	private String auditCustomerCode;
	
	/**
	 * 申请重置用户名
	 */
	@Column(name="reset_user_name", length=32, nullable=false)
	private String resetCustomerName;
	
	/**
	 * 录入日期
	 */
	@Column(name="entry_date", nullable=false)
	private Date entryDate;
	
	/**
	 * 审批日期
	 */
	@Column(name="audit_date", nullable=true)
	private Date auditDate;
	
	/**
	 * 流水状态
	 */
	@Column(name="serial_status", nullable=false, length=4)
	private Integer serialStatus;
	
	@Column(name="remark", nullable=true, length=200)
	private String remark;

	public String getRecordId() {
		return recordId;
	}

	public void setRecordId(String recordId) {
		this.recordId = recordId;
	}

	public String getResetCustomerCode() {
		return resetCustomerCode;
	}

	public void setResetCustomerCode(String resetCustomerCode) {
		this.resetCustomerCode = resetCustomerCode;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAuditCustomerCode() {
		return auditCustomerCode;
	}

	public void setAuditCustomerCode(String auditCustomerCode) {
		this.auditCustomerCode = auditCustomerCode;
	}

	public String getResetCustomerName() {
		return resetCustomerName;
	}

	public void setResetCustomerName(String resetCustomerName) {
		this.resetCustomerName = resetCustomerName;
	}

	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public Integer getSerialStatus() {
		return serialStatus;
	}

	public void setSerialStatus(Integer serialStatus) {
		this.serialStatus = serialStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
