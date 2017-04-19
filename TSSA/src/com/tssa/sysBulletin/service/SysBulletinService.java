/**
 * 
 */
package com.tssa.sysBulletin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.common.service.BaseBusinessService;
import com.tssa.sysBulletin.dao.SysBulletinDao;
import com.tssa.sysBulletin.pojo.SysBulletin;

/**
 * @author gmc
 *
 */
@Service
public class SysBulletinService extends BaseBusinessService<SysBulletin> {

	@Autowired
	private SysBulletinDao sysBulletinDao;
}
