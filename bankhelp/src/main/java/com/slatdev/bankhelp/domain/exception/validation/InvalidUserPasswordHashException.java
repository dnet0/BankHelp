package com.slatdev.bankhelp.domain.exception.validation;

import com.slatdev.bankhelp.domain.exception.DomainInputException;

public class InvalidUserPasswordHashException extends DomainInputException {
	private static final long serialVersionUID = 1L;

	public InvalidUserPasswordHashException(String message) {
		super(message);
	}

}
