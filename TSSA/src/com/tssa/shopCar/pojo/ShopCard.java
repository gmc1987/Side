/**
 * 购物车实体
 */
package com.tssa.shopCar.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;
import com.tssa.customer.pojo.Customer;

/**
 * @author gmc870223
 *
 */

@Entity
@Table(name="BUSINESS_SHOPCAR")
public class ShopCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String shopCardId;
	
	@Column
	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name = "id", insertable=true, nullable=true)
	private Customer customer;
	
	@Column
	@OneToMany(mappedBy="businessProductId", cascade=CascadeType.REMOVE, fetch=FetchType.LAZY)
	private Set<CooperationBusinessProduct> products;
	
	@Column
	private Double price;
	
	@Column
	private Double totalPrice;
	
	@Column
	private Date createDate;
	
	@Column
	private Date LastUpdateTime;

	public String getShopCardId() {
		return shopCardId;
	}

	public void setShopCardId(String shopCardId) {
		this.shopCardId = shopCardId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Set<CooperationBusinessProduct> getProducts() {
		return products;
	}

	public void setProducts(Set<CooperationBusinessProduct> products) {
		this.products = products;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateTime() {
		return LastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		LastUpdateTime = lastUpdateTime;
	}

}
