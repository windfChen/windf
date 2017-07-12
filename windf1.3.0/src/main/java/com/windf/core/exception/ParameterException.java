package com.windf.core.exception;

public class ParameterException extends Exception{
	private static final long serialVersionUID = 5500814701544832454L;
	
	public ParameterException() {
	}
	
	public ParameterException(String message) {
	    super(message);
	}
	
	public ParameterException(String message, Throwable cause) {
	    super(message, cause);
	}
	
	public ParameterException(Throwable cause) {
	    super(cause);
	}
	
	public boolean matchMessage(String message) {
		return message.equals(this.getMessage());
	}
	
}
