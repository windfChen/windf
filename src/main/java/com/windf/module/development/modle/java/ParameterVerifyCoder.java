package com.windf.module.development.modle.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.windf.core.util.CollectionUtil;
import com.windf.core.util.StringUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.pojo.Parameter;

public class ParameterVerifyCoder implements Codeable<List<Parameter>> {
	
	private ThreadLocal<Integer> tabCount = new ThreadLocal<Integer>();
	
	@Override
	public List<String> toCodes(List<Parameter> parameters, int tabCount) {
		List<String> result = new ArrayList<String>();
		this.tabCount.set(tabCount);
		
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
						String pattern = iterator.next();
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
		return CodeConst.getTabString(tabCount.get() + tempCount);
	}
	
	private void newEmptyLine(List<String> lines) {
		if (lines.size() > 1) {
			if (!StringUtil.isEmpty(lines.get(lines.size() - 1))) {
				lines.add("");
			}
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Parameter> toObject(List<String> codes) {
		Map<String, String> allPatterns = Constant.getAllPattern();
		allPatterns = CollectionUtil.reversalMap(allPatterns);
		
		Map<String, Parameter> strNameParameterMap = new HashMap<String, Parameter>();
		Parameter frontParameter = null;
		String frontPattern = null;
		for (String line : codes) {
			line = line.trim();
			
			// 变量声明
			String[] parameterStatement = CodeConst.getInnerString(line, "(.*) (.*) = null;");
			if (parameterStatement.length == 2) {
				String type = parameterStatement[0];
				String name = parameterStatement[1];
				
				Parameter parameter = new Parameter();
				parameter.setName(name);
				parameter.setType(type);
				
				strNameParameterMap.put(name, parameter);
				continue;
			}
			
			// 变量获取
			String[] parameterGet = CodeConst.getInnerString(line, "String (.*) = this\\.getParameter\\(\"(.*)\"\\);");
			if (parameterGet.length == 2) {
				String nameStr = parameterGet[0];
				String name = parameterGet[1];
				
				Parameter parameter = null;
				if (name.equals(nameStr)) {
					parameter = new Parameter();
					parameter.setName(name);
					parameter.setType("String");
				} else {
					parameter = strNameParameterMap.get(name);
				}
				
				strNameParameterMap.put(nameStr, parameter);
				continue;
			}
			
			// 非空验证
			String[] emptyVariableStrs = CodeConst.getInnerString(line, " if \\(ParameterUtil\\.hasEmpty\\((.*)\\)\\) \\{");
			if (emptyVariableStrs.length == 1) {
				String variableStrs = emptyVariableStrs[0];
				String[] variables = variableStrs.split(",");
				if (variables.length > 0) {
					for (int i = 0; i < variables.length; i++) {
						String nameStr = variables[i].trim();
						Parameter parameter = strNameParameterMap.get(nameStr);
						parameter.setNotEmpty(true);
					}
				}
				continue;
			}
			
			// 正则验证
			String[] regularVariables = CodeConst.getInnerString(line, "if \\(\\!RegularUtil.match\\((.*), (.*)\\)\\) \\{");
			if (regularVariables.length == 2) {
				String nameStr = regularVariables[0];
				String regularStr = regularVariables[1];
				Parameter parameter = strNameParameterMap.get(nameStr);
				
				if (regularStr.startsWith("RegularUtil.")) {
					String regularVariable = regularStr.substring("RegularUtil.".length());
					String regularNote = allPatterns.get(regularVariable);
					parameter.addPattern(regularNote, regularNote);
				} else {
					frontParameter = parameter;
					frontPattern = regularStr;
				}
				continue;
			}
			
			// 正则验证错误提示
			String[] regularNotes = CodeConst.getInnerString(line, "throw new ParameterException\\(\"(.*)\"\\);");
			if (regularNotes.length == 1) {
				if (frontParameter != null && frontPattern != null) {
					String regularNote = regularNotes[0];
					frontParameter.addPattern(frontPattern, regularNote);
					frontParameter = null;
					frontPattern = null;
				}
				continue;
			}
		}
		

		List<Parameter> result = new ArrayList<Parameter>();
		result.addAll(new HashSet<Parameter>(strNameParameterMap.values()));
		return result;
	}
}
