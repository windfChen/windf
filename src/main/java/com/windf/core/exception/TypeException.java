package com.windf.core.exception;

import java.io.Serializable;

public class TypeException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;

	public TypeException() {
	}

	public TypeException(String message) {
		super(message);
	}
	
	public TypeException(String type, String message) {
		super(message);
		this.type = type;
	}

	public TypeException(Throwable cause) {
		super(cause);
	}

	public TypeException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TypeException(String type, String message, Throwable cause) {
		super(message, cause);
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
