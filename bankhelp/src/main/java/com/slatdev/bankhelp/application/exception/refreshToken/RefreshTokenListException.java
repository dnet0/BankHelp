package com.slatdev.bankhelp.application.exception.refreshToken;

import com.slatdev.bankhelp.application.exception.AplicationValidationException;

public class RefreshTokenListException extends AplicationValidationException {
	private static final long serialVersionUID = 1L;

	public RefreshTokenListException(String message) {
		super(message);
	}

	public RefreshTokenListException(String message, Throwable cause) {
		super(message, cause);
	}
}