package com.windf.module.development.web.controler;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.windf.core.exception.UserException;
import com.windf.core.util.ParameterUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.pojo.UrlInfo;
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
	
	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String index() {
		return  Constant.WEB_BASE_VIEW + "/url";
	}
	
	@ResponseBody
	@RequestMapping(value = "/create", method = {RequestMethod.GET})
	public Map<String, Object> create() {
		// 验证参数
		String url = this.getParameter("url");
		String moduleCode = this.getParameter("module");
		String getStr = this.getParameter("get");
		if (ParameterUtil.hasEmpty(moduleCode, url)) {
			return jsonReturn.paramErrorMap();
		}
		boolean get = Boolean.parseBoolean(getStr);
		
		// 调用服务
		try {
			urlService.createUrl(moduleCode, url, get);
		} catch (UserException e) {
			return jsonReturn.errorMap(e.getMessage());
		}
		
		return jsonReturn.returnMap(true, "创建成功");
	}
	
	@ResponseBody
	@RequestMapping(value = "/list", method = {RequestMethod.GET})
	public Map<String, Object> list()  {
		// 验证参数
		String moduleCode = this.getParameter("module");
		if (ParameterUtil.hasEmpty(moduleCode)) {
			return jsonReturn.paramErrorMap();
		}
		
		// 调用服务
		List<UrlInfo> data = null;
		try {
			data = urlService.listUrls(moduleCode);
		} catch (UserException e) {
			return jsonReturn.errorMap(e.getMessage());
		}
		
		return jsonReturn.successMap(data);
	}
	

}
