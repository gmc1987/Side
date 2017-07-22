/**
 * 
 */
package com.tssa.allocateSeats.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.tssa.allocateSeats.pojo.AllocateSeatTypeSet;
import com.tssa.allocateSeats.service.AllocateSeatTypeSetService;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.common.mode.TssaBaseException;

/**
 * @author gmc
 * @see 商户派位服务模块
 */
@Controller
@RequestMapping("/allocateSeats")
@SessionAttributes(value={"businessCustomer","user"}, types={BusinessCustomer.class,Users.class})
public class AllocateSeatsTypeController {

	private static final Logger LOG = LoggerFactory.getLogger(AllocateSeatsTypeController.class);
	
	@Autowired
	private AllocateSeatTypeSetService allocateSeatTypeSetService;
	
	@RequestMapping("/list")
	public String toList(ModelMap map){
		return "/AllocateSeatsType/allocateSeatsTypeSetManage";
	}
	
	@RequestMapping("/getList")
	@ResponseBody
	public PageMode<AllocateSeatTypeSet> list(String start, String limit, String businessCustomerCode){
		
		int pageNumber = 1;
		int pageSize = 0;
		try{
			if (start != null && !"".equals(start) && !"0".equals(start)) {
				pageNumber = Integer.parseInt(start);
			}
			if (limit != null && !"".equals(limit)) {
				pageSize = Integer.parseInt(limit);
			} else {
				pageSize = 10;
			}
			
			DetachedCriteriaTS<AllocateSeatTypeSet> criteria = new DetachedCriteriaTS<AllocateSeatTypeSet>(AllocateSeatTypeSet.class);
			if(!StringUtils.isEmpty(businessCustomerCode)){
				criteria.add(Restrictions.eq("businessCustomerCode", businessCustomerCode));
			}
			return allocateSeatTypeSetService.findForList(criteria, pageNumber, pageSize);
		}catch(Exception e){
			LOG.error("查询异常", e);
			return null;
		}
		
	}
	
	/**
	 * 派位类型新增方法
	 * @param allocateSeatType
	 * @return
	 * @throws TssaBaseException 
	 */
	@RequestMapping(value="/save", method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> allocateSeatTypeAdd(AllocateSeatTypeSet allocateSeatType, HttpServletRequest request) {
		
		Map<String, Object> result = new HashMap<String, Object>();
		BusinessCustomer sessionbusinessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		allocateSeatType.setBusinessCustomerCode(sessionbusinessCustomer.getBusinessCustomerCode());
		allocateSeatType.setCreater(sessionbusinessCustomer.getBusinessCustomerName());
		allocateSeatType.setCreateDate(new Date());
		try{
			if(allocateSeatTypeSetService.allocateSeatTypeSetAdd(allocateSeatType)){
				result.put("success", true);
				result.put("msg", "新增成功");
			}else{
				result.put("success", false);
				result.put("msg", "新增失败");
			}
		}catch(Exception e){
			LOG.error("新增异常！", e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 规则修改方法
	 * @param allocateSeatType
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/update", method={RequestMethod.POST})
	@ResponseBody
	public Map<String, Object> allocateSeatTypeUpdate(AllocateSeatTypeSet allocateSeatType, HttpServletRequest request){
		
		Map<String, Object> result = new HashMap<String, Object>();
		BusinessCustomer sessionbusinessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		allocateSeatType.setBusinessCustomerCode(sessionbusinessCustomer.getBusinessCustomerCode());
		allocateSeatType.setCreater(sessionbusinessCustomer.getBusinessCustomerName());
		allocateSeatType.setCreateDate(new Date());
		try{
			if(allocateSeatTypeSetService.allocateSeatTypeSetUpdate(allocateSeatType)){
				result.put("success", true);
				result.put("msg", "修改成功");
			}else{
				result.put("success", false);
				result.put("msg", "修改失败");
			}
		}catch(Exception e){
			LOG.error("修改异常！", e);
			result.put("success", false);
			result.put("msg", e.getMessage());
		}
		
		return result;
	}
	
	/**
	 * 规则删除方法
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> allocateSeatTypeDelete(String uuid){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(uuid)){
			result.put("success", false);
			result.put("msg", "参数异常");
		}
		
		AllocateSeatTypeSet delObj = allocateSeatTypeSetService.get(AllocateSeatTypeSet.class, uuid);
		
		if(delObj == null){
			result.put("success", false);
			result.put("msg", "没有可删除的记录");
		}
		try{
			allocateSeatTypeSetService.delete(delObj);
			result.put("success", true);
			result.put("msg", "删除成功");
		}catch(Exception e){
			LOG.error("删除异常！", e);
			result.put("success", false);
			result.put("msg", "删除失败");
		}
		
		return result;
	}
}
