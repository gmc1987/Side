/**
 * 
 */
package com.tssa.options.dao;

import java.util.List;

import org.hibernate.Criteria;

import com.tssa.common.dao.HibernateEntitryDaoImpl;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.options.pojo.Option;

/**
 * @author gmc
 *
 */
public class OptionDaoImpl extends HibernateEntitryDaoImpl implements OptionDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<Option> getAllOption(DetachedCriteriaTS<Option> detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		return criteria.list();
	}

}
