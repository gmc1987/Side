/**
 * 
 */
package com.tssa.scheduler.job;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.tssa.myOrders.pojo.Orders;
import com.tssa.myOrders.service.MyOrderService;

/**
 * @author gmc
 * @see 自动检测商户订单，判断订单是否已经超过了最后执行日期。如果是，标记为已完成(2)
 */
public class AutoCheckOrderStatusJob extends QuartzJobBean {

	private static final Logger logger = LoggerFactory
			.getLogger(AutoCheckOrderStatusJob.class);

	private MyOrderService myOrderService;
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		
		logger.info("检测订单开始");
		
		List<Orders> list = myOrderService.findOrdersByAllVendor();
		try{
			//获取JobExecutionContext中的service对象    
//            SchedulerContext skedCtx = arg0.getScheduler().getContext();  
//            
//            myOrderService = (MyOrderService)skedCtx.get("myOrderService");
            
			if(list != null && list.size() > 0){
				logger.info("检测到" + list.size() + "个订单需要自动处理");
				
				for(Orders order : list){
					order.setOrderStatus(2);
				}
				
				myOrderService.updateAllOrder(list);
			}
		}catch(Exception e){
			logger.error("批量更新订单状态时发生异常", e);
		}
		
		logger.info("检测订单结束");
		
	}

	public MyOrderService getMyOrderService() {
		return myOrderService;
	}

	public void setMyOrderService(MyOrderService myOrderService) {
		this.myOrderService = myOrderService;
	}

}
