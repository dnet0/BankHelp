package com.slatdev.bankhelp.application.exception;

public class UserCreationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserCreationException(String message, Throwable cause) {
		super(message, cause);
	}

}
