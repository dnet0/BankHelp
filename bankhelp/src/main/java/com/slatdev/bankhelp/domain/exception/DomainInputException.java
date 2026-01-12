package com.slatdev.bankhelp.domain.exception;

public abstract class DomainInputException extends RuntimeException {

	private static final long serialVersionUID = 1841253750221130141L;

	public DomainInputException(String message) {
		super(message);
	}

	public DomainInputException(String message, Throwable cause) {
		super(message, cause);
	}
}
