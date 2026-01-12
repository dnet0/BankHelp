package com.slatdev.bankhelp.domain.exception.validation;

import com.slatdev.bankhelp.domain.exception.DomainInputException;

public class InvalidUserEmailException extends DomainInputException {
	private static final long serialVersionUID = 1L;

	public InvalidUserEmailException(String message) {
		super(message);
	}

}

