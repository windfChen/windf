package com.windf.module.sso.frame;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.windf.module.sso.SsoUserSession;
import com.windf.plugins.web.filter.Filter;

@Component
public class LoginFilter implements Filter{

	
	@Override
	public List<String> getUrlPattern() {
		return null;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
		try {
			
			if (request.getRequestURI().contains("/login") || request.getRequestURI().contains("/resource")) {
				return true;
			}
			
			if (SsoUserSession.getCurrentUser() == null) {
				// TODO 通用controler跳转
				response.sendRedirect(request.getContextPath() + "/login");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Exception ex)
			throws Exception {
	}

}
