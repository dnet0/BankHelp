package com.slatdev.bankhelp.application.exception;

public class AplicationNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AplicationNotFoundException(String message) {
		super(message);
	}

	public AplicationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}