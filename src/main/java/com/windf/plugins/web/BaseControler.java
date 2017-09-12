package com.windf.plugins.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import com.windf.core.frame.Moudle;
import com.windf.core.util.StringUtil;
import com.windf.core.util.reflect.ReflectUtil;
import com.windf.plugins.log.LogFactory;
import com.windf.plugins.log.Logger;
import com.windf.plugins.web.response.ResponseReturn;
import com.windf.plugins.web.response.ResponseReturnFactory;

/**
 * 控制层的父类
 * @author 陈亚峰
 *
 */
public abstract class BaseControler {
	
	protected static Logger logger = null;
	
	protected HttpServletRequest request;
	protected ResponseReturn responseReturn;
	
	public BaseControler () {
		// 日初始化日志
		if (logger == null) {
			logger = LogFactory.getLogger(this.getClass());
		}

		// 初始化模块
		Moudle.setCurrentMoudle(this);
		
		// 获取请求
		request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		
		// 设置返回
		responseReturn = ResponseReturnFactory.getResponseReturn(this.getRequestSuffix(), this);
	}
	
	/**
	 * 返回项目的根路径
	 * @return
	 */
	protected String getBasePath() {
		return request.getContextPath();
	}
	
	/**
	 * 获得请求路径
	 * @return
	 */
	public String getRequestPath() {
		return (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
	}
	
	/**
	 * 获得控制器的路径
	 * @return
	 */
	public String getControlerPath() {
		RequestMapping a = (RequestMapping) ReflectUtil.getAnnotation(this, RequestMapping.class);
		return a.value()[0];
	}
	
	public String getRequestSuffix() {
		String path = request.getRequestURI();
		String result = "";
		if (path.lastIndexOf(".") > 0) {
			result = path.substring(path.lastIndexOf(".") + 1, path.length());
		}
		return result;
	}
	
	/**
	 * 获取参数
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		return request.getParameter(name);
	}
	
	/**
	 * 获得map形式的parameter
	 * @param name
	 * @return
	 */
	public Map<String, Object> getMapParameter(String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String nameKey = name + ".";
		
		@SuppressWarnings("rawtypes")
		Enumeration  enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String parameterName = (String) enumeration.nextElement();
			if (parameterName.startsWith(nameKey)) {
				String key = parameterName.substring(nameKey.length());
				String value = this.getParameter(parameterName);
				if (StringUtil.isNotEmpty(value)) {
					result.put(key, value);
				}
			}
			
		}
		
		if (result.size() > 0) {
			return result;
		} else {
			return null;
		}
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
