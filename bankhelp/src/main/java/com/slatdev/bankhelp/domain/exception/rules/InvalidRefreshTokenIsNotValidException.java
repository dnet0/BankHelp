package com.slatdev.bankhelp.domain.exception.rules;

import com.slatdev.bankhelp.domain.exception.DomainRuleException;

public class InvalidRefreshTokenIsNotValidException extends DomainRuleException {

	private static final long serialVersionUID = 1L;

	public InvalidRefreshTokenIsNotValidException(String message) {
		super(message);
	}
}