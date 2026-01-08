package com.slatdev.bankhelp.application.exception;

public class TicketCreationException extends RuntimeException{
	private static final long serialVersionUID = 1L;
	public TicketCreationException(String message, Throwable cause) {
		super(message, cause);
	}
}
