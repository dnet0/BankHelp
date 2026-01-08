package com.slatdev.bankhelp.application.usecase;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slatdev.bankhelp.application.service.AuthTokenService;
import com.slatdev.bankhelp.application.service.PasswordService;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.repository.UserRepository;

public class AuthenticateUserUseCase {
	private static final Logger log = LoggerFactory.getLogger(AuthenticateUserUseCase.class);
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
		log.info("[AUTHENTICATE_USER_USE_CASE][AUTHENTICATE] Inicio");
		User user = userRepository.findByEmail(email).orElseThrow(() -> {
			String emailHash = DigestUtils.sha256Hex(email);
			log.info("[AUTHENTICATE_USER_USE_CASE][AUTHENTICATE] Usuario no reconcido, emailHash={}", emailHash);
			return new IllegalArgumentException("Usuario no reconocido");
		});
		if (!passwordService.checkPassword(user, password)) {
			String emailHash = DigestUtils.sha256Hex(email);
			log.warn("[AUTHENTICATE_USER_USE_CASE][AUTHENTICATE] Contraseña introducia incorrecta, emailHash={}",
					emailHash);
			throw new IllegalArgumentException("Validación fallida");
		}
		String token = authTokenService.generateToken(user);
		log.info("[AUTHENTICATE_USER_USE_CASE][AUTHENTICATE] Fin | resultado=OK");
		return token;
	}

}
