/**
 * 
 */
package com.tssa.sysBulletin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.MatchMode;
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

import com.mysql.jdbc.StringUtils;
import com.tssa.adminUsers.pojo.Users;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.mode.PageMode;
import com.tssa.common.util.DateWarpUtils;
import com.tssa.sysBulletin.pojo.SysBulletin;
import com.tssa.sysBulletin.service.SysBulletinService;
import com.tssa.sysBulletin.vo.SysBulletinVO;

/**
 * @author gmc
 * @see 系统公告
 */
@Controller
@RequestMapping("/sys-Bulletin")
@SessionAttributes(value = { "user" }, types = { Users.class })
public class SysBulletinController {

	private static final Logger LOG = LoggerFactory
			.getLogger(SysBulletinController.class);

	@Autowired
	private SysBulletinService sysBulletinService;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, true));
	}

	@RequestMapping("toList")
	public String toList(ModelMap map) {
		return "/system/SysBulletin";
	}

	@RequestMapping("/list")
	@ResponseBody
	public PageMode<SysBulletin> list(SysBulletinVO sysBulletinVo, ModelMap map) {

		DetachedCriteriaTS<SysBulletin> criteria = new DetachedCriteriaTS<SysBulletin>(
				SysBulletin.class);

		if (sysBulletinVo.getStartDate() != null
				&& sysBulletinVo.getEndDate() != null) {
			criteria.add(Restrictions.between(
					"publishDate",
					DateWarpUtils.parseDate(sysBulletinVo.getStartDate()
							+ " 00:00:00"),
					DateWarpUtils.parseDate(sysBulletinVo.getEndDate()
							+ " 23:59:59")));
		}

		if (!StringUtils.isEmptyOrWhitespaceOnly(sysBulletinVo.getTitle())) {
			criteria.add(Restrictions.like("title", sysBulletinVo.getTitle(),
					MatchMode.ANYWHERE));
		}

		return sysBulletinService.findForList(criteria,
				sysBulletinVo.getPageNumber(), sysBulletinVo.getPageSize());
	}

	@RequestMapping("/bulletin-add")
	@ResponseBody
	public Map<String, Object> add(HttpServletRequest request,
			SysBulletin sysBulletin) {

		Map<String, Object> result = new HashMap<String, Object>();
		Users user = (Users) request.getSession().getAttribute("user");
		try {

			sysBulletin.setPublisher(user.getUserCode());

			sysBulletinService.save(sysBulletin);

			result.put("success", true);
			result.put("msg", "新增公告成功");

		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "新增公告失败");
			LOG.error("公告新增异常", e);
		}

		return result;
	}

	@RequestMapping("/bulletin-modify")
	@ResponseBody
	public Map<String, Object> modify(SysBulletin sysBulletin) {

		Map<String, Object> result = new HashMap<String, Object>();
		SysBulletin sysBulletinOld = null;
		if (sysBulletin.getId() != null && !"".equals(sysBulletin.getId())) {
			sysBulletinOld = sysBulletinService.get(SysBulletin.class,
					sysBulletin.getId());
		}
		try {
			if (sysBulletinOld != null) {

				sysBulletinOld.setTitle(sysBulletin.getTitle());
				sysBulletinOld.setMainText(sysBulletin.getMainText());
				sysBulletinOld.setPublishDate(sysBulletin.getPublishDate());

				sysBulletinService.update(sysBulletinOld);
			}
			result.put("success", true);
			result.put("msg", "公告修改成功");
		} catch (Exception e) {
			result.put("success", false);
			result.put("msg", "公告修改失败");
			LOG.error("修改公告时发生异常", e);
		}

		return result;
	}

	/**
	 * 公告删除
	 * 
	 * @param sysBulletin
	 * @return
	 */
	@RequestMapping("/bulletin-delete")
	@ResponseBody
	public Map<String, Object> delete(SysBulletin sysBulletin) {

		Map<String, Object> result = new HashMap<String, Object>();

		SysBulletin sysBulletinOld = sysBulletinService.get(SysBulletin.class,
				sysBulletin.getId());

		if (sysBulletinOld != null) {
			sysBulletinService.delete(sysBulletinOld);
			result.put("success", true);
			result.put("msg", "删除成功");
		} else {
			result.put("success", false);
			result.put("msg", "没有找到相应纪录");
		}

		return result;
	}
	
	@RequestMapping("/business-Buttetin")
	public String businessButtetin(Integer pageNumber, Integer pageSize, ModelMap map){
		
		if(pageNumber == null || pageSize == null){
			pageNumber = 1;
			pageSize = 10;
		}
		
		DetachedCriteriaTS<SysBulletin> criteria = new DetachedCriteriaTS<SysBulletin>(
				SysBulletin.class);
		PageMode<SysBulletin> list = sysBulletinService.findForList(criteria, pageNumber, pageSize);
		
		map.addAttribute("pageMode", list);
		
		return "sysBulletin/bulletinList";
	}
	
	@RequestMapping("/bulletinInformation")
	public String bulletinInformation(String title, String mainText, String publishDate, String publisher, ModelMap map){
		map.put("title", title);
		map.put("mainText", mainText);
		map.put("publishDate", publishDate);
		map.put("publisher", publisher);
		return "sysBulletin/Information";
	}

}
