/**
 * 
 */
package com.tssa.myOrders.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tssa.common.mode.TssaBaseException;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;
import com.tssa.myOrders.dao.IOrderDao;
import com.tssa.myOrders.pojo.Orders;
import com.tssa.util.DateWarpUtils;

/**
 * @author gmc
 *
 */
@Service
public class MyOrderService extends BaseBusinessService<Orders> {

	private static final Logger logger = LoggerFactory
			.getLogger(MyOrderService.class);
	
	@Autowired
	private IOrderDao orderDao;
	
	public List<Orders> findOrdersByAllVendor(){
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String toDayStr = DateWarpUtils.format(new Date()) + " 23:59:59";
		Date sevenDayAgo = null;
		List<Orders> orders = null;
		try {
			sevenDayAgo = DateUtils.addDays(dateFormat.parse(toDayStr), -7);
			if(sevenDayAgo != null){
				orders = orderDao.findAllOrders(DateWarpUtils.formatFull(sevenDayAgo), 2);
			}
		} catch (ParseException e) {
			logger.error("日期转换异常", e);
		}
		
		return orders;
	}
	
	@Transactional
	public void updateAllOrder(List<Orders> orders) throws Exception{
		
		if(orders != null && orders.size() > 0){
			orderDao.saveOrUpdateAll(orders);
		}else{
			throw new TssaBaseException("没有需要更新的订单数据");
		}
		
	}
	
}
