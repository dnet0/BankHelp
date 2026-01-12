package com.slatdev.bankhelp.domain.exception.validation;

import com.slatdev.bankhelp.domain.exception.DomainInputException;

public class InvalidRefreshTokenIdException extends DomainInputException {

	private static final long serialVersionUID = 1L;

	public InvalidRefreshTokenIdException(String message) {
		super(message);
	}
}