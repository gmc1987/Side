/**
 * 
 */
package com.tssa.options.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.options.dao.OptionDao;
import com.tssa.options.pojo.Option;

/**
 * @author gmc
 *
 */
@Service
public class OptionService extends BaseBusinessService<Option> {

	@Autowired
	private OptionDao optionDao;
	
	public List<Option> getAllOption(DetachedCriteriaTS<Option> optionCriteriaTS){
		return optionDao.getAllOption(optionCriteriaTS);
	}
}
