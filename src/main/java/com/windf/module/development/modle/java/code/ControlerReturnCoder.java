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
		
		if (ControlerReturn.STRING.equals(t.getType())) {
			return toPageCodes(t);
		} else if (ControlerReturn.MAP_STRING_OBJECT.equals(t.getType())) {
			return toAjaxCodes(t);
		}
		
		return null;
	}
	
	/**
	 * 转换为页面返回的代码
	 * @param t
	 * @return
	 */
	private List<String> toPageCodes(ControlerReturn t) {
		StringBuffer result = new StringBuffer();
		result.append(tab());
		
		if (StringUtil.isNotEmpty(t.getViewPath())) {
			result.append("return Constant.WEB_BASE_VIEW + \"" + t.getViewPath() + "\";");
		} else {
			if (StringUtil.isNotEmpty(t.getMessage()) && StringUtil.isEmpty(t.getSureHref())) {
				result.append("return pageReturn.");
				if (t.isSuccess()) {
					result.append("success(\"" + t.getMessage() + "\");");
				} else {
					result.append("error(\"" + t.getMessage() + "\");");
				}
			} else if (ParameterUtil.hasNotEmpty(t.getMessage(), t.getSureButtonWord(), t.getSureHref())) {
				result.append("return pageReturn.info(" + t.isSuccess() + ", \"" + t.getMessage() + "\", "
						+ "\"" + t.getSureHref() + "\", \"" + t.getSureButtonWord() + "\")");
			}
		}
		
		List<String> resultList = new ArrayList<String>();
		resultList.add(result.toString());
		return resultList;
	}

	/**
	 * 转换为ajax返回的代码
	 * @param t
	 * @return
	 * @throws UserException
	 */
	private List<String> toAjaxCodes(ControlerReturn t) throws UserException {
		StringBuffer result = new StringBuffer();
		
		result.append(tab());
		result.append("return jsonReturn.");
		if (t.isSuccess()) {
			if (ParameterUtil.allEmpty(t.getMessage(), t.getDataName())) {
				result.append("successMap();");
			} else if (StringUtil.isEmpty(t.getMessage()) && StringUtil.isNotEmpty( t.getDataName())) {
				result.append("successMap(" +  t.getDataName() + ");");
			} else if (StringUtil.isEmpty( t.getDataName()) && StringUtil.isNotEmpty(t.getMessage())) {
				result.append("successMap(\"" + t.getMessage() + "\");");
			} else {
				result.append("returnMap(true, \"" + t.getMessage() + "\", " +  t.getDataName() + ");");
			}
		} else {
			if (ParameterUtil.allEmpty(t.getMessage(),  t.getDataName())) {
				throw new UserException("错误信息不能没有提示");
			} else if (StringUtil.isEmpty(t.getMessage()) && StringUtil.isNotEmpty( t.getDataName())) {
				result.append("errorMap(" +  t.getDataName() + ");");
			} else if (StringUtil.isEmpty( t.getDataName()) && StringUtil.isNotEmpty(t.getMessage())) {
				result.append("errorMap(\"" + t.getMessage() + "\");");
			} else {
				result.append("returnMap(false, \"" + t.getMessage() + "\", " + t.getDataName() + ");");
			}
		}
		
		List<String> resultList = new ArrayList<String>();
		resultList.add(result.toString());
		return resultList;
	}

	@Override
	public ControlerReturn toObject(List<String> codes) {
		ControlerReturn result = null;
				
		for (String line : codes) {
			line = line.trim();
			
			String[] infos = CodeConst.getInnerString(line, "return (.*)\\.(.*)\\((.*)\\);");
			if (infos.length == 3) {
				String returnMethod = infos[0];
				String methodName = infos[1];
				String parametersStr = infos[2];
				
				if ("jsonReturn".equals(returnMethod)) {
					result = fromAjax(methodName, parametersStr);
				} else if ("pageReturn".equals(returnMethod)) {
					result = fromPage(methodName, parametersStr);
				}
				
			} else {
				infos = CodeConst.getInnerString(line, "return Constant\\.WEB_BASE_VIEW \\+ \"(.*)\";");
				if (infos.length == 1) {
					result = new ControlerReturn(ControlerReturn.STRING);
					result.setViewPath(infos[0]);
				}
			}
		}
		
		return result;
	}
	
	private ControlerReturn fromPage(String methodName, String parametersStr) {
		ControlerReturn result = new ControlerReturn(ControlerReturn.STRING);
		
		String[] parameters = parametersStr.split(",");
		if ("success".equals(methodName)) {
			result.setSuccess(true);
		} else if ("error".equals(methodName)) {
			result.setSuccess(false);
		} else if ("info".equals(methodName)) {
			 if (parameters.length == 4) {
				 String success = parameters[0].trim();
				 result.setSuccess("true".equals(success));
			 }
		}
		
		if (parameters.length == 1) {
			String parameter = parameters[0].trim();
			String message = CodeConst.getStringContent(parameter);
			if (StringUtil.isNotEmpty(message)) {
				result.setMessage(message);
			}
		} else if (parameters.length == 4) {
			String message = parameters[1].trim();
			String sureHref = parameters[2].trim();
			String sureButtonWord = parameters[3].trim();
			result.setMessage(CodeConst.getStringContent(message));
			result.setSureHref(CodeConst.getStringContent(sureHref));
			result.setSureButtonWord(CodeConst.getStringContent(sureButtonWord));
		}
		
		return result;
	}

	private ControlerReturn fromAjax(String methodName, String parametersStr) {
		ControlerReturn result = new ControlerReturn(ControlerReturn.MAP_STRING_OBJECT);
		
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
			String message = CodeConst.getStringContent(parameter);
			if (StringUtil.isNotEmpty(message)) {
				result.setMessage(message);
			} else {
				result.setDataName(parameter);
			}
		} else if (parameters.length == 2) {
			
		} else if (parameters.length == 3) {
			String message = parameters[1].trim();
			String dataName = parameters[2].trim();
			result.setMessage(CodeConst.getStringContent(message));
			result.setDataName(dataName);
		}
		
		return result;
	}
	
}
