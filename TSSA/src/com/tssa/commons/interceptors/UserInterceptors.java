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
 *
 */

public class UserInterceptors implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
	}

	/**
	 * 生成视图之前执行
	 */
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2, ModelAndView modelAndView) throws Exception {
		//判断是否登陆请求
		Users user = (Users)request.getSession().getAttribute("user");
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		if(user == null && businessCustomer == null){
			modelAndView.setViewName("/login.jsp");
		}
	}

	/**
	 * 该方法将在请求处理之前进行调用
	 * 执行action前执行
	 */
	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		BusinessCustomer businessCustomer = (BusinessCustomer)request.getSession().getAttribute("businessCustomer");
		Users user = (Users)request.getSession().getAttribute("user");
		if(businessCustomer != null || user != null) {
			return true;
		} else if(businessCustomer == null && user != null) {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return false;
		} else if(businessCustomer != null && user == null){
			request.getRequestDispatcher("/businessCustomerLogin.jsp").forward(request, response);
			return false;
		} else {
			request.getRequestDispatcher("/login.jsp").forward(request, response);
			return false;
		}
	}

}
