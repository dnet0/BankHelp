package com.slatdev.bankhelp.domain.exception.validation;

import com.slatdev.bankhelp.domain.exception.DomainInputException;

public class InvalidTicketDescriptionException extends DomainInputException {

	private static final long serialVersionUID = 1L;

	public InvalidTicketDescriptionException(String message) {
		super(message);
	}
}