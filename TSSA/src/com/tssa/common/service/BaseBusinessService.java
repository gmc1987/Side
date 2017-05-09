/**
 * 
 */
package com.tssa.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tssa.common.dao.HibernateEntitryDao;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;

/**
 * @author gmc
 * @see 基础业务service类
 *
 */
@Service
public class BaseBusinessService<T> {
	
	@Autowired
	private HibernateEntitryDao hibernateEntitryDao;

	/**
	 * 保存
	 * @param entity
	 */
	@Transactional
	public void save(T entity){
		hibernateEntitryDao.save(entity);
	}
	
	/**
	 * 更新
	 * @param entity
	 */
	@Transactional
	public void update(T entity){
		hibernateEntitryDao.saveOrUpdate(entity);
	}
	
	/**
	 * 批量更新
	 * @param list
	 */
	@Transactional
	public void updateAll(List<T> list){
		hibernateEntitryDao.saveOrUpdateAll(list);
	}
	/**
	 * 删除
	 * @param entity
	 */
	@Transactional
	public void delete(T entity){
		hibernateEntitryDao.delete(entity);
	}
	
	/**
	 * 批量删除
	 * @param list
	 */
	@Transactional
	public void deleteAll(List<T> list){
		hibernateEntitryDao.deleteAll(list);
	}
	
	/**
	 * 分页查询
	 * @param criteria 参数对象
	 * @param pageNumber 页码
	 * @param pageSize	页记录数
	 * @return
	 */
	public PageMode<T> findForList(DetachedCriteriaTS<T> criteria, int pageNumber, int pageSize){
		return hibernateEntitryDao.findForPage(criteria, pageNumber, pageSize);
	}
	
	/**
	 * 查询单个对象
	 * @param criteria
	 * @return
	 */
	public <T> T find(final DetachedCriteriaTS<T> criteria){
		return hibernateEntitryDao.find(criteria);
	}
	
	/**
	 * 查询列表
	 * @param detachedCriteria
	 * @param maxResult
	 * @return
	 */
	public <T> List<T> find(DetachedCriteriaTS<T> detachedCriteria, int maxResult){
		return hibernateEntitryDao.find(detachedCriteria, maxResult);
	}
	
	/**
	 * 查询所有符合条件记录
	 * @param detachedCriteria
	 * @return
	 */
	public <T> List<T> findAll(DetachedCriteriaTS<T> detachedCriteria){
		return hibernateEntitryDao.findAll(detachedCriteria);
	}
	
	/**
	 * 通过id查询
	 * @param entity
	 */
	public <T> T get(Class<T> entityClass,String id){
		return hibernateEntitryDao.get(entityClass, id);
	}
}
