package com.windf.core.util;

import java.lang.reflect.Array;
import java.util.Collection;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONUtil {
	/**
	 * 把对象转换为json字符串
	 * @param obj
	 * @return
	 */
	public static String toJSONStr(Object obj) {
		String result = null;
		if (obj == null) {
			
		} else if (obj instanceof Array || obj instanceof Collection) {
			result = JSONArray.toJSONString(obj);
		} else {
			result = JSONObject.toJSONString(obj);
		}
		return result;
	}
	
	/**
	 * 解析JSON字符串，变成对象
	 * @param <T>
	 * @param json
	 * @return
	 */
	public static <T> T pasrseJSONStr(String text, Class<T> clazz) {
		return JSONObject.parseObject(text, clazz);
	}
}
