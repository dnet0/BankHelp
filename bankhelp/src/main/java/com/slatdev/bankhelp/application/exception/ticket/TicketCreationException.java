package com.slatdev.bankhelp.application.exception.ticket;

import com.slatdev.bankhelp.application.exception.AplicationValidationException;

public class TicketCreationException extends AplicationValidationException {
	private static final long serialVersionUID = 1L;

	public TicketCreationException(String message) {
		super(message);
	}

	public TicketCreationException(String message, Throwable cause) {
		super(message, cause);
	}
}
