package com.windf.core.exception;

import java.io.Serializable;

public class CodeException extends TypeException implements Serializable {
	private static final long serialVersionUID = 1L;

	public CodeException() {
	}

	public CodeException(String message) {
		super(message);
	}
	
	public CodeException(String type, String message) {
		super(type, message);
	}

	public CodeException(Throwable cause) {
		super(cause);
	}

	public CodeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public CodeException(String type, String message, Throwable cause) {
		super(type, message, cause);
	}
}
