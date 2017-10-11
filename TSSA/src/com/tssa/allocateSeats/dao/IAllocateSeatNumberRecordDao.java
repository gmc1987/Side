/**
 * 
 */
package com.tssa.allocateSeats.dao;

import com.tssa.allocateSeats.vo.AllocateSeatNumberSetVO;
import com.tssa.common.dao.HibernateEntitryDao;

/**
 * @author gmc
 *
 */
public interface IAllocateSeatNumberRecordDao extends HibernateEntitryDao {
	
	public AllocateSeatNumberSetVO getNewRecord(String typeId, String custId) throws Exception;
}
