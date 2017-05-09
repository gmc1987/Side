/**
 * 
 */
package com.tssa.businessUser.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.base.controller.BaseController;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.businessUser.pojo.BusinessCustomerResetRecord;
import com.tssa.businessUser.service.BusinessCustomerResetRecordService;
import com.tssa.businessUser.service.BusinessCustomerService;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.common.mode.TssaBaseException;
import com.tssa.common.util.JsonDateValueProcessor;

/**
 * @author gmc
 * 
 */

@Controller
@RequestMapping("/pwdReset")
@SessionAttributes(value={"businessCustomer","user"}, types={BusinessCustomer.class,Users.class})
public class BusinessCustomerResetRecordController extends BaseController {

	private static final Logger LOG = LoggerFactory.getLogger(BusinessCustomerResetRecordController.class);
	
	@Autowired
	private BusinessCustomerResetRecordService businessCustomerResetRecordService;
	
	@Autowired
	private BusinessCustomerService businessCustomerService;

	@RequestMapping("list")
	public String toList() {
		return "/businessCustomer/pwdResetRecord";
	}

	@RequestMapping("getList")
	@ResponseBody
	public ModelMap getList(HttpServletRequest request, ModelMap map){
		
		String mobilePhone = request.getParameter("mobilePhone");
		String userName = request.getParameter("userName");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int pageNumber = 1;
		int pageSize = 10;
		
		if(start != null && !"".equals(start) && !"0".equals(start)){
			pageNumber = Integer.parseInt(start);
		}
		if(limit != null && !"".equals(limit)){
			pageSize = Integer.parseInt(limit);
		}else{
			pageSize = 10;
		}
		
		DetachedCriteriaTS<BusinessCustomerResetRecord> criteria = new DetachedCriteriaTS<BusinessCustomerResetRecord>(BusinessCustomerResetRecord.class);
		
		if(!StringUtils.isEmpty(mobilePhone)){
			criteria.add(Restrictions.eq("mobilePhone", mobilePhone));
		}
		
		if(!StringUtils.isEmpty(userName)){
			criteria.add(Restrictions.eq("resetCustomerName", userName));
		}
		
		criteria.addOrder(Order.asc("serialStatus"));
		criteria.addOrder(Order.desc("entryDate"));
		
		PageMode<BusinessCustomerResetRecord> pageModel = businessCustomerResetRecordService.findForList(criteria, pageNumber, pageSize);
		
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		PropertyFilter pf = new PropertyFilter(){

			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				// TODO Auto-generated method stub
				if(arg2 == null || String.valueOf(arg2).equals("")){
					return true;
				}
				return false;
			}
			
		};
		config.setJsonPropertyFilter(pf);
		
		map.addAttribute("pageMode", JSONObject.fromObject(pageModel, config));
		
		return map;
	}

	@RequestMapping(value="addRecord", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addRecord(HttpServletRequest request) throws TssaBaseException{
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		String loginID = request.getParameter("loginID");
		String remark = request.getParameter("remark");
		
		if(StringUtils.isEmpty(loginID)){
			throw new TssaBaseException("无效的登录id...");
		}
		try{
			DetachedCriteriaTS<BusinessCustomer> criteria = new DetachedCriteriaTS<BusinessCustomer>(BusinessCustomer.class);
			criteria.add(Restrictions.eq("businessCustomerCode", loginID));
			BusinessCustomer businessCustomer = businessCustomerService.find(criteria);
			
			if(businessCustomer != null){
				BusinessCustomerResetRecord record = new BusinessCustomerResetRecord();
				record.setEntryDate(new Date());
				record.setMobilePhone(businessCustomer.getMobilePhone());
				record.setResetCustomerCode(businessCustomer.getBusinessCustomerCode());
				record.setResetCustomerName(businessCustomer.getBusinessCustomerName());
				record.setSerialStatus(new Integer(0));
				record.setRemark(remark);
				
				businessCustomerResetRecordService.save(record);
				
				result.put("success", true);
				result.put("msg", "申请重置密码成功，请等待审批重置");
				
			}
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", "申请重置失败，发生系统异常，请联系管理员");
			LOG.error("发生异常", e);
		}
		
		return result;
	}

	@RequestMapping(value="reset", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> passwordReset(HttpServletRequest request) throws TssaBaseException {

		Map<String, Object> result = new HashMap<String, Object>();
		String loginID = request.getParameter("resetCustomerCode"); 
		String password = request.getParameter("password");
		BusinessCustomer sessionbusinessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		Users user = (Users)request.getSession().getAttribute("user");
		
		if(StringUtils.isEmpty(password) || StringUtils.isEmpty(loginID)){
			throw new TssaBaseException("重置密码参数错误");
		}
		try{
			DetachedCriteriaTS<BusinessCustomer> criteria = new DetachedCriteriaTS<BusinessCustomer>(BusinessCustomer.class);
			criteria.add(Restrictions.eq("businessCustomerCode", loginID));
			BusinessCustomer businessCustomer = businessCustomerService.find(criteria);
			
			if(businessCustomer != null){
				businessCustomer.setPassword(password);
				businessCustomerService.update(businessCustomer);
				
				DetachedCriteriaTS<BusinessCustomerResetRecord> criteriaRecord = new DetachedCriteriaTS<BusinessCustomerResetRecord>(BusinessCustomerResetRecord.class);
				criteriaRecord.add(Restrictions.eq("resetCustomerCode", loginID));
				criteriaRecord.add(Restrictions.ne("serialStatus", 1));
				List<BusinessCustomerResetRecord> records = businessCustomerResetRecordService.findAll(criteriaRecord);
				for(BusinessCustomerResetRecord record : records){
					if(sessionbusinessCustomer != null){
						record.setAuditCustomerCode(sessionbusinessCustomer.getBusinessCustomerCode());
					}else if(user != null){
						record.setAuditCustomerCode(user.getUserCode());
					}
					record.setAuditDate(new Date());
					record.setSerialStatus(new Integer(1));
				}
				
				if(records != null && records.size() > 0){
					businessCustomerResetRecordService.updateAll(records);
				}
				
				result.put("success", true);
				result.put("msg", "审批通过");
				
			}else{
				result.put("success", false);
				result.put("msg", "审批失败，用户不存在");
			}
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", "审批失败，发生系统异常，请联系管理员");
			LOG.error("系统异常", e);
		}
		
		return result;
	}
}
