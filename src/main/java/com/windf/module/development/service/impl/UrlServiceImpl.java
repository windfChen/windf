package com.windf.module.development.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.windf.core.exception.UserException;
import com.windf.core.util.ParameterUtil;
import com.windf.module.development.pojo.Controler;
import com.windf.module.development.pojo.Module;
import com.windf.module.development.pojo.URL;
import com.windf.module.development.service.ModuleManageService;
import com.windf.module.development.service.UrlService;

@Service
public class UrlServiceImpl  implements UrlService {
	
	@Resource
	private ModuleManageService moduleManageService ;

	@Override
	public void createUrl(String url) throws UserException {
		/*
		 * 验证参数
		 */
		if (ParameterUtil.hasEmpty(url)) {
			return;
		}
		
		/*
		 * 解析地址
		 */
		String[] subUrls = url.split("/");
		if (subUrls.length < 3) {
			throw new UserException("路径段不对");
		}
		String modulePath = subUrls[0];
		@SuppressWarnings("unused")
		String methodName = subUrls[subUrls.length - 1];
		StringBuffer controlerPath = new StringBuffer();
		for (int i = 1; i < subUrls.length - 1; i++) {
			controlerPath.append(subUrls[i] + "/");
		}
		
		/*
		 * 获取模块
		 */
		Module module = moduleManageService.getModuleByPath(modulePath);
		if (module == null) {
			throw new UserException("指定模块不存在");
		}
		
		/*
		 * 查询模块url
		 */
		URL u = getUrl(module, url);
		if (u != null) {
			throw new UserException("url已存在");
		}
		
		/*
		 * 查询控制器
		 */
		Controler controler = this.getModuleControlerByPath(module, controlerPath.toString());
		if (controler == null) {
			throw new UserException("控制器不存");
		}
		
		/*
		 * 获取指定控制器的代码
		 */
		//ControlerCoder controlerCoder = new ControlerCoder(module.getCode(), );
		
		
	}

	/**
	 * 根据路径查询指定模块的控制器
	 * @param module
	 * @param string
	 * @return
	 */
	private Controler getModuleControlerByPath(Module module, String path) {
		Controler result = null;
		
		List<Controler> controlers = module.getControlers();
		
		if (controlers != null) {
			for (Controler c : controlers) {
				if (c != null && c.getUrlPath().equals(path)) {
					result = c;
					break;
				}
			}
		}
		
		return result;
	}

	/**
	 * 查询模块的url
	 * @param module
	 * @param url
	 */
	private URL getUrl(Module module, String url) {
		URL result = null;
		
		List<URL> urls = module.getUrls();
		
		if (urls != null) {
			for (URL u : urls) {
				if (u != null && u.getName().equals(url)) {
					result = u;
					break;
				}
			}
		}
		
		return result;
		
	}

}
