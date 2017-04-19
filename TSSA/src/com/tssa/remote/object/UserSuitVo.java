/**
 * 
 */
package com.tssa.remote.object;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.customer.pojo.Customer;
import com.tssa.suit.pojo.ProcessingResult;
import com.tssa.suit.pojo.SuitType;

/**
 * @author gmc
 * @see 用户投诉数据实体
 */
public class UserSuitVo extends BaseVo {
	
	private String suitId;
	
	private String title;
	
	private String content;
	
	private CooperationBusiness suitObject;
	
	private Customer suiter;
	
//	private String customerId;
//	
//	private String buisnessId;
//	
//	private String suiTypeId;
	
	private String processContext;
	
	private Date suitDate;
	
	private SuitType suiType;
	
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

//	public String getCustomerId() {
//		return customerId;
//	}
//
//	public void setCustomerId(String customerId) {
//		this.customerId = customerId;
//	}
//
//	public String getBuisnessId() {
//		return buisnessId;
//	}
//
//	public void setBuisnessId(String buisnessId) {
//		this.buisnessId = buisnessId;
//	}
//
//	public String getSuiTypeId() {
//		return suiTypeId;
//	}
//
//	public void setSuiTypeId(String suiTypeId) {
//		this.suiTypeId = suiTypeId;
//	}

	public String getProcessContext() {
		return processContext;
	}

	public void setProcessContext(String processContext) {
		this.processContext = processContext;
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

	public Set<ProcessingResult> getResults() {
		return results;
	}

	public void setResults(Set<ProcessingResult> results) {
		this.results = results;
	}
}
