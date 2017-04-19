/**
 * 
 */
package com.tssa.myOrders.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author gmc
 *
 */
@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("hotelOrder")
public class HotelOrder extends Orders implements Serializable{

	/*入住日期*/
	@Column
	private Date startDate;
	
	/*离开日期*/
	@Column
	private Date endDate;
	
	/*入住天数*/
	@Column
	private Integer inNum;
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getInNum() {
		return inNum;
	}

	public void setInNum(Integer inNum) {
		this.inNum = inNum;
	}

}
