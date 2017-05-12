package com.windf.core.exception;

import java.io.Serializable;

public class EntityException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public EntityException() {
	}

	public EntityException(String message) {
		super(message);
	}

	public EntityException(Throwable cause) {
		super(cause);
	}

	public EntityException(String message, Throwable cause) {
		super(message, cause);
	}

}
