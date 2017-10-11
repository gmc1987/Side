/**
 * 
 */
package com.tssa.myOrders.controller;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.myOrders.pojo.Orders;
import com.tssa.myOrders.service.MyOrderService;
import com.tssa.util.DateWarpUtils;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

/**
 * @author gmc
 *
 */

@Controller
@RequestMapping("/order")
@SessionAttributes(value={"user","businessCustomer"}, types={Users.class, BusinessCustomer.class})
public class OrderController {

	private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private MyOrderService orderService;
	
	@RequestMapping("/toList")
	public String toPage(HttpServletRequest request){
		return "/order/orderList";
	}
	
	@RequestMapping("/list")
	@ResponseBody
	public ModelMap list(HttpServletRequest request,  ModelMap map){
		PageMode<Orders> page = null;
		String orderId = request.getParameter("orderId");
		String custName = request.getParameter("custName");
		String custId = request.getParameter("custId");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int pageNumber = 1;
		int pageSize = 0;
		
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		
		DetachedCriteriaTS<Orders> criteria = new DetachedCriteriaTS<Orders>(Orders.class);
		
		if(businessCustomer != null){
			criteria.add(Restrictions.eq("vendorCode", businessCustomer.getVendorCode()));
		}
		
		if(orderId != null && !"".equals(orderId)){
			criteria.add(Restrictions.eq("orderId", orderId));
		}
		if(custName != null && !"".equals(custName)){
			criteria.add(Restrictions.like("custName", custName, MatchMode.ANYWHERE));
		}
		if(custId != null && !"".equals(custId)){
			criteria.add(Restrictions.eq("custId", custId));
		}
		if(startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)){
			criteria.add(Restrictions.between("createDate", DateWarpUtils.parseDate(startDate + " 00:00:00"), DateWarpUtils.parseDate(endDate + " 23:59:59")));
		}
		/*分页查询参数*/
		if(start != null && !"".equals(start) && !"0".equals(start)){
			pageNumber = Integer.parseInt(start);
		}
		if(limit != null && !"".equals(limit)){
			pageSize = Integer.parseInt(limit);
		}else{
			pageSize = 10;
		}
		
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{
				"cooperId",
				"pareTypes",
				"products"
		});
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
		
		page = orderService.findForList(criteria, pageNumber, pageSize);
		if(page != null){
			map.addAttribute("pageMode", JSONObject.fromObject(page, config));
		}
		return map;
	}
}
