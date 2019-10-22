/**
 * 
 */
package com.tssa.myOrders.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.tssa.common.service.BaseBusinessService;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;
import com.tssa.myOrders.pojo.ShopOrder;

/**
 * @author gmc870223
 *
 */

@Service
public class ShopOrderService extends BaseBusinessService<ShopOrder> {

	/** 根据orderId查询订单所有产品信息
	 * @param orderId
	 * @return
	**/
	public List<CooperationBusinessProduct> findOrderProductById(String orderId){
		
		List<CooperationBusinessProduct> products = null;
		
		if(orderId != null && !"".equals(orderId)){
			ShopOrder order = get(ShopOrder.class, orderId);
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
	
	/**
	 * 添加购物车
	 * @param product
	 */
	public void addShopCar(CooperationBusinessProduct product) {
		
	}
}
