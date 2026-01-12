package com.slatdev.bankhelp.application.exception;

public class AplicationSecurityException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AplicationSecurityException(String message) {
		super(message);
	}

	public AplicationSecurityException(String message, Throwable cause) {
		super(message, cause);
	}
}