/**
 * 
 */
package com.tssa.allocateSeats.pojo;

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
 * @see 出号类别设置对象
 */
@Entity
@Table(name="business_allocateSeatType")
public class AllocateSeatTypeSet implements Serializable {

	private static final long serialVersionUID = -2701809683706382736L;
	
	/**
	 * 主键
	 */
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String uuid;
	
	/**
	 * 出号类别编码
	 */
	@Column(length=32, nullable=false)
	private String typeCode;
	
	/**
	 * 出号类别名称
	 */
	@Column(length=100, nullable=false)
	private String typeName;
	
	/**
	 * 创建人
	 */
	@Column(length=50, nullable=false)
	private String creater;
	
	/**
	 * 创建日期
	 */
	@Column
	private Date createDate;
	
	/**
	 * 商户编码
	 */
	@Column(length=32, nullable=false)
	private String businessCustomerCode;
	
	/**
	 * 最大号数
	 */
	@Column(length=18, nullable=true)
	private Integer maxNumber;
	
	/**
	 * 出号规则
	 */
	@Column(length=200, nullable=true)
	private String allocateReg;
	
	/**
	 * 出号基数
	 */
	@Column(length=4, nullable=false)
	private Integer basiceNumber;
	
	/**
	 * 是否循环 0-否，1-是
	 */
	@Column(length=1, nullable=false)
	private Integer isCycle;
	
	/**
	 * 备注
	 */
	@Column(length=500, nullable=true)
	private String remark;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getCreater() {
		return creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getBusinessCustomerCode() {
		return businessCustomerCode;
	}

	public void setBusinessCustomerCode(String businessCustomerCode) {
		this.businessCustomerCode = businessCustomerCode;
	}

	public Integer getMaxNumber() {
		return maxNumber;
	}

	public void setMaxNumber(Integer maxNumber) {
		this.maxNumber = maxNumber;
	}

	public String getAllocateReg() {
		return allocateReg;
	}

	public void setAllocateReg(String allocateReg) {
		this.allocateReg = allocateReg;
	}

	public Integer getBasiceNumber() {
		return basiceNumber;
	}

	public void setBasiceNumber(Integer basiceNumber) {
		this.basiceNumber = basiceNumber;
	}

	public Integer getIsCycle() {
		return isCycle;
	}

	public void setIsCycle(Integer isCycle) {
		this.isCycle = isCycle;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
