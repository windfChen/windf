package com.windf.module.development.service;

import com.windf.core.exception.UserException;

public interface UrlService {

	/**
	 * 创建url
	 * @param moduleCode
	 * @param url
	 * @param get
	 * @throws UserException
	 */
	void createUrl(String moduleCode, String url, boolean get) throws UserException;
}
