/**
 * 
 */
package com.tssa.bisServiceModel.catering.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author gmc870223
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="BUSINESS_COOPERATION_CATERINGTABLE")
public class CateringTable implements Serializable {

	/**
	 * 主键
	 */
	@Id
	@Column(length=32)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	private String uuid;
	
	/**
	 * 桌子编号
	 */
	@Column(length=32, nullable=false)
	private String tableNo;
	
	/**
	 * 商户编码
	 */
	@Column(length=32, nullable=false)
	private String cooperId;
	
	/**
	 * 桌子状态
	 */
	@Column(length=4, nullable=false)
	private String status;
	
	/**
	 * 桌子类型
	 */
	@Column(length=4, nullable=false)
	private String tableType;
	
	/**
	 * 容纳人数
	 */
	@Column(length=8, nullable=false)
	private String holdNum;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTableNo() {
		return tableNo;
	}

	public void setTableNo(String tableNo) {
		this.tableNo = tableNo;
	}

	public String getCooperId() {
		return cooperId;
	}

	public void setCooperId(String cooperId) {
		this.cooperId = cooperId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTableType() {
		return tableType;
	}

	public void setTableType(String tableType) {
		this.tableType = tableType;
	}

	public String getHoldNum() {
		return holdNum;
	}

	public void setHoldNum(String holdNum) {
		this.holdNum = holdNum;
	}
}
