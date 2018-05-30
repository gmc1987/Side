/**
 * 
 */
package com.tssa.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.context.ApplicationContext;

import com.tssa.base.pojo.BaseCode;
import com.tssa.base.service.BaseService;
import com.tssa.common.mode.DetachedCriteriaTS;

/**
 * @author gmc
 * @see 工具类，包含格式化日期，生成编码
 */
public class TSSAUtil {
	
//	@Autowired
//	private static BaseService baseService;
	
	private static Map<String,String> nameMap = new HashMap<String,String>();
	
	static{
		nameMap.put("01", "餐饮");
		nameMap.put("02", "娱乐");
		nameMap.put("03", "购物");
		nameMap.put("04", "酒店住宿");
		nameMap.put("05", "旅游");
	}

	/**
	 * 根据类型生成不同的编码，可生成客户编码、商户编码、产品编码
	 * @param type 生成编码类型
	 * @param busCode 商户编码/用户编码
	 * @return code
	 * @throws Exception 
	 */
	public static String getCode(String type, String busCode){
		ApplicationContext ac = SpringUtil.getApplicationContext();
		BaseService baseService = (BaseService)ac.getBean("baseService");
		DetachedCriteriaTS<BaseCode> criteria = new DetachedCriteriaTS<BaseCode>(BaseCode.class);
		BaseCode myCode = null;
		String newCode = null;
		
		//生成商户编码
		if(type != null && !"".equals(type)){
			criteria.add(Restrictions.like("codeType", type, MatchMode.ANYWHERE));
			criteria.addOrder(Order.desc("busCode"));
			myCode = baseService.find(criteria);
			//数据库已存在编码纪录
			if(myCode != null){
				newCode = myCode.getBusCode();
				myCode.setBusCode(newCode(myCode.getBusCode()));
				baseService.update(myCode);
			}else{//数据库不存在编码纪录
				newCode = newCode(null);
				myCode = new BaseCode();
				myCode.setCodeType(type);
				myCode.setBusCode(newCode(newCode));
				baseService.save(myCode);
			}
		}
		return newCode;
	}
	
	/**
	 * 生成新编码
	 * @param oldCode
	 * @return
	 */
	public synchronized static String newCode(String oldCode){
		int newCode = 0;
		if(oldCode != null && !"".equals(oldCode)){
			newCode = Integer.parseInt(oldCode) + 1;
		}else{
			newCode = 100000;
		}
		return String.valueOf(newCode);
	}
	
	/**
	 * 生成商户员工编号
	 * @param type
	 * @param busCode
	 * @return
	 */
	public static String getBusinessCustomerUserCode(String type, String busCode){
		ApplicationContext ac = SpringUtil.getApplicationContext();
		BaseService baseService = (BaseService)ac.getBean("baseService");
		DetachedCriteriaTS<BaseCode> criteria = new DetachedCriteriaTS<BaseCode>(BaseCode.class);
		BaseCode myCode = null;
		String newFinalCode = null;
		
		//生成商户编码
		if(type != null && !"".equals(type)){
			criteria.add(Restrictions.eq("codeType", type));
			criteria.add(Restrictions.eq("busCode", busCode));
			myCode = baseService.find(criteria);
			//数据库已存在编码纪录
			if(myCode != null){
				if(myCode.getBusCode() != null && !"".equals(myCode.getBusCode()) && myCode.getBusCode().equals(busCode)){
					newFinalCode = myCode.getFinalCode();
					myCode.setFinalCode(newCode(myCode.getFinalCode()));
					baseService.update(myCode);
				}else{
					myCode.setBusCode(busCode);
					myCode.setFinalCode(newCode(null));
					baseService.update(myCode);
				}
			}else{//数据库不存在编码纪录
				newFinalCode = newCode(null);
				myCode = new BaseCode();
				myCode.setCodeType(type);
				myCode.setBusCode(busCode);
				myCode.setFinalCode(newCode(newFinalCode));
				baseService.save(myCode);
			}
		}
		return newFinalCode;
	}
	
	/**
	 * 返回行业名称
	 * @param typeId
	 * @return
	 */
	public static String getTypeName(String typeId){
		return nameMap.get(typeId);
	}
	
	/**
	 * 生成新的唯一订单号
	 * <P>生成格式：YYYYMMddHHmmss.SSS+channel+vendorCode+typeCode+productCode
	 * 	  订单号位长：小于等于32位
	 * <P>
	 * @param channel 渠道标识
	 * @param venderCode 商户编码
	 * @param typeCode 产品类型编码
	 * @param productCode 产品编码
	 * @return
	 */
	public synchronized static String getOrderId(int channel, String venderCode, String typeCode, String productCode){
		
		StringBuffer sb = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmssSSS");
		String orderPrefix = format.format(new Date());
		sb.append(orderPrefix);
		if(channel >= 0){
			sb.append(channel);
		}
		if(venderCode != null && !"".equals(venderCode)){
			sb.append(venderCode);
		}
		if(typeCode != null && !"".equals(typeCode)){
			sb.append(typeCode);
		}
		if(productCode != null && !"".equals(productCode)){
			sb.append(productCode);
		}
		if(sb.length() > 32){
			sb.substring(0, 32);
			Random random = new Random();
			int num = random.nextInt(100);
			sb.replace(30, 32, String.valueOf(num));
		}
		return sb.toString();
	}
	
	public static String numberFormat(int number) {
		return String.format("%03d", number);
	}
	
	public static void main(String[] args) {
		System.out.println(numberFormat(9));
		System.out.println(numberFormat(23));
		System.out.println(numberFormat(100));
	}
	
}
