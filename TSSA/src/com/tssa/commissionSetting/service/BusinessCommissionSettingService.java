/**
 * 
 */
package com.tssa.commissionSetting.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.commissionSetting.dao.BusinessCommissionSettingDao;
import com.tssa.commissionSetting.pojo.BusinessCommissionSetting;
import com.tssa.common.service.BaseBusinessService;

/**
 * @author gmc
 * @see 佣金设置服务类,针对每个商户设置其协议的佣金提成
 */
@Service
public class BusinessCommissionSettingService extends
		BaseBusinessService<BusinessCommissionSetting> {

	@Autowired
	private BusinessCommissionSettingDao businessCommissionSettingDao;
	
	
}
