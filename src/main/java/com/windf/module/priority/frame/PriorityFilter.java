package com.windf.module.priority.frame;

import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.windf.core.frame.Filter;

@Component
public class PriorityFilter implements Filter{

	
	@Override
	public List<String> getUrlPattern() {
		return null;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
//		try {
//			
//			if (true || request.getRequestURI().contains("/index") 
//					|| request.getRequestURI().contains("/login") 
//					|| request.getRequestURI().contains("/menu") 
//					|| request.getRequestURI().contains("/error") 
//					|| request.getRequestURI().contains("/logout") 
//					|| request.getRequestURI().contains("/resource")) {
//				return true;
//			}
//			
//			String uri = request.getRequestURI();
//			String contextPath = request.getContextPath();
//			if (uri.startsWith(contextPath)) {
//				uri = uri.substring(contextPath.length());
//			}
//			if (uri.lastIndexOf(".") > 0) {
//				uri = uri.substring(0, uri.lastIndexOf("."));
//			}
//			if (!PriorityContext.verify(uri)) {
//				response.sendRedirect(request.getContextPath() + "/login?code=2");
//				return false;
//			}
//			
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws Exception {
	}

	@Override
	public int getOrder() {
		return EARLY - 1;
	}

}
