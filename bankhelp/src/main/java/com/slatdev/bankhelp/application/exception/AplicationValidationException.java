package com.slatdev.bankhelp.application.exception;

public class AplicationValidationException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AplicationValidationException(String message) {
		super(message);
	}

	public AplicationValidationException(String message, Throwable cause) {
		super(message, cause);
	}
}