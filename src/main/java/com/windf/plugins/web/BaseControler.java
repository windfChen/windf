package com.windf.plugins.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.HandlerMapping;

import com.windf.core.frame.Moudle;
import com.windf.core.util.StringUtil;
import com.windf.core.util.reflect.ReflectUtil;
import com.windf.plugins.log.LogFactory;
import com.windf.plugins.log.Logger;
import com.windf.plugins.web.request.RequestParamenter;
import com.windf.plugins.web.request.RequestPath;
import com.windf.plugins.web.response.ResponseReturn;
import com.windf.plugins.web.response.ResponseReturnFactory;

/**
 * 控制层的父类
 * @author 陈亚峰
 *
 */
public abstract class BaseControler {
	
	protected static Logger logger = null;

	public RequestParamenter paramenter;
	public RequestPath path;
	protected HttpServletRequest request;
	protected ResponseReturn responseReturn;
	
	public BaseControler () {
		// 日初始化日志
		if (logger == null) {
			logger = LogFactory.getLogger(this.getClass());
		}

		// 初始化模块
		Moudle.setCurrentMoudle(this);
		
		// 获取请求
		request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

		// 参数
		paramenter = new RequestParamenter(request);
		
		// 路径
		path = new RequestPath(request);
		
		// 设置返回
		responseReturn = ResponseReturnFactory.getResponseReturn(path.getSuffix(), this);
		
		
	}
	
	
}
