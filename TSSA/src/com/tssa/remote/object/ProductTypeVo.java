/**
 * 
 */
package com.tssa.remote.object;

import java.io.Serializable;
import java.util.Date;

/**
 * @author gmc
 * @see 产品类型分类实体
 */
@SuppressWarnings("serial")
public class ProductTypeVo extends BaseVo implements Serializable {

	private String productTypeId;
	
	/**
	 * 父级产品类型ID
	 */
	private String parentId;

	/**
	 * 类型编码
	 */
	private String typeCode;
	
	/**
	 * 类型名称
	 */
	private String typeName;
	
	/**
	 * 排序
	 */
	private Integer orderid;
	
	private Date createDate;

	public String getProductTypeId() {
		return productTypeId;
	}

	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
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

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
