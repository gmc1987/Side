/**
 * 
 */
package com.tssa.allocateSeats.dao;

import java.util.List;

import com.tssa.allocateSeats.pojo.AllocateSeatTypeSet;
import com.tssa.allocateSeats.vo.AllocateSeatNumberSetVO;
import com.tssa.common.dao.HibernateEntitryDao;

/**
 * @author gmc
 *
 */
public interface IAllocateSeatTypeSetDao extends HibernateEntitryDao {

	public List<AllocateSeatNumberSetVO> getAllocateSeatType(AllocateSeatTypeSet allocateSeatTypeSet) throws Exception;
	
}
