package com.slatdev.bankhelp.domain.exception.rules;

import com.slatdev.bankhelp.domain.exception.DomainRuleException;

public class InvalidRefreshTokenIsRevokedException extends DomainRuleException {

	private static final long serialVersionUID = 1L;

	public InvalidRefreshTokenIsRevokedException(String message) {
		super(message);
	}
}