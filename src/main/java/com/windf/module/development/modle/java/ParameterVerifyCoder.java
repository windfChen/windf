package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.windf.core.util.CollectionUtil;
import com.windf.core.util.StringUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.pojo.Parameter;

public class ParameterVerifyCoder implements Codeable<Parameter> {
	
	private List<Parameter> parameters;
	private int tabCount;
	
	public ParameterVerifyCoder(List<Parameter> parameters, int tabCount) {
		this.parameters = parameters;
		this.tabCount = tabCount;
	}
	
	@Override
	public List<String> toCodes() {
		List<String> result = new ArrayList<String>();
		
		if (CollectionUtil.isNotEmpty(parameters)) {
			for (Parameter parameter : parameters) {
				String type = parameter.getType();
				String name = parameter.getName();
				boolean isString = "String".equals(type);
				String strName = name + (isString? "": "Str");
				
				if (isString) {
					result.add(tab() + "String " + name + " = this.getParameter(\"" + name + "\");");
				} else {
					result.add(tab() + type + " " + name + " = null;");
					result.add(tab() + "String " + strName + " = this.getParameter(\"" + name + "\");");
				}
			}
			this.newEmptyLine(result);
			
			result.add(tab() + "try {");
			tabCount ++;
			/*
			 * 验证是否为空 
			 */
			StringBuffer notEmptyVariableNames = new StringBuffer();
			for (int i = 0; i < parameters.size(); i++) {
				Parameter parameter = parameters.get(i);
				String name = parameter.getName();
				String type = parameter.getType();
				String strName = name + ("String".equals(type)? "": "Str");
				boolean notEmpty = parameter.isNotEmpty();
				
				if (notEmpty) {
					notEmptyVariableNames.append(strName);
					notEmptyVariableNames.append(", ");
				}
			}
			if (notEmptyVariableNames.length() > 0) {
				if (", ".equals(notEmptyVariableNames.substring(notEmptyVariableNames.length() - 2))) {
					notEmptyVariableNames.delete(notEmptyVariableNames.length() - 2, notEmptyVariableNames.length());
				}
				result.add(tab() + "if (ParameterUtil.hasEmpty(" + notEmptyVariableNames.toString() + ")) {");
				result.add(tab(1) + "throw new ParameterException();");
				result.add(tab() +  "}");
				this.newEmptyLine(result);
			}
			
			/*
			 * 验证正则表达式
			 */
			Map<String, String> allPatterns = Constant.getAllPattern();
			for (Parameter parameter : parameters) {
				String type = parameter.getType();
				String name = parameter.getName();
				String strName = name + ("String".equals(type)? "": "Str");
				
				Map<String, String> patterns = parameter.getPatterns();
				if (patterns != null) {
					Iterator<String> iterator = patterns.keySet().iterator();
					while (iterator.hasNext()) {
						String pattern = iterator.next();	// TODO 和正则的静态变量关联起来
						String note = patterns.get(pattern);
						
						String regularValueName = allPatterns.get(pattern);
						if (regularValueName != null) {
							result.add(tab() + "if (!RegularUtil.match(" + strName + ", RegularUtil." + regularValueName + ")) {");
							note = pattern;
						} else {
							result.add(tab() + "if (!RegularUtil.match(" + strName + ", \"" + pattern + "\")) {");
						}
						
						result.add(tab(1) + "throw new ParameterException(\"" + note + "\");");
						result.add(tab() + "}");
					}
				}
			}
			this.newEmptyLine(result);
			
			/*
			 * 参数转换
			 */
			for (Parameter parameter : parameters) {
				String type = parameter.getType();
				String name = parameter.getName();
				String strName = name + ("String".equals(type)? "": "Str");
				
				if ("Date".equals(type)) {
					result.add(tab() + name + " = DateUtil.parseDate(" + strName + ");");
				} else if ("Integer".equals(type)) {
					result.add(tab() + name + " = Integer.parseInt(" + strName + ");");
				} else if ("Double".equals(type)) {
					result.add(tab() + name + " = Double.parseDouble(" + strName + ");");
				} else if ("Long".equals(type)) {
					result.add(tab() + name + " = Long.parseLong(" + strName + ");");
				} else {
					
				}
			}
			
			tabCount --;
			result.add(tab() + "} catch(Exception e) {");
			result.add(tab() + "");
			result.add(tab() + "}");
		}
		
		return result;
	}
	
	private String tab() {
		return tab(0);
	}
	
	private String tab(int tempCount) {
		return CodeConst.getTabString(tabCount + tempCount);
	}
	
	private void newEmptyLine(List<String> lines) {
		if (lines.size() > 1) {
			if (!StringUtil.isEmpty(lines.get(lines.size() - 1))) {
				lines.add("");
			}
		}
		
	}

	@Override
	public Parameter toObject() {
		return null;
	}

	@Override
	public boolean verifyCodes() {
		return false;
	}

}
