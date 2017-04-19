/**
 * 
 */
package com.tssa.common.mode;

import java.io.Serializable;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;


/**
 * @author gmc
 *
 */
public class DetachedCriteriaTS<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5763210287146282173L;

	private final String entityName;

	private final DetachedCriteria criteria;
	
	public DetachedCriteriaTS(final String entityName){
		criteria = DetachedCriteria.forEntityName(entityName);
		this.entityName = entityName;
	}
	
	public DetachedCriteriaTS(final String entityName, final String alias){
		criteria = DetachedCriteria.forEntityName(entityName, alias);
		this.entityName = entityName;
	}
	
	public DetachedCriteriaTS(final Class<T> clazz){
		criteria = DetachedCriteria.forClass(clazz);
		this.entityName = clazz.getName();
	}
	
	public DetachedCriteriaTS(final Class<T> clazz, String alias){
		criteria = DetachedCriteria.forClass(clazz, alias);
		this.entityName = clazz.getName();
	}
	
	public String getEntityName() {
		return entityName;
	}

	public DetachedCriteria getCriteria() {
		return this.criteria;
	}

	public DetachedCriteriaTS<T> add(final Criterion criterion) {
		criteria.add(criterion);
		return this;
	}
	
	public DetachedCriteriaTS<T> setProjection(final Projection projection){
		criteria.setProjection(projection);
		return this;
	}

	public DetachedCriteriaTS<T> addOrder(final Order order) {
		criteria.addOrder(order);
		return this;
	}
	
	public String getAlias() {
		return criteria.getAlias();
	}

	public Criteria getExecutableCriteria(final Session session) {
		return criteria.getExecutableCriteria(session);
	}
}
