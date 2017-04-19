/**
 * 
 */
package com.tssa.remote.member;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.common.mode.PageMode;
import com.tssa.customer.pojo.Customer;
import com.tssa.customer.service.CustomerService;
import com.tssa.member.pojo.Member;
import com.tssa.member.service.MemberService;
import com.tssa.remote.object.MemberVo;

/**
 * @author gmc
 * @see 用户会员卡信息
 */
@Controller
@RequestMapping("cust-member")
public class CustMemberCardController {

	private static final Logger logger = LoggerFactory
			.getLogger(CustMemberCardController.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CustomerService customerService;
	
	@RequestMapping("/findCustAllMemberCard")
	@ResponseBody
	public List<MemberVo> findCustAllMemberCard(String requestData){
		
		List<MemberVo> memberList = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		if(!StringUtils.isEmpty(requestData)){
			
			String custId = JSONObject.fromObject(requestData).getString("custId");
			int pageNumber = JSONObject.fromObject(requestData).getInt("pageNumber");
			int pageSize = JSONObject.fromObject(requestData).getInt("pageSize");
			
			if(custId != null && !"".equals(custId)){
				Member member = new Member();
				Customer customer = customerService.get(Customer.class, custId);
				if(customer != null){
					member.setCustomer(customer);
				}
				PageMode<Member> list = memberService.findMemberByParams(member, pageNumber, pageSize);
				
				if(list != null && list.getRecords() != null && list.getRecords().size() > 0){
					
					memberList = new ArrayList<MemberVo>();
					
					for(Member obj : list.getRecords()){
						
						MemberVo vo = new MemberVo();
						vo.setCardNo(obj.getCardNo());
						if(obj.getCooperation() != null){
							vo.setCooperation(obj.getCooperation().getCooperName());
							vo.setCooperationCode(obj.getCooperation().getCooperCode());
						}
						
						if(obj.getCustomer() != null){
							vo.setCustomerCode(obj.getCustomer().getUid());
							vo.setCustomerName(obj.getCustomer().getUsername());
						}
						
						vo.setDiscount(obj.getDiscount());
						vo.setMemberId(obj.getMemberId());
						vo.setMemberPhoneNo(obj.getMemberPhoneNo());
						vo.setSendDate(format.format(obj.getSendDate()));
						vo.setValidity(format.format(obj.getValidity()));
						vo.setReturnCode(MemberVo.RETURN_SUCCESS);
						vo.setReturnDesc(MemberVo.DATA_HAVE_FOUND_DESC);
						
						memberList.add(vo);
						
					}
				}
			}
			
			return memberList;
			
		} else {
			memberList = new ArrayList<MemberVo>();
			MemberVo vo = new MemberVo();
			vo.setReturnCode(MemberVo.RETURN_EXCEPTION);
			vo.setReturnDesc("查询参数错误");
			memberList.add(vo);
			return memberList;
		}
		
	}
}
