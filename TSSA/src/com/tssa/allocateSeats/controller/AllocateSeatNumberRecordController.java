/**
 * 
 */
package com.tssa.allocateSeats.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.allocateSeats.pojo.AllocateSeatNumberRecord;
import com.tssa.allocateSeats.pojo.AllocateSeatTypeSet;
import com.tssa.allocateSeats.service.AllocateSeatNumberRecordService;
import com.tssa.allocateSeats.service.AllocateSeatTypeSetService;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;

/**
 * @author gmc
 * @see 商户派位模块
 */
@Controller
@RequestMapping("/allocateSeatNumberRecord")
@SessionAttributes(value={"businessCustomer","user"}, types={BusinessCustomer.class,Users.class})
public class AllocateSeatNumberRecordController {

	private static final Logger LOG = LoggerFactory.getLogger(AllocateSeatNumberRecordController.class);
	
	@Autowired
	private AllocateSeatNumberRecordService allocateSeatNumberRecordService;
	@Autowired
	private AllocateSeatTypeSetService allocateSeatTypeSetService;
	
	@RequestMapping("/toList")
	public String toList(){
		return "/AllocateSeatsManage/AllocateSeatsNumberRecordManage";
	}
	
	/**
	 * 查询商户所有派位规则
	 * @param cooperationCode
	 * @return
	 */
	@RequestMapping("/getList")
	@ResponseBody
	public Map<String, Object> getList(HttpServletRequest request, String cooperationCode){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
			
			DetachedCriteriaTS<AllocateSeatTypeSet> criteria = new DetachedCriteriaTS<AllocateSeatTypeSet>(AllocateSeatTypeSet.class);
			if(StringUtils.isEmpty(cooperationCode)){
				criteria.add(Restrictions.eq("businessCustomerCode", businessCustomer.getBusinessCustomerCode()));
			}else{
				criteria.add(Restrictions.eq("businessCustomerCode", cooperationCode));
			}
			List<AllocateSeatTypeSet> result = allocateSeatTypeSetService.findAll(criteria);
			if(result != null && result.size() > 0){
				resultMap.put("success", true);
				resultMap.put("data", result);
				resultMap.put("msg", "查询成功");
			}
		}catch(Exception e){
			LOG.error("查询异常", e);
			resultMap.put("success", false);
			resultMap.put("data", null);
			resultMap.put("msg", "查询无记录");
		}
		
		return resultMap;
		
	}
	
	/**
	 * 用户派号设置
	 * @param typeId
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/createNo")
	@ResponseBody
	public Map<String, Object> createNo(String typeId, String custCode){
		
		return null;
	}
	
}
