package com.windf.module.development.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.exception.ParameterException;
import com.windf.core.exception.UserException;
import com.windf.core.util.ParameterUtil;
import com.windf.core.util.StringUtil;
import com.windf.module.development.entity.Controler;
import com.windf.module.development.entity.Module;
import com.windf.module.development.entity.UrlInfo;
import com.windf.module.development.modle.controler.ControlerCoder;
import com.windf.module.development.service.ModuleManageService;
import com.windf.module.development.service.UrlService;

@Service
public class UrlServiceImpl  implements UrlService {
	
	@Resource
	private ModuleManageService moduleManageService ;

	@Override
	public void createUrl(String moduleCode, String url, boolean get) throws UserException {
		/*
		 * 验证参数
		 */
		if (ParameterUtil.hasEmpty(moduleCode, url)) {
			throw new ParameterException();
		}
		
		/*
		 * 获得模块
		 */
		Module module = moduleManageService.getModuleByCode(moduleCode);
		if (module == null) {
			throw new UserException("指定模块不存在");
		}
		
		/*
		 * 解析地址
		 */
		if (!url.startsWith("/")) {
			url = "/" + url;
		}
		url = url.substring(module.getBasePath().length());
		String methodPath = url.substring(url.lastIndexOf("/"));
		String controlerPath = url.substring(0, url.lastIndexOf("/"));
		if (StringUtil.isEmpty(controlerPath)) {
			throw new ParameterException();
		}
		String[] subControlerPaths = controlerPath.split("/");
		StringBuffer controlerNameBuff = new StringBuffer();
		for (int i = 0; i < subControlerPaths.length; i++) {
			controlerNameBuff.append(StringUtil.firstLetterUppercase(subControlerPaths[i]));
		}
		String controlerName = controlerNameBuff.toString();
		
		/*
		 * 查询模块url
		 */
		UrlInfo urlInfo = getUrl(module.getCode(), url);
		if (urlInfo != null) {
			throw new UserException("url已存在");
		}
		
		/*
		 * 获取指定控制器的代码
		 */
		ControlerCoder controlerCoder = new ControlerCoder(module.getCode(), controlerName);
		controlerCoder.setWebPath(controlerPath.toString());
		urlInfo = new UrlInfo();
		urlInfo.setAjaxReturn(true);
		String methodName = methodPath;
		if (methodName.startsWith("/")) {
			methodName = methodName.substring(1);
		}
		urlInfo.setName(methodName);
		urlInfo.setSubPath(methodPath);
		if (get) {
			urlInfo.setRequestMethod("GET");
		} else {
			urlInfo.setRequestMethod("POST");
		}
		controlerCoder.addSubPath(urlInfo);
		controlerCoder.write();
		
	}

	@Override
	public List<UrlInfo> listUrls(String moudleCode) throws UserException {
		Module module = moduleManageService.getModuleByCode(moudleCode);
		List<UrlInfo> urlInfos = new ArrayList<UrlInfo>();
		for (Controler controler : module.getControlers()) {
			urlInfos.addAll(controler.getUrlInfos());
		}
		return urlInfos;
	}

	/**
	 * 查询模块的url
	 * @param module
	 * @param url
	 * @throws UserException 
	 */
	public UrlInfo getUrl(String moduleCode, String url) throws UserException {
		UrlInfo result = null;
		
		if (StringUtil.isNotEmpty(url)) {
			Module module = moduleManageService.getModuleByCode(moduleCode);
			for (Controler controler : module.getControlers()) {
				for (UrlInfo urlInfo : controler.getUrlInfos()) {
					String url2 = module.getBasePath() + controler.getUrlPath() + urlInfo.getSubPath();
					if (urlInfo != null && url2.equals(url)) {
						result = urlInfo;
						break;
					}
				}
			}
		}
		
		return result;
		
	}


}
