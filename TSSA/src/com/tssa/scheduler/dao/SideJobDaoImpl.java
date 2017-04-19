/**
 * 
 */
package com.tssa.scheduler.dao;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import com.tssa.common.dao.HibernateEntitryDaoImpl;
import com.tssa.common.mode.PageMode;
import com.tssa.scheduler.vo.SideJobDetailVO;

/**
 * @author gmc
 * 
 */
public class SideJobDaoImpl extends HibernateEntitryDaoImpl implements
		SideJobDao {

	@Override
	public List<SideJobDetailVO> findAllSideJobList() {
		// TODO Auto-generated method stub

		List<SideJobDetailVO> jobs = null;
		StringBuilder strSql = new StringBuilder(
				"select details.JOB_NAME, details.JOB_GROUP, details.DESCRIPTION, cronTriggers.CRON_EXPRESSION, triggers.NEXT_FIRE_TIME, triggers.PREV_FIRE_TIME, triggers.START_TIME, triggers.END_TIME from QRTZ_JOB_DETAILS details");
		strSql.append(" inner join QRTZ_TRIGGERS triggers on details.JOB_NAME=triggers.JOB_NAME and details.JOB_GROUP=triggers.JOB_GROUP");
		strSql.append(" inner join QRTZ_CRON_TRIGGERS cronTriggers on triggers.TRIGGER_NAME=cronTriggers.TRIGGER_NAME and triggers.TRIGGER_GROUP=cronTriggers.TRIGGER_GROUP");
		//获取hibernate session
		Session session = getSession();
		SQLQuery sqlQuery = session.createSQLQuery(strSql.toString());

		@SuppressWarnings("unchecked")
		List<Object[]> list = sqlQuery.list();

		if (list != null && list.size() > 0) {
			jobs = new ArrayList<SideJobDetailVO>();
			for (Object[] objs : list) {
				SideJobDetailVO detail = new SideJobDetailVO();
				detail.setJobName(String.valueOf(objs[0]));
				detail.setJobGroup(String.valueOf(objs[1]));
				detail.setDescription(objs[2] != null ? String.valueOf(objs[2])
						: "");
				detail.setCronExpression(String.valueOf(objs[3]));
				detail.setNextFireTime(String.valueOf(objs[4]));
				detail.setPrevFireTime(String.valueOf(objs[5]));
				detail.setStartTime(String.valueOf(objs[6]));
				detail.setEndTime(String.valueOf(objs[7]));

				jobs.add(detail);
			}
		}
		session.close();
		return jobs;
	}

	@Override
	public PageMode<SideJobDetailVO> findJobByPage(int pageNumber, int pageSize) {
		// TODO Auto-generated method stub

		PageMode<SideJobDetailVO> pageMode = new PageMode<SideJobDetailVO>();
		List<SideJobDetailVO> jobs = null;
		//查询总数
		StringBuilder countSql = new StringBuilder(
				"select count(1) from QRTZ_JOB_DETAILS details");
		countSql.append(" inner join QRTZ_TRIGGERS triggers on details.JOB_NAME=triggers.JOB_NAME and details.JOB_GROUP=triggers.JOB_GROUP");
		countSql.append(" inner join QRTZ_CRON_TRIGGERS cronTriggers on triggers.TRIGGER_NAME=cronTriggers.TRIGGER_NAME and triggers.TRIGGER_GROUP=cronTriggers.TRIGGER_GROUP");

		Session session = getSession();
		SQLQuery countQuery = session.createSQLQuery(countSql.toString());
		@SuppressWarnings("unchecked")
		List<Object> count = countQuery.list();
		BigInteger countSize = (BigInteger)count.get(0);
		int countNum = countSize.intValue();
		pageMode.setCount(countNum);
		
		if(pageNumber <= 0 || pageSize <= 0){
			throw new IllegalArgumentException("参数错误！");
		}
		
		//分页查询数据
		StringBuilder dataSql = new StringBuilder(
				"select details.JOB_NAME, details.JOB_GROUP, details.DESCRIPTION, cronTriggers.CRON_EXPRESSION, triggers.NEXT_FIRE_TIME, triggers.PREV_FIRE_TIME, triggers.START_TIME, triggers.END_TIME, details.JOB_CLASS_NAME, triggers.TRIGGER_NAME, triggers.TRIGGER_GROUP from QRTZ_JOB_DETAILS details");
		dataSql.append(" inner join QRTZ_TRIGGERS triggers on details.JOB_NAME=triggers.JOB_NAME and details.JOB_GROUP=triggers.JOB_GROUP");
		dataSql.append(" inner join QRTZ_CRON_TRIGGERS cronTriggers on triggers.TRIGGER_NAME=cronTriggers.TRIGGER_NAME and triggers.TRIGGER_GROUP=cronTriggers.TRIGGER_GROUP");
		dataSql.append(" limit ").append((pageNumber - 1) * pageSize).append(",").append(pageSize);
		
		SQLQuery dataQuery = getSession().createSQLQuery(dataSql.toString());
		@SuppressWarnings("unchecked")
		List<Object[]> list = dataQuery.list();
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		if (list != null && list.size() > 0) {
			jobs = new ArrayList<SideJobDetailVO>();
			for (Object[] objs : list) {
				SideJobDetailVO detail = new SideJobDetailVO();
				detail.setJobName(String.valueOf(objs[0]));
				detail.setJobGroup(String.valueOf(objs[1]));
				detail.setDescription(objs[2] != null ? String.valueOf(objs[2])
						: "");
				detail.setCronExpression(String.valueOf(objs[3]));
				
				Date nextFireTime = new Date(Long.parseLong(String.valueOf(objs[4])));
				Date prevFireTime = new Date(Long.parseLong(String.valueOf(objs[5])));
				Date startTime = new Date(Long.parseLong(String.valueOf(objs[6])));
				Date endTime = new Date(Long.parseLong(String.valueOf(objs[7])));
				
				detail.setNextFireTime(format.format(nextFireTime));
				detail.setPrevFireTime(format.format(prevFireTime));
				detail.setStartTime(format.format(startTime));
				detail.setEndTime(format.format(endTime));
				detail.setClassName(String.valueOf(objs[8]));
				detail.setTriggerName(String.valueOf(objs[9]));
				detail.setTriggerGroup(String.valueOf(objs[10]));

				jobs.add(detail);
			}
		}
		pageMode.setPageSize(pageSize);
		pageMode.setPageNumber(pageNumber);
		pageMode.setRecords(jobs);
		
		//关闭hibernate Session
		session.close();
		return pageMode;
	}

}
