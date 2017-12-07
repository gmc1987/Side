/**
 * 
 */
package com.tssa.remote.object;

import java.io.Serializable;

/**
 * @author gmc870223
 *
 */
public class CustTakeNumberVO extends BaseVo implements Serializable {

	public CustTakeNumberVO() {
	}

	public CustTakeNumberVO(String numberId) {
		this.numberId = numberId;
	}

	public CustTakeNumberVO(String numberId, String vendorCode, String vendorName, String number, String waitNum,
			String status, String takeTime) {
		this.numberId = numberId;
		this.vendorCode = vendorCode;
		this.vendorName = vendorName;
		this.number = number;
		this.waitNum = waitNum;
		this.status = status;
		this.takeTime = takeTime;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 出号记录主键
	 */
	private String numberId;

	/**
	 * 商户编码
	 */
	private String vendorCode;

	/**
	 * 商户名称
	 */
	private String vendorName;

	/**
	 * 当前号码
	 */
	private String number;

	/**
	 * 前方等待人数
	 */
	private String waitNum;

	/**
	 * 当前状态
	 */
	private String status;

	/**
	 * 取号时间
	 */
	private String takeTime;

	public String getNumberId() {
		return numberId;
	}

	public void setNumberId(String numberId) {
		this.numberId = numberId;
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

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getWaitNum() {
		return waitNum;
	}

	public void setWaitNum(String waitNum) {
		this.waitNum = waitNum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(String takeTime) {
		this.takeTime = takeTime;
	}

}
