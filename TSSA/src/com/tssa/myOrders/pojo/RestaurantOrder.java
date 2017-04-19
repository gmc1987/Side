/**
 * 
 */
package com.tssa.myOrders.pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * @author gmc
 *
 */
@SuppressWarnings("serial")
@Entity
@DiscriminatorValue("restaurantOrder")
public class RestaurantOrder extends Orders implements Serializable {

	/*预订桌数*/
	@Column
	private Integer quantity;
	
	/*人数*/
	@Column
	private Integer population;

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getPopulation() {
		return population;
	}

	public void setPopulation(Integer population) {
		this.population = population;
	}
	
}
