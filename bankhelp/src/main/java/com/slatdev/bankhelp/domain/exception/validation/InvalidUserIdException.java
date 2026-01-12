package com.slatdev.bankhelp.domain.exception.validation;

import com.slatdev.bankhelp.domain.exception.DomainInputException;

public class InvalidUserIdException extends DomainInputException {
	private static final long serialVersionUID = 1L;

	public InvalidUserIdException(String message) {
		super(message);
	}

}
