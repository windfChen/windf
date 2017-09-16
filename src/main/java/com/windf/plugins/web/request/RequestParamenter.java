package com.windf.plugins.web.request;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.windf.core.util.StringUtil;
import com.windf.core.util.reflect.BeanUtil;

public class RequestParamenter {
	
	protected HttpServletRequest request;
	
	public RequestParamenter(HttpServletRequest request) {
		this.request = request;
	}

	/**
	 * 获取参数
	 * @param name
	 * @return
	 */
	public String getString(String name) {
		return request.getParameter(name);
	}
	
	/**
	 * 获得map形式的parameter
	 * @param name
	 * @return
	 */
	public Map<String, Object> getMap(String name) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		String nameKey = name + ".";
		
		@SuppressWarnings("rawtypes")
		Enumeration  enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String parameterName = (String) enumeration.nextElement();
			if (parameterName.startsWith(nameKey)) {
				String key = parameterName.substring(nameKey.length());
				String value = this.getString(parameterName);
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
	 * 获取对象
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getObject(Class<T> clazz) {
		T result = (T) BeanUtil.getObjectByMap(clazz, request.getParameterMap());
		return result;
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
