package com.slatdev.bankhelp.application.exception.refreshToken;

import com.slatdev.bankhelp.application.exception.AplicationNotFoundException;

public class RefreshTokenNotFoundException extends AplicationNotFoundException {
	private static final long serialVersionUID = 1L;

	public RefreshTokenNotFoundException(String message) {
		super(message);
	}

	public RefreshTokenNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
