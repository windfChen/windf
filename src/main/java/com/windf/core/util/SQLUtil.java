package com.windf.core.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SQLUtil {
	
	private static Map<String, String> dataTypeMap = new HashMap<String, String>();
	static {
		dataTypeMap.put("varchar", "String");
		dataTypeMap.put("text", "String");
		dataTypeMap.put("longtext", "String");
		dataTypeMap.put("char", "String");
		dataTypeMap.put("int", "Integer");
		dataTypeMap.put("tinyint", "Integer");
		dataTypeMap.put("bigint", "Integer");
		dataTypeMap.put("double", "Double");
		dataTypeMap.put("decimal", "Double");
		dataTypeMap.put("float", "Float");
		dataTypeMap.put("date", "Date");
		dataTypeMap.put("datetime", "Date");
		dataTypeMap.put("time", "Date");
	}

	/**
	 * 查询sql中select和from中的字段
	 * @param querySql
	 * @return
	 */
	public static List<String> getSqlSelectNames(String querySql) {
		querySql = querySql.toLowerCase();
		String selectStr = querySql.substring(querySql.indexOf("select ") + "select ".length(), querySql.indexOf("from "));
		
		// 删除select中的子查询
		while (selectStr.contains("(")) {
			selectStr = selectStr.replaceAll("\\([^\\)\\(]*\\)", "");
		}
		String[] selects = selectStr.split(",");
		
		List<String> selectNames = new ArrayList<String>();
		for (int i = 0; i < selects.length; i++) {
			String key = null;

			String select = selects[i].trim();
			String[] ss = select.split("\\s");
			if (ss.length == 1) {
				ss = ss[0].split(".");
				if (ss.length == 1) {
					key = ss[0];
				} else {
					key = ss[ss.length - 1];
				}
			} else {
				key = ss[ss.length - 1];
			}
			
			if (key != null) {
				selectNames.add(key.trim());
			}
			
		}
		
		return selectNames;
	}
	
	public static String dbName2JavaName(String dbName) {
		return StringUtil.toCamelCase(dbName, "_");
	}
	
	/**
	 * 表名称转换为实体名称
	 * @param tableName
	 * @return
	 */
	public static String tableName2EntityName(String tableName) {
		if (tableName.contains("_r_")) {
			tableName = tableName.replace("_r_", "_");
		}

		String result = StringUtil.toCamelCase(tableName, "_");
		result = StringUtil.firstLetterUppercase(result);
		
		return result;
	}
	
	/**
	 * 数据库类型转换为java类型
	 * TODO 应该从配置文件中读取
	 * @return
	 */
	public static String dbType2JavaType(String dbType) {
		return dataTypeMap.get(dbType);
	}
}
