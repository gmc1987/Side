/**
 * 
 */
package com.tssa.myOrders.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author gmc870223
 *
 */
@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("shopOrder")
public class ShopOrder extends Orders implements Serializable {

	/**
	 * 收货地址
	 */
	@Column(length=255)
	private String toAddress;
	
	/**
	 * 发件地址
	 */
	@Column(length=255)
	private String formAddress;
	
	/**
	 * 收件人id
	 */
	@Column(length=32)
	private String toUserId;
	
	/**
	 * 寄件人id
	 */
	@Column(length=32)
	private String formUserId;
	
	/**
	 * 收件人手机
	 */
	@Column(length=20)
	private String toUserPhone;
	
	/**
	 * 寄件人手机
	 */
	@Column(length=20)
	private String formUserPhone;
	
	/**
	 * 快递单号
	 */
	@Column(length=32)
	private String postListId;
	
	/**
	 * 订单状态：0-待发货
	 * 		   1-已发货
	 * 		   2-已收获
	 */
	@Column
	private String shopOrderStatus;

	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getFormAddress() {
		return formAddress;
	}

	public void setFormAddress(String formAddress) {
		this.formAddress = formAddress;
	}

	public String getToUserId() {
		return toUserId;
	}

	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
	}

	public String getFormUserId() {
		return formUserId;
	}

	public void setFormUserId(String formUserId) {
		this.formUserId = formUserId;
	}

	public String getToUserPhone() {
		return toUserPhone;
	}

	public void setToUserPhone(String toUserPhone) {
		this.toUserPhone = toUserPhone;
	}

	public String getFormUserPhone() {
		return formUserPhone;
	}

	public void setFormUserPhone(String formUserPhone) {
		this.formUserPhone = formUserPhone;
	}

	public String getPostListId() {
		return postListId;
	}

	public void setPostListId(String postListId) {
		this.postListId = postListId;
	}

	public String getShopOrderStatus() {
		return shopOrderStatus;
	}

	public void setShopOrderStatus(String shopOrderStatus) {
		this.shopOrderStatus = shopOrderStatus;
	}
}
