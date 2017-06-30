package com.windf.module.development.modle;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

class Annotation {
	private static final String DEFAULT_KEY = "value";
	
	String name;
	Map<String, String> values;
	
	Annotation(String codes) {
		String[] ss = codes.trim().split("\\(|\\)");;
		name = ss[0].substring(1);
		
		if (ss.length > 1) {
			String[] valueStrs = ss[1].split(",");
			if (valueStrs.length == 1) {
				values = new HashMap<String, String>();
				values.put(DEFAULT_KEY, valueStrs[0]);
			} else if (valueStrs.length > 1) {
				values = new HashMap<String, String>();
				for (String valueStr : valueStrs) {
					String[] keyValue = valueStr.trim().split("=");
					String key = keyValue[0];
					String value = keyValue[1];
					values.put(key, value);
				}
			}
		}
		
	}
	
	Annotation(String name, String value) {
		this.name = name;
		values = new HashMap<String, String>();
		values.put(DEFAULT_KEY, value);
	}
	
	void addValue(String key, String value) {
		if (values != null) {
			values = new HashMap<String, String>();
		}
		values.put(key, value);
	}
	
	String write(int tabCount) {
		StringBuffer result = new StringBuffer();
		
		for (int i = 0; i < tabCount; i++) {
			result.append(CodeConst.TAB);
		}
		
		result.append("@");
		result.append(name);
		if (values != null) {
			result.append("(");
			
			if (values.size() == 1) {
				result.append(values.get(DEFAULT_KEY));
			} else {
				Iterator<String> keyIterator = values.keySet().iterator();
				int i = 0;
				while (keyIterator.hasNext()) {
					String key = (String) keyIterator.next();
					String value = values.get(key);
					
					if (i != 0) {
						result.append("," + CodeConst.WORD_SPLIT);
					}

					result.append(key + CodeConst.WORD_SPLIT +"=" + CodeConst.WORD_SPLIT + value);
					
					i++;
					
				}
			}
			
			result.append(")");
		}
		
		return result.toString();
	}
}
