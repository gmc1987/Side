/**
 * 
 */
package com.tssa.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;

/**
 * @author gmc
 *
 */
public interface HibernateEntitryDao {

	/**
	 * 查询单个实体
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> entityClass, Serializable id);
	
	/**
	 * 返回单条数据
	 * 
	 * @see DetachedCriteriaWrapper
	 */
	<T> T find(final DetachedCriteriaTS<T> criteria);
	
	/**
	 * 分页查询
	 * @param criteria
	 * @param first 第一条记录
	 * @param maxResult 最后一条记录
	 * @return
	 */
	public <T> List<T> find(DetachedCriteriaTS<T> detachedCriteria, int first, int maxResult);
	
	/**
	 * 查询总数
	 * @param criteria
	 * @param maxResult
	 * @return
	 */
	public <T> List<T> find(DetachedCriteriaTS<T> detachedCriteria, int maxResult);
	
	/**
	 * 保存一个对象
	 * @param obj
	 */
	public void save(Object entity);
	
	/**
	 * 更新指定对象
	 * @param entity 对象
	 */
	public void saveOrUpdate(Object entity);
	
	/**
	 * 更新指定集合对象
	 * @param entities	对象集合
	 */
	public <T> void saveOrUpdateAll(Collection<T> entities) ;
	
	/**
	 * 删除指定对象
	 * @param entity 实体对象
	 */
	public void delete(Object entity);
	
	/**
	 * 删除指定对象集合
	 * @param entities	对象集合
	 */
	public <T> void deleteAll(Collection<T> entities);
	
	/**
	 * 查询总记录数
	 * @param criteria
	 * @return
	 */
	public <T> int count(final DetachedCriteriaTS<T> criteria);
	
	/**
	 * 分页查询
	 * @param criteria 参数对象
	 * @param pi
	 * @param ps
	 * @return
	 */
	public <T> PageMode<T> findForPage(DetachedCriteriaTS<T> criteria, int pageNumber, int pageSize);
}
