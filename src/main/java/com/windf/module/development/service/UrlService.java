package com.windf.module.development.service;

import com.windf.core.exception.UserException;

public interface UrlService {

	/**
	 * 创建url
	 * @param url
	 */
	void createUrl(String url) throws UserException;
}
