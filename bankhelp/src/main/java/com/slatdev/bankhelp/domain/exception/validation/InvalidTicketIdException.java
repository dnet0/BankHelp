package com.slatdev.bankhelp.domain.exception.validation;

import com.slatdev.bankhelp.domain.exception.DomainInputException;

public class InvalidTicketIdException extends DomainInputException {
	private static final long serialVersionUID = 1L;

	public InvalidTicketIdException(String message) {
		super(message);
	}

}
