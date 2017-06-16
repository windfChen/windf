package com.windf.module.development.service;

import com.windf.core.exception.EntityException;

public interface UrlService {

	/**
	 * 创建url
	 * @param url
	 */
	void createUrl(String url) throws EntityException;
}
