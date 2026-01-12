package com.slatdev.bankhelp.application.exception.refreshToken;

import com.slatdev.bankhelp.application.exception.AplicationValidationException;

public class RefreshTokenCreationException extends AplicationValidationException{
	private static final long serialVersionUID = 1L;

	public RefreshTokenCreationException(String message) {
		super(message);
	}

	public RefreshTokenCreationException(String message, Throwable cause) {
		super(message, cause);
	}
}