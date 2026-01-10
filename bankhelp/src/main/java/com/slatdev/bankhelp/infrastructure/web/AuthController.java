package com.slatdev.bankhelp.infrastructure.web;

import javax.security.sasl.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slatdev.bankhelp.application.service.AuthTokenService;
import com.slatdev.bankhelp.application.service.PasswordService;
import com.slatdev.bankhelp.application.usecase.auth.AuthenticateUserUseCase;
import com.slatdev.bankhelp.application.usecase.user.RegisterUserUseCase;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.repository.UserRepository;
import com.slatdev.bankhelp.infrastructure.web.mapper.UserResponseMapper;
import com.slatdev.bankhelp.infrastructure.web.request.LoginRequest;
import com.slatdev.bankhelp.infrastructure.web.request.RegisterRequest;
import com.slatdev.bankhelp.infrastructure.web.response.LoginResponse;
import com.slatdev.bankhelp.infrastructure.web.response.RegisterResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	private final RegisterUserUseCase registerUserUseCase;
	private final AuthenticateUserUseCase authenticateUserUseCase;

	public AuthController(UserRepository userRepository, AuthTokenService authTokenService,
			PasswordService passwordService) {
		this.registerUserUseCase = new RegisterUserUseCase(userRepository, passwordService);
		this.authenticateUserUseCase = new AuthenticateUserUseCase(userRepository, authTokenService, passwordService);
	}
	
	@PostMapping("/login")
	public LoginResponse login(@Valid @RequestBody LoginRequest request) throws AuthenticationException {
		log.info("[AUTH][LOGIN] Inicio");
		String token = authenticateUserUseCase.authenticate(request.email(), request.password());
		log.info("[AUTH][LOGIN] FIN | resultado=OK");
		return new LoginResponse(token);
	}

	@PostMapping("/register")
	public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
		log.info("[AUTH][REGISTER] Inicio ");

		User user = registerUserUseCase.save(request.name(), request.email(), request.role(), request.password());
		RegisterResponse response = UserResponseMapper.toResponse(user);
		log.info("[AUTH][REGISTER] Fin | user.id={} ", response.id(), response.name(), response.email(),
				response.role());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
