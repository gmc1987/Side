/**
 * 
 */
package com.tssa.myOrders.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.common.service.BaseBusinessService;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;
import com.tssa.myOrders.dao.IOrderDao;
import com.tssa.myOrders.pojo.HotelOrder;

/**
 * @author gmc
 *
 */
@Service
public class HotelOrderService extends BaseBusinessService<HotelOrder> {

	@Autowired
	private IOrderDao orderDao;
	
	/** 根据orderId查询订单所有产品信息
	 * @param orderId
	 * @return
	**/
	public List<CooperationBusinessProduct> findOrderProductById(String orderId){
		
		List<CooperationBusinessProduct> products = null;
		
		if(orderId != null && !"".equals(orderId)){
			HotelOrder order = get(HotelOrder.class, orderId);
			if( order != null ){
				Set<CooperationBusinessProduct> productSet = order.getProducts();
				products = new ArrayList<CooperationBusinessProduct>();
				for(CooperationBusinessProduct product : productSet){
					products.add(product);
				}
			}
		}
		
		return products;
	}
}
