/**
 * 
 */
package com.tssa.remote.custmer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tssa.businessInterface.service.OrderService;
import com.tssa.customer.pojo.Customer;
import com.tssa.customer.service.CustomerService;
import com.tssa.remote.common.BaseController;
import com.tssa.remote.object.CustomerVo;
import com.tssa.remote.object.OrderVo;

/**
 * @author gmc
 *
 */
@Controller
@RequestMapping("/orderRemote")
public class OrderRemoteController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderRemoteController.class);
	
	@Autowired
	private OrderService<?> orderService;
	
	@Autowired
	private CustomerService customerService;

	/**
	 * 手机客户端创建订单接口
	 * <P>数据格式:requestData={custId:500231,cooperationId:523299,products:[52324,52344,54243],createDate:yyyy-MM-dd HH:mm:ss,channel:1}<P>
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/createOrder")
	public String custCreateOrder(HttpServletRequest request,HttpServletResponse response, String requestData){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			List<String> productList = new ArrayList<String>();
			
			JSONObject object = JSONObject.fromObject(requestData);
			String custName = object.getString("custName");
			String custPhone = object.getString("custPhone");
			Customer customer = null;
			CustomerVo customerVo = null;

			//下单用户未注册，自动注册
			if(custPhone != null && !"".equals(custPhone)){
				customerVo = checkCustomerIshave(custPhone);
				if(StringUtils.isEmpty(customerVo.getUid())){
					customer = new Customer();
					customer.setPhone(custPhone);
					customer.setNickname(custName);
					customer.setUsername(custName);
					Random random = new Random();

					String randoms="";
					for(int i=0;i<6;i++){
						randoms+=random.nextInt(10);
					}
					customer.setPassword(randoms);
					customer.setCreateDate(new Date());
					customerVo = customerService.registration(customer);
					result.put("customerRegister", 1);
				}else{
					result.put("customerRegister", 0);
				}
			}
			
			String vendorId = object.getString("vendorId");
			JSONArray productArray = object.getJSONArray("products");
			for(int i = 0; i < productArray.size(); i++){
				JSONObject product = productArray.getJSONObject(i);
				productList.add(product.getString("productId"));
			}
			String createDate = object.getString("createTime");
			String appointmentTime = object.getString("appointmentTime");
			int channel = object.getInt("channelCode");
			
			
			/*附加信息*/
			String extraMessage = null;
			
			String orderId = orderService.createOrder(customerVo.getCid(), vendorId, productList, createDate, appointmentTime, channel, object);
			
			if(orderId != null && "Exception".equals(orderId)){
				result.put("returnCode", OrderVo.RETURN_EXCEPTION);
				result.put("returnDesc", OrderVo.RETURN_EXCEPTION_DESC);
			}else if("0".equals(orderId)){
				result.put("returnCode", OrderVo.RETURN_EXCEPTION);
				result.put("returnDesc", "预订失败、库存不足!");
			}else{
				result.put("returnCode", OrderVo.RETURN_SUCCESS);
				result.put("returnDesc", "预订成功");
			}
			responseOutStream(response,JSONObject.fromObject(result).toString());
		}catch(Exception e){
			logger.error("客户下单异常", e);
			result.put("returnCode", OrderVo.RETURN_EXCEPTION);
			result.put("returnDesc", OrderVo.RETURN_EXCEPTION_DESC);
			try{
				responseOutStream(response,JSONObject.fromObject(result).toString());
			}catch(IOException ex){
				logger.error("返回信息异常", e);
			}
		}
		return null;
	}
	
	/**
	 * 手机客户端订单查询接口
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/myOrderList")
	public String myOrderList(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			String data = request.getParameter(KEY_REQUEST_DATA);
			if(data == null || "".equals(data)){
				data = (String) request.getAttribute(KEY_REQUEST_DATA);
			}
			
			JSONObject object = JSONObject.fromObject(data);
			String custId = object.getString("custId");
			int pageSize = object.getInt("pageSize");
			int pageNumber = object.getInt("pageNumber");
			int start = pageNumber * pageSize; //计算开始记录行数
			//查询订单数据
			List<OrderVo> orderList = orderService.findOrders(custId, pageSize, start);
			
			//数据经过了压缩处理
			responseOutStream(response, JSONArray.fromObject(orderList).toString());
		}catch(Exception e){
			result.put("returnCode", OrderVo.RETURN_EXCEPTION);
			result.put("returnDesc", OrderVo.RETURN_EXCEPTION_DESC);
			logger.error("查询异常", e);
			try{
				responseOutStream(response, JSONObject.fromObject(result).toString());
			}catch(IOException ioe){
				logger.error("返回异常", ioe);
			}
		}
		return null;
	}
	
	@RequestMapping("/modifierOrder")
	public void	modifierOrder(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			String data = request.getParameter(KEY_REQUEST_DATA);
			if(data == null || "".equals(data)){
				data = (String) request.getParameter(KEY_REQUEST_DATA);
			}
			JSONObject object = JSONObject.fromObject(data);
			String orderId = object.getString("orderId");
			String custName = object.getString("custName");
			String custPhone = object.getString("custPhone");
			String appointmentTime = object.getString("appointmentTime");
			
			boolean modifierResult = orderService.modifierOrder(orderId, custPhone, custName, appointmentTime);
			
			if(modifierResult){
				result.put("returnCode", OrderVo.RETURN_SUCCESS);
				result.put("returnDesc", "修改成功");
				responseOutStream(response,JSONObject.fromObject(result).toString());
			}
			
		}catch(Exception e){
			result.put("returnCode", OrderVo.RETURN_EXCEPTION);
			result.put("returnDesc", "修改失败");
			logger.error("修改时发生异常", e);
			try{
				responseOutStream(response,JSONObject.fromObject(result).toString());
			}catch(IOException ioe){
				logger.error("返回异常", e);
			}
		}
	}
	
	@RequestMapping("/cancelMyOrder")
	public String cancelMyOrder(HttpServletRequest request,HttpServletResponse response){
		Map<String,Object> result = new HashMap<String, Object>();
		try{
			String data = request.getParameter(KEY_REQUEST_DATA);
			if(data == null || "".equals(data)){
				data = (String) request.getAttribute(KEY_REQUEST_DATA);
			}
			JSONObject object = JSONObject.fromObject(data);
			String custId = object.getString("custId");
			String orderId = object.getString("orderId");
			boolean cancelResult = orderService.cancelOrder(custId, orderId);
			if(cancelResult){
				result.put("returnCode", OrderVo.RETURN_SUCCESS);
				result.put("returnDesc", "订单取消成功");
			}else{
				result.put("returnCode", OrderVo.RETURN_EXCEPTION);
				result.put("returnDesc", OrderVo.RETURN_EXCEPTION_DESC);
			}
			responseOutStream(response,JSONObject.fromObject(result).toString());
		}catch(Exception e){
			result.put("returnCode", OrderVo.RETURN_EXCEPTION);
			result.put("returnDesc", OrderVo.RETURN_EXCEPTION_DESC);
			logger.error("取消异常", e);
			try{
				responseOutStream(response,JSONObject.fromObject(result).toString());
			}catch(IOException ioe){
				logger.error("返回异常", e);
			}
		}
		return null;
	}
	
	/**
	 * 检查用户是否已存在
	 * @param custPhone
	 * @return
	 */
	private CustomerVo checkCustomerIshave(String custPhone){
		Customer customer = null;
		CustomerVo customerVo = new CustomerVo();
		if(!StringUtils.isEmpty(custPhone)){
			customer = customerService.checkCustomerIsHave(custPhone);
			if(customer != null){
				BeanUtils.copyProperties(customer, customerVo);
				customerVo.setCid(customer.getId());
			}
		}
		return customerVo;
	}
	
}
