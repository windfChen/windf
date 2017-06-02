package com.windf.plugins.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.windf.core.util.JSONUtil;
import com.windf.plugins.log.Logger;
import com.windf.plugins.log.LogFactory;

/**
 * 控制层的父类
 * @author 陈亚峰
 *
 */
public abstract class BaseControler {
	protected static final String PARAMETER_ERROR_PAGE = "/common/error/parameter";
	protected static final String SUCCESS_MESSAGE_PAGE = "/common/info/success";
	
	protected static Logger logger = null;
	
	protected HttpServletRequest request;
	
	public BaseControler () {
		// 日初始化日志
		if (logger == null) {
			logger = LogFactory.getLogger(this.getClass());
		}
		
		request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 返回项目的跟路径
	 * @return
	 */
	protected String getBasePath() {
		return request.getContextPath();
	}
	
	/**
	 * 获取参数
	 * @param name
	 * @return
	 */
	protected String getParameter(String name) {
		return request.getParameter(name);
	}
	
	/**
	 * 设置值
	 * @param name
	 * @param value
	 */
	protected void setValue(String name, Object value) {
		request.setAttribute(name, value);
	}
	
	/**
	 * 返回错误信息
	 * @return
	 */
	protected Map<String, Object> paramErrorMap() {
		return errorMessageMap("parameter error");
	}
	/**
	 * 返回错误信息
	 * @return
	 */
	protected String paramErrorJson() {
		return JSONUtil.toJSONStr(paramErrorMap());
	}
	/**
	 * 返回错误信息
	 * @param Message
	 * @return
	 */
	protected Map<String, Object> errorMessageMap(String message) {
		return returnMap(false, message);
	}
	
	/**
	 * 返回成功提示
	 * @return
	 */
	protected Map<String, Object> successMap() {
		return returnMap(true, "success");
	}
	
	/**
	 * 返回带数据的成功信息
	 * @param data
	 * @return
	 */
	protected Map<String, Object> successMap(Object data) {
		return returnMap(true, "success", data);
	}
	
	/**
	 * 返回map信息,没有数据
	 * @param success
	 * @param tip
	 * @return
	 */
	protected Map<String, Object> returnMap(boolean success, String tip) {
		return returnMap(success, tip, null);
	}
	
	/**
	 * 返回带数据的信息
	 * @param success
	 * @param tip
	 * @param data
	 * @return
	 */
	protected Map<String, Object> returnMap(boolean success, String tip, Object data) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (success) {
			map.put("success", "Y");
		} else {
			map.put("success", "N");
		}
		if (data != null) {
			map.put("data", data);
		}
		map.put("tip", tip);
		return map;
	}
	
	/**
	 * 返回正确信息
	 * @param message
	 * @param sureHref
	 * @param sureButtonWord
	 * @return
	 */
	protected String returnSuccessPage(String message, String sureHref, String sureButtonWord) {
		
		this.setValue("message", message);
		this.setValue("sureHref", sureHref);
		
		return SUCCESS_MESSAGE_PAGE;
	}
	
}
