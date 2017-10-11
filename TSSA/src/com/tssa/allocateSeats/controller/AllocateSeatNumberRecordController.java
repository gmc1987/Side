/**
 * 
 */
package com.tssa.allocateSeats.controller;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.allocateSeats.pojo.AllocateSeatNumberRecord;
import com.tssa.allocateSeats.pojo.AllocateSeatTypeSet;
import com.tssa.allocateSeats.service.AllocateSeatNumberRecordService;
import com.tssa.allocateSeats.service.AllocateSeatTypeSetService;
import com.tssa.allocateSeats.vo.AllocateSeatNumberSetVO;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;

/**
 * @author gmc
 * @see 商户派位模块
 */
@Controller
@RequestMapping("/allocateSeatNumberRecord")
@SessionAttributes(value={"businessCustomer","user"}, types={BusinessCustomer.class,Users.class})
public class AllocateSeatNumberRecordController {

	private static final Logger LOG = LoggerFactory.getLogger(AllocateSeatNumberRecordController.class);
	
	@Autowired
	private AllocateSeatNumberRecordService allocateSeatNumberRecordService;
	@Autowired
	private AllocateSeatTypeSetService allocateSeatTypeSetService;
	
	@RequestMapping("/toList")
	public String toList(HttpServletRequest request, ModelMap map){
		map.clear();
		try {
			BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
			DetachedCriteriaTS<AllocateSeatTypeSet> criteria = new DetachedCriteriaTS<AllocateSeatTypeSet>(AllocateSeatTypeSet.class);
			criteria.add(Restrictions.eq("businessCustomerCode", businessCustomer.getVendorCode()));//取商户编码，并非商户用户编码
			AllocateSeatTypeSet allocateSeatTypeSet = allocateSeatTypeSetService.find(criteria);
			List<AllocateSeatNumberSetVO> list = allocateSeatNumberRecordService.getAllocateSeatNumberRecord(allocateSeatTypeSet, null);
			if(list != null && list.size() > 0) {
				map.put("result", list);
			}else{
				map.put("result", null);
			}
			
		}catch(Exception e) {
			e.printStackTrace();
			LOG.error("查询异常", e);
		}
		return "/AllocateSeatsManage/AllocateSeatsNumberRecordManage";
	}
	
	/**
	 * 用户派号设置
	 * @param typeId
	 * @param custCode
	 * @return
	 */
	@RequestMapping("/createNo")
	@ResponseBody
	public Map<String, Object> createNo(String typeId, String custCode){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(typeId) && StringUtils.isEmpty(custCode)){
			result.put("success", false);
			result.put("msg", "参数异常");
			return result;
		}
		
		try {
			AllocateSeatNumberSetVO vo = allocateSeatNumberRecordService.extraNum(typeId, custCode);
			if(vo != null) {
				result.put("success", true);
				result.put("msg", "出号成功");
				result.put("data", vo);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("msg", "出号异常");
		}
		
		return result;
	}
	
	@RequestMapping("/numInvalid")
	@ResponseBody
	public Map<String, Object> numInvalid(String uuid, String num){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(StringUtils.isEmpty(uuid) && StringUtils.isEmpty(num)){
			result.put("success", false);
			result.put("msg", "参数异常");
			return result;
		}
		
		try {
			AllocateSeatNumberRecord record = allocateSeatNumberRecordService.get(AllocateSeatNumberRecord.class, uuid);
			if(record != null) {
				record.setRecodeStatus("3");
				allocateSeatNumberRecordService.update(record);
				result.put("success", true);
				result.put("data", record);
				result.put("msg", "号码已作废");
			}
		}catch(Exception e) {
			e.printStackTrace();
			result.put("success", true);
			result.put("msg", "发生异常，请稍后再试");
		}
		
		return result;
	}
	
}
