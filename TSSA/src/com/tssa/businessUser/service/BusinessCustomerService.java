/**
 * 
 */
package com.tssa.businessUser.service;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.businessUser.dao.BusinessCustomerDao;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;

/**
 * @author gmc
 * @see do somthing about business customer manager ! 
 * 		For example Verification and add, get a customer list
 */
@Service
public class BusinessCustomerService extends
		BaseBusinessService<BusinessCustomer> {

	@Autowired
	private BusinessCustomerDao businessCustomerDao;
	
	public BusinessCustomer businessCustomerLogin(String loginId, String password){
		DetachedCriteriaTS<BusinessCustomer> criteriaUser = new DetachedCriteriaTS<BusinessCustomer>(BusinessCustomer.class);
		criteriaUser.add(Restrictions.eq("businessCustomerCode", loginId));
		criteriaUser.add(Restrictions.eq("password", password));
		BusinessCustomer BusinessCustomer = businessCustomerDao.find(criteriaUser);
		if(BusinessCustomer != null){
			return BusinessCustomer;
		}else{
			return null;
		}
	}
	
}
