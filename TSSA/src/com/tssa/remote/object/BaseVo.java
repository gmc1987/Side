/**
 * 
 */
package com.tssa.remote.object;

/**
 * @see <p>返回手机端基类实体 </p>
 * @author gmc
 * Date: 2014-1-3
 * @version 1.00
 */
public class BaseVo {

	/***
	 * returnCode -1  异常
	 */
	public static final Integer RETURN_EXCEPTION = -1;
	
	/***
	 * returnCode对应中文描述
	 */
	public static final String RETURN_EXCEPTION_DESC = "返回异常，请稍后再试";
	
	/***
	 * 成功
	 */
	public static final Integer RETURN_SUCCESS = 0;
	/***
	 * 登录名不正确
	 */
	public static final Integer LOGINNAME_ERROR = 1;
	/***
	 * 密码不正确
	 */
	public static final Integer PASSWORD_ERROR = 2;
	
	
	public static final Integer REGISTER_SUCCESS = 1;
	public static final Integer REGISTER_FAILURE = 0;
	
	public static final String REGIEST_SUCCESS_DESC = "注册成功!";
	
	public static final String REGIEST_FAILURE_DESC = "注册失败!";
	
	/***
	 * 找不到相应数据
	 */
	public static final Integer DATA_NOT_FOUND = 3;
	
	/**
	 * DATA_NOT_FOUND对应中文描述
	 */
	public static final String DATA_NOT_FOUND_DESC = "找不到相关数据";
	
	/**
	 * 找到相应数据
	 */
	public static final Integer DATA_HAVE_FOUND = 4;
	
	/**
	 * DATA_HAVE_FOUND对应中文描述
	 */
	public static final String DATA_HAVE_FOUND_DESC = "数据已找到";
	

	/** 返回码 */
	private Integer returnCode;

	/** 描述 */
	private String returnDesc;
	

	public Integer getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(Integer returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnDesc() {
		return returnDesc;
	}

	public void setReturnDesc(String returnDesc) {
		this.returnDesc = returnDesc;
	}
	
}
