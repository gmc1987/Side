package com.tssa.common.util;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
/**
 * 二维码生成器
 * @blog http://sjsky.iteye.com
 * @author Michael
 */
public class QRCodeEncoderHandler {
	/**
	 * encode 方法
	 * <p>生成二维码</p>
	 * @param contents 内容
	 * @param width 宽度
	 * @param height 高度
	 * @param format 图片格式
	 * @param imgPath 图片存放路径
	 */
	public void encode(String contents, int width, int height, String format ,File imgPath) {
		Map<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		// 指定纠错等级
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
		// 指定图片的边框大小，如0,1,2,3,4
		hints.put(EncodeHintType.MARGIN, 0);
		// 指定编码格式
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(contents,
					BarcodeFormat.QR_CODE, width, height, hints);
			MatrixToImageWriter
					.writeToFile(bitMatrix, format, imgPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	 public static void main(String[] args) throws Exception {  
	        String contents = "http://www.baidu.com123456";   
	        String imgPath = "f:/ss";
	        String imgName = "new.png";
	        File imgDirectory = new File(imgPath);
	        if (!imgDirectory.isDirectory()) {
	        	imgDirectory.mkdirs();
			}
	        File imgFile = new File(imgDirectory+File.separator+imgName);
	        QRCodeEncoderHandler qrCode = new QRCodeEncoderHandler();
	        qrCode.encode(contents, 150, 150, "png" ,imgFile);
	    }  
}
