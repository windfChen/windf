package com.windf.module.development.modle.java.code;

import java.util.ArrayList;
import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.core.util.ParameterUtil;
import com.windf.core.util.StringUtil;
import com.windf.module.development.pojo.Return;

public class ControlerReturnCoder extends AbstractCodeable<Return> {
	
	private boolean success;
	private String tip;
	private String dataName;
	
	public ControlerReturnCoder(boolean success, String tip, String dataName) {
		this.success = success;
		this.tip = tip;
		this.dataName = dataName;
	}

	@Override
	public List<String> toCodes(Return t, int tabCount) throws UserException {
		this.tabCount = tabCount;
		
		StringBuffer result = new StringBuffer();
		
		result.append(tab());
		result.append("return jsonReturn.");
		if (success) {
			if (ParameterUtil.allEmpty(tip, dataName)) {
				result.append("successMap();");
			} else if (StringUtil.isEmpty(tip) && StringUtil.isNotEmpty(dataName)) {
				result.append("successMap(" + dataName + ");");
			} else if (StringUtil.isEmpty(dataName) && StringUtil.isNotEmpty(tip)) {
				result.append("successMap(\"" + tip + "\");");
			} else {
				result.append("returnMap(true, \"" + tip + "\", " + dataName + ");");
			}
		} else {
			if (ParameterUtil.allEmpty(tip, dataName)) {
				throw new UserException("错误信息不能没有提示");
			} else if (StringUtil.isEmpty(tip) && StringUtil.isNotEmpty(dataName)) {
				result.append("errorMap(" + dataName + ");");
			} else if (StringUtil.isEmpty(dataName) && StringUtil.isNotEmpty(tip)) {
				result.append("errorMap(\"" + tip + "\");");
			} else {
				result.append("returnMap(false, \"" + tip + "\", " + dataName + ");");
			}
		}
		
		List<String> resultList = new ArrayList<String>();
		resultList.add(result.toString());
		return resultList;
	}

	@Override
	public Return toObject(List<String> codes) {
		return null;
	}
	
}
