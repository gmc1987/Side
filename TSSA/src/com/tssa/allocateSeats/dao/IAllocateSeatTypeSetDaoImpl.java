/**
 * 
 */
package com.tssa.allocateSeats.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.SQLQuery;

import com.tssa.allocateSeats.pojo.AllocateSeatTypeSet;
import com.tssa.allocateSeats.vo.AllocateSeatNumberSetVO;
import com.tssa.common.dao.HibernateEntitryDaoImpl;
import com.tssa.common.util.DateWarpUtils;

/**
 * @author gmc
 *
 */
public class IAllocateSeatTypeSetDaoImpl extends HibernateEntitryDaoImpl
		implements IAllocateSeatTypeSetDao {

	@Override
	public List<AllocateSeatNumberSetVO> getAllocateSeatType(
			AllocateSeatTypeSet allocateSeatTypeSet) throws Exception {
		
		List<AllocateSeatNumberSetVO> result = new ArrayList<AllocateSeatNumberSetVO>();
		
		String businessCustomerCode = allocateSeatTypeSet.getBusinessCustomerCode();
		StringBuffer sql = new StringBuffer();
		sql.append("select t.aUuid, t.cooperName, t.num, t.allocateSeatType, t.typeName, t.custId, max(t.createDate), t.recodeStatus, t.remark, t.uuid FROM (");
		sql.append("SELECT a.uuid as aUuid, c.cooperName, b.allocateNo as num, b.allocateSeatType, a.typeName, b.custId, b.createDate, b.recodeStatus, a.remark, b.uuid ");
		sql.append("FROM business_allocateSeatType a ");
		sql.append("LEFT JOIN business_allocateseat_number_record b ON a.uuid = b.allocateSeatType ");
		sql.append("and b.createDate between '" + DateWarpUtils.format(new Date()) + " 00:00:00" + "' ").append("and '").append(DateWarpUtils.format(new Date()) + " 23:59:59' ");
		sql.append("left join BUSINESS_COOPERATIONBUSINESS c on a.businessCustomerCode=c.cooperCode ");
		sql.append("where a.businessCustomerCode=? ");
//		sql.append("and b.recodeStatus!='1' ");
		sql.append("GROUP BY a.uuid, c.cooperName, b.allocateSeatType, a.typeName, b.custId, b.recodeStatus, a.remark, b.uuid, b.createDate");
		sql.append(") t group by t.aUuid");
		
		SQLQuery query = getSession().createSQLQuery(sql.toString());
		query.setParameter(0, businessCustomerCode);

		for(Object objects : query.list()){
			Object[] object = (Object[])objects;
			AllocateSeatNumberSetVO vo = new AllocateSeatNumberSetVO();
			vo.setUuid(String.valueOf(object[0]));
			vo.setCooperName(String.valueOf(object[1]));
			vo.setNum(String.valueOf(object[2]));
			vo.setTypeId(String.valueOf(object[3]));
			vo.setTypeName(String.valueOf(object[4]));
			vo.setCustNo(String.valueOf(object[5]));
			vo.setCreateDate(String.valueOf(object[6]));
			vo.setStatus(String.valueOf(object[7]));
			vo.setRemark(String.valueOf(object[8]));
			vo.setNumUuid(String.valueOf(object[9]));
			result.add(vo);
		}
		
		return result;
	}
	
}
