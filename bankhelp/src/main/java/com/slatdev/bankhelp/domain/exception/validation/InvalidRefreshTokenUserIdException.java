package com.slatdev.bankhelp.domain.exception.validation;

import com.slatdev.bankhelp.domain.exception.DomainInputException;

public class InvalidRefreshTokenUserIdException extends DomainInputException {

	private static final long serialVersionUID = 1L;

	public InvalidRefreshTokenUserIdException(String message) {
		super(message);
	}
}