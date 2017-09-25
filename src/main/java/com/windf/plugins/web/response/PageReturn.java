package com.windf.plugins.web.response;

import com.windf.core.util.StringUtil;
import com.windf.plugins.web.BaseControler;

public class PageReturn extends AbstractResponseRetrun {
	
	private BaseControler baseControler;
	private String redirectUrl;
	
	public PageReturn(BaseControler baseControler) {
		this.baseControler = baseControler;
	}
	
	/**
	 * 返回错误页面提示
	 * @return
	 */
	@Override
	public String parameterError() {
		this.page = PARAMETER_ERROR_PAGE;
		return returnData(false, "参数错误", null);
	}
	
	/**
	 * 返回正确的正确的页面
	 * @param message
	 * @return
	 */
	@Override
	public String success() {
		return returnData(true, null, null);
	}

	/**
	 * 返回正确的正确的页面
	 * @param message
	 * @return
	 */
	@Override
	public String success(String message) {
		this.page = SUCCESS_MESSAGE_PAGE;
		return returnData(true, message, null);
	}
	
	/**
	 * 返回错误页面，并且提示信息，页面上显示返回之前的连接的按钮
	 * @return
	 */
	@Override
	public String error(String message) {
		this.page = ERROR_PAGE;
		return returnData(false, message, null);
	}

	@Override
	public String successData(Object data) {
		return returnData(true, null, data);
	}

	@Override
	public String errorData(Object data) {
		return returnData(false, null, data);
	}

	@Override
	public String returnData(Object data) {
		return returnData(true, null, data);
	}

	@Override
	public String returnData(boolean success, String message) {
		return returnData(success, message, null);
	}

	@Override
	public String returnData(boolean success, String message, Object data) {
		baseControler.paramenter.setValue(RESULT_SUCCESS_KEY, success);
		if (data != null) {
			baseControler.paramenter.setValue(RESULT_DATA_KEY, data);
		}
		if (StringUtil.isNotEmpty(message)) {
			baseControler.paramenter.setValue(RESULT_MESSAGE_KEY, message);
		}
		
		if (page == null) {
			// TODO 设置默认路径
//			page = baseControler.getFullPath();
		}
		// TODO 设置跳转的数据
		
		return page;
	}

	@Override
	public String redirect(String url) {
		redirectUrl = "redirect:" + url;
		return redirectUrl;
	}
}
