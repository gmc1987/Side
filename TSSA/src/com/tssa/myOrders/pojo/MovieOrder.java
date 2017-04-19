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
@DiscriminatorValue("movieOrder")
public class MovieOrder extends Orders implements Serializable {

	/*放映时间*/
	@Column
	private Date useTime;
	
	/*放映厅名称*/
	@Column(length=200, nullable=true)
	private String showHallName;
	
	/*放映厅编号*/
	@Column
	private Integer showHallNum;
	
	/*座位号*/
	@Column
	private Integer seatNum;
	
	/*行号*/
	@Column
	private Integer lineNum; 
	
	/*数量*/
	@Column
	private Integer movieNum;
	
	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	public String getShowHallName() {
		return showHallName;
	}

	public void setShowHallName(String showHallName) {
		this.showHallName = showHallName;
	}

	public Integer getShowHallNum() {
		return showHallNum;
	}

	public void setShowHallNum(Integer showHallNum) {
		this.showHallNum = showHallNum;
	}

	public Integer getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(Integer seatNum) {
		this.seatNum = seatNum;
	}

	public Integer getLineNum() {
		return lineNum;
	}

	public void setLineNum(Integer lineNum) {
		this.lineNum = lineNum;
	}

	public Integer getMovieNum() {
		return movieNum;
	}

	public void setMovieNum(Integer movieNum) {
		this.movieNum = movieNum;
	}

}
