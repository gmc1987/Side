/**
 * 
 */
package com.tssa.allocateSeats.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author gmc
 * @see 商户出号记录对象
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "business_allocateseat_number_record")
public class AllocateSeatNumberRecord implements Serializable {

	public AllocateSeatNumberRecord() {

	}

	public AllocateSeatNumberRecord(String allocateNo, String custId,
			AllocateSeatTypeSet allocateSeatType) {
		this.allocateNo = allocateNo;
		this.custId = custId;
		this.allocateSeatType = allocateSeatType;
		this.recodeStatus = "0";
		this.createDate = new Date();
	}

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String uuid;

	@Column(length = 18, nullable = false)
	private String allocateNo;

	@ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
	@JoinColumn(name = "allocateSeatType")
	private AllocateSeatTypeSet allocateSeatType;

	@Column(length = 32)
	private String custId;

	@Column(length = 4, nullable = false)
	private String recodeStatus;

	@Column
	private Date createDate;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getAllocateNo() {
		return allocateNo;
	}

	public void setAllocateNo(String allocateNo) {
		this.allocateNo = allocateNo;
	}

	public AllocateSeatTypeSet getAllocateSeatType() {
		return allocateSeatType;
	}

	public void setAllocateSeatType(AllocateSeatTypeSet allocateSeatType) {
		this.allocateSeatType = allocateSeatType;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getRecodeStatus() {
		return recodeStatus;
	}

	public void setRecodeStatus(String recodeStatus) {
		this.recodeStatus = recodeStatus;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
