package com.windf.core.exception;

public class UserMessageException extends Exception{
	private static final long serialVersionUID = 5500814701544832454L;
	
	public UserMessageException() {
	}
	
	public UserMessageException(String message) {
	    super(message);
	}
	
	public UserMessageException(String message, Throwable cause) {
	    super(message, cause);
	}
	
	public UserMessageException(Throwable cause) {
	    super(cause);
	}
	
	public boolean matchMessage(String message) {
		return message.equals(this.getMessage());
	}
	
}
