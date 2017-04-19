/**
 * 
 */
package com.tssa.remote.custmer;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tssa.businessInterface.service.ProductService;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.service.CooperationService;
import com.tssa.remote.common.BaseController;
import com.tssa.remote.object.ProductVo;

/**
 * @author gmc
 * @see 产品信息接口
 */
@Controller
@RequestMapping("/productInfoRemote")
public class ProductRemoteController extends BaseController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CooperationService cooperationService;
	
	/**
	 * 请求方式：
	 * http://localhost:8080/TSSA/productInfoRemote/findProductList.do?requestData={vendorCode:xxxxx,productType:xxxxx,productCode:xxxx,productName:xxxxx,pageSize:10,pageNumber:1}
	 * <p>查询商户产品信息</p>
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("findProductList")
	public String findProductList(String requestData, HttpServletRequest request,HttpServletResponse response){
		
		List<ProductVo> products = null;
		Map<String,Object> result = null;
		
		try{
//			String data = request.getParameter(KEY_REQUEST_DATA);
//			if(data == null || "".equals(data)){
//				data = (String) request.getAttribute(KEY_REQUEST_DATA);
//			}
			JSONObject object = JSONObject.fromObject(requestData);
			String vendorCode = object.getString("vendorId");
			String productName = object.getString("productName");
			int pageSize = 10;
			int pageNumber = object.getInt("pageNumber");
			
			DetachedCriteriaTS<CooperationBusiness> vendorCriteria = new DetachedCriteriaTS<CooperationBusiness>(CooperationBusiness.class);
			
			/*查询商户信息*/
			if(vendorCode != null && !"".equals(vendorCode)){
				vendorCriteria.add(Restrictions.eq("cooperId", vendorCode));
			}
			CooperationBusiness vendor = cooperationService.find(vendorCriteria);
			
			/*查询产品列表*/
			products = productService.findProduct(vendor, productName, pageSize, pageNumber);
			
			//数据经过了压缩处理
			responseOutStream(response,JSONArray.fromObject(products).toString());
		}catch(Exception e){
			result = new HashMap<String, Object>();
			result.put("returnCode", ProductVo.RETURN_EXCEPTION);
			result.put("returnDesc", ProductVo.RETURN_EXCEPTION_DESC);
			logger.error("查询异常", e);
			try {
				responseOutStream(response,JSONArray.fromObject(result).toString());
			} catch (IOException e1) {
				logger.error("返回异常", e1);
			}
		}
		return null;
	}
	
	
	@RequestMapping("findProductInfo")
	public String findProductInfo(HttpServletRequest request,HttpServletResponse response){
		
		try{
			String data = request.getParameter(KEY_REQUEST_DATA);
			if(data == null || "".equals(data)){
				data = (String) request.getAttribute(KEY_REQUEST_DATA);
			}
			JSONObject object = JSONObject.fromObject(data);
			String productCode = object.getString("productCode");
			String productName = object.getString("productName");
			String productType = object.getString("productTypeId");
			
			/*查询产品列表*/
			ProductVo vo = productService.findProductInfo(productCode, productName, productType);
			//数据经过了压缩处理
			responseOutStream(response,JSONObject.fromObject(vo).toString());
		}catch(Exception e){
			ProductVo vo = new ProductVo();
			vo.setReturnCode(ProductVo.RETURN_EXCEPTION);
			vo.setReturnDesc(ProductVo.RETURN_EXCEPTION_DESC);
			logger.error("查询异常", e);
			try {
				responseOutStream(response,JSONObject.fromObject(vo).toString());
			} catch (IOException e1) {
				logger.error("返回异常", e1);
			}
		}
		return null;
	}
}
