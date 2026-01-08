package com.slatdev.bankhelp.infrastructure.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.slatdev.bankhelp.application.service.AuthTokenService;
import com.slatdev.bankhelp.application.service.PasswordService;
import com.slatdev.bankhelp.application.usecase.AuthenticateUserUseCase;
import com.slatdev.bankhelp.application.usecase.RegisterUserUseCase;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.repository.UserRepository;
import com.slatdev.bankhelp.infrastructure.web.request.RegisterRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	private final RegisterUserUseCase registerUserUseCase;
	private final AuthenticateUserUseCase authenticateUserUseCase;

	public AuthController(UserRepository userRepository, AuthTokenService authTokenService,
			PasswordService passwordService) {
		this.authenticateUserUseCase = new AuthenticateUserUseCase(userRepository, authTokenService, passwordService);
		this.registerUserUseCase = new RegisterUserUseCase(userRepository, passwordService);
	}

	@PostMapping("/login")
	public String login(@RequestParam String email, @RequestParam String password) {
		log.info("[AUTH][LOGIN] Inicio");
		String token = authenticateUserUseCase.authenticate(email, password);
		log.info("[AUTH][LOGIN] FIN | resultado=OK");
		return token;
	}

	@PostMapping("/register")
	public User register(@RequestBody RegisterRequest registerRequest) {
		log.info("[AUTH][REGISTER] Inicio ");

		User user = registerUserUseCase.save(registerRequest.name(), registerRequest.email(), registerRequest.role(),
				registerRequest.password());
		log.info("[AUTH][REGISTER] Fin | user.id={} ", user.getId(), user.getName(), user.getEmail(), user.getRole());
		return user;
	}

}
