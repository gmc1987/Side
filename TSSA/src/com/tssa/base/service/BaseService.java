/**
 * 
 */
package com.tssa.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.base.dao.BaseCodeDao;
import com.tssa.base.pojo.BaseCode;
import com.tssa.common.service.BaseBusinessService;

/**
 * @author gmc
 *
 */

@Service
public class BaseService extends BaseBusinessService<BaseCode> {

	@Autowired
	private BaseCodeDao baseCodeDao;
}
