package com.windf.plugins.web.util;

import com.windf.plugins.web.BaseControler;

public class PageReturn {
	protected static final String PARAMETER_ERROR_PAGE = "/common/error/parameter";
	protected static final String ERROR_PAGE = "/common/error";
	protected static final String SUCCESS_MESSAGE_PAGE = "/common/info/success";
	
	private BaseControler baseControler;
	
	public PageReturn(BaseControler baseControler) {
		this.baseControler = baseControler;
	}
	
	/**
	 * 返回错误页面提示
	 * @return
	 */
	public String parameterError() {
		
		baseControler.setValue("message", "参数错误");
		
		return PARAMETER_ERROR_PAGE;
	}
	
	/**
	 * 返回错误页面，并且提示信息，页面上显示返回之前的连接的按钮
	 * @return
	 */
	public String error(String message) {
		baseControler.setValue("message", message);
		return ERROR_PAGE;
	}

	/**
	 * 返回正确的正确的页面
	 * @param message
	 * @return
	 */
	public String success(String message) {
		baseControler.setValue("message", message);
		return SUCCESS_MESSAGE_PAGE;
	}
	
	/**
	 * 返回正确信息
	 * @param message
	 * @param sureHref
	 * @param sureButtonWord
	 * @return
	 */
	public String info(boolean succeses, String message, String sureHref, String sureButtonWord) {
		
		baseControler.setValue("message", message);
		baseControler.setValue("sureHref", sureHref);
		
		return SUCCESS_MESSAGE_PAGE;
	}
}
