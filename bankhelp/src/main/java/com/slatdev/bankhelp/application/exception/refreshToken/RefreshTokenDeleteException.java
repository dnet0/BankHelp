package com.slatdev.bankhelp.application.exception.refreshToken;

import com.slatdev.bankhelp.application.exception.AplicationSecurityException;

public class RefreshTokenDeleteException extends AplicationSecurityException {
	private static final long serialVersionUID = 1L;

	public RefreshTokenDeleteException(String message) {
		super(message);
	}

	public RefreshTokenDeleteException(String message, Throwable cause) {
		super(message, cause);
	}
}