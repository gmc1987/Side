/**
 * 
 */
package com.tssa.allocateSeats.vo;

import java.io.Serializable;

/**
 * @author gmc870223
 *
 */
public class AllocateSeatNumberSetVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4164470354054606673L;
	
	/**
	 * uuid
	 */
	private String uuid;
	
	private String numUuid;
	
	/**
	 * 商户名称
	 */
	private String cooperName;
	
	/**
	 * 当前号
	 */
	private String num;
	
	/**
	 * 出号规则ID
	 */
	private String typeId;
	
	/**
	 * 规则名称
	 */
	private String typeName;
	
	/**
	 * 客户编号
	 */
	private String custNo;
	
	/**
	 * 创建日期
	 */
	private String createDate;
	
	/**
	 * 状态
	 */
	private String status;
	
	/**
	 * 备注
	 */
	private String remark;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNumUuid() {
		return numUuid;
	}

	public void setNumUuid(String numUuid) {
		this.numUuid = numUuid;
	}

	public String getCooperName() {
		return cooperName;
	}

	public void setCooperName(String cooperName) {
		this.cooperName = cooperName;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
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

	public String getCustNo() {
		return custNo;
	}

	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
