/**
 * 
 */
package com.tssa.member.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.service.CooperationService;
import com.tssa.customer.pojo.Customer;
import com.tssa.customer.service.CustomerService;
import com.tssa.member.dao.MemberDao;
import com.tssa.member.pojo.Member;


/**
 * @author gmc
 *
 */
@Service
public class MemberService extends BaseBusinessService<Member> {

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private CooperationService cooperationService;
	
	@Autowired
	private CustomerService customerService;
	
	/**
	 * 会员查询接口
	 * @param member
	 * @return
	 */
	public PageMode<Member> findMemberByParams(Member member, int pageNumber, int pageSize){
		
		PageMode<Member> list = null;
		DetachedCriteriaTS<Member> criteria = new DetachedCriteriaTS<>(Member.class);
		if(member.getMemberPhoneNo() != null && !"".equals(member.getMemberPhoneNo())){
			criteria.add(Restrictions.like("memberPhoneNo", member.getMemberPhoneNo(), MatchMode.ANYWHERE));
		}
		if(member.getCustomer() != null){
			criteria.add(Restrictions.eq("customer", member.getCustomer()));
		}
		if(member.getCooperation() != null){
			criteria.add(Restrictions.eq("cooperation", member.getCooperation()));
		}
		
		list = memberDao.findForPage(criteria, pageNumber, pageSize);
		
		return list;
	}
	
	/**
	 * 新增会员
	 * @param member
	 * @return
	 */
	public boolean memberAdd(Member member){
		boolean result = false;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		
		if(member != null){
			DetachedCriteriaTS<CooperationBusiness> cooperationCriteria = new DetachedCriteriaTS<CooperationBusiness>(CooperationBusiness.class);
			DetachedCriteriaTS<Customer> customerCriteria = new DetachedCriteriaTS<Customer>(Customer.class);
			
			cooperationCriteria.add(Restrictions.eq("cooperCode", member.getCooperation().getCooperCode()));
			customerCriteria.add(Restrictions.eq("phone", member.getMemberPhoneNo()));
			
			CooperationBusiness cooperation = cooperationService.find(cooperationCriteria);
			Customer customer = customerService.find(customerCriteria);
			
			if(customer == null){
				//会员尚未注册平台账户，系统自动注册
			}
			
			if(cooperation != null && customer != null){
				String memberCardNo = format.format(new Date()) + cooperation.getCooperCode() + customer.getUid();
				member.setCardNo(memberCardNo);
				member.setCustomer(customer);
				member.setCooperation(cooperation);
				member.setSendDate(new Date());
				
				memberDao.save(member);
				result = true;
			} else {
				result = false;
			}
			
		}
		
		return result;
	}
	
	public boolean memberMod(Member member){
		boolean result = false;
		
		if(member != null){
			
			Member oldMember = memberDao.get(Member.class, member.getMemberId());
			
			if(oldMember != null){
				oldMember.setMemberPhoneNo(member.getMemberPhoneNo());
				oldMember.setValidity(member.getValidity());
				oldMember.setDiscount(member.getDiscount());
			}
			memberDao.saveOrUpdate(oldMember);
			result = true;
		}
		
		return result;
	}
	
	public boolean memberDel(Member member){
		boolean result = false;
		
		if(member != null){
			Member oldMember = memberDao.get(Member.class, member.getMemberId());
			memberDao.delete(oldMember);
			result = true;
		}
		return result;
	}
}
