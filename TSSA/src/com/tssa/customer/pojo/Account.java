package com.tssa.customer.pojo;

import java.io.Serializable;
import java.util.Date;

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
 * 类名 : Account.java
 * <p> 类说明 </p>
 * @author lch
 * Date: 2013-11-13
 * @version 1.01
 */
@Entity
@Table(name="CUST_ACCOUNT")
public class Account implements Serializable{

	/** 属性说明 */
	private static final long serialVersionUID = 4669965400324065960L;
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String auid;
	
	/*客户编码*/
	@Column(length=16, nullable=false)
	private String aid;
	
	/**/
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id", nullable = true)
	private Customer customer;
	
	/*总额*/
	@Column
	private Long totalMoney;
	
	/*积分*/
	@Column
	private Long integral;
	
	/*使用次数*/
	@Column
	private Long useCount;
	
	/*创建时间*/
	@Column
	private Date createDate;

	public String getAuid() {
		return auid;
	}

	public void setAuid(String auid) {
		this.auid = auid;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Long totalMoney) {
		this.totalMoney = totalMoney;
	}

	public Long getIntegral() {
		return integral;
	}

	public void setIntegral(Long integral) {
		this.integral = integral;
	}

	public Long getUseCount() {
		return useCount;
	}

	public void setUseCount(Long useCount) {
		this.useCount = useCount;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
