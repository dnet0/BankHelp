package com.slatdev.bankhelp.application.usecase.auth;

import javax.security.sasl.AuthenticationException;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.slatdev.bankhelp.application.service.AuthTokenService;
import com.slatdev.bankhelp.application.service.PasswordService;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.repository.UserRepository;

@Service
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

	public String authenticate(String email, String password) throws AuthenticationException {
		log.info("[AUTHENTICATE_USER_USE_CASE][AUTHENTICATE] Inicio");
		User user = userRepository.findByEmail(email).orElseThrow(() -> {
			String emailHash = DigestUtils.sha256Hex(email);
			log.info("[AUTHENTICATE_USER_USE_CASE][AUTHENTICATE] Usuario no reconcido, emailHash={}", emailHash);
			return new AuthenticationException("Credenciales invalidas");
		});
		if (!passwordService.checkPassword(user, password)) {
			String emailHash = DigestUtils.sha256Hex(email);
			log.warn("[AUTHENTICATE_USER_USE_CASE][AUTHENTICATE] Contraseña introducida incorrecta, emailHash={}",
					emailHash);
			throw new AuthenticationException("Credenciales invalidas");
		}
		String token = authTokenService.generateToken(user);
		log.info("[AUTHENTICATE_USER_USE_CASE][AUTHENTICATE] Fin | resultado=OK");
		return token;
	}
	public String generateAccessToken(String email) throws AuthenticationException {
		log.info("[AUTHENTICATE_USER_USE_CASE][UPDATE_TOKEN] Inicio");
		User user = userRepository.findByEmail(email).orElseThrow(() -> {
			String emailHash = DigestUtils.sha256Hex(email);
			log.info("[AUTHENTICATE_USER_USE_CASE][UPDATE_TOKEN] Usuario no reconcido, emailHash={}", emailHash);
			return new AuthenticationException("Credenciales invalidas");
		});

		String token = authTokenService.generateToken(user);
		log.info("[AUTHENTICATE_USER_USE_CASE][UPDATE_TOKEN] Fin | resultado=OK");
		return token;
	}

}
