/**
 * 
 */
package com.tssa.commissionSetting.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tssa.cooperationBusiness.pojo.CooperationBusiness;

/**
 * @author gmc
 * @see 商户佣金设置实体
 */
@SuppressWarnings("serial")
@Entity
@Table(name="BUSINESS_COMMISSION")
public class BusinessCommissionSetting implements Serializable {

	/**
	 * id
	 */
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String commissionId;
	
	/**
	 * 商户
	 */
	@OneToOne
	@JoinColumn(name = "cooperId", nullable = false)
	private CooperationBusiness cooperationBusiness;
	
	/**
	 * 佣金百分比
	 */
	@Column
	private double commissionPercentage;
	
	/**
	 * 结算周期
	 */
	@Column
	private Integer cycle;
	
	/**
	 * 周期单位,0-日,1-周,2-月,3-季度,4-年
	 */
	@Column
	private Integer cycleUnit;
	
	/**
	 * 创建人
	 */
	@Column(length=32, nullable=false)
	private String createUser;
	
	/**
	 * 创建时间
	 */
	@Column
	private Date createDate;

	public String getCommissionId() {
		return commissionId;
	}

	public void setCommissionId(String commissionId) {
		this.commissionId = commissionId;
	}

	public CooperationBusiness getCooperationBusiness() {
		return cooperationBusiness;
	}

	public void setCooperationBusiness(CooperationBusiness cooperationBusiness) {
		this.cooperationBusiness = cooperationBusiness;
	}

	public double getCommissionPercentage() {
		return commissionPercentage;
	}

	public void setCommissionPercentage(double commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public Integer getCycleUnit() {
		return cycleUnit;
	}

	public void setCycleUnit(Integer cycleUnit) {
		this.cycleUnit = cycleUnit;
	}

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
	
}
