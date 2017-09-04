package com.windf.core.exception;

import java.io.Serializable;

/**
 * 数据访问异常
 * @author chenyafeng
 *
 */
public class DataAccessException extends TypeException implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public DataAccessException() {
	}

	public DataAccessException(String message) {
		super(message);
	}
	
	public DataAccessException(String type, String message) {
		super(type, message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}

	public DataAccessException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public DataAccessException(String type, String message, Throwable cause) {
		super(type, message, cause);
	}
}
