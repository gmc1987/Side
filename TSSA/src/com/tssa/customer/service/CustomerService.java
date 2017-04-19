/**
 * 
 */
package com.tssa.customer.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.common.util.TSSAUtil;
import com.tssa.common.util.UtilMD5;
import com.tssa.customer.dao.CustomerDao;
import com.tssa.customer.pojo.Account;
import com.tssa.customer.pojo.Customer;
import com.tssa.remote.object.CustomerVo;


/**
 * 类名 : CustomerService.java
 * <p> 客户Service </p>
 * @author lch
 * Date: 2013-10-6
 * @version 1.01
 */
@Service
public class CustomerService extends BaseBusinessService<Customer> {
	
	@Autowired
	private CustomerDao customerDao;
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	public CustomerVo login(String loginName, String password) {
		logger.info("手机登陆...........................");
		Customer customer = null;
		CustomerVo customerVo = new CustomerVo();
		customerVo.setReturnCode(CustomerVo.RETURN_SUCCESS);
		
		if (StringUtils.isNotEmpty(loginName)) {
			List<Customer> list = customerDao.findCustomerListByLoginname(loginName);
			if (list != null && list.size() > 0){
				customer = list.get(0);
			}else{
				customerVo.setReturnCode(CustomerVo.LOGINNAME_ERROR);
			}
		} else{
			customerVo.setReturnCode(CustomerVo.LOGINNAME_ERROR);
		}
		if (customer != null) {
			customerVo = validateInfo(customer, customerVo, password);
		}
		customerVo.setReturnDesc("successful");
		logger.debug("验证完成,验证返回码："+customerVo.getReturnCode());
		return customerVo;
	}

	private CustomerVo validateInfo(Customer customer, CustomerVo customerVo,
			String password) {
		if (StringUtils.isNotEmpty(password)) {
//			password = UtilMD5.MD5(password);//admin--21232F297A57A5A743894A0E4A801FC3
			if (password.equals(customer.getPassword())){
				customerVo.setBirthday(customer.getBirthday());
				customerVo.setNickname(customer.getNickname());
				customerVo.setEmail(customer.getEmail());
				customerVo.setCid(customer.getId());
				customerVo.setSex(customer.getSex());
				customerVo.setPhone(customer.getPhone());
				customerVo.setQq(customer.getQq());
				customerVo.setUid(customer.getUid());
				customerVo.setUsername(customer.getUsername());
				customerVo.setHeadPicture(customer.getHeadPicture());
				customerVo.setCreateDate(customer.getCreateDate());
				customerVo.setCustomerLever(customer.getCustomerLever());
			}else{
				customerVo.setReturnCode(CustomerVo.PASSWORD_ERROR);
			}
		} else{
			customerVo.setReturnCode(CustomerVo.PASSWORD_ERROR);
		}
		return customerVo;
	}
	
	
	public CustomerVo registration(Customer customer){
		CustomerVo vo = null;
		logger.info("用户注册...........................");
		if(customer != null){
			String password = customer.getPassword();
			//加密密码
			customer.setPassword(UtilMD5.MD5(password));
			customer.setCustomerLever(1);
			customer.setUid(TSSAUtil.getCode("01", null));
			Account account = new Account();
			account.setAid(TSSAUtil.getCode("01", customer.getUid()));
			account.setCustomer(customer);
			account.setCreateDate(new Date());
			
			customer.setCustAccount(account);
			customerDao.save(customer);//创建用户,关联创建账户
			
			Customer cust = null;
			DetachedCriteriaTS<Customer> detachedCriteria = new DetachedCriteriaTS<Customer>(Customer.class);
			detachedCriteria.add(Restrictions.eq("uid", customer.getUid()));
			cust = customerDao.find(detachedCriteria);
			if(cust != null){
				vo = new CustomerVo();
				vo.setCid(cust.getId());
				vo.setUid(cust.getUid());
				vo.setPhone(cust.getPhone());
				vo.setEmail(customer.getEmail());
				vo.setCustomerLever(customer.getCustomerLever());
				vo.setReturnCode(CustomerVo.REGISTER_SUCCESS);
				vo.setReturnDesc(CustomerVo.REGIEST_SUCCESS_DESC);
			}else{
				vo = new CustomerVo();
				vo.setReturnCode(CustomerVo.REGISTER_FAILURE);
				vo.setReturnDesc(CustomerVo.REGIEST_FAILURE_DESC)  ;
			}
		}
		return vo;
	}
	
	/**
	 * 根据客户名、手机号查询客户是否已存在
	 * @param custPhone
	 * @return
	 */
	public Customer checkCustomerIsHave(String key){
		Customer cust = null;
		if(key != null && !"".equals(key)){
			DetachedCriteriaTS<Customer> detachedCriteria = new DetachedCriteriaTS<Customer>(Customer.class);
			detachedCriteria.add(Restrictions.or(Restrictions.eq("phone", key), Restrictions.eq("uid", key)));
			cust = customerDao.find(detachedCriteria);
		}
		return cust;
	}
	
}

