/**
 * 
 */
package com.tssa.bisServiceModel.catering.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tssa.bisServiceModel.catering.dao.CateringTableDao;
import com.tssa.bisServiceModel.catering.pojo.CateringTable;
import com.tssa.common.service.BaseBusinessService;
import com.tssa.common.util.QRCodeEncoderHandler;
import com.tssa.common.util.TSSAUtil;

/**
 * @author gmc870223
 * @see 餐桌管理服务类
 */
@Service
public class CateringTableService extends BaseBusinessService<CateringTable> {

	@Autowired
	private CateringTableDao iCateringTableDao;
	
	/**
	 * 餐桌批量新增
	 * @param start 起始编号
	 * @param limit 结束编号
	 * @param table 餐桌公共信息
	 * @throws Exception
	 */
	public void tabelBatchAdd(int start, int limit, CateringTable table) throws Exception {
		List<CateringTable> list = new ArrayList<CateringTable>();
		String qrCodePath = table.getQrCodeURL();
		if(start > 0 && limit > 0 && limit >= start) {
			for(int i = start; i <= limit; i++) {
				CateringTable newTable = new CateringTable();
				BeanUtils.copyProperties(table, newTable);
				String tableNo = table.getQz()+TSSAUtil.numberFormat(i);
				
				String imgName = tableNo+".png";
				StringBuffer contents = new StringBuffer();
				contents.append("cooperId=");
				contents.append(table.getCooperId());
				contents.append("&tableNo="+tableNo);
				File imgFile = new File(table.getQrCodeFileURL()+File.separator+imgName);
		        QRCodeEncoderHandler qrCode = new QRCodeEncoderHandler();
		        qrCode.encode(contents.toString(), 100, 100, "png" ,imgFile);
		        newTable.setQrCodeURL(qrCodePath + File.separator+imgName);
				newTable.setTableNo(tableNo);
				
				System.out.println(qrCodePath + File.separator+imgName);
				
				list.add(newTable);
			}
		} else {
			list.add(table);
		}
		iCateringTableDao.saveOrUpdateAll(list);
	}
	
}
