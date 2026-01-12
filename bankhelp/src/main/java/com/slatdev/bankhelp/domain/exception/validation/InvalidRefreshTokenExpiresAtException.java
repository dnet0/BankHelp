package com.slatdev.bankhelp.domain.exception.validation;

import com.slatdev.bankhelp.domain.exception.DomainInputException;

public class InvalidRefreshTokenExpiresAtException extends DomainInputException {

	private static final long serialVersionUID = 1L;

	public InvalidRefreshTokenExpiresAtException(String message) {
		super(message);
	}
}