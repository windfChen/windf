package com.windf.module.development.modle.java.code;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.core.util.ParameterUtil;
import com.windf.core.util.StringUtil;
import com.windf.module.development.modle.controler.ControlerReturn;
import com.windf.module.development.modle.java.CodeConst;

public class ControlerReturnCoder extends AbstractCodeable<ControlerReturn> {

	@Override
	public List<String> toCodes(ControlerReturn t, int tabCount) throws UserException {
		this.tabCount = tabCount;
		
		StringBuffer result = new StringBuffer();
		
		result.append(tab());
		result.append("return jsonReturn.");
		if (t.isSuccess()) {
			if (ParameterUtil.allEmpty(t.getTip(), t.getDataName())) {
				result.append("successMap();");
			} else if (StringUtil.isEmpty(t.getTip()) && StringUtil.isNotEmpty( t.getDataName())) {
				result.append("successMap(" +  t.getDataName() + ");");
			} else if (StringUtil.isEmpty( t.getDataName()) && StringUtil.isNotEmpty(t.getTip())) {
				result.append("successMap(\"" + t.getTip() + "\");");
			} else {
				result.append("returnMap(true, \"" + t.getTip() + "\", " +  t.getDataName() + ");");
			}
		} else {
			if (ParameterUtil.allEmpty(t.getTip(),  t.getDataName())) {
				throw new UserException("错误信息不能没有提示");
			} else if (StringUtil.isEmpty(t.getTip()) && StringUtil.isNotEmpty( t.getDataName())) {
				result.append("errorMap(" +  t.getDataName() + ");");
			} else if (StringUtil.isEmpty( t.getDataName()) && StringUtil.isNotEmpty(t.getTip())) {
				result.append("errorMap(\"" + t.getTip() + "\");");
			} else {
				result.append("returnMap(false, \"" + t.getTip() + "\", " + t.getDataName() + ");");
			}
		}
		
		List<String> resultList = new ArrayList<String>();
		resultList.add(result.toString());
		return resultList;
	}

	@Override
	public ControlerReturn toObject(List<String> codes) {
		ControlerReturn result = new ControlerReturn(ControlerReturn.MAP_STRING_OBJECT);
		
		for (String line : codes) {
			line = line.trim();
			
			// 变量声明
			String[] infos = CodeConst.getInnerString(line, "return jsonReturn\\.(.*)\\((.*)\\);");
			if (infos.length == 2) {
				String methodName = infos[0];
				String parametersStr = infos[1];

				String[] parameters = parametersStr.split(",");
				
				if ("successMap".equals(methodName)) {
					result.setSuccess(true);
				} else if ("errorMap".equals(methodName)) {
					result.setSuccess(false);
				} else if ("returnMap".equals(methodName)) {
					 if (parameters.length == 3) {
						 String success = parameters[0].trim();
						 result.setSuccess("true".equals(success));
					 }
				}
				
				if (parameters.length == 0) {
					
				} else if (parameters.length == 1) {
					String parameter = parameters[0].trim();
					if (parameter.startsWith("\"") && parameter.endsWith("\"")) {
						result.setTip(parameter.substring(1, parameter.length() - 1));
					} else {
						result.setDataName(parameter);
					}
				} else if (parameters.length == 2) {
					
				} else if (parameters.length == 3) {
					String tip = parameters[1].trim();
					String dataName = parameters[2].trim();
					result.setTip(tip.substring(1, tip.length() - 1));
					result.setDataName(dataName);
				}
			}
		}
		
		return result;
	}
	
}
