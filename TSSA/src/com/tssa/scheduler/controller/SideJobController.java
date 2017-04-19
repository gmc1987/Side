/**
 * 
 */
package com.tssa.scheduler.controller;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.common.mode.PageMode;
import com.tssa.scheduler.pojo.SideJobDetail;
import com.tssa.scheduler.service.PlatformSchedulerService;
import com.tssa.scheduler.vo.SideJobDetailVO;

/**
 * @author gmc
 * @see side job控制
 */
@Controller
@RequestMapping("/job")
public class SideJobController {
	
	private static final Logger LOG = LoggerFactory.getLogger(SideJobController.class);
	
	@Autowired
	private PlatformSchedulerService platofrmSchedulerService;

	@RequestMapping("toList")
	public String toList(){
		return "/scheduler/schedulerManager";
	}
	
	@RequestMapping("list")
	@ResponseBody
	public ModelMap list(String start, String limit, ModelMap map){
		PageMode<SideJobDetailVO> list = null;
		try{
			int pageNumber = 0;
			int pageSize = 20;
			if (start != null){
				if("0".equals(start)){
					pageNumber = Integer.parseInt(start) + 1; 
				} else {
					pageNumber = Integer.parseInt(start);
				}
			}else {
				pageNumber = 1;
			}
			list = platofrmSchedulerService.findJobByPage(pageNumber, pageSize);
		} catch(Exception e){
			LOG.error("查询异常", e);
		}
		map.addAttribute("pageMode", JSONObject.fromObject(list));
		return map;
	}
	
	@RequestMapping("jobAdd")
	@ResponseBody
	public Map<String, Object> jobAdd(SideJobDetailVO sideJobDetail){
		
		Map<String, Object> result = new HashMap<String, Object>();
		Class<?> clazz = null;
		try{
			if(sideJobDetail != null){
				
				SideJobDetail jobDetail = new SideJobDetail();
				BeanUtils.copyProperties(sideJobDetail, jobDetail);
				
				jobDetail.setTriggerName(jobDetail.getTriggerName());
				jobDetail.setTriggerGroup(jobDetail.getTriggerGroup());
				
				clazz = Class.forName(sideJobDetail.getClassName());
				
				platofrmSchedulerService.addJob(jobDetail, clazz);
				
				result.put("success", true);
				result.put("msg", "作业新增成功");
			} else {
				result.put("success", false);
				result.put("msg", "参数有误");
			}
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", "执行异常");
			LOG.error("新增作业异常", e);
		}
		
		return result;
	}
	
	@RequestMapping("jobUpdate")
	@ResponseBody
	public Map<String, Object> jobModify(SideJobDetailVO sideJobDetail){
		
		Map<String, Object> result = new HashMap<String, Object>();
		Class<?> clazz = null;
		try{
			if(sideJobDetail != null){
				
				SideJobDetail jobDetail = new SideJobDetail();
				BeanUtils.copyProperties(sideJobDetail, jobDetail);
				
				platofrmSchedulerService.modifyJob(jobDetail, clazz);
				
				result.put("success", true);
				result.put("msg", "作业修改成功");
			} else {
				result.put("success", false);
				result.put("msg", "参数有误");
			}
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", "执行异常");
			LOG.error("修改作业异常", e);
		}
		
		return result;
	}
	
	@RequestMapping("jobDel")
	@ResponseBody
	public Map<String, Object> jobDel(String jobName, String jobGroup){
		
		Map<String, Object> result = new HashMap<String, Object>();
		try{
			if(!StringUtils.isEmpty(jobName) && !StringUtils.isEmpty(jobGroup)){
				
				SideJobDetail jobDetail = new SideJobDetail();
				jobDetail.setJobName(jobName);
				jobDetail.setJobGroup(jobGroup);
				
				platofrmSchedulerService.delJob(jobDetail);
				
				result.put("success", true);
				result.put("msg", "作业删除成功");
			} else {
				result.put("success", false);
				result.put("msg", "参数有误");
			}
		}catch(Exception e){
			result.put("success", false);
			result.put("msg", "执行异常");
			LOG.error("删除作业异常", e);
		}
		
		return result;
	}
}
