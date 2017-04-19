/**
 * 
 */
package com.tssa.base.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author gmc
 * @see 1、根据codeType判断生成编码类型（01-客户编码、02-商户编码、03-产品编码、04-商户员工编号）
 * 		2、当codeType为商户编码（02）时，busCode字段存放最新商户编码，finalCode值可能存在，该字段存放最新产品编码
 * 		3、当codeType为客户编码（01）时，busCode字段存放最新客户编码
 * 		4、当codeType为产品编码（03）时，根据busCode值生成新的finalCode值
 * 		5、当codeType为商户编码（04）时，根据busCode值生成新的finalCode值，finalCode属于员工编号
 */
@Entity
@Table(name="BUSINESS_CODE")
public class BaseCode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8281659059423587346L;

	@Id
	@Column(length=32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	String codeId;
	
	/**
	 * 编码
	 */
	@Column(length=32, nullable=false)
	String busCode;
	
	/**
	 * 编码类型
	 */
	@Column(length=2, nullable=false)
	String codeType;
	
	/**
	 * 产品编码
	 */
	@Column(length=32, nullable=true)
	String finalCode;

	public String getCodeId() {
		return codeId;
	}

	public void setCodeId(String codeId) {
		this.codeId = codeId;
	}

	public String getBusCode() {
		return busCode;
	}

	public void setBusCode(String busCode) {
		this.busCode = busCode;
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getFinalCode() {
		return finalCode;
	}

	public void setFinalCode(String finalCode) {
		this.finalCode = finalCode;
	}
}
