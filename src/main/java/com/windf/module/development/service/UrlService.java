package com.windf.module.development.service;

import java.util.List;

import com.windf.core.exception.UserException;
import com.windf.module.development.pojo.UrlInfo;

public interface UrlService {

	/**
	 * 创建url
	 * @param moduleCode
	 * @param url
	 * @param get
	 * @throws UserException
	 */
	void createUrl(String moduleCode, String url, boolean get) throws UserException;
	
	/**
	 * 获得模块下所有的UrlInfo
	 * @param moduleCode
	 * @return
	 * @throws UserException
	 */
	List<UrlInfo> listUrls(String moudleCode) throws UserException;
}
