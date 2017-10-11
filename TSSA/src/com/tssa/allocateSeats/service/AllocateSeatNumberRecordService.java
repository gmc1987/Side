/**
 * 
 */
package com.tssa.allocateSeats.service;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hsqldb.lib.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tssa.allocateSeats.dao.IAllocateSeatNumberRecordDao;
import com.tssa.allocateSeats.pojo.AllocateSeatNumberRecord;
import com.tssa.allocateSeats.pojo.AllocateSeatTypeSet;
import com.tssa.allocateSeats.vo.AllocateSeatNumberSetVO;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.TssaBaseException;
import com.tssa.common.service.BaseBusinessService;

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
			}
			iAllocateSeatNumberRecordDao.saveOrUpdate(record);
			newNum = Integer.parseInt(record.getAllocateNo().replaceAll("[A-Za-z]", ""));
		}
		
		allocateNum += typeSet.getPrefix();
		newNum += typeSet.getBasiceNumber();
		allocateNum += newNum;
		
		AllocateSeatNumberRecord newRecord = new AllocateSeatNumberRecord(allocateNum, null, typeSet);
		iAllocateSeatNumberRecordDao.save(newRecord);
		
		return getNewNumberRecord(typeId, custId);
	}
	
	/**
	 * 查询最新号码
	 * @param typeId
	 * @param custId
	 * @return
	 * @throws Exception
	 */
	public AllocateSeatNumberSetVO getNewNumberRecord(String typeId, String custId) throws Exception {
		if(StringUtil.isEmpty(typeId)) {
			return null;
		}
		return iAllocateSeatNumberRecordDao.getNewRecord(typeId, custId);
	}
}
