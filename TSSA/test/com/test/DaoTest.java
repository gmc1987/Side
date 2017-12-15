package com.test;

import static org.junit.Assert.fail;

import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.tssa.allocateSeats.pojo.AllocateSeatTypeSet;
import com.tssa.allocateSeats.service.AllocateSeatNumberRecordService;
import com.tssa.allocateSeats.service.AllocateSeatTypeSetService;
import com.tssa.allocateSeats.vo.AllocateSeatNumberSetVO;
import com.tssa.common.mode.DetachedCriteriaTS;
import com.tssa.common.util.SpringUtil;


public class DaoTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testCase1(){
		AllocateSeatNumberRecordService numberService = (AllocateSeatNumberRecordService) SpringUtil.getApplicationContext().getBean("allocateSeatNumberRecordService");
		AllocateSeatTypeSetService typeService = (AllocateSeatTypeSetService)SpringUtil.getApplicationContext().getBean("allocateSeatTypeSetService");
		try {
			DetachedCriteriaTS<AllocateSeatTypeSet> criteria = new DetachedCriteriaTS<AllocateSeatTypeSet>(AllocateSeatTypeSet.class);
			criteria.add(Restrictions.eq("businessCustomerCode", "100125"));
			AllocateSeatTypeSet allocateSeatTypeSet = typeService.find(criteria);
			List<AllocateSeatNumberSetVO> list = numberService.getAllocateSeatNumberRecord(allocateSeatTypeSet, null);
			if(list != null && list.size() > 0){
				for(AllocateSeatNumberSetVO vo : list){
					System.out.println("vo" + vo.getCooperName() + "\n");
					System.out.println("vo" + vo.getCustNo() + "\n");
					System.out.println("vo" + vo.getStatus() + "\n");
					System.out.println("vo" + vo.getNum() + "\n");
					System.out.println("vo" + vo.getTypeName() + "\n");
					System.out.println("vo" + vo.getTypeId() + "\n");
					System.out.println("vo" + vo.getCreateDate() + "\n");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCase2() {
		AllocateSeatNumberRecordService numberService = (AllocateSeatNumberRecordService) SpringUtil.getApplicationContext().getBean("allocateSeatNumberRecordService");
		AllocateSeatNumberSetVO vo;
		try {
			vo = numberService.extraNum("ff8080815ef7af1e015ef7b4420d0001", null);
			if(vo != null) {
				System.out.println("vo" + vo.getCooperName() + "\n");
				System.out.println("vo" + vo.getCustNo() + "\n");
				System.out.println("vo" + vo.getStatus() + "\n");
				System.out.println("vo" + vo.getNum() + "\n");
				System.out.println("vo" + vo.getTypeName() + "\n");
				System.out.println("vo" + vo.getTypeId() + "\n");
				System.out.println("vo" + vo.getCreateDate() + "\n");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
