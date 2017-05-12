package com.windf.plugins.log.impl;

import org.apache.log4j.Logger;

import com.windf.plugins.log.LogService;

public class Log4jLogService implements LogService{
	private Logger logger = null;
	
	@SuppressWarnings("rawtypes") 
	public Log4jLogService(Class clazz) {
		logger = Logger.getLogger(clazz);
	}
	
	@Override
	public void error(String message) {
		logger.error(message);
	}

	@Override
	public void warn(String message) {
		logger.warn(message);
	}

	@Override
	public void debug(String message) {
		logger.debug(message);
	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void error(String message, Throwable e) {
		logger.error(message,  e);
	}

	@Override
	public void warn(String message, Throwable e) {
		logger.warn(message,  e);
	}

	@Override
	public void debug(String message, Throwable e) {
		logger.debug(message,  e);
	}

	@Override
	public void info(String message, Throwable e ){
		logger.info(message,  e);
	}

}
