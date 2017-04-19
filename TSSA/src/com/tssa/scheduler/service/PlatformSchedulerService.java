/**
 * 
 */
package com.tssa.scheduler.service;

import java.text.ParseException;
import java.util.List;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import com.tssa.common.mode.PageMode;
import com.tssa.scheduler.dao.SideJobDao;
import com.tssa.scheduler.pojo.SideJobDetail;
import com.tssa.scheduler.vo.SideJobDetailVO;

/**
 * @author gmc
 * @see 动态定时任务服务类。
 * 		提供新增、修改、删除、查询、暂停、运行定时器服务
 */
@Service
public class PlatformSchedulerService {
	
	@Autowired
	private SchedulerFactoryBean scheduler;
	
	@Autowired
	private SideJobDao sideJobDao;
	
	public List<SideJobDetailVO> findJobList(){
		
		return sideJobDao.findAllSideJobList();
	}
	
	public PageMode<SideJobDetailVO> findJobByPage(int pageNumber, int pageSize){
		return sideJobDao.findJobByPage(pageNumber, pageSize);
	}
	
	/**
	 * 动态添加定时任务
	 * @param sideJobDetail 调度信息实体
	 * @param clazz 调度执行类
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	@SuppressWarnings("rawtypes")
	public void addJob(SideJobDetail sideJobDetail, Class clazz) throws SchedulerException, ParseException{
		//从spring中获取scheduler（调度容器）
		Scheduler sideScheduler = scheduler.getScheduler();
		//创建一个jobDetail
		JobDetail jobDetail = new JobDetail(sideJobDetail.getJobName(), sideJobDetail.getJobGroup(), clazz);
		jobDetail.setDescription(sideJobDetail.getDescription());
		//创建一个trigger
		CronTrigger trigger = new CronTrigger(sideJobDetail.getTriggerName(), sideJobDetail.getTriggerGroup());
		//设置cronExpression表达式
		trigger.setCronExpression(sideJobDetail.getCronExpression());
		//加入到调度容器内
		sideScheduler.scheduleJob(jobDetail, trigger);
		
	}
	
	/**
	 * 动态修改定时任务
	 * @param sideJobDetail 调度信息实体
	 * @param clazz 调度执行类
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	@SuppressWarnings("rawtypes")
	public void modifyJob(SideJobDetail sideJobDetail, Class clazz) throws SchedulerException, ParseException{
		//从spring中获取scheduler（调度容器）
		Scheduler sideScheduler = scheduler.getScheduler();
		//根据jobName、jobGroup获取当前已有的job信息
		JobDetail jobDetail = sideScheduler.getJobDetail(sideJobDetail.getJobName(), sideJobDetail.getJobGroup());
		//重新设置执行类
		if(jobDetail != null){
			clazz = jobDetail.getJobClass();
			this.delJob(sideJobDetail);
			this.addJob(sideJobDetail, clazz);
		}
		
	}
	
	/**
	 * 删除定时任务
	 * @param sideJobDetail
	 * @param clazz
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void delJob(SideJobDetail sideJobDetail) throws SchedulerException, ParseException{
		//从spring中获取scheduler（调度容器）
		Scheduler sideScheduler = scheduler.getScheduler();
		//根据jobName、jobGroup获取当前已有的job信息
		JobDetail jobDetail = sideScheduler.getJobDetail(sideJobDetail.getJobName(), sideJobDetail.getJobGroup());
		//重新设置执行类
		if(jobDetail != null){
			//删除定时任务
			sideScheduler.deleteJob(sideJobDetail.getJobName(), sideJobDetail.getJobGroup());
		}
	}
	
}
