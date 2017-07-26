package com.windf.plugins.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.windf.plugins.log.LogFactory;
import com.windf.plugins.log.Logger;
import com.windf.plugins.web.util.JsonReturn;
import com.windf.plugins.web.util.PageReturn;

/**
 * 控制层的父类
 * @author 陈亚峰
 *
 */
public abstract class BaseControler {
	
	protected static Logger logger = null;
	
	protected HttpServletRequest request;
	protected JsonReturn jsonReturn;
	protected PageReturn pageReturn;
	
	public BaseControler () {
		// 日初始化日志
		if (logger == null) {
			logger = LogFactory.getLogger(this.getClass());
		}
		
		request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		request.setAttribute("modulePath", this.getBasePath() + this.getModulePath());
		
		pageReturn = new PageReturn(this);
		jsonReturn = new JsonReturn();
	}
	
	/**
	 * 返回项目的跟路径
	 * @return
	 */
	protected String getBasePath() {
		return request.getContextPath();
	}
	
	/**
	 * 返回项目的跟路径
	 * @return
	 */
	protected abstract String getModulePath();
	
	/**
	 * 获取参数
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		return request.getParameter(name);
	}
	
	/**
	 * 设置值
	 * @param name
	 * @param value
	 */
	public void setValue(String name, Object value) {
		request.setAttribute(name, value);
	}
	
}
