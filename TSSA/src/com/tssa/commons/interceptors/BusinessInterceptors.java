/**
 * 
 */
package com.tssa.commons.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.tssa.adminUsers.pojo.Users;
import com.tssa.businessUser.pojo.BusinessCustomer;

/**
 * @author gmc
 * @see 拦截器主要用于防止未经登陆用户直接输入平台请求路径进入后台界面，
 * 		根据用户session对象判断是否已登录平台
 */
public class BusinessInterceptors implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		// TODO Auto-generated method stub
		
		Users user = (Users)request.getSession().getAttribute("user");
		if(user != null){
			return true;
		}
		
		//判断是否登陆请求
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		if(businessCustomer != null){
			return true;
		} else {
			request.getRequestDispatcher("/businessCustomerLogin.jsp").forward(request, response);
			return false;
		}
	}

}
