package com.windf.core.exception;

import java.io.Serializable;

public class UserException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public UserException() {
	}

	public UserException(String message) {
		super(message);
	}

	public UserException(Throwable cause) {
		super(cause);
	}

	public UserException(String message, Throwable cause) {
		super(message, cause);
	}

}
