/**
 * 
 */
package com.tssa.cooperationBusiness.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import com.tssa.myOrders.pojo.Orders;

/**
 * @author gmc
 * @see 产品表
 */

@Entity
@Table(name="BUSINESS_COOPERATIONBUSINESSPRODUCT")
public class CooperationBusinessProduct implements Serializable {
	
	private static final long serialVersionUID = -6285564205960683162L;
	
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String businessProductId;
	
	/**
	 * 产品图片
	 */
	@Column(length=32, nullable=true)
	private String image;
	
	/**
	 * 商户编码
	 */
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "cooperId")
	private CooperationBusiness cooperId;
	
	/**
	 * 产品类型
	 */
	@JsonIgnore
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "productTypeId")
	private CooperationProductType productType;
	
	/**
	 * 产品特殊分类
	 */
	private String specialType;
	
	/**
	 * 产品编码
	 */
	@Column(length=32, nullable=false)
	private String productCode;
	
	/**
	 * 产品名称
	 */
	@Column(length=100, nullable=false)
	private String productName;
	
	/**
	 * 产品描述
	 */
	@Column(length=1000, nullable=false)
	private String description;
	
	/**
	 * 产品总数
	 */
	@Column
	private Integer totalNum;
	
	/**
	 * 剩余数
	 */
	@Column
	private Integer residualNum;
	
	/**
	 * 单价
	 */
	@Column
	private BigDecimal singlePrice;
	
	/**
	 * 优惠价
	 */
	@Column
	private BigDecimal salePrice;
	
	/**
	 * 创建日期
	 */
	@Column
	private Date createDate;
	
	@ManyToMany(mappedBy="products", fetch=FetchType.LAZY)
	private Set<Orders> order;
	
	/**
	 * 创建人
	 */
	@Column(length=500, nullable=false)
	private String operationUser;
	
	public CooperationProductType getProductType() {
		return productType;
	}

	public void setProductType(CooperationProductType productType) {
		this.productType = productType;
	}

	public String getSpecialType() {
		return specialType;
	}

	public void setSpecialType(String specialType) {
		this.specialType = specialType;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
		this.totalNum = totalNum;
	}

	public Integer getResidualNum() {
		return residualNum;
	}

	public void setResidualNum(Integer residualNum) {
		this.residualNum = residualNum;
	}

	public BigDecimal getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(BigDecimal salePrice) {
		this.salePrice = salePrice;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getOperationUser() {
		return operationUser;
	}

	public void setOperationUser(String operationUser) {
		this.operationUser = operationUser;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBusinessProductId() {
		return businessProductId;
	}

	public void setBusinessProductId(String businessProductId) {
		this.businessProductId = businessProductId;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public BigDecimal getSinglePrice() {
		return singlePrice;
	}

	public void setSinglePrice(BigDecimal singlePrice) {
		this.singlePrice = singlePrice;
	}

	public CooperationBusiness getCooperId() {
		return cooperId;
	}

	public void setCooperId(CooperationBusiness cooperId) {
		this.cooperId = cooperId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Set<Orders> getOrder() {
		return order;
	}

	public void setOrder(Set<Orders> order) {
		this.order = order;
	}

}
