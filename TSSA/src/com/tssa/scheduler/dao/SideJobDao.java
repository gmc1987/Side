/**
 * 
 */
package com.tssa.scheduler.dao;

import java.util.List;

import com.tssa.common.dao.HibernateEntitryDao;
import com.tssa.common.mode.PageMode;
import com.tssa.scheduler.vo.SideJobDetailVO;

/**
 * @author gmc
 *
 */
public interface SideJobDao extends HibernateEntitryDao {

	/**
	 * 查询所有定时任务记录
	 * @return
	 */
	public List<SideJobDetailVO> findAllSideJobList();
	
	/**
	 * 分页查询定时任务记录
	 * @param jobDetail
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageMode<SideJobDetailVO> findJobByPage(int pageNumber, int pageSize);
	
}
