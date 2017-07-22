/**
 * 
 */
package com.tssa.allocateSeats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.allocateSeats.dao.IAllocateSeatNumberRecordDao;
import com.tssa.allocateSeats.pojo.AllocateSeatNumberRecord;
import com.tssa.common.service.BaseBusinessService;

/**
 * @author gmc
 *
 */
@Service
public class AllocateSeatNumberRecordService extends BaseBusinessService<AllocateSeatNumberRecord> {
	
	@Autowired
	private IAllocateSeatNumberRecordDao iAllocateSeatNumberRecordDaoImpl;
}
