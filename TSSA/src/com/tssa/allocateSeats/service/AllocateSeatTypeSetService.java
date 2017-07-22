/**
 * 
 */
package com.tssa.allocateSeats.service;

import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tssa.allocateSeats.dao.IAllocateSeatTypeSetDao;
import com.tssa.allocateSeats.pojo.AllocateSeatTypeSet;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.TssaBaseException;
import com.tssa.common.service.BaseBusinessService;

/**
 * @author gmc
 * @see 派位服务类
 */
@Service
public class AllocateSeatTypeSetService extends BaseBusinessService<AllocateSeatTypeSet> {

	@Autowired
	private IAllocateSeatTypeSetDao iAllocateSeatTypeSetDao;
	
	@Transactional
	public boolean allocateSeatTypeSetAdd(AllocateSeatTypeSet allocateSeatTypeSet) throws TssaBaseException{
		
		boolean flag = false;
		
		if(allocateSeatTypeSet == null){
			throw new TssaBaseException("参数异常!");
		}
		
		String allocateSeatTypeCode = allocateSeatTypeSet.getTypeCode();
		
		DetachedCriteriaTS<AllocateSeatTypeSet> criteria = new DetachedCriteriaTS<AllocateSeatTypeSet>(AllocateSeatTypeSet.class);
		criteria.add(Restrictions.eq("typeCode", allocateSeatTypeCode));
		criteria.add(Restrictions.eq("businessCustomerCode", allocateSeatTypeSet.getBusinessCustomerCode()));
		
		AllocateSeatTypeSet allocateSeatType = iAllocateSeatTypeSetDao.find(criteria);
		
		if(allocateSeatType != null){
			throw new TssaBaseException("该类型代码已存在");
		}
		
		try{
			iAllocateSeatTypeSetDao.save(allocateSeatTypeSet);
			flag = true;
		}catch(Exception e){
			throw new TssaBaseException("新增异常");
		}
		
		return flag;
	}
	
	@Transactional
	public boolean allocateSeatTypeSetUpdate(AllocateSeatTypeSet allocateSeatTypeSet) throws TssaBaseException{
		
		boolean flag = false;
		
		if(allocateSeatTypeSet == null){
			throw new TssaBaseException("参数异常!");
		}
		
		String uuid = allocateSeatTypeSet.getUuid();
		String allocateSeatTypeCode = allocateSeatTypeSet.getTypeCode();
		
		AllocateSeatTypeSet oldAllocateSeatTypeSet = iAllocateSeatTypeSetDao.get(AllocateSeatTypeSet.class, uuid);
		
		DetachedCriteriaTS<AllocateSeatTypeSet> criteria = new DetachedCriteriaTS<AllocateSeatTypeSet>(AllocateSeatTypeSet.class);
		criteria.add(Restrictions.eq("typeCode", allocateSeatTypeCode));
		criteria.add(Restrictions.eq("businessCustomerCode", allocateSeatTypeSet.getBusinessCustomerCode()));
		criteria.add(Restrictions.ne("uuid", uuid));
		
		AllocateSeatTypeSet allocateSeatType = iAllocateSeatTypeSetDao.find(criteria);
		
		if(allocateSeatType != null){
			throw new TssaBaseException("该类型代码已存在");
		}
		
		BeanUtils.copyProperties(allocateSeatTypeSet, oldAllocateSeatTypeSet, new String[]{"createDate", "creater"});
		
		try{
			iAllocateSeatTypeSetDao.saveOrUpdate(oldAllocateSeatTypeSet);
			flag = true;
		}catch(Exception e){
			throw new TssaBaseException("修改异常");
		}
		
		return flag;
	}
}
