package com.windf.module.development.modle.java.code;

import java.util.List;

import com.windf.core.util.StringUtil;
import com.windf.module.development.modle.java.CodeConst;

public abstract class  AbstractCodeable<T> implements Codeable<T> {
	protected int tabCount = 0;
	
	protected String tab() {
		return tab(0);
	}
	
	protected String tab(int tempCount) {
		return CodeConst.getTabString(tabCount + tempCount);
	}
	
	protected void newEmptyLine(List<String> lines) {
		if (lines.size() > 1) {
			if (!StringUtil.isEmpty(lines.get(lines.size() - 1))) {
				lines.add("");
			}
		}
		
	}
}
