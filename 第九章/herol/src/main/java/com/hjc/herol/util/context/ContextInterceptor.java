package com.hjc.herol.util.context;
/*package com.mochasoft.minsheng.util.context;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mochasoft.salesmanager.util.Constants;
import com.mochasoft.salesmanager.weichat.model.UserAccessInfo;


public class ContextInterceptor implements HandlerInterceptor {

	*//**
	 * 构造函数.
	 *//*
	public ContextInterceptor() {
	}

	*//**
	 * 
	 * 初始上下文.
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 *//*
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		Context.setUser((UserAccessInfo)session.getAttribute(Constants.SESSION_USER));
		return true;
	}

	*//**
	 * 
	 * 清理上下文.
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 *//*
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		Context.removeUser();
	}

	*//**
	 * (non-Javadoc).
	 * 
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 *//*
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		
	}
	
}
*/