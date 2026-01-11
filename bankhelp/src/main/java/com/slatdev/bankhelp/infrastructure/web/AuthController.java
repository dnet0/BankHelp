package com.slatdev.bankhelp.infrastructure.web;

import java.util.UUID;

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
import com.slatdev.bankhelp.application.usecase.refreshtoken.CreateRefreshTokenUseCase;
import com.slatdev.bankhelp.application.usecase.refreshtoken.ListRefreshTokenUseCase;
import com.slatdev.bankhelp.application.usecase.refreshtoken.UpdateRefreshTokenUseCase;
import com.slatdev.bankhelp.application.usecase.user.ListUserUseCase;
import com.slatdev.bankhelp.application.usecase.user.RegisterUserUseCase;
import com.slatdev.bankhelp.domain.model.RefreshToken;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.repository.RefreshTokenRepository;
import com.slatdev.bankhelp.domain.repository.UserRepository;
import com.slatdev.bankhelp.infrastructure.security.jwt.RefreshTokenProperties;
import com.slatdev.bankhelp.infrastructure.web.mapper.UserResponseMapper;
import com.slatdev.bankhelp.infrastructure.web.request.LoginRequest;
import com.slatdev.bankhelp.infrastructure.web.request.LogoutRequest;
import com.slatdev.bankhelp.infrastructure.web.request.RefreshRequest;
import com.slatdev.bankhelp.infrastructure.web.request.RegisterRequest;
import com.slatdev.bankhelp.infrastructure.web.response.LoginResponse;
import com.slatdev.bankhelp.infrastructure.web.response.RefreshResponse;
import com.slatdev.bankhelp.infrastructure.web.response.RegisterResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	private final RegisterUserUseCase registerUserUseCase;
	private final AuthenticateUserUseCase authenticateUserUseCase;
	private final CreateRefreshTokenUseCase createRefreshTokenUseCase;
	private final UpdateRefreshTokenUseCase updateRefreshTokenUseCase;
	private final ListRefreshTokenUseCase listRefreshTokenUseCase;
	private final ListUserUseCase listUserUseCase;

	public AuthController(UserRepository userRepository, AuthTokenService authTokenService,
			PasswordService passwordService, RefreshTokenRepository refreshTokenRepository,
			RefreshTokenProperties refreshTokenproperties) {
		this.registerUserUseCase = new RegisterUserUseCase(userRepository, passwordService);
		this.authenticateUserUseCase = new AuthenticateUserUseCase(userRepository, authTokenService, passwordService);
		this.createRefreshTokenUseCase = new CreateRefreshTokenUseCase(refreshTokenRepository, refreshTokenproperties);
		this.listUserUseCase = new ListUserUseCase(userRepository);
		this.listRefreshTokenUseCase = new ListRefreshTokenUseCase(refreshTokenRepository);
		this.updateRefreshTokenUseCase = new UpdateRefreshTokenUseCase(refreshTokenRepository);
	}

	@PostMapping("/login")
	public LoginResponse login(@Valid @RequestBody LoginRequest request) throws AuthenticationException {
		log.info("[AUTH][LOGIN] Inicio");
		String accessToken = authenticateUserUseCase.authenticate(request.email(), request.password());
		UUID userId = listUserUseCase.getUserByEmail(request.email()).getId();
		RefreshToken refreshToken = createRefreshTokenUseCase.create(userId);
		log.info("[AUTH][LOGIN] FIN | resultado=OK");
		return new LoginResponse(accessToken, refreshToken.getId());
	}

	@PostMapping("/logout")
	public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) throws AuthenticationException {
		log.info("[AUTH][LOGOUT] Inicio");
		RefreshToken currentRefreshToken = listRefreshTokenUseCase.getRefreshTokenValidatedById(request.refreshToken());
		updateRefreshTokenUseCase.updateToRevoke(currentRefreshToken);
		log.info("[AUTH][LOGOUT] FIN | resultado=OK");
		return ResponseEntity.status(HttpStatus.OK).body(null);
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

	@PostMapping("/refresh")
	public ResponseEntity<RefreshResponse> refresh(@Valid @RequestBody RefreshRequest request)
			throws AuthenticationException {
		log.info("[AUTH][REFRESH] Inicio ");

		RefreshToken currentRefreshToken = listRefreshTokenUseCase.getRefreshTokenValidatedById(request.refreshToken());
		User user = listUserUseCase.getUserById(currentRefreshToken.getUserId());
		updateRefreshTokenUseCase.updateToRevoke(currentRefreshToken);
		String newAccessToken = authenticateUserUseCase.generateAccessToken(user.getEmail());
		RefreshToken newRefreshToken = createRefreshTokenUseCase.create(currentRefreshToken.getUserId());
		RefreshResponse response = new RefreshResponse(newAccessToken, newRefreshToken.getId());
		log.info("[AUTH][REFRESH] FIN | resultado=OK");
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
