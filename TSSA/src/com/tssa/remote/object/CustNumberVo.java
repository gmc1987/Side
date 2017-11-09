/**
 * 
 */
package com.tssa.remote.object;

import java.io.Serializable;

/**
 * @author gmc870223
 *
 */
@SuppressWarnings("serial")
public class CustNumberVo extends BaseVo implements Serializable {
	
	/**
	 * 客户编号
	 */
	private String custId;

	/**
	 * 出号号码
	 */
	private String custNum;
	
	/**
	 * 商户名称
	 */
	private String vendorName;
	
	/**
	 * 商户编码
	 */
	private String vendorCode;
	/**
	 * 前方等待人数
	 */
	private int frontCount;
	
	/**
	 * 派位类型id
	 */
	private String typeId;
	
	/**
	 * 派位类型名称
	 */
	private String typeName;
	
	/**
	 * 预计等待时间
	 */
	private String estimatesTime;

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getCustNum() {
		return custNum;
	}

	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getVendorCode() {
		return vendorCode;
	}

	public void setVendorCode(String vendorCode) {
		this.vendorCode = vendorCode;
	}

	public int getFrontCount() {
		return frontCount;
	}

	public void setFrontCount(int frontCount) {
		this.frontCount = frontCount;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getEstimatesTime() {
		return estimatesTime;
	}

	public void setEstimatesTime(String estimatesTime) {
		this.estimatesTime = estimatesTime;
	}
}
