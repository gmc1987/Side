/**
 * 
 */
package com.tssa.remote.CustSuit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.suit.pojo.SuitType;
import com.tssa.suit.service.SuitTypeService;

/**
 * @author gmc
 * @see 用户投诉类型
 */

@Controller
@RequestMapping("/custSuitType")
public class CustSuitTypeRemoteController {

	@Autowired
	private SuitTypeService suitTypeService;
	
	@RequestMapping("/findAllSuitType")
	@ResponseBody
	public Map<String, Object> findAllCustSuitType(HttpServletRequest request){
		
		Map<String, Object> result = new HashMap<String, Object>();
		
		List<SuitType> list = suitTypeService.findAllSuiType();
		if(list != null && list.size() > 0){
			result.put("success", "0");
			result.put("data", list);
		} else {
			result.put("success", "1");
			result.put("data", list);
		}
		return result;
	}
}
