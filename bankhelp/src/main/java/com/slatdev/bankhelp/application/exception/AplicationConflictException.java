package com.slatdev.bankhelp.application.exception;

public abstract class AplicationConflictException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public AplicationConflictException(String message) {
		super(message);
	}
	
	public AplicationConflictException(String message, Throwable cause) {
		super(message, cause);
	}
}