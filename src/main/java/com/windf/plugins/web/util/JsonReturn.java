package com.windf.plugins.web.util;

import java.util.HashMap;
import java.util.Map;

public class JsonReturn {
	
	public JsonReturn() {
		
	}
	
	/**
	 * 返回错误信息
	 * @return
	 */
	public Map<String, Object> paramErrorMap() {
		return errorMap("parameter error");
	}

	/**
	 * 返回成功提示
	 * @return
	 */
	public Map<String, Object> successMap() {
		return returnMap(true, "success");
	}
	
	/**
	 * 返回错误信息
	 * @param Message
	 * @return
	 */
	public Map<String, Object> errorMap(String tip) {
		return returnMap(false, tip);
	}
	
	/**
	 * 返回成功提示
	 * @return
	 */
	public Map<String, Object> successMap(String tip) {
		return returnMap(true, tip);
	}
	
	/**
	 * 返回带数据的成功信息
	 * @param data
	 * @return
	 */
	public Map<String, Object> successMap(Object data) {
		return returnMap(true, "success", data);
	}

	/**
	 * 返回带数据的错误信息
	 * @param data
	 * @return
	 */
	public Map<String, Object> errorMap(Object data) {
		return returnMap(true, "error", data);
	}
	
	/**
	 * 返回map信息,没有数据
	 * @param success
	 * @param tip
	 * @return
	 */
	public Map<String, Object> returnMap(boolean success, String tip) {
		return returnMap(success, tip, null);
	}
	
	/**
	 * 返回带数据的信息
	 * @param success
	 * @param tip
	 * @param data
	 * @return
	 */
	public Map<String, Object> returnMap(boolean success, String tip, Object data) {
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
