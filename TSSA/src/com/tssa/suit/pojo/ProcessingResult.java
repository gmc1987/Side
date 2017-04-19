/**
 * 
 */
package com.tssa.suit.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author gmc
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name="SYS_PROCESSING_RESULT")
public class ProcessingResult implements Serializable {

	@Id
	@Column(length=32)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	private String resultId;
	
	@Column(length=500)
	private String processContext;
	
	@Column
	private Date processDate;
	
	@Column
	private Integer processStatus;
	
	@Column(length=32)
	private String processer;
	
	@Column(length=100)
	private String processResult;
	
//	@ManyToOne
//	@JoinColumn(name = "suitId")
//	private CustSuit suitId;

	public String getResultId() {
		return resultId;
	}

	public void setResultId(String resultId) {
		this.resultId = resultId;
	}

	public String getProcessContext() {
		return processContext;
	}

	public void setProcessContext(String processContext) {
		this.processContext = processContext;
	}

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(Date processDate) {
		this.processDate = processDate;
	}

	public Integer getProcessStatus() {
		return processStatus;
	}

	public void setProcessStatus(Integer processStatus) {
		this.processStatus = processStatus;
	}

	public String getProcesser() {
		return processer;
	}

	public void setProcesser(String processer) {
		this.processer = processer;
	}

	public String getProcessResult() {
		return processResult;
	}

	public void setProcessResult(String processResult) {
		this.processResult = processResult;
	}

//	public CustSuit getSuitId() {
//		return suitId;
//	}
//
//	public void setSuitId(CustSuit suitId) {
//		this.suitId = suitId;
//	}
	
}
