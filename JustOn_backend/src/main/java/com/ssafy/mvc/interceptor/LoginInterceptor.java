package com.ssafy.mvc.interceptor;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class LoginInterceptor {
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
//			throws Exception {
//		HttpSession session = request.getSession();
//		
//		request.getMethod(); 
//		
//		"GET".equals(request.getMethod());
//		"GET".equalsIgnoreCase(request.getMethod());
//		
//		
//		if (!"admin".equals(session.getAttribute("loginUser"))) {
//			response.sendRedirect("list");
//			return false;
//		}
//		return true;
//
//	}
}
