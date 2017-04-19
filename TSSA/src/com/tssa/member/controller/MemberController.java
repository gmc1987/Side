/**
 * 
 */
package com.tssa.member.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.businessUser.pojo.BusinessCustomer;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.cooperationBusiness.pojo.CooperationBusiness;
import com.tssa.cooperationBusiness.service.CooperationService;
import com.tssa.member.pojo.Member;
import com.tssa.member.service.MemberService;

/**
 * @author gmc
 * @see 会员管理
 */

@Controller
@RequestMapping("/member")
@SessionAttributes(value={"user","businessCustomer"}, types={Users.class, BusinessCustomer.class})
public class MemberController {
	
	private static final Logger LOG = LoggerFactory
			.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private CooperationService cooperationService;
	
	@InitBinder 
    protected  void initBinder(WebDataBinder binder) {  
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));  
    }
	
	/**
	 * 菜单地址
	 * @return
	 */
	@RequestMapping("/toList")
	public String toList(){
		return "/member/memberList";
	}
	
	/**
	 * 加载会员数据
	 * @param member
	 * @param request
	 * @param map
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public ModelMap list(Member member, HttpServletRequest request, ModelMap map){
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		
		String start = request.getParameter("start");
		String limit = request.getParameter("limit");
		int pageNumber = 1;
		int pageSize = 0;
		
		map.clear();
		try{
			if(businessCustomer != null){
				DetachedCriteriaTS<CooperationBusiness> criteria = new DetachedCriteriaTS<CooperationBusiness>(CooperationBusiness.class);
				criteria.add(Restrictions.eq("cooperCode", businessCustomer.getVendorCode()));
				CooperationBusiness cooperation = cooperationService.find(criteria);
				if(cooperation != null){
					member.setCooperation(cooperation);
				}
				
				/*分页查询参数*/
				if(start != null && !"".equals(start) && !"0".equals(start)){
					pageNumber = Integer.parseInt(start);
				}
				if(limit != null && !"".equals(limit)){
					pageSize = Integer.parseInt(limit);
				}else{
					pageSize = 10;
				}
				
				PageMode<Member> members = memberService.findMemberByParams(member, pageNumber, pageSize);
				
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[]{
						"productTypeId",
						"locationReference",
						"custAccount"
				});
				config.setJsonPropertyFilter(new PropertyFilter(){
		            @Override
		            public boolean apply(Object source, String name, Object value) {
		                return value == null;
		            }
		        });
				config.setIgnoreDefaultExcludes(false); 
				
				map.addAttribute("pageMode", JSONObject.fromObject(members, config));
				
			} else {
				
				/*分页查询参数*/
				if(start != null && !"".equals(start) && !"0".equals(start)){
					pageNumber = Integer.parseInt(start);
				}
				if(limit != null && !"".equals(limit)){
					pageSize = Integer.parseInt(limit);
				}else{
					pageSize = 10;
				}
				
				PageMode<Member> members = memberService.findMemberByParams(member, pageNumber, pageSize);
				
				JsonConfig config = new JsonConfig();
				config.setExcludes(new String[]{
						"productTypeId",
						"locationReference",
						"custAccount"
				});
				
				config.setJsonPropertyFilter(new PropertyFilter(){
		            @Override
		            public boolean apply(Object source, String name, Object value) {
		                return value == null;
		            }
		        });
				config.setIgnoreDefaultExcludes(false); 
				
				map.addAttribute("pageMode", JSONObject.fromObject(members, config));
			}
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
		}
		
		return map;
	}
	
	/**
	 * 新增会员
	 * @param member
	 * @param request
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Map<String, Object> memberAdd(Member member, HttpServletRequest request){
		
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			if(member != null){
				if(businessCustomer != null){
					CooperationBusiness cooperation = new CooperationBusiness();
					cooperation.setCooperCode(businessCustomer.getVendorCode());
					member.setCooperation(cooperation);
					boolean rs = memberService.memberAdd(member);
					if(rs){
						result.put("success", true);
						result.put("msg", "会员新增成功");
					} else {
						result.put("success", true);
						result.put("msg", "该手机号码未注册平台账号，无法注册为本店会员");
					}
				} else {
					result.put("success", false);
					result.put("msg", "您没有权限增加商户会员信息");
				}
				
			}
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", "会员新增失败");
		}
		
		return result;
	}
	
	/**
	 * 修改会员信息
	 * @param member
	 * @param request
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Map<String, Object> memberMod(Member member, HttpServletRequest request){
		
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			if(member != null){
				if(businessCustomer != null){
					
					boolean rs = memberService.memberMod(member);
					if(rs){
						result.put("success", true);
						result.put("msg", "会员修改成功");
					}
				} else {
					result.put("success", false);
					result.put("msg", "您没有权限修改商户会员信息");
				}
				
			}
		}catch(Exception e){
			LOG.error(e.getMessage(), e);
			result.put("success", false);
			result.put("msg", "会员修改失败");
		}
		
		return result;
	}
	
	/**
	 * 删除会员
	 * @param memberId
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> memberDel(String memberId, HttpServletRequest request){
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(memberId != null && !"".equals(memberId)){
			
			Member member = new Member();
			member.setMemberId(memberId);
			
			if(businessCustomer != null){
				
				boolean rs = memberService.memberDel(member);
				if(rs){
					result.put("success", true);
					result.put("msg", "会员删除成功");
				}
			} else {
				result.put("success", false);
				result.put("msg", "您没有权限删除商户会员信息");
			}
		}
		
		return result;
	}
	
}
