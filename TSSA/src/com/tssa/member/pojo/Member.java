/**
 * 
 */
package com.tssa.member.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.customer.pojo.Customer;

/**
 * @author gmc
 *
 */

@SuppressWarnings("serial")
@Entity
@Table(name="BUSINESS_MEMBER")
public class Member implements Serializable {

	/**
	 * 主键
	 */
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String memberId;
	
	/**
	 * 会员手机号码
	 */
	@Column(length=32, nullable=false)
	private String memberPhoneNo;
	
	/**
	 * 会员卡号
	 */
	@Column(length=32, nullable=false)
	private String cardNo;
	
	/**
	 * 发卡日期
	 */
	@Column
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date sendDate;
	
	/**
	 * 有效日期
	 */
	@Column
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date validity;
	
	/**
	 * 发卡商户
	 */
	@OneToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="cooperId", nullable=false)
	private CooperationBusiness cooperation;
	
	/**
	 * 持卡人
	 */
	@ManyToOne(cascade=CascadeType.MERGE)
	@JoinColumn(name="cust_id", nullable=false)
	private Customer customer;
	
	/**
	 * 会员折扣
	 */
	@Column
	private Double discount;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberPhoneNo() {
		return memberPhoneNo;
	}

	public void setMemberPhoneNo(String memberPhoneNo) {
		this.memberPhoneNo = memberPhoneNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Date getValidity() {
		return validity;
	}

	public void setValidity(Date validity) {
		this.validity = validity;
	}

	public CooperationBusiness getCooperation() {
		return cooperation;
	}

	public void setCooperation(CooperationBusiness cooperation) {
		this.cooperation = cooperation;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}
