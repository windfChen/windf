package com.windf.module.development.web.controler;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.windf.core.exception.EntityException;
import com.windf.core.util.ParameterUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.service.UrlService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = Constant.WEB_BASE_PATH + "/url")
public class UrlControler extends BaseControler{
	
	@Resource
	private UrlService urlService ;
	
	@Override
	protected String getModulePath() {
		return Constant.WEB_BASE_PATH;
	}
	
	@ResponseBody
	@RequestMapping(value = "/create", method = {RequestMethod.POST})
	public Map<String, Object> create() {
		// 验证参数
		String url = this.getParameter("url");
		if (ParameterUtil.hasEmpty(url)) {
			return paramErrorMap();
		}
		
		// 调用服务
		try {
			urlService.createUrl(url);
		} catch (EntityException e) {
			return errorMessageMap(e.getMessage());
		}
		
		return returnMap(true, "创建成功");
	}

}
