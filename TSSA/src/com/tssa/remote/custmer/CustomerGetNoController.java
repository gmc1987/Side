/**
 * 
 */
package com.tssa.remote.custmer;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.allocateSeats.pojo.AllocateSeatNumberRecord;
import com.tssa.allocateSeats.pojo.AllocateSeatTypeSet;
import com.tssa.allocateSeats.service.AllocateSeatNumberRecordService;
import com.tssa.allocateSeats.service.AllocateSeatTypeSetService;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.util.DateWarpUtils;
import com.tssa.cooperationBusiness.service.CooperationService;
import com.tssa.remote.common.BaseController;
import com.tssa.remote.object.CustNumberVo;
import com.tssa.remote.object.CustTakeNumberVO;

/**
 * @author gmc870223
 * @see 客户手机取号服务接口，提供客户取号
 */

@Controller
@RequestMapping("/clientGetNo")
public class CustomerGetNoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerGetNoController.class);

	@Autowired
	private AllocateSeatTypeSetService allocateSeatTypeSetService;
	@Autowired
	private AllocateSeatNumberRecordService allocateSeatNumberRecordService;
	@Autowired
	private CooperationService cooperationService;

	/**
	 * 查询商户当前号数与排队等待人数 请求方式：
	 * http://localhost:8080/TSSA/clientGetNo/showNum.do?vendorCode=xx
	 * 
	 * @param vendorCode
	 * @param typeId
	 * @return
	 */
	@RequestMapping("/showNum")
	@ResponseBody
	public Map<String, Object> showNum(String vendorCode) {
		// 1、查出商户当前不同出号规则所出号码
		// 2、计算出不同出号规则所有记录状态等于1的记录进行统计，作为前面等待人数输出显示
		Map<String, Object> result = new HashMap<String, Object>();
		if (StringUtils.isEmpty(vendorCode)) {
			result.put("success", false);
			result.put("msg", "商户未启用手机取号功能");
			return result;
		} else {
			try {
				List<CustNumberVo> list = allocateSeatNumberRecordService.getVendorAllNumberCount(vendorCode);
				if (list != null && list.size() > 0) {
					result.put("success", true);
					result.put("msg", "");
					result.put("data", list);
				}
			} catch (Exception e) {
				result.put("success", false);
				result.put("msg", "获取商户等待人数失败");
				logger.error(e.getMessage(), e);
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 手机端出号功能 请求方式：
	 * http://localhost:8080/TSSA/clientGetNo/extraNum.do?vendorCode=xx&custId=xx
	 * 
	 * @param vendorCode
	 * @param typeId
	 * @param vendorCode
	 * @return
	 */
	@RequestMapping("/extraNum")
	@ResponseBody
	public Map<String, Object> extraNum(String typeId, String custId, String vendorCode) {

		Map<String, Object> result = new HashMap<String, Object>();
		try {
			allocateSeatNumberRecordService.extraNum(typeId, custId);
			List<CustNumberVo> list = allocateSeatNumberRecordService.getVendorAllNumberCount(vendorCode);
			if (list != null && list.size() > 0) {
				result.put("success", true);
				result.put("msg", "");
				result.put("data", list);
			}
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "获取商户等待人数失败");
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 请求方式：
	 * http://localhost:8080/TSSA/clientGetNo/checkCustHasTakeNumber.do?vendorCode=xx&custId=xx&typeId=xx
	 * 检查用户是否已经取号
	 * 
	 * @param custId
	 *            客户编码
	 * @param typeId
	 *            派号类型主键
	 * @return
	 */
	@RequestMapping("/checkCustHasTakeNumber")
	@ResponseBody
	public Map<String, Object> checkCustHasTakeNumber(String custId, String typeId) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			AllocateSeatTypeSet set = allocateSeatTypeSetService.get(AllocateSeatTypeSet.class, typeId);
			if (set != null) {
				DetachedCriteriaTS<AllocateSeatNumberRecord> detachedCriteria = new DetachedCriteriaTS<AllocateSeatNumberRecord>(
						AllocateSeatNumberRecord.class);
				detachedCriteria.add(Restrictions.eq("allocateSeatType", set));
				detachedCriteria.add(Restrictions.eq("custId", custId));
				detachedCriteria.add(Restrictions.eq("recodeStatus", "1"));
				detachedCriteria.add(Restrictions.between("createDate",
						DateWarpUtils.parseDate(DateWarpUtils.format(new Date()) + " 00:00:00"),
						DateWarpUtils.parseDate(DateWarpUtils.format(new Date()) + " 23:59:59")));
				AllocateSeatNumberRecord record = allocateSeatNumberRecordService.find(detachedCriteria);
				if (record != null && StringUtils.isNotEmpty(record.getCustId())) {
					result.put("success", true);
					result.put("msg", "您已取号,请勿重复取号");
					result.put("flag", "1");// 向前端返回1表示客户已经取过号。前端应判断为不能取号
				} else {
					result.put("success", true);
					result.put("msg", "您已取号,请勿重复取号");
					result.put("flag", "0");// 向前端返回0表示客户未曾取号。前端应判断为可取号
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", "检查时发生异常");
		}
		return result;
	}

	/**
	 * 请求方式：
	 * http://localhost:8080/TSSA/clientGetNo/getCustTakeNumDetail.do?custId=xx&start=0&limit=10
	 * 获取用户所有取号数据
	 * 
	 * @param custId
	 * @return
	 */
	@RequestMapping("getCustTakeNumDetail")
	@ResponseBody
	public Map<String, Object> getCustTakeNumDetail(String custId, Integer start, Integer limit) {
		Map<String, Object> result = new HashMap<String, Object>();

		if (StringUtils.isEmpty(custId)) {
			result.put("success", false);
			result.put("msg", "参数有误");
			return result;
		}

		try {
			List<CustTakeNumberVO> resultData = allocateSeatNumberRecordService.getCustTakeNumberDeatil(custId, start, limit);
			if(resultData != null && resultData.size() > 0) {
				result.put("success", true);
				result.put("data", resultData);
				result.put("msg", "");
			}else {
				result.put("success", false);
				result.put("data", null);
				result.put("msg", "没有任何记录");
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", false);
			result.put("data", null);
			result.put("msg", "查询异常");
		}

		return result;
	}

}
