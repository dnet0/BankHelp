package com.slatdev.bankhelp.application.exception.user;

import com.slatdev.bankhelp.application.exception.AplicationNotFoundException;

public class UserNotFoundException extends AplicationNotFoundException {
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String message) {
		super(message);
	}

	public UserNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}
