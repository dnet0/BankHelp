package com.slatdev.bankhelp.application.service;

import com.slatdev.bankhelp.domain.model.User;

public interface AuthTokenService {
	String generateToken(User user);

	boolean isTokenValid(String token);

	String extractUserName(String token);
}
