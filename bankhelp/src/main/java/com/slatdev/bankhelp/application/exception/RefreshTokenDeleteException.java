package com.slatdev.bankhelp.application.exception;

public class RefreshTokenDeleteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RefreshTokenDeleteException(String message) {
		super(message);
	}

	public RefreshTokenDeleteException(String message, Throwable cause) {
		super(message, cause);
	}
}