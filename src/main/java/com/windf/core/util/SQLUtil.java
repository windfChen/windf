package com.windf.core.util;

import java.util.ArrayList;
import java.util.List;

public class SQLUtil {

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
}
