package com.slatdev.bankhelp.domain.exception;

public class DomainException extends RuntimeException {

	private static final long serialVersionUID = 1841253750221130141L;

	public DomainException(String message) {
		super(message);
	}

	public DomainException(String message, Throwable cause) {
		super(message, cause);
	}
}
