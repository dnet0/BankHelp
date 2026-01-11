package com.slatdev.bankhelp.application.exception;

public class RefreshTokenCreationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public RefreshTokenCreationException(String message) {
		super(message);
	}

	public RefreshTokenCreationException(String message, Throwable cause) {
		super(message, cause);
	}
}