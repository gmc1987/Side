/**
 * 
 */
package com.tssa.remote.sysBulletin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.remote.common.BaseController;
import com.tssa.remote.object.CustBulletinVo;
import com.tssa.sysBulletin.pojo.SysBulletin;
import com.tssa.sysBulletin.service.SysBulletinService;

/**
 * @author gmc
 * @see 客户端系统公告接口
 *
 */
@Controller
@RequestMapping("/sysbulletinRemote")
public class CustBulletinRemoteController extends BaseController{

	@Autowired
	private SysBulletinService sysBulletinService;
	
	@RequestMapping("list")
	@ResponseBody
	public List<CustBulletinVo> sysBulletinList(){
		
		List<CustBulletinVo> list = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		
		DetachedCriteriaTS<SysBulletin> detachedCriteria = new DetachedCriteriaTS<SysBulletin>(SysBulletin.class);
		detachedCriteria.addOrder(Order.desc("publishDate"));
		
		List<SysBulletin> bulletinList = sysBulletinService.find(detachedCriteria, 5);
		
		if(bulletinList != null && bulletinList.size() > 0){
			list = new ArrayList<CustBulletinVo>();
			
			for(SysBulletin bulletin : bulletinList){
				
				CustBulletinVo vo = new CustBulletinVo();
				vo.setId(bulletin.getId());
				vo.setMainText(bulletin.getMainText());
				vo.setPublishDate(format.format(bulletin.getPublishDate()));
				vo.setPublisher(bulletin.getPublisher());
				vo.setTitle(bulletin.getTitle());
				vo.setReturnCode(CustBulletinVo.RETURN_SUCCESS);
				vo.setReturnDesc(CustBulletinVo.DATA_HAVE_FOUND_DESC);
				
				list.add(vo);
			}
		}
		
		return list;
	}
}
