/**
 * 类名 : CustomerController.java
 * <p> 客户控制器  </p>
 * @author lch
 * Date: 2013-10-6
 * @version 1.01
 */
package com.tssa.customer.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.common.util.JsonDateValueProcessor;
import com.tssa.common.util.Parameter;
import com.tssa.common.util.QRCodeEncoderHandler;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.customer.pojo.Customer;
import com.tssa.customer.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	private static final Logger LOG = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/customerList")
	public String customerList(Model model){
		LOG.info("查询客户资料");
		return "/custUserinfo/custUserinfo";
	}
	
	@ResponseBody
	@RequestMapping("/msg")
	public Map<String, String> message() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("aaa", "1234");
		map.put("bbb", "5678");
		return map;
	}
	
	@ResponseBody
	@RequestMapping("/msgJson")
	public Map<String, Object> message1() {
		List<CooperationBusiness> list = new ArrayList<CooperationBusiness>();
		CooperationBusiness user = new CooperationBusiness();
		user.setCooperCode("");
		user.setAccount("dddd");
		list.add(user);
		CooperationBusiness user1 = new CooperationBusiness();
		user.setCooperCode("111");
		user.setAccount("dddd111");
		list.add(user1);
		JsonConfig jsonConfig = new JsonConfig();

		// 设置javabean中日期转换时的格式
		jsonConfig.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
		// 获取json数组
		@SuppressWarnings("unused")
		JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("aaa", list);
		map.put("bbb", "5678");
		return map;
	}
	
	/**
	 * 生成二维码
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "makeQrCode" , method = RequestMethod.POST)
	public String makeQrCode(String id,HttpServletRequest request){
		Customer customer = customerService.get(Customer.class, id);
		customer.setQrCode(Customer.HAS_QRCODE);
		
		/*生成二维码，生成后放在的路径为：/工程名称/id/id.png*/
		String imgPath = request.getServletContext().getRealPath("");
		imgPath = imgPath+File.separator+"QrCode"+File.separator+id;
		String imgName = id+".png";
		StringBuffer contents = new StringBuffer();
		contents.append("{nickname:");
		contents.append(customer.getNickname());
		contents.append(",email:"+customer.getEmail());
		contents.append("}");
        File imgDirectory = new File(imgPath);
        if (!imgDirectory.isDirectory()) {
        	imgDirectory.mkdirs();
		}
        File imgFile = new File(imgDirectory+File.separator+imgName);
        QRCodeEncoderHandler qrCode = new QRCodeEncoderHandler();
        qrCode.encode(contents.toString(), 150, 150, "png" ,imgFile);
        LOG.info("成功生成二维码，存放路径为："+imgPath);
		customerService.update(customer);
		return "true";
	}
	
	/**
	 * <p>查询客户资料</p>
	 * @param request
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="getCustLilst")
	public ModelMap getCustLilst(HttpServletRequest request,ModelMap model){
		LOG.info("查询客户资料");
		PageMode<Customer> pageMode =  null;
		Integer start = Parameter.getParameter2Int(request, "start", 1);
		Integer limit =  Parameter.getParameter2Int(request, "limit", 10);
		start = start/limit + 1;
		LOG.info("start="+start+"limit="+limit);
		String uid = request.getParameter("uid");
		String phone = request.getParameter("phone");
		String customerLever = request.getParameter("customerLever"); 
		
		DetachedCriteriaTS<Customer> criteria = new DetachedCriteriaTS<Customer>(Customer.class);
		
		if(uid != null && !"".equals(uid)){
			criteria.add(Restrictions.like("uid", uid, MatchMode.ANYWHERE));
		}
		if(phone != null && !"".equals(phone)){
			criteria.add(Restrictions.like("phone", phone, MatchMode.ANYWHERE));
		}
		if(customerLever != null && !"".equals(customerLever)){
			criteria.add(Restrictions.like("customerLever", customerLever, MatchMode.ANYWHERE));
		}
		
		pageMode = customerService.findForList(criteria, start, limit);
		if(pageMode != null){
			JsonConfig jsonConfig = new JsonConfig();
			// 设置javabean中日期转换时的格式
			jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
			jsonConfig.setExcludes(new String[] { "custAccount" });
//			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
//			PropertyFilter pf = new PropertyFilter(){
//
//				@Override
//				public boolean apply(Object arg0, String arg1, Object arg2) {
//					// TODO Auto-generated method stub
//					if(arg2 == null || String.valueOf(arg2).equals("")){
//						return true;
//					}
//					return false;
//				}
//				
//			};
//			jsonConfig.setJsonPropertyFilter(pf);
			model.addAttribute("pageMode", JSONObject.fromObject(pageMode,jsonConfig));
			LOG.info("JSONArray.fromObject(pageMode)="+JSONArray.fromObject(pageMode, jsonConfig).toString());
			return model;
		}
		return null;
	}

}
