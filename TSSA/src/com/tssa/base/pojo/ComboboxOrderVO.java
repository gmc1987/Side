/**
 * 
 */
package com.tssa.base.pojo;

import java.io.Serializable;

/**
 * @author gmc
 * @see 顺序下拉列表对象
 */
public class ComboboxOrderVO implements Serializable {
	
	public ComboboxOrderVO(int display, int orderValue){
		this.display = display;
		this.orderValue = orderValue;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int display;
	
	private int orderValue;

	public int getDisplay() {
		return display;
	}

	public void setDisplay(int display) {
		this.display = display;
	}

	public int getOrderValue() {
		return orderValue;
	}

	public void setOrderValue(int orderValue) {
		this.orderValue = orderValue;
	}

}
