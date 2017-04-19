/**
 * 
 */
package com.tssa.scheduler.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author gmc
 * @see 平台统一定时任务调度类，负责商户订单结算、状态变更。商户佣金结算等功能
 */
public class PlatformSchedulerJob extends QuartzJobBean{

	private static final Logger logger = LoggerFactory
			.getLogger(PlatformSchedulerJob.class);
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.info("定时任务执行开始");
		
		logger.info("定时任务执行结束");
	}

}
