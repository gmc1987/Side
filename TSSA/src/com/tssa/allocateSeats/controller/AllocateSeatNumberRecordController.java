/**
 * 
 */
package com.tssa.allocateSeats.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.businessUser.pojo.BusinessCustomer;

/**
 * @author gmc
 * @see 商户派位模块
 */
@Controller
@RequestMapping("/allocateSeatNumberRecord")
@SessionAttributes(value={"businessCustomer","user"}, types={BusinessCustomer.class,Users.class})
public class AllocateSeatNumberRecordController {

	private static final Logger LOG = LoggerFactory.getLogger(AllocateSeatNumberRecordController.class);
	
	@RequestMapping("/toList")
	public String toList(){
		return "/AllocateSeatsManage/AllocateSeatsNumberRecordManage";
	}
	
	
}
