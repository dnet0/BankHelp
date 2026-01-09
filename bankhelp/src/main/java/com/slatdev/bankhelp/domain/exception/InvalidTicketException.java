package com.slatdev.bankhelp.domain.exception;

public class InvalidTicketException extends DomainException {

	private static final long serialVersionUID = -4534883715367484976L;

	public InvalidTicketException(String message) {
		super(message);
	}

}
