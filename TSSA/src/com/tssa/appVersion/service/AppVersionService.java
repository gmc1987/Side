/**
 * 
 */
package com.tssa.appVersion.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.appVersion.dao.AppVersionDao;
import com.tssa.appVersion.pojo.AppVersion;
import com.tssa.common.service.BaseBusinessService;

/**
 * @author gmc
 * @see 应用版本业务逻辑类
 */
@Service
public class AppVersionService extends BaseBusinessService<AppVersion> {

	private static final Logger LOG = LoggerFactory.getLogger(AppVersionService.class);
	
	@Autowired
	private AppVersionDao appVersionDao;
	
}
