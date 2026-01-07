package com.slatdev.bankhelp.application.usecase;

import com.slatdev.bankhelp.application.service.AuthTokenService;
import com.slatdev.bankhelp.application.service.PasswordService;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.repository.UserRepository;

public class AuthenticateUserUseCase {

	private final UserRepository userRepository;
	private final AuthTokenService authTokenService;
	private final PasswordService passwordService;

	public AuthenticateUserUseCase(UserRepository userRepository, AuthTokenService authTokenService,
			PasswordService passwordService) {
		this.userRepository = userRepository;
		this.authTokenService = authTokenService;
		this.passwordService = passwordService;
	}

	public String authenticate(String email, String password) {
		User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no reconocido"));
		if (!passwordService.checkPassword(user, password)) {
			throw new IllegalArgumentException("Validación fallida");
		}
		return authTokenService.generateToken(user);
	}

}
