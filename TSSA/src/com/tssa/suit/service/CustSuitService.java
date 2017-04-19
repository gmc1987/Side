/**
 * 
 */
package com.tssa.suit.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.customer.dao.CustomerDao;
import com.tssa.customer.pojo.Customer;
import com.tssa.remote.object.UserSuitVo;
import com.tssa.suit.dao.CustSuitDao;
import com.tssa.suit.pojo.CustSuit;

/**
 * @author gmc
 *
 */
@Service
public class CustSuitService extends BaseBusinessService<CustSuit> {

	private static final Logger LOG = LoggerFactory.getLogger(CustSuitService.class);
	
	@Autowired
	private CustSuitDao custSuitDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	/**
	 * 手机客户端查询接口
	 * @param custId
	 * @return
	 */
	public List<UserSuitVo> getCustSuitList(String custId, int pageNumber, int pageSize){
		
		List<UserSuitVo> list = null;
		Customer customer = null;
		
		try{
			if(!StringUtils.isEmpty(custId)){
				customer = customerDao.get(Customer.class, custId);
				
				DetachedCriteriaTS<CustSuit> custSuitCriteria = new DetachedCriteriaTS<CustSuit>(
						CustSuit.class);
				custSuitCriteria.add(Restrictions.eq("suiter", customer));
				
				List<CustSuit> suits = custSuitDao.find(custSuitCriteria, pageNumber, pageSize);
				
				if(suits != null && suits.size() > 0){
					
					list = new ArrayList<UserSuitVo>();
					
					for(CustSuit suit : suits){
						
						UserSuitVo userSuitVo = new UserSuitVo();
						BeanUtils.copyProperties(suit, userSuitVo);
						
						list.add(userSuitVo);
					}
					
				}
				
			}
		}catch(Exception e){
			LOG.error("找不到对应的客户资料", e);
		}
		
		return list;
	}
}
