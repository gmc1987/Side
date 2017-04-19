/**
 * 
 */
package com.tssa.cooperationBusiness.pojo;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;


/**
 * @author gmc
 * @see 产品类型
 */

@Entity
@Table(name="BUSINESS_COOPERATIONPRODUCTTYPE")
public class CooperationProductType implements Serializable {
	
	private static final long serialVersionUID = 6770010480415745359L;
	
	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String productTypeId;
	
	/**
	 * 父级产品类型ID
	 */
	@ManyToOne
	@JoinColumn(name = "pareId")
	private CooperationProductType pareTypes;

	/**
	 * 子级产品类型ID集
	 */
	@OneToMany(mappedBy = "pareTypes", cascade = CascadeType.REMOVE, fetch=FetchType.LAZY)
	private Set<CooperationProductType> subProductTypes = new HashSet<CooperationProductType>(0);
	
	/**
	 * 类型编码
	 */
	@Column(length=32, nullable=false)
	private String typeCode;
	
	/**
	 * 类型名称
	 */
	@Column(length=100, nullable=false)
	private String typeName;
	
	/**
	 * 排序
	 */
	@Column
	private Integer orderid;
	
	@Column(nullable=false)
	private Date createDate;
	
	/**
	 * 操作用户
	 */
	@Column(length=32, nullable=false)
	private String operationUser;


	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}

	public CooperationProductType getPareTypes() {
		return pareTypes;
	}

	public void setPareTypes(CooperationProductType pareTypes) {
		this.pareTypes = pareTypes;
	}

	public Set<CooperationProductType> getSubProductTypes() {
		return subProductTypes;
	}

	public void setSubProductTypes(Set<CooperationProductType> subProductTypes) {
		this.subProductTypes = subProductTypes;
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

	public Integer getOrderid() {
		return orderid;
	}

	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
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

}
