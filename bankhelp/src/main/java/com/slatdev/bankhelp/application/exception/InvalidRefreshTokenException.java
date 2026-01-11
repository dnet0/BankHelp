package com.slatdev.bankhelp.application.exception;

public class InvalidRefreshTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public InvalidRefreshTokenException(String message) {
		super(message);
	}

	public InvalidRefreshTokenException(String message, Throwable cause) {
		super(message, cause);
	}
}