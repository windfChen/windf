package com.windf.core.exception;

public class SystemException extends Exception{
	private static final long serialVersionUID = 5500814701544832454L;
	
	public SystemException() {
	}
	
	public SystemException(String message) {
	    super(message);
	}
	
	public SystemException(String message, Throwable cause) {
	    super(message, cause);
	}
	
	public SystemException(Throwable cause) {
	    super(cause);
	}
	
}
