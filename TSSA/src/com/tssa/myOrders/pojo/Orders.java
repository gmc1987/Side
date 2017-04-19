/**
 * 
 */
package com.tssa.myOrders.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;

/**
 * @author gmc
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="CUST_ORDER")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="orderType")
@DiscriminatorValue("orderMain")
public class Orders implements Serializable {

	/*订单编号*/
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "orderGenerator")
	@GenericGenerator(name = "orderGenerator", strategy = "assigned")
	private String orderId;
	
	/*下单时间*/
	@Column
	private Date createDate;
	
	/*预约时间*/
	@Column
	private Date appointmentTime;
	
	/*商户编码*/
	@Column(length=32, nullable=false)
	private String vendorCode;
	
	/*商户名称*/
	@Column(length=50, nullable=false)
	private String vendorName;
	
	/*客户编码*/
	@Column(length=32, nullable=false)
	private String custId;
	
	/*客户姓名*/
	@Column(length=50, nullable=false)
	private String custName;
	
	/*客户手机*/
	@Column(length=32, nullable=false)
	private String custPhone;
	
	/**
	 * 结算价
	 */
	@Column
	private BigDecimal settlementPrice;
	
	/**
	 * 附加信息
	 */
	@Column(length=1000, nullable=true)
	private String extraMessage;
	
	/**
	 * 下单渠道（1-手机渠道、2-网站渠道、3-第三方接入渠道）
	 * 
	 **/
	@Column
	private Integer channelCode;
	
	/**
	 * 订单状态， 0-新建，1-执行中 ，2-已完成，3-已过期，4-归档
	 */
	@Column
	private Integer orderStatus;
	
//	/*产品编码*/
	@JsonIgnore
	@ManyToMany(fetch=FetchType.LAZY, cascade={CascadeType.REFRESH})
	@JoinTable(name="CUST_ORDER_PRODUCT",
	joinColumns={@JoinColumn(name="orderId",referencedColumnName="orderId")},
	inverseJoinColumns={@JoinColumn(name="businessProductId",referencedColumnName="businessProductId")})
	private Set<CooperationBusinessProduct> products;
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Integer getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(Integer channelCode) {
		this.channelCode = channelCode;
	}

	public Set<CooperationBusinessProduct> getProducts() {
		return products;
	}

	public void setProducts(Set<CooperationBusinessProduct> products) {
		this.products = products;
	}

	public BigDecimal getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(BigDecimal settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public String getExtraMessage() {
		return extraMessage;
	}

	public void setExtraMessage(String extraMessage) {
		this.extraMessage = extraMessage;
	}

	public Date getAppointmentTime() {
		return appointmentTime;
	}

	public void setAppointmentTime(Date appointmentTime) {
		this.appointmentTime = appointmentTime;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}

}
