package com.slatdev.bankhelp.application.exception.refreshToken;

import com.slatdev.bankhelp.application.exception.AplicationSecurityException;

public class RefreshTokenUpdateException extends AplicationSecurityException {
	private static final long serialVersionUID = 1L;

	public RefreshTokenUpdateException(String message) {
		super(message);
	}

	public RefreshTokenUpdateException(String message, Throwable cause) {
		super(message, cause);
	}
}
