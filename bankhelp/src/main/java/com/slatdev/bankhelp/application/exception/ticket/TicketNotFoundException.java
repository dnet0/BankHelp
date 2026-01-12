package com.slatdev.bankhelp.application.exception.ticket;

import com.slatdev.bankhelp.application.exception.AplicationNotFoundException;

public class TicketNotFoundException extends AplicationNotFoundException {
	private static final long serialVersionUID = 1L;

	public TicketNotFoundException(String message) {
		super(message);
	}

	public TicketNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
