package com.slatdev.bankhelp.domain.exception.validation;

import com.slatdev.bankhelp.domain.exception.DomainInputException;

public class InvalidTicketUserIdException extends DomainInputException {
	private static final long serialVersionUID = 1L;

	public InvalidTicketUserIdException(String message) {
		super(message);
	}

}
