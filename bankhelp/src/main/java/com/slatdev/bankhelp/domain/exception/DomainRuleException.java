package com.slatdev.bankhelp.domain.exception;

public abstract class DomainRuleException extends RuntimeException {

	private static final long serialVersionUID = 1841253750221130141L;

	public DomainRuleException(String message) {
		super(message);
	}

	public DomainRuleException(String message, Throwable cause) {
		super(message, cause);
	}
}
