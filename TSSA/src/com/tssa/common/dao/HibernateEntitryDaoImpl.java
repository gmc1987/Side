/**
 * 
 */
package com.tssa.common.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.SerializationUtils;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;

/**
 * @author gmc
 * 
 */
public class HibernateEntitryDaoImpl extends HibernateDaoSupport implements HibernateEntitryDao {

	private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(HibernateEntitryDaoImpl.class);

	/**
	 * 查询单个实体
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> entityClass, Serializable id) {
		return (T) getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * 分页查询
	 * 
	 * @param criteria
	 * @param first
	 *            第一条记录
	 * @param maxResult
	 *            最后一条记录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> find(DetachedCriteriaTS<T> detachedCriteria, int first, int maxResult) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		if (first > 0 && maxResult != 0) {
			criteria.setFirstResult(first / maxResult + 1);
		}
		if (maxResult > 0) {
			criteria.setMaxResults(maxResult);
		}
		return criteria.list();
	}

	/**
	 * 查询总数
	 * 
	 * @param criteria
	 * @param maxResult
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> List<T> find(DetachedCriteriaTS<T> detachedCriteria, int maxResult) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		if (maxResult > 0) {
			criteria.setMaxResults(maxResult);
		}
		return criteria.list();
	}

	/**
	 * 保存一个对象
	 * 
	 * @param obj
	 */
	@Transactional
	public void save(Object entity) {
		super.getHibernateTemplate().save(entity);
	}

	/**
	 * 更新指定对象
	 * 
	 * @param entity
	 *            对象
	 */
	@Transactional
	public void saveOrUpdate(Object entity) {
		super.getHibernateTemplate().saveOrUpdate(this.getSession().merge(entity));
	}

	/**
	 * 更新指定集合对象
	 * 
	 * @param entities
	 *            对象集合
	 */
	@Transactional
	public <T> void saveOrUpdateAll(Collection<T> entities) {
		if (entities != null && entities.size() > 0) {
			for (T t : entities) {
				getHibernateTemplate().saveOrUpdate(t);
			}
		}
	}

	/**
	 * 删除指定对象
	 * 
	 * @param entity
	 *            实体对象
	 */
	@Transactional
	public void delete(Object entity) {
		super.getHibernateTemplate().delete(this.getSession().merge(entity));
	}

	/**
	 * 删除指定对象集合
	 * 
	 * @param entities
	 *            对象集合
	 */
	@Transactional
	public <T> void deleteAll(Collection<T> entities) {
		if (entities != null && entities.size() > 0) {
			// for (T t : entities) {
			// getHibernateTemplate().delete(t);
			// }
			getHibernateTemplate().deleteAll(entities);
		}
	}

	/**
	 * 查询总记录数
	 * 
	 * @param criteria
	 * @return
	 */
	public <T> int count(final DetachedCriteriaTS<T> criteria) {
		final Criteria c = criteria.getExecutableCriteria(getSession());
		Object obj = c.setProjection(Projections.rowCount()).uniqueResult();
		if (LOG.isDebugEnabled()) {
			LOG.debug(obj.getClass().getName() + "; count : " + obj.toString());
		}
		if (Long.class.isAssignableFrom(obj.getClass())) {
			long count = (Long) obj;
			return (int) (count);
		} else if (Integer.class.isAssignableFrom(obj.getClass())) {
			return (Integer) obj;
		}
		throw new ClassCastException(obj.getClass().getName());
	}

	@Override
	public <T> PageMode<T> findForPage(DetachedCriteriaTS<T> criteria, int pageNumber, int pageSize) {
		return findForPage(criteria, pageNumber, pageSize, null, null, false);
	}

	private <T> PageMode<T> findForPage(DetachedCriteriaTS<T> criteria, int pageNumber, int pageSize,
			CacheMode cacheMode, String cacheRegion, boolean cacheable) {

		if (pageNumber <= 0 || pageSize <= 0) {
			throw new IllegalArgumentException("参数错误！");
		}

		@SuppressWarnings("unchecked")
		DetachedCriteriaTS<T> criteria2 = (DetachedCriteriaTS<T>) SerializationUtils.clone(criteria);
		int count = count(criteria);

		if (count <= 0) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("count <= 0");
			}
			return new PageMode<T>();
		}
		
		List<T> list = find(criteria2, (pageNumber - 1) * pageSize, pageSize);

		if (list == null) {
			return new PageMode<T>();
		}

		return new PageMode<T>(list, pageNumber, pageSize, count);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T find(DetachedCriteriaTS<T> criteria) {
		return (T) criteria.getExecutableCriteria(getSession()).setMaxResults(1).uniqueResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findAll(DetachedCriteriaTS<T> detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		return criteria.list();
	}

}
