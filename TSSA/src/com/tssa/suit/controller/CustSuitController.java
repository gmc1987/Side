/**
 * 
 */
package com.tssa.suit.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.service.CooperationService;
import com.tssa.customer.pojo.Customer;
import com.tssa.customer.service.CustomerService;
import com.tssa.suit.pojo.CustSuit;
import com.tssa.suit.pojo.ProcessingResult;
import com.tssa.suit.pojo.SuitType;
import com.tssa.suit.service.CustSuitService;
import com.tssa.suit.service.SuitTypeService;

/**
 * @author gmc
 * 
 */
@Controller
@RequestMapping(value="/custSuit")
public class CustSuitController {

	private static final Logger LOG = LoggerFactory.getLogger(CustSuitController.class);
	
	@Autowired
	private CustSuitService custSuitService;

	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CooperationService cooperationService;
	
	@Autowired
	private SuitTypeService suitTypeService;

	@RequestMapping("/toList")
	public String toList(HttpServletRequest request, ModelMap map) {
		return "/system/cust-suit";
	}

	@RequestMapping("/list")
	@ResponseBody
	public ModelMap list(String custPhone, String startDate, String endDate,
			Integer pageNumber, Integer pageSize, ModelMap map) {

		Customer customer = null;
		DetachedCriteriaTS<CustSuit> criteria = new DetachedCriteriaTS<CustSuit>(
				CustSuit.class);
		try {
			if (!StringUtils.isEmpty(custPhone)) {
				DetachedCriteriaTS<Customer> customerCriteria = new DetachedCriteriaTS<Customer>(
						Customer.class);
				customerCriteria.add(Restrictions.eq("phone", custPhone));
				customer = customerService.find(customerCriteria);
			}

			if (!StringUtils.isEmpty(custPhone)) {
				criteria.add(Restrictions.eq("suiter", customer));
			}

			if (!StringUtils.isEmpty(startDate)
					&& !StringUtils.isEmpty(endDate)) {
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");

				criteria.add(Restrictions.between("suitDate",
						format.parse(startDate + " 00:00:00"),
						format.parse(endDate + " 23:59:59")));
			}
			
			if(pageNumber == null){
				pageNumber = 1;
			}
			
			if(pageSize == null){
				pageSize = 20;
			}
			
		} catch (Exception e) {
			LOG.error("参数错误", e);
		}

		PageMode<CustSuit> pageMode = custSuitService.findForList(criteria,
				pageNumber, pageSize);
		
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[] { "productTypeId", "locationReference", "custAccount", "results" });
		config.setIgnoreDefaultExcludes(false); 
		config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		PropertyFilter pf = new PropertyFilter(){

			@Override
			public boolean apply(Object arg0, String arg1, Object arg2) {
				// TODO Auto-generated method stub
				if(arg2 == null){
					return true;
				}
				return false;
			}
			
		};
		config.setJsonPropertyFilter(pf);
		
		map.clear();
		
		map.addAttribute("pageModel", JSONObject.fromObject(pageMode, config));

		return map;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Map<String, Object> save(CustSuit suit){
		
		Map<String, Object> result = new HashMap<String, Object>();
		Customer customer = null;
		if( !StringUtils.isEmpty(suit.getCustomerId()) ){
			customer = customerService.get(Customer.class, suit.getCustomerId());
		}
		
		CooperationBusiness cooperation = null;
		if( !StringUtils.isEmpty(suit.getBuisnessId())){
			cooperation = cooperationService.get(CooperationBusiness.class, suit.getBuisnessId());
		}
		
		SuitType suitType = null;
		if( !StringUtils.isEmpty(suit.getSuiTypeId())){
			suitType = suitTypeService.get(SuitType.class, suit.getSuiTypeId());
		}
		
		suit.setSuitDate(new Date());
		
		if(customer != null && cooperation != null){
			
			suit.setSuiter(customer);
			suit.setSuitObject(cooperation);
			suit.setSuiType(suitType);
			
			custSuitService.save(suit);
			
			result.put("success", true);
			result.put("msg", "新增投诉成功");
			
		} else {
			result.put("success", false);
			result.put("msg", "新增投诉失败");
		}
		
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> update(CustSuit suit){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(suit != null){
			
			CustSuit custSuit = custSuitService.get(CustSuit.class, suit.getSuitId());
			
			if(custSuit != null){
				
				custSuit.setContent(suit.getContent());
				
				Customer customer = null;
				if( !StringUtils.isEmpty(suit.getCustomerId()) ){
					customer = customerService.get(Customer.class, suit.getCustomerId());
				}
				
				CooperationBusiness cooperation = null;
				if( !StringUtils.isEmpty(suit.getBuisnessId())){
					cooperation = cooperationService.get(CooperationBusiness.class, suit.getBuisnessId());
				}
				
				SuitType suitType = null;
				if( !StringUtils.isEmpty(suit.getSuiTypeId())){
					suitType = suitTypeService.get(SuitType.class, suit.getSuiTypeId());
				}
				
				custSuit.setSuitObject(cooperation);
				custSuit.setSuiType(suitType);
				custSuit.setSuiter(customer);
				custSuitService.update(custSuit);
				
				result.put("success", true);
				result.put("msg", "投诉修改成功");
				
			} else {
				
				result.put("success", false);
				result.put("msg", "找不到相关记录");
				
			}
		} else {
			result.put("success", false);
			result.put("msg", "参数有误");
		}
		
		return result;
	}
	
	@RequestMapping("/process")
	@ResponseBody
	public Map<String, Object> process(String suitId, String processContext, HttpServletRequest request){
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		Users user = (Users) request.getSession().getAttribute("user");
		if(suitId != null){
			CustSuit custSuit = custSuitService.get(CustSuit.class, suitId);
			if(!StringUtils.isEmpty(processContext)){
				ProcessingResult result = new ProcessingResult();
				result.setProcessContext(processContext);
				result.setProcesser(user.getUserName());
				result.setProcessDate(new Date());
				result.setProcessStatus(1);
				result.setProcessResult("处理成功");
//				result.setSuitId(custSuit);
				custSuit.getResults().add(result);
				custSuitService.update(custSuit);
				
				map.put("success", true);
				map.put("msg", "投诉处理成功");
			}
			
		} else {
			map.put("success", false);
			map.put("msg", "投诉处理失败");
		}
		
		return map;
	}
}
