/**
 * 
 */
package com.tssa.remote.object;

import java.util.Date;

/**
 * @author gmc
 *
 */
public class MemberVo extends BaseVo{

	/**
	 * 主键
	 */
	private String memberId;
	
	/**
	 * 会员手机号码
	 */
	private String memberPhoneNo;
	
	/**
	 * 会员卡号
	 */
	private String cardNo;
	
	/**
	 * 发卡日期
	 */
	private String sendDate;
	
	/**
	 * 有效日期
	 */
	private String validity;
	
	/**
	 * 发卡商户
	 */
	private String cooperation;
	
	/**
	 * 发卡商户编码
	 */
	private String cooperationCode;
	
	/**
	 * 持卡人编码
	 */
	private String customerCode;
	
	/**
	 * 持卡人姓名
	 */
	private String customerName;
	
	/**
	 * 会员卡模版
	 */
	private String cardModel;
	
	/**
	 * 会员折扣
	 */
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

	public String getSendDate() {
		return sendDate;
	}

	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}

	public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCooperation() {
		return cooperation;
	}

	public void setCooperation(String cooperation) {
		this.cooperation = cooperation;
	}

	public String getCooperationCode() {
		return cooperationCode;
	}

	public void setCooperationCode(String cooperationCode) {
		this.cooperationCode = cooperationCode;
	}

	public String getCustomerCode() {
		return customerCode;
	}

	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCardModel() {
		return cardModel;
	}

	public void setCardModel(String cardModel) {
		this.cardModel = cardModel;
	}

	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}
}
