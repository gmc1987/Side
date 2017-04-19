package com.tssa.remote.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * <p> 
 * 类说明 远程访问控制器 基类
 * </p>
 * @author Lib
 * Date: 2013-7-1
 * @version 1.01
 *
 */
@Controller
public abstract class BaseController {

	protected final Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	/** 属性说明 获取 提交的 JSON 数据的参数名称 */
	protected static final String KEY_REQUEST_DATA = "requestData";
	
	/** 属性说明 一条JSON字符串,用于 只是简单的返回结果 */
	protected static final String JSON_STR_RETURN = "{returnCode:\"#code\",returnDesc:\"#desc\"}";
	

	/**
	 * 以 JSON 数据 反馈 请求 的 response 方法
	 * @param response
	 * @param jsonString
	 * @throws IOException
	 */
	protected void responseWriter(HttpServletResponse response,String jsonString) throws IOException {
		response.setContentType("text/xml;charset=utf-8"); 
		response.setCharacterEncoding("UTF-8"); 
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter(); 
		StringBuilder sb = new StringBuilder();
		sb.append(jsonString);
		out.print(sb.toString());
		out.flush();
		out.close();
	}
	
	/**
	 * 以 压缩格式的 byte 数据 反馈 请求 的 response 方法
	 * @param response
	 * @param jsonString
	 * @throws IOException
	 */
	protected void responseOutStream(HttpServletResponse response,String jsonString) throws IOException {
			//压缩处理
//			byte[] data = gzip(jsonString);//jsonString.getBytes();
		
			OutputStream out = response.getOutputStream();
			out.write(jsonString.getBytes());
			out.flush();
			out.close();
	}
	
	/**
	 * gzip 方法
	 * <p>方法说明: 压缩数据 </p>
	 * @param jsonString
	 * @return
	 */
	private byte[] gzip(String jsonString) {
		//建立字节数组输出流
		ByteArrayOutputStream o = new ByteArrayOutputStream();
		try {
			//建立gzip压缩输出流
			GZIPOutputStream gzout = new GZIPOutputStream(o);
			//建立对象序列化输出流
			ObjectOutputStream out = new ObjectOutputStream(gzout);
			//ObjectOutputStream out = new ObjectOutputStream(o);//非压缩
			System.out.println("jsonString is:" + jsonString);
			out.writeObject(jsonString);
			out.flush();
			out.close();
			gzout.close();
		} catch (IOException e) {
			logger.error("IOException:",e);
		}
		// 返回压缩字节流
		byte[] data = o.toByteArray();
		logger.debug("" + data.length);
		return data;
	}
}
