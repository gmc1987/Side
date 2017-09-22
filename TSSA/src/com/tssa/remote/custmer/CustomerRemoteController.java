package com.tssa.remote.custmer;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.tssa.common.util.JsonDateValueProcessor;
import com.tssa.common.util.UtilMD5;
import com.tssa.customer.pojo.Customer;
import com.tssa.customer.service.CustomerService;
import com.tssa.remote.common.BaseController;
import com.tssa.remote.object.CustomerVo;

@Controller
@RequestMapping("/customerRemote")
public class CustomerRemoteController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(CustomerRemoteController.class);
	
	private static final String initPassword = "123456";

	@Autowired
	CustomerService customerService;

	/**
	 * 请求方式：
	 * http://localhost:8080/TSSA/custmerRemote/Login.do?requestData={password:234,loginName:234}
	 * <p>
	 * 方法说明
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/Login")
	public String phoneCustmerLogin(HttpServletRequest request,
			HttpServletResponse response) {
		CustomerVo vo = null;
		try {
			String jsonString = request.getParameter(KEY_REQUEST_DATA);
			if (jsonString == null) {
				jsonString = (String) request.getAttribute(KEY_REQUEST_DATA);
			}
			JSONObject obj = JSONObject.fromObject(jsonString);
			vo = doLogin(obj);
		} catch (Throwable e) {
			logger.error(e.getMessage());
			vo = new CustomerVo();
			vo.setReturnCode(CustomerVo.RETURN_EXCEPTION);
		}
		try {
			if (vo == null) {
				vo = new CustomerVo();
				vo.setReturnCode(CustomerVo.RETURN_EXCEPTION);
			}
			JsonConfig jsonConfig = new JsonConfig();
			// 设置javabean中日期转换时的格式
			jsonConfig.registerJsonValueProcessor(Date.class,
					new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));

			JSONObject obj = JSONObject.fromObject(vo, jsonConfig);
			// 数据经过了压缩处理
			responseOutStream(response, obj.toString());
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * parsetDriverInfo 方法
	 * <p>
	 * 方法说明 执行登录
	 * </p>
	 * 
	 * @param obj
	 * @return
	 * @throws Throwable
	 */
	private CustomerVo doLogin(JSONObject obj) throws Throwable {
		String password = obj.getString("password");
		String loginName = obj.getString("loginId");
		return customerService.login(loginName, password);
	}

	public String changeCharset(String str) throws UnsupportedEncodingException {
		if (str != null) {
			// 用默认字符编码解码字符串。
			byte[] bs = str.getBytes("ISO-8859-1");
			// 用新的字符编码生成字符串
			return new String(bs, "UTF-8");
		}
		return null;
	}

	/**
	 * 请求方式： http://localhost:8080/TSSA/custmerRemote/register.do?requestData={password:234,loginName:234}
	 * <p>
	 * 方法说明
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("register")
	public void register(HttpServletRequest request,
			HttpServletResponse response) {
		String jsonData = request.getParameter(KEY_REQUEST_DATA);
		Customer customer = null;
		CustomerVo vo = null;
		if (jsonData != null) {
			jsonData = (String) request.getAttribute(KEY_REQUEST_DATA);
		}

		JSONObject jsonObject = JSONObject.fromObject(jsonData);
		JSONObject customerObject = jsonObject.getJSONObject("customerInfo");
		try {
			customer = toCustomer(customerObject);
			vo = customerService.registration(customer);
			if (vo != null) {
				if (vo.getReturnCode() == 1) {
					JsonConfig jsonConfig = new JsonConfig();
					// 设置javabean中日期转换时的格式
					jsonConfig.registerJsonValueProcessor(Date.class,
							new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));

					JSONObject obj = JSONObject.fromObject(vo, jsonConfig);
					// 数据经过了压缩处理
					responseOutStream(response, obj.toString());
				}
			}
		} catch (Exception e) {
			logger.error("注册失败", e);
		}
	}

	/**
	 * 检查用户信息
	 * 
	 * @param response
	 * @param requestData
	 */
	@RequestMapping("checkCustomerInfo")
	public void checkCustomerInfo(HttpServletResponse response,
			String requestData) {
		Map<String, Object> result = null;
		try {
			if (StringUtils.isEmpty(requestData)) {
				JSONObject jsonData = JSONObject.fromObject(requestData);
				String key = jsonData.getString("key");
				Customer customer = customerService.checkCustomerIsHave(key);
				if (customer != null) {
					result = new HashMap<String, Object>();
					result.put("returnCode", 0);
					result.put("returnDesc", "用户已存在");
					JSONObject returnObj = JSONObject.fromObject(result);
					responseOutStream(response, returnObj.toString());
				}
			}
		} catch (Exception e) {
			result = new HashMap<String, Object>();
			result.put("returnCode", -1);
			result.put("returnDesc", "检查用户是发生异常");
			JSONObject returnObj = JSONObject.fromObject(result);
			try {
				responseOutStream(response, returnObj.toString());
			} catch (Exception ex) {
				logger.error("异常信息返回失败", e);
			}
			logger.error("检查用户信息异常", e);
		}

	}

	/**
	 * 注册用户信息
	 * 
	 * @param jsonObject
	 * @return
	 */
	public Customer toCustomer(JSONObject jsonObject) {

		Customer customer = null;
		String registerPhone = null;
		String password = null;

		if (jsonObject != null) {
			customer = new Customer();
			registerPhone = jsonObject.getString("mobilePhone");
			password = jsonObject.getString("password");
			if ((registerPhone != null && !"".equals(registerPhone))
					&& (password != null && !"".equals(password))) {
				customer.setPhone(registerPhone);
				customer.setPassword(UtilMD5.MD5(password));
				customer.setUsername("u" + registerPhone);
				customer.setNickname("u" + registerPhone);
				customer.setCustomerLever(1);
			}
		}

		return customer;
	}

	@RequestMapping("/customerPictureUpload")
	@ResponseBody
	public Map<String, Object> customerPictureUpload(HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		String filePath = "";
		try {
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile multiFile = multipartRequest.getFile("file");
			String cid = multipartRequest.getParameter("cid");

			Customer customer = customerService.get(Customer.class,
					cid);
			
			if(customer == null){
				result.put("success", false);
				result.put("msg", "获取上传文件失败");
				return result;
			}
			
			String fileUrl = request.getServletContext().getRealPath("/")
					+ "cust-upload/";
			String viewUrl = request.getServletContext().getContextPath()
					+ "/cust-upload/";
			// 测试使用
			// String fileUrl =
			// "/Users/gmc/Works/eclipse/workspace/TSSA/WebContent/WEB-INF/upload/";

			File file = new File(fileUrl);
			if (!file.exists()) {
				file.mkdirs();
			}

			if (multiFile != null
					&& (multiFile.getOriginalFilename() != null || !""
							.equals(multiFile.getOriginalFilename()))) {
				File uploadFile = new File(fileUrl
						+ multiFile.getOriginalFilename());
				multiFile.transferTo(uploadFile);

				filePath = viewUrl + multiFile.getOriginalFilename();

				result.put("success", true);
				result.put("url", filePath);
				
				customer.setHeadPicture(filePath);
				customerService.update(customer);

			} else {
				result.put("success", false);
				result.put("msg", "获取上传文件失败");
				return null;
			}
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
		}
		return result;
	}

	/**
	 * 客户修改信息接口
	 * 
	 * @param customerVo
	 * @return
	 */
	@RequestMapping("/updateField")
	@ResponseBody
	public Map<String, Object> updateFields(HttpServletRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();

		String data = request.getParameter("customerVo");
		try {

			JSONObject jsonData = JSONObject.fromObject(data);
			
			String[] dateFormats = new String[] {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"};  
			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(dateFormats)); 
			
			CustomerVo customerVo = (CustomerVo) JSONObject.toBean(jsonData
					, CustomerVo.class);

			if (customerVo != null) {

				Customer customer = customerService.get(Customer.class,
						customerVo.getCid());
				// 昵称
				if (customerVo.getNickname() != null
						&& !customer.getNickname().equals(
								customerVo.getNickname())) {
					customer.setNickname(customerVo.getNickname());
				}
				// 用户名
				if (customerVo.getUsername() != null
						&& !customer.getUsername().equals(
								customerVo.getUsername())) {
					customer.setUsername(customerVo.getUsername());
				}
				// 手机号
				if (customerVo.getPhone() != null
						&& !customer.getPhone().equals(customerVo.getPhone())) {
					customer.setPhone(customerVo.getPhone());
				}
				// 性别
				if (customerVo.getSex() != null
						&& customer.getSex() != customerVo.getSex()) {
					customer.setSex(customerVo.getSex());
				}
				// 生日
				if (customerVo.getBirthday() != null) {
					customer.setBirthday(customerVo.getBirthday());
				}
				// qq
				if (customerVo.getQq() != null) {
					
					customer.setQq(customerVo.getQq());
				}

				customerService.update(customer);
				result.put("success", true);
				result.put("msg", "信息修改成功");
			} else {
				result.put("success", false);
				result.put("msg", "传输参数异常");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", "数据转换出错");
		}

		return result;
	}
	
	/**
	 * 密码更新方法
	 * @param cid
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	@RequestMapping("/updatePassword")
	@ResponseBody
	public Map<String, Object> updatePassword(String cid, String oldPassword, String newPassword){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(cid != null && !"".equals(cid)){
			Customer customer = customerService.get(Customer.class, cid);
			if(customer != null){
				if(customer.getPassword().equals(oldPassword)){
					customer.setPassword(newPassword);
					customerService.update(customer);
					result.put("success", true);
					result.put("msg", "密码修改成功");
				} else {
					result.put("success", false);
					result.put("msg", "原密码不正确");
				}
			} else {
				result.put("success", false);
				result.put("msg", "查找用户信息失败");
			}
		} else {
			result.put("success", false);
			result.put("msg", "参数异常");
		}
		
		return result;
	}
	
	/**
	 * 忘记密码
	 * @param cid 客户编号
	 * @return
	 */
	@RequestMapping("forgetPassword")
	@ResponseBody
	public Map<String, Object> forgetPassword(String cid){
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(cid != null && !"".equals(cid)){
			Customer customer = customerService.get(Customer.class, cid);
			if(customer != null){
				customer.setPassword(initPassword);
				customerService.update(customer);
				result.put("success", true);
				result.put("msg", "密码已重置为123456，请重新登录并修改您的密码");
			} else {
				result.put("success", false);
				result.put("msg", "查找用户信息失败");
			}
		} else {
			result.put("success", false);
			result.put("msg", "参数异常");
		}
		return result;
	}

}
