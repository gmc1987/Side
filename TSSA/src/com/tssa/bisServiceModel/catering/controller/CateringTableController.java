/**
 * 
 */
package com.tssa.bisServiceModel.catering.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.bisServiceModel.catering.pojo.CateringTable;
import com.tssa.bisServiceModel.catering.service.CateringTableService;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.common.util.QRCodeEncoderHandler;
import com.tssa.common.util.TSSAUtil;

import net.sf.json.JSONObject;

/**
 * @author gmc870223
 * @see 餐桌管理控制器
 */
@Controller
@RequestMapping("/cateringTable")
public class CateringTableController {

	private static final Logger logger = LoggerFactory.getLogger(CateringTableController.class);
	
	@Autowired
	private CateringTableService cateringTableService;
	
	@RequestMapping("/toCateringTable")
	public String toCateringTable() {
		return "/cateringTable/list";
	}
	
	/**
	 * 查询数据
	 * @param pageNumber
	 * @param pageSize
	 * @param table
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping("/getData")
	@ResponseBody
	public Map<String, Object> getData(Integer pageNumber, Integer pageSize, CateringTable table, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		if(pageNumber == null && pageSize == null) {
			pageNumber = 1;
			pageSize = 10;
		}
		DetachedCriteriaTS<CateringTable> criteria = new DetachedCriteriaTS<CateringTable>(CateringTable.class);
		if(businessCustomer != null) {
			criteria.add(Restrictions.eq("cooperId", businessCustomer.getBusinessCustomerCode()));
		}else {
			if(!StringUtils.isBlank(table.getCooperId())) {
				criteria.add(Restrictions.ilike("cooperId", table.getCooperId(), MatchMode.ANYWHERE));
			}
		}
		criteria.addOrder(Order.desc("tableNo"));
		
		if(!StringUtils.isBlank(table.getStatus())) {
			criteria.add(Restrictions.eq("status", table.getStatus()));
		}
		if(!StringUtils.isBlank(table.getTableType())) {
			criteria.add(Restrictions.eq("tableType", table.getTableType()));
		}
		if(!StringUtils.isBlank(table.getTableNo())) {
			criteria.add(Restrictions.like("tableNo", table.getTableNo(), MatchMode.ANYWHERE));
		}
		if(!StringUtils.isBlank(table.getHoldNum())) {
			criteria.add(Restrictions.eq("holdNum", table.getHoldNum()));
		}
		try {
			PageMode<CateringTable> pageMode = cateringTableService.findForList(criteria, pageNumber, pageSize);
			if(businessCustomer != null) {
				result.put("cooperId", businessCustomer.getVendorCode());
			}
			result.put("success", true);
			result.put("data", JSONObject.fromObject(pageMode));
		} catch(Exception e) {
			logger.error("查询异常", e);
			result.put("success", false);
			result.put("msg", "查询失败");
		}
		return result;
	}
	
	/**
	 * 新增餐桌信息
	 * @param table
	 * @return
	 */
	@RequestMapping("/addTable")
	@ResponseBody
	public Map<String, Object> addTable(CateringTable table, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		try {
			if(businessCustomer == null) {
				result.put("success", false);
				result.put("msg", "您不属于该商户员工，无权做此操作");
				return result;
			}
			table.setCooperId(businessCustomer.getBusinessCustomerCode());
			table.setStatus("0");
			table.setQrCodeURL(this.createQRCode(request, businessCustomer.getBusinessCustomerId(), table.getTableNo()));
			cateringTableService.save(table);
			result.put("success", true);
			result.put("msg", "新增成功");
		} catch(Exception e) {
			logger.error("新增异常", e);
			result.put("success", false);
			result.put("msg", "新增失败");
		}
		return result;
	}
	
	/**
	 * 批量新增餐桌信息
	 * @param begin
	 * @param end
	 * @param suffx
	 * @param table
	 * @return
	 */
	@RequestMapping("/batchAddTable")
	@ResponseBody
	public Map<String, Object> batchAddTable(Integer begin, Integer end, String suffx, CateringTable table, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		if(begin == null || end == null || suffx == null) {
			result.put("success", false);
			result.put("msg", "前缀、开始编号、结束编号不能为空");
			return result;
		}
		try {
			if(businessCustomer == null) {
				result.put("success", false);
				result.put("msg", "您不属于该商户员工，无权做此操作");
				return result;
			}
			table.setCooperId(businessCustomer.getBusinessCustomerCode());
			table.setStatus("0");
			table.setQz(suffx);
			table.setQrCodeURL(this.createQRCode2(request, businessCustomer.getBusinessCustomerId()));
			table.setQrCodeFileURL(this.getRealPath(request, businessCustomer.getBusinessCustomerId()));
			cateringTableService.tabelBatchAdd(begin, end, table);
			result.put("success", true);
			result.put("msg", "新增成功");
		}catch(Exception e) {
			logger.error("异常信息", e);
			result.put("success", false);
			result.put("msg", "新增失败");
		}
		return result;
	}
	
	@RequestMapping("/delTable")
	@ResponseBody
	public Map<String, Object> delTable(CateringTable table, HttpServletRequest request){
		Map<String, Object> result = new HashMap<String, Object>();
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		try {
			if(businessCustomer == null) {
				result.put("success", false);
				result.put("msg", "您不属于该商户员工，不能删除数据");
				return result;
			}
			cateringTableService.delete(table);
			result.put("success", true);
			result.put("msg", "删除成功");
		} catch(Exception e) {
			logger.error("删除异常", e);
			result.put("success", false);
			result.put("msg", "删除失败");
		}
		return result;
	}
	
	@RequestMapping("/modTable")
	@ResponseBody
	public Map<String, Object> modTable(CateringTable table, HttpServletRequest request) {
		Map<String, Object> result = new HashMap<String, Object>();
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		try {
			if(businessCustomer == null) {
				result.put("success", false);
				result.put("msg", "您不属于该商户员工，不能删除数据");
				return result;
			}
			cateringTableService.update(table);
			result.put("success", true);
			result.put("msg", "修改成功");
		} catch(Exception e) {
			logger.error("修改异常", e);
			result.put("success", false);
			result.put("msg", "修改失败");
		}
		return result;
	}
	
	/**
	 * 生成餐桌二维码
	 * @param bisCustId
	 * @param tableNo
	 * @return
	 * @throws Exception
	 */
	private String createQRCode(HttpServletRequest request, String bisCustId, String tableNo) throws Exception{
		String imgPath = request.getServletContext().getRealPath("");
		imgPath = imgPath+File.separator+"QrCode"+File.separator+bisCustId;
		String imgName = tableNo+".png";
		StringBuffer contents = new StringBuffer();
		contents.append("cooperId=");
		contents.append(bisCustId);
		contents.append("&tableNo="+tableNo);
        File imgDirectory = new File(imgPath);
        if (!imgDirectory.isDirectory()) {
        		imgDirectory.mkdirs();
		}
        File imgFile = new File(imgDirectory+File.separator+imgName);
        QRCodeEncoderHandler qrCode = new QRCodeEncoderHandler();
        qrCode.encode(contents.toString(), 100, 100, "png" ,imgFile);
		return File.separator+"QrCode"+File.separator+bisCustId + File.separator+imgName;
	}
	
	/**
	 * 生成餐桌二维码
	 * @param bisCustId
	 * @param tableNo
	 * @return
	 * @throws Exception
	 */
	private String createQRCode2(HttpServletRequest request, String bisCustId) throws Exception{
		String imgPath = request.getServletContext().getRealPath("");
		imgPath = imgPath+File.separator+"QrCode"+File.separator+bisCustId;
        File imgDirectory = new File(imgPath);
        if (!imgDirectory.isDirectory()) {
        		imgDirectory.mkdirs();
		}
		return File.separator+"QrCode"+File.separator+bisCustId;
	}
	
	/**
	 * 获取真实路径
	 * @param request
	 * @param bisCustId
	 * @return
	 * @throws Exception
	 */
	private String getRealPath(HttpServletRequest request, String bisCustId) throws Exception{
		String imgPath = request.getServletContext().getRealPath("");
		imgPath = imgPath+File.separator+"QrCode"+File.separator+bisCustId;
        File imgDirectory = new File(imgPath);
        if (!imgDirectory.isDirectory()) {
        		imgDirectory.mkdirs();
		}
		return imgDirectory.getPath();
	}
}
