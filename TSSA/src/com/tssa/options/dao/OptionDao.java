/**
 * 
 */
package com.tssa.options.dao;

import java.util.List;

import com.tssa.common.dao.HibernateEntitryDao;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.options.pojo.Option;

/**
 * @author gmc
 *
 */
public interface OptionDao extends HibernateEntitryDao {

	/**
	 * 查询所有权限操作
	 * @param detachedCriteria
	 * @return
	 */
	public List<Option> getAllOption(DetachedCriteriaTS<Option> detachedCriteria);
}
