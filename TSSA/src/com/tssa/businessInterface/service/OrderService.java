/**
 * 
 */
package com.tssa.businessInterface.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONObject;

import org.apache.commons.lang.SerializationUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.businessInterface.myInterface.dao.MyOrderDao;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.common.util.TSSAUtil;
import com.tssa.cooperationBusiness.dao.CooperationDao;
import com.tssa.cooperationBusiness.dao.CooperationProductDao;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.pojo.CooperationBusinessProduct;
import com.tssa.customer.dao.CustomerDao;
import com.tssa.customer.pojo.Customer;
import com.tssa.myOrders.pojo.HotelOrder;
import com.tssa.myOrders.pojo.Orders;
import com.tssa.myOrders.pojo.RestaurantOrder;
import com.tssa.remote.object.OrderVo;
import com.tssa.remote.object.ProductVo;
import com.tssa.util.DateWarpUtils;

/**
 * @author gmc
 * @param <T>
 *
 */
@Service
public class OrderService<T> extends BaseBusinessService<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);
	
	@Autowired
	private CooperationDao cooperationDao;
	
	@Autowired
	private CooperationProductDao cooperationProductDao;
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private MyOrderDao orderDao;

	/**
	 * 通用订单类型下单接口
	 * @param custId 客户id
	 * @param vendorId 商户id
	 * @param productCodes 产品列表
	 * @param createDate 下单日期
	 * @param channel 下单渠道标识
	 * @param extraMessage 订单附加信息
	 * @param orderType 订单类型
	 * @param clazz 订单对象
	 */
	public String createOrder(String custId, String vendorId, List<String> productCodes, String createDate, String appointmentTime, int channel, JSONObject jsonData){
		
		Customer customer = null;
		CooperationBusiness cooperationBusiness = null;
		String orderId = null;
		Set<CooperationBusinessProduct> productSet = new HashSet<CooperationBusinessProduct>();
		try{
			/*总价*/
			BigDecimal totalPrice = new BigDecimal(0);
			
			/*查询客户信息*/
			if(custId != null && !"".equals(custId)){
				customer = customerDao.get(Customer.class, custId);
			}
			
			/*查询商户信息*/
			if(vendorId != null && !"".equals(vendorId)){
				DetachedCriteriaTS<CooperationBusiness> vendorCriteria = new DetachedCriteriaTS<CooperationBusiness>(CooperationBusiness.class);
				vendorCriteria.add(Restrictions.eq("cooperId", vendorId));
				cooperationBusiness = cooperationDao.find(vendorCriteria);
			}
			
			/*库存充足标志,默认库存充足*/
			boolean isSufficient = true;
			/*查询产品信息*/
			if(productCodes != null && productCodes.size() > 0){
				for(String productCode : productCodes){
					DetachedCriteriaTS<CooperationBusinessProduct> productCriteria = new DetachedCriteriaTS<CooperationBusinessProduct>(CooperationBusinessProduct.class);
					productCriteria.add(Restrictions.eq("businessProductId", productCode));
					CooperationBusinessProduct product = cooperationProductDao.find(productCriteria);
					/*计算订单总价，默认已最优价结算*/
					if(product != null){
						if(product.getSalePrice() != null){
							totalPrice = totalPrice.add(product.getSalePrice());
						}else{
							totalPrice = totalPrice.add(product.getSinglePrice());
						}
						productSet.add(product);
						/*获取当前数量*/
//						int presently = product.getResidualNum();
//						if(presently <= 0){
//							isSufficient = false;
//							break;
//						}
//						/*当前数量-1 = 剩余数量*/
//						product.setResidualNum(presently - 1);
//						/*更新剩余数量*/
//						cooperationProductDao.saveOrUpdate(product);
					}
				}
			}
			
			/*库存不足*/
//			if(!isSufficient){
//				return "0";
//			}
			
			if(jsonData != null){
				String orderType = jsonData.getString("orderType");
				
				if("restaurantOrder".equals(orderType)){
					RestaurantOrder order = new RestaurantOrder();
					order.setOrderId(TSSAUtil.getOrderId(channel, 
										cooperationBusiness.getCooperCode(), 
										cooperationBusiness.getProductTypeId().getTypeCode(), 
										null));
					order.setChannelCode(channel);
					order.setCreateDate(DateWarpUtils.parseDate(createDate));
					order.setAppointmentTime(DateWarpUtils.parseDate(appointmentTime));
					order.setCustId(customer.getUid());
					order.setCustName(customer.getUsername());
					order.setCustPhone(customer.getPhone());
					order.setProducts(productSet);
					order.setSettlementPrice(BigDecimal.valueOf(jsonData.getDouble("settlementPrice")));
					order.setVendorCode(cooperationBusiness.getCooperCode());
					order.setVendorName(cooperationBusiness.getCooperName());
					order.setOrderStatus(0);
					order.setPopulation(jsonData.getInt("population"));
					order.setQuantity(jsonData.getInt("quantity"));
//					if(extraMessage != null && !"".equals(extraMessage)){
//						order.setExtraMessage(extraMessage);
//					}
					orderDao.save(order);
					orderId = order.getOrderId();
				} else if("hotelOrder".equals(orderType)){
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					HotelOrder order = new HotelOrder();
					order.setOrderId(TSSAUtil.getOrderId(channel, 
							cooperationBusiness.getCooperCode(), 
							cooperationBusiness.getProductTypeId().getTypeCode(), 
							null));
					order.setChannelCode(channel);
					order.setCreateDate(DateWarpUtils.parseDate(createDate));
					order.setAppointmentTime(DateWarpUtils.parseDate(appointmentTime));
					order.setCustId(customer.getUid());
					order.setCustName(customer.getUsername());
					order.setCustPhone(customer.getPhone());
					order.setProducts(productSet);
					order.setSettlementPrice(BigDecimal.valueOf(jsonData.getDouble("settlementPrice")));
					order.setVendorCode(cooperationBusiness.getCooperCode());
					order.setVendorName(cooperationBusiness.getCooperName());
					order.setOrderStatus(0);
					order.setStartDate(format.parse(jsonData.getString("startDate")));
					order.setEndDate(format.parse(jsonData.getString("endDate")));
					order.setInNum(jsonData.getInt("inNum"));
					
					orderDao.save(order);
					orderId = order.getOrderId();
				}
			}
			
			return orderId;
		}catch(Exception e){
			logger.error("创建订单异常，异常信息" + e.getMessage());
			return "Exception";
		}
	}
	
	/**
	 * 通用取消订单接口
	 * @param custId 客户编码
	 * @param orderId 订单编号
	 * @return
	 */
	public boolean cancelOrder(String custId, String orderId){
		
		boolean result = false;
		Orders order = null;
		/*根据客户编码、订单编号查询对应订单*/
		if((custId != null && !"".equals(custId)) && (orderId != null && !"".equals(orderId))){
			DetachedCriteriaTS<Orders> orderCriteria = new DetachedCriteriaTS<Orders>(Orders.class);
			orderCriteria.add(Restrictions.eq("custId", custId));
			orderCriteria.add(Restrictions.eq("orderId", orderId));
			order = orderDao.find(orderCriteria);
		}
		if(order != null){
			orderDao.delete(order);
			result = true;
		}else{
			result = false;
		}
		return result;
	}
	
	/**
	 * 订单修改接口
	 * @param orderVo
	 * @return
	 */
	public boolean modifierOrder(String orderId, String custPhone, String custName, String appointmentTime){
		boolean result = false;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			
			Orders order = orderDao.get(Orders.class, orderId);
			if(order != null){
				order.setAppointmentTime(format.parse(appointmentTime));
				order.setCustName(custName);
				order.setCustPhone(custPhone);
				
				orderDao.saveOrUpdate(order);
				
				result = true;
			}
			
		}catch(Exception e){
			result = false;
			logger.error("修改订单异常", e);
		}
		
		return result;
	}

	/**
	 * 通用订单查询接口
	 * @param custId 客户编号
	 * @param pageSize 页大小
	 * @param pageNumber 当前页
	 * @return
	 */
	public List<OrderVo> findOrders(String custId, int pageSize, int pageNumber){
		List<OrderVo> list = new ArrayList<OrderVo>();
		DetachedCriteriaTS<Orders> orderCriteria = new DetachedCriteriaTS<Orders>(Orders.class);
		if(custId != null && !"".equals(custId)){
			orderCriteria.add(Restrictions.eq("custId", custId));
			orderCriteria.add(Restrictions.lt("orderStatus", 2));
			orderCriteria.addOrder(Order.desc("createDate"));
			orderCriteria.addOrder(Order.desc("appointmentTime"));
			
		}
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		List<?> orders = orderDao.find(orderCriteria, (pageNumber - 1) * pageSize , pageSize);
		
		DetachedCriteriaTS<Orders> criteria2 = new DetachedCriteriaTS<Orders>(Orders.class);
		criteria2.add(Restrictions.eq("custId", custId));
		criteria2.add(Restrictions.lt("orderStatus", 2));
		
		int totalCount = orderDao.count(criteria2);
		
		if(orders != null && orders.size() > 0){
			for(Object order : orders){
				
				OrderVo vo = new OrderVo();
				Orders mainOrder = (Orders)order;
				vo.setOrderId(mainOrder.getOrderId());
				vo.setCustId(mainOrder.getCustId());
				vo.setCustName(mainOrder.getCustName());
				vo.setCreateTime(format.format(mainOrder.getCreateDate()));
				vo.setChannelCode(mainOrder.getChannelCode());
				vo.setSettlementPrice(mainOrder.getSettlementPrice());
				vo.setAppointmentTime(format.format(mainOrder.getAppointmentTime()));
				vo.setOrderStatus(mainOrder.getOrderStatus());
				vo.setCustPhone(mainOrder.getCustPhone());
				vo.setVendorId(mainOrder.getVendorCode());
				vo.setTotalCount(totalCount);
				
				if(mainOrder.getProducts() != null && mainOrder.getProducts().size() > 0){
					List<ProductVo> products = new ArrayList<ProductVo>();
					for(CooperationBusinessProduct product : mainOrder.getProducts()){
						ProductVo pvo = new ProductVo();
						vo.setVendorAddress(product.getCooperId().getAddress());
						vo.setVendorName(product.getCooperId().getCooperName());
						pvo.setProductId(product.getBusinessProductId());
						pvo.setProductCode(product.getProductCode());
						pvo.setProductName(product.getProductName());
						pvo.setProductTypeId(product.getProductType().getTypeCode());
						pvo.setProductTypeName(product.getProductType().getTypeName());
						pvo.setResidualNum(product.getResidualNum());
						pvo.setPrice(product.getSinglePrice().toString());
						pvo.setSalePrice(product.getSalePrice().toString());
						pvo.setTotalNum(product.getTotalNum());
						pvo.setDescription(product.getDescription());
						pvo.setVendorId(product.getCooperId().getCooperId());
						pvo.setVendorName(product.getCooperId().getCooperName());
						products.add(pvo);
					}
					vo.setProducts(products);
				}
				
				if( order instanceof RestaurantOrder ){
					RestaurantOrder restaurantOrder = (RestaurantOrder)order;
					vo.setOrderType("restaurantOrder");
					vo.setQuantity(restaurantOrder.getQuantity() != null ? restaurantOrder.getQuantity() : 0);
					vo.setPopulation(restaurantOrder.getPopulation() != null ? restaurantOrder.getPopulation() : 0);
					
					if(restaurantOrder.getProducts() != null && restaurantOrder.getProducts().size() > 0){
						List<ProductVo> products = new ArrayList<ProductVo>();
						for(CooperationBusinessProduct product : restaurantOrder.getProducts()){
							ProductVo pvo = new ProductVo();
							vo.setVendorAddress(product.getCooperId().getAddress());
							vo.setVendorName(product.getCooperId().getCooperName());
							pvo.setProductId(product.getBusinessProductId());
							pvo.setProductCode(product.getProductCode());
							pvo.setProductName(product.getProductName());
							pvo.setProductTypeId(product.getProductType().getTypeCode());
							pvo.setProductTypeName(product.getProductType().getTypeName());
							pvo.setResidualNum(product.getResidualNum());
							pvo.setPrice(product.getSinglePrice().toString());
							pvo.setSalePrice(product.getSalePrice().toString());
							pvo.setTotalNum(product.getTotalNum());
							pvo.setDescription(product.getDescription());
							pvo.setVendorId(product.getCooperId().getCooperId());
							pvo.setVendorName(product.getCooperId().getCooperName());
							products.add(pvo);
						}
						vo.setProducts(products);
					}
					
				} else if( order instanceof HotelOrder ){
					SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
					HotelOrder hotelOrder = (HotelOrder)order;
					
					if(hotelOrder.getProducts() != null && hotelOrder.getProducts().size() > 0){
						List<ProductVo> products = new ArrayList<ProductVo>();
						for(CooperationBusinessProduct product : hotelOrder.getProducts()){
							ProductVo pvo = new ProductVo();
							vo.setVendorAddress(product.getCooperId().getAddress());
							vo.setVendorName(product.getCooperId().getCooperName());
							pvo.setProductId(product.getBusinessProductId());
							pvo.setProductCode(product.getProductCode());
							pvo.setProductName(product.getProductName());
							pvo.setProductTypeId(product.getProductType().getTypeCode());
							pvo.setProductTypeName(product.getProductType().getTypeName());
							pvo.setResidualNum(product.getResidualNum());
							pvo.setPrice(product.getSinglePrice().toString());
							pvo.setSalePrice(product.getSalePrice().toString());
							pvo.setTotalNum(product.getTotalNum());
							pvo.setDescription(product.getDescription());
							pvo.setVendorId(product.getCooperId().getCooperId());
							pvo.setVendorName(product.getCooperId().getCooperName());
							products.add(pvo);
						}
						vo.setProducts(products);
					}
					
					vo.setStartDate(format2.format(hotelOrder.getStartDate()));
					vo.setEndDate(format2.format(hotelOrder.getEndDate()));
					vo.setInNum(hotelOrder.getInNum());
					vo.setOrderType("hotelOrder");
				}
				list.add(vo);
			}
		}
		return list;
	}
}
