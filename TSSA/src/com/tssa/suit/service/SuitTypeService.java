/**
 * 
 */
package com.tssa.suit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.suit.dao.SuitTypeDao;
import com.tssa.suit.pojo.SuitType;

/**
 * @author gmc
 *
 */
@Service
public class SuitTypeService extends BaseBusinessService<SuitType> {

	@Autowired
	private SuitTypeDao suitTypeDao;
	
	public List<SuitType> findAllSuiType(){
		DetachedCriteriaTS<SuitType> criteria = new DetachedCriteriaTS<>(SuitType.class);
		return suitTypeDao.find(criteria, 0);
	}
	
	public void saveSuitType(SuitType type){
		
	}
}
