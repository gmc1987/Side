/**
 * 
 */
package com.tssa.scheduler.pojo;

/**
 * @author gmc
 *
 */
public class SideJobDetail {

	/**
	 * job名字
	 */
	private String jobName;
	
	/**
	 * job组
	 */
	private String jobGroup;
	
	/**
	 * 触发器名字
	 */
	private String triggerName;
	
	/**
	 * 触发器组名
	 */
	private String triggerGroup;
	
	/**
	 * 时间表达式
	 */
	private String cronExpression;
	
	/**
	 * 作业描述
	 */
	private String description;

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getTriggerName() {
		return triggerName;
	}

	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
	}

	public String getTriggerGroup() {
		return triggerGroup;
	}

	public void setTriggerGroup(String triggerGroup) {
		this.triggerGroup = triggerGroup;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
