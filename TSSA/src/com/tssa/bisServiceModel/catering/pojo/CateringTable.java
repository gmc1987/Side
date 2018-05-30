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
import javax.persistence.Transient;

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
	
	/**
	 * 餐桌二维码
	 */
	@Column(length=250, nullable=true)
	private String qrCodeURL;
	
	@Transient
	private String qz;
	
	@Transient
	private String tableTypeText;
	@Transient
	private String statusText;
	@Transient
	private String qrCodeFileURL;
	

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

	public String getQz() {
		return qz;
	}

	public void setQz(String qz) {
		this.qz = qz;
	}

	public String getTableTypeText() {
		if("0".equals(this.tableType)) {
			tableTypeText = "小桌";
		} else if("1".equals(this.tableType)) {
			tableTypeText = "中桌";
		} else {
			tableTypeText = "大桌";
		}
		return tableTypeText;
	}

	public void setTableTypeText(String tableTypeText) {
		this.tableTypeText = tableTypeText;
	}

	public String getStatusText() {
		statusText = "0".equals(this.status) ? "可用" : "不可用";
		return statusText;
	}

	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getQrCodeURL() {
		return qrCodeURL;
	}

	public void setQrCodeURL(String qrCodeURL) {
		this.qrCodeURL = qrCodeURL;
	}

	public String getQrCodeFileURL() {
		return qrCodeFileURL;
	}

	public void setQrCodeFileURL(String qrCodeFileURL) {
		this.qrCodeFileURL = qrCodeFileURL;
	}
}
