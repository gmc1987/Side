/**
 * 
 */
package com.tssa.remote.CustSuit;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tssa.remote.object.UserSuitVo;
import com.tssa.suit.service.CustSuitService;

/**
 * @author gmc
 *
 */
@Controller
@RequestMapping("/custSuitService")
public class CustSuitRemoteController {
	
	@Autowired
	private CustSuitService custSuitService;

	@RequestMapping("/createCustSuit")
	public Map<String, Object> createCustSuit(UserSuitVo userSuitVo){
		
		if(userSuitVo != null){
			
		}
		
		return null;
	}
}
