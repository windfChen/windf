package com.windf.plugins.web;

import java.util.HashMap;
import java.util.Map;

import com.windf.core.util.JSONUtil;
import com.windf.plugins.log.Logger;
import com.windf.plugins.log.LogFactory;

/**
 * 控制层的父类
 * @author 陈亚峰
 *
 */
public abstract class ParentControler {
	protected static final String PARAMETER_ERROR_PAGE = "/error/parameter";
	protected static Logger logger = null;
	
	
	
	public ParentControler () {
		// 日初始化日志
		if (logger == null) {
			logger = LogFactory.getLogger(this.getClass());
		}
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
}
