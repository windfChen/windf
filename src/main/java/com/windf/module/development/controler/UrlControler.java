package com.windf.module.development.controler;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.windf.core.exception.UserException;
import com.windf.core.util.ParameterUtil;
import com.windf.module.development.Constant;
import com.windf.module.development.entity.UrlInfo;
import com.windf.module.development.service.UrlService;
import com.windf.plugins.web.BaseControler;

@Controller
@Scope("prototype")
@RequestMapping(value = UrlControler.CONTROLER_PATH)
public class UrlControler extends BaseControler{
	protected final static String CONTROLER_PATH = Constant.MODULE_WEB_PATH + "/url";
	
	@Resource
	private UrlService urlService ;
	
	@RequestMapping(value = "", method = {RequestMethod.GET})
	public String index() {
		return  Constant.WEB_BASE_VIEW + "/url";
	}
	
	@RequestMapping(value = "/create", method = {RequestMethod.GET})
	public String create() {
		// 验证参数
		String url = paramenter.getString("url");
		String moduleCode = paramenter.getString("module");
		String getStr = paramenter.getString("get");
		if (ParameterUtil.hasEmpty(moduleCode, url)) {
			return responseReturn.parameterError();
		}
		boolean get = Boolean.parseBoolean(getStr);
		
//		// 调用服务
//		try {
//			urlService.createUrl(moduleCode, url, get);
//		} catch (UserException e) {
//			return responseReturn.error(e.getMessage());
//		}
		
		return responseReturn.returnData(true, "创建成功");
	}
	
	@RequestMapping(value = "/list", method = {RequestMethod.GET})
	public String list()  {
		// 验证参数
		String moduleCode = paramenter.getString("module");
		if (ParameterUtil.hasEmpty(moduleCode)) {
			return responseReturn.parameterError();
		}
		
		// 调用服务
		List<UrlInfo> data = null;
//		try {
//			data = urlService.listUrls(moduleCode);
//		} catch (UserException e) {
//			return responseReturn.error(e.getMessage());
//		}
		
		return responseReturn.successData(data);
	}
	

}
