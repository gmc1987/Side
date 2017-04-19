/**
 * 
 */
package com.tssa.suit.pojo;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.customer.pojo.Customer;

/**
 * @author gmc
 * @see 用户投诉对象
 */
@SuppressWarnings("serial")
@Entity
@Table(name="CUST_SUIT")
public class CustSuit implements Serializable {

	@Id
	@Column(length=32)
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy="uuid")
	private String suitId;
	
	@Column(length=100)
	private String title;
	
	@Column(length=500)
	private String content;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="cooperId")
	private CooperationBusiness suitObject;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="id")
	private Customer suiter;
	
	@Transient
	private String customerId;
	
	@Transient
	private String buisnessId;
	
	@Transient
	private String suiTypeId;
	
	@Transient
	private String processContext;
	
	@Column
	private Date suitDate;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="typeId")
	private SuitType suiType;
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="resultId")
	private Set<ProcessingResult> results;

	public String getSuitId() {
		return suitId;
	}

	public void setSuitId(String suitId) {
		this.suitId = suitId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public CooperationBusiness getSuitObject() {
		return suitObject;
	}

	public void setSuitObject(CooperationBusiness suitObject) {
		this.suitObject = suitObject;
	}

	public Customer getSuiter() {
		return suiter;
	}

	public void setSuiter(Customer suiter) {
		this.suiter = suiter;
	}

	public Date getSuitDate() {
		return suitDate;
	}

	public void setSuitDate(Date suitDate) {
		this.suitDate = suitDate;
	}

	public SuitType getSuiType() {
		return suiType;
	}

	public void setSuiType(SuitType suiType) {
		this.suiType = suiType;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getBuisnessId() {
		return buisnessId;
	}

	public void setBuisnessId(String buisnessId) {
		this.buisnessId = buisnessId;
	}

	public String getSuiTypeId() {
		return suiTypeId;
	}

	public void setSuiTypeId(String suiTypeId) {
		this.suiTypeId = suiTypeId;
	}

	public String getProcessContext() {
		return processContext;
	}

	public void setProcessContext(String processContext) {
		this.processContext = processContext;
	}

	public Set<ProcessingResult> getResults() {
		return results;
	}

	public void setResults(Set<ProcessingResult> results) {
		this.results = results;
	}
	
	
}
