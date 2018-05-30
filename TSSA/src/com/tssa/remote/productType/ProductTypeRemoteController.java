/**
 * 
 */
package com.tssa.remote.productType;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.common.util.JsonDateValueProcessor;
import com.tssa.cooperationBusiness.service.CooperationProductTypeService;
import com.tssa.remote.common.BaseController;
import com.tssa.remote.object.ProductTypeVo;
import com.tssa.remote.object.ProductVo;

/**
 * @author gmc
 * @see 产品类型查询服务
 */

@Controller
@RequestMapping("/productTypeRemote")
public class ProductTypeRemoteController extends BaseController {

	private static final Logger logger = LoggerFactory
			.getLogger(ProductTypeRemoteController.class);

	@Autowired
	private CooperationProductTypeService cooperatinoProductTypeService;

	/**
	 * 请求方式：
	 * http://localhost:8080/TSSA/productTypeRemote/productList.do?requestData
	 * ={professionId
	 * :xxxxx,professionName:xxxxx,ProfessionCode:xxxx,pageSize:10,pageNumber:1}
	 * <p>
	 * 查询行业产品信息
	 * </p>
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("productList")
	public void productTypeList(String requestData,
			HttpServletResponse response) {
		
//		String requestData = request.getParameter(KEY_REQUEST_DATA);
//		if (requestData != null && !"".equals(requestData)) {
//			requestData = (String) request.getAttribute(KEY_REQUEST_DATA);
//		}

		try {
			JSONObject jsonObject = JSONObject.fromObject(requestData);
			String productTypeId = jsonObject.getString("professionId");

			List<ProductTypeVo> productTypes = cooperatinoProductTypeService
					.findProductTypeByParentId(productTypeId);

			if (productTypes != null) {
				JsonConfig jsonConfig = new JsonConfig();
				// 设置javabean中日期转换时的格式
				jsonConfig.registerJsonValueProcessor(Date.class,
						new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
				JSONArray array = JSONArray
						.fromObject(productTypes, jsonConfig);

				responseOutStream(response, array.toString());
			}

		} catch (Exception e) {
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("returnCode", ProductVo.RETURN_EXCEPTION);
			result.put("returnDesc", ProductVo.RETURN_EXCEPTION_DESC);
			logger.error(e.getMessage(), e);
			try {
				responseOutStream(response, JSONArray.fromObject(result)
						.toString());
			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 请求方式: http://localhost:8080/TSSA/productTypeRemote/professionList.do
	 * <p>
	 * 查询行业分类
	 * </p>
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("professionList")
	public void findProfessionClass(String requestData,
			HttpServletResponse response) {

		try {
			
			List<ProductTypeVo> productTypes = cooperatinoProductTypeService.findAllProfessionProduct();
			
			if (productTypes != null) {
				JsonConfig jsonConfig = new JsonConfig();
				// 设置javabean中日期转换时的格式
				jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd HH:mm:ss"));
				JSONArray array = JSONArray.fromObject(productTypes, jsonConfig);
				responseOutStream(response, array.toString());
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("returnCode", ProductVo.RETURN_EXCEPTION);
			result.put("returnDesc", ProductVo.RETURN_EXCEPTION_DESC);
			logger.error(e.getMessage(), e);
			try {
				responseOutStream(response, JSONArray.fromObject(result)
						.toString());
			} catch (Exception e1) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	/**
	 * 根据商户id查找商户产品信息 
	 * 请求方式：
	 * 
	 * @param request
	 * @param response
	 * @param businessId
	 * @param searchKey
	 * @param searchType
	 */
	@RequestMapping("/findBusinessProductById")
	@ResponseBody
	public void findBusinessProductById(String businessId, String searchKey,
			String searchType, HttpServletRequest request,
			HttpServletResponse response) {

	}
}
