/**
 * 
 */
package com.tssa.allocateSeats.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tssa.allocateSeats.dao.IAllocateSeatNumberRecordDao;
import com.tssa.allocateSeats.pojo.AllocateSeatNumberRecord;
import com.tssa.allocateSeats.pojo.AllocateSeatTypeSet;
import com.tssa.allocateSeats.vo.AllocateSeatNumberSetVO;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.common.mode.TssaBaseException;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.common.util.DateWarpUtils;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.service.CooperationService;
import com.tssa.remote.object.CustNumberVo;
import com.tssa.remote.object.CustTakeNumberVO;

/**
 * @author gmc
 *
 */
@Service
public class AllocateSeatNumberRecordService extends BaseBusinessService<AllocateSeatNumberRecord> {
	@Autowired
	private AllocateSeatTypeSetService allocateSeatTypeSetService;
	@Autowired
	private IAllocateSeatNumberRecordDao iAllocateSeatNumberRecordDao;
	@Autowired
	private CooperationService cooperationService;
	
	/**
	 * 初始化出号
	 * @param allocateSeatTypeSet
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public List<AllocateSeatNumberSetVO> getAllocateSeatNumberRecord(AllocateSeatTypeSet allocateSeatTypeSet, String custId) throws Exception{
		
		if(allocateSeatTypeSet == null){
			throw new TssaBaseException("参数异常");
		}
		
		List<AllocateSeatNumberSetVO> voList = allocateSeatTypeSetService.getAllocateSeatType(allocateSeatTypeSet);
		
		for(AllocateSeatNumberSetVO record : voList) {
			if(record != null && !"null".equals(record.getNum())){
				continue;
			}else{
				AllocateSeatTypeSet type = allocateSeatTypeSetService.get(AllocateSeatTypeSet.class, record.getUuid());
				String allocateNo = type.getPrefix();
				Integer basiceNumber = 0;
				basiceNumber += type.getBasiceNumber();
				allocateNo += basiceNumber;
				AllocateSeatNumberRecord number = new AllocateSeatNumberRecord(allocateNo, custId, type);
				iAllocateSeatNumberRecordDao.saveOrUpdate(number);
			}
		}
		
		return allocateSeatTypeSetService.getAllocateSeatType(allocateSeatTypeSet);
	}
	
	/**
	 * 取号
	 * @param typeId
	 * @param custId
	 * @throws Exception
	 */
	@Transactional
	public AllocateSeatNumberSetVO extraNum(String typeId, String custId) throws Exception {
		
		AllocateSeatTypeSet typeSet = allocateSeatTypeSetService.get(AllocateSeatTypeSet.class, typeId);
		AllocateSeatNumberRecord record = null;
		
		if(typeSet == null) {
			return null;
		}
		
		DetachedCriteriaTS<AllocateSeatNumberRecord> criteria = new DetachedCriteriaTS<AllocateSeatNumberRecord>(AllocateSeatNumberRecord.class);
		criteria.add(Restrictions.eq("allocateSeatType", typeSet));
//		criteria.add(Restrictions.eq("recodeStatus", "1"));
		criteria.add(Restrictions.between("createDate", DateWarpUtils.parseDate(DateWarpUtils.format(new Date()) + " 00:00:00"), DateWarpUtils.parseDate(DateWarpUtils.format(new Date()) + " 23:59:59")));
		criteria.addOrder(Order.desc("createDate"));
		List<AllocateSeatNumberRecord> records = iAllocateSeatNumberRecordDao.find(criteria, 1);
		if(records != null && records.size() > 0) {
			record = records.get(0);
		}
		int newNum = 0;
		String allocateNum = "";
		
		if(record != null) {
			if("0".equals(record.getRecodeStatus())) {
				record.setRecodeStatus("1");
				record.setCustId(custId);
			}
			iAllocateSeatNumberRecordDao.saveOrUpdate(record);
			newNum = Integer.parseInt(record.getAllocateNo().replaceAll("[A-Za-z]", ""));
		}
		
		allocateNum += typeSet.getPrefix();
		newNum += typeSet.getBasiceNumber();
		allocateNum += newNum;
		
		AllocateSeatNumberRecord newRecord = new AllocateSeatNumberRecord(allocateNum, null, typeSet);
		iAllocateSeatNumberRecordDao.save(newRecord);
		
		return getNewNumberRecord(typeId);
	}
	
	/**
	 * 查询最新号码
	 * @param typeId
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public AllocateSeatNumberSetVO getNewNumberRecord(String typeId) throws Exception {
		if(StringUtil.isEmpty(typeId)) {
			return null;
		}
		return iAllocateSeatNumberRecordDao.getNewRecord(typeId);
	}
	
	/**
	 * 查询等待人数
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public int getCount(String typeId)  throws Exception {
		AllocateSeatTypeSet typeSet = allocateSeatTypeSetService.get(AllocateSeatTypeSet.class, typeId);
		DetachedCriteriaTS<AllocateSeatNumberRecord> criteria = new DetachedCriteriaTS<AllocateSeatNumberRecord>(AllocateSeatNumberRecord.class);
		criteria.add(Restrictions.eq("allocateSeatType", typeSet));
		criteria.add(Restrictions.eq("recodeStatus", "1"));
		criteria.add(Restrictions.between("createDate", DateWarpUtils.parseDate(DateWarpUtils.format(new Date()) + " 00:00:00"), DateWarpUtils.parseDate(DateWarpUtils.format(new Date()) + " 23:59:59")));
		return iAllocateSeatNumberRecordDao.count(criteria);
	}
	
	/**
	 * 查询商户所有派位规则等待人数
	 */
	public List<CustNumberVo> getVendorAllNumberCount(String vendor) throws Exception {
		List<CustNumberVo> list = null;
		DetachedCriteriaTS<AllocateSeatTypeSet> criteria1 = new DetachedCriteriaTS<AllocateSeatTypeSet>(AllocateSeatTypeSet.class);
		criteria1.add(Restrictions.eq("businessCustomerCode", vendor));
		List<AllocateSeatTypeSet> typeList = allocateSeatTypeSetService.findAll(criteria1);
		DetachedCriteriaTS<CooperationBusiness> criteria2 = new DetachedCriteriaTS<CooperationBusiness>(CooperationBusiness.class);
		criteria2.add(Restrictions.eq("cooperCode", vendor));
		CooperationBusiness cooperationBusiness = cooperationService.find(criteria2);
		if(typeList != null && typeList.size() > 0) {
			list = new ArrayList<CustNumberVo>();
			for(AllocateSeatTypeSet type : typeList) {
				CustNumberVo vo = new CustNumberVo();
				DetachedCriteriaTS<AllocateSeatNumberRecord> criteria = new DetachedCriteriaTS<AllocateSeatNumberRecord>(AllocateSeatNumberRecord.class);
				criteria.add(Restrictions.eq("allocateSeatType", type));
				criteria.add(Restrictions.eq("recodeStatus", "1"));
				criteria.add(Restrictions.between("createDate", DateWarpUtils.parseDate(DateWarpUtils.format(new Date()) + " 00:00:00"), DateWarpUtils.parseDate(DateWarpUtils.format(new Date()) + " 23:59:59")));
				int num = iAllocateSeatNumberRecordDao.count(criteria);
				
				List<AllocateSeatNumberSetVO> numberSet = this.getAllocateSeatNumberRecord(type, null);
				
				for(AllocateSeatNumberSetVO setVO : numberSet) {
					if(setVO.getTypeId().equals(type.getUuid())) {
						vo.setFrontCount(num);
						vo.setTypeId(type.getUuid());
						vo.setTypeName(type.getTypeName());
						vo.setVendorCode(cooperationBusiness.getCooperCode());
						vo.setVendorName(cooperationBusiness.getCooperName());
						vo.setCustNum(setVO.getNum());
						list.add(vo);
					}
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取当前客户所有取号明细数据。
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public List<CustTakeNumberVO> getCustTakeNumberDeatil(String custId, int start, int limit) throws Exception{
		List<CustTakeNumberVO> list = null;
		DetachedCriteriaTS<AllocateSeatNumberRecord> detachedCriteria = new DetachedCriteriaTS<AllocateSeatNumberRecord>(
				AllocateSeatNumberRecord.class);
		detachedCriteria.add(Restrictions.eq("custId", custId));
		detachedCriteria.addOrder(Order.desc("createDate"));
		PageMode<AllocateSeatNumberRecord> pageMode = findForList(detachedCriteria,
				start, limit);
		if (pageMode != null && pageMode.getRecords().size() > 0) {
			list = new ArrayList<CustTakeNumberVO>();
			for (AllocateSeatNumberRecord record : pageMode.getRecords()) {
				String vendorCode = record.getAllocateSeatType().getBusinessCustomerCode();
				DetachedCriteriaTS<CooperationBusiness> vendorCriteria = new DetachedCriteriaTS<CooperationBusiness>(
						CooperationBusiness.class);
				vendorCriteria.add(Restrictions.eq("cooperCode", vendorCode));
				CooperationBusiness vendor = cooperationService.find(vendorCriteria);
				//统计等待人数
				DetachedCriteriaTS<AllocateSeatNumberRecord> detachedCriteria2= new DetachedCriteriaTS<AllocateSeatNumberRecord>(
						AllocateSeatNumberRecord.class);
				detachedCriteria2.add(Restrictions.between("createDate", DateWarpUtils.parseDate(DateWarpUtils.format(new Date()) + " 00:00:00"), DateWarpUtils.parseDate(DateWarpUtils.format(new Date()) + " 23:59:59")));
				detachedCriteria2.add(Restrictions.eq("allocateSeatType", record.getAllocateSeatType()));
				detachedCriteria2.add(Restrictions.eq("recodeStatus", "1"));
				detachedCriteria2.add(Restrictions.lt("allocateNo", record.getAllocateNo()));
				int waitNum = iAllocateSeatNumberRecordDao.count(detachedCriteria2);
				String wait = "0";
				if(waitNum != 0) {
					wait = String.valueOf(waitNum);
				}else {
					wait = "0";
				}
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				CustTakeNumberVO vo = new CustTakeNumberVO(record.getUuid(), vendorCode, vendor.getCooperName(),
						record.getAllocateNo(), wait, record.getRecodeStatus(),
						dateFormat.format(record.getCreateDate()));
				list.add(vo);
			}
		}

		return list;
	}
	
}
