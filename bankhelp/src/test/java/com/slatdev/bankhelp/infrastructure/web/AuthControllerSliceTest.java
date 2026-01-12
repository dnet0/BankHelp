package com.slatdev.bankhelp.infrastructure.web;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.slatdev.bankhelp.application.usecase.auth.AuthenticateUserUseCase;
import com.slatdev.bankhelp.application.usecase.refreshtoken.CreateRefreshTokenUseCase;
import com.slatdev.bankhelp.application.usecase.refreshtoken.ListRefreshTokenUseCase;
import com.slatdev.bankhelp.application.usecase.refreshtoken.UpdateRefreshTokenUseCase;
import com.slatdev.bankhelp.application.usecase.user.ListUserUseCase;
import com.slatdev.bankhelp.application.usecase.user.RegisterUserUseCase;
import com.slatdev.bankhelp.domain.model.RefreshToken;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.model.UserRole;
import com.slatdev.bankhelp.infrastructure.security.JwtAuthenticationFilter;
import com.slatdev.bankhelp.infrastructure.security.WebSecurityConfig;
import com.slatdev.bankhelp.infrastructure.web.request.RefreshRequest;
import com.slatdev.bankhelp.infrastructure.web.request.RegisterRequest;

@WebMvcTest(controllers = AuthController.class, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class) })
public class AuthControllerSliceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private RegisterUserUseCase registerUserUseCase;
	@MockitoBean
	private AuthenticateUserUseCase authenticateUserUseCase;
	@MockitoBean
	private CreateRefreshTokenUseCase createRefreshTokenUseCase;
	@MockitoBean
	private UpdateRefreshTokenUseCase updateRefreshTokenUseCase;
	@MockitoBean
	private ListRefreshTokenUseCase listRefreshTokenUseCase;
	@MockitoBean
	private ListUserUseCase listUserUseCase;

	@Test
	void loginShouldRerturnToken() throws Exception {
		String name = "test";
		String email = "test@example.com";
		String password = "Prueba1234*";

		UUID userId = UUID.randomUUID();
		UUID refreshTokenId = UUID.randomUUID();
		User user = new User(userId, name, email, UserRole.CLIENT, "hashedpass", LocalDateTime.now());

		// Mocks
		when(authenticateUserUseCase.authenticate(email, password)).thenReturn("ACCESS_TOKEN");
		when(listUserUseCase.getUserByEmail(email)).thenReturn(user);
		when(createRefreshTokenUseCase.create(user.getId()))
				.thenReturn(new RefreshToken(refreshTokenId, userId, Instant.now(), false));

		// Act
		mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("""
				{
					"email": "test@example.com",
					"password" : "Prueba1234*"
				}
				""")).andExpect(status().isOk()).andExpect(jsonPath("$.accessToken").value("ACCESS_TOKEN"))
				.andExpect(jsonPath("$.refreshToken").value(refreshTokenId.toString()));

		// Verifica que los casos de uso fueron llamados correctamente
		verify(authenticateUserUseCase).authenticate(email, password);
		verify(listUserUseCase).getUserByEmail(email);
		verify(createRefreshTokenUseCase).create(userId);
	}

	@Test
	void logoutShouldRerturnOK() throws Exception {
		UUID refreshToken = UUID.fromString("83435065-4f30-4941-ad9d-dc8f497f3574");
		UUID userId = UUID.randomUUID();
		Instant expiresAt = Instant.now();
		RefreshToken currentRefreshToken = new RefreshToken(refreshToken, userId, expiresAt, false);
		// Mocks
		when(listRefreshTokenUseCase.getRefreshTokenValidatedById(refreshToken)).thenReturn(currentRefreshToken);

		// Act
		mockMvc.perform(post("/api/auth/logout").contentType(MediaType.APPLICATION_JSON).content("""
				{
					"refreshToken": "83435065-4f30-4941-ad9d-dc8f497f3574"
				}
				""")).andExpect(status().isOk());

		// Verifica que los casos de uso fueron llamados correctamente
		verify(listRefreshTokenUseCase).getRefreshTokenValidatedById(refreshToken);
	}

	@Test
	void registerShouldRerturnCreated() throws Exception {
		RegisterRequest request = new RegisterRequest("test", "test@gmail.com", UserRole.CLIENT, "Test1234*");
		User user = new User(request.name(), request.email(), request.role(), request.password());
		// Mocks
		when(registerUserUseCase.save(request.name(), request.email(), request.role(), request.password()))
				.thenReturn(user);

		// Act
		mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
				{
					"name":"test",
					"email":"test@gmail.com",
					"role":"CLIENT",
					"password":"Test1234*"
				}
				""")).andExpect(status().isCreated());
		// Verifica que los casos de uso fueron llamados correctamente
		verify(registerUserUseCase).save(request.name(), request.email(), request.role(), request.password());
	}

	@Test
	void refreshShouldRerturnOk() throws Exception {
		UUID refreshTokenId = UUID.fromString("711b7895-b102-425c-b092-7c32ee0f4e9b");
		UUID userID = UUID.randomUUID();
		Instant expiresAt = Instant.now();
		RefreshRequest request = new RefreshRequest(refreshTokenId);
		RefreshToken currentRefreshToken = new RefreshToken(refreshTokenId, userID, expiresAt, false);
		User user = new User(userID, "test", "test@gmail.com", UserRole.CLIENT, "Test1234*", LocalDateTime.now());
		String newAccessToken = "ACCES_TOKEN";
		RefreshToken newRefreshToken = new RefreshToken(UUID.fromString("83435065-4f30-4941-ad9d-dc8f497f3574"), userID,
				expiresAt, false);
		// Mocks
		when(listRefreshTokenUseCase.getRefreshTokenValidatedById(request.refreshToken()))
				.thenReturn(currentRefreshToken);
		when(listUserUseCase.getUserById(currentRefreshToken.getUserId())).thenReturn(user);
		when(authenticateUserUseCase.generateAccessToken(user.getEmail())).thenReturn(newAccessToken);
		when(createRefreshTokenUseCase.create(currentRefreshToken.getUserId())).thenReturn(newRefreshToken);
		// Act
		mockMvc.perform(post("/api/auth/refresh").contentType(MediaType.APPLICATION_JSON).content("""
				{
					"refreshToken":"711b7895-b102-425c-b092-7c32ee0f4e9b"
				}
				""")).andExpect(status().isOk());
		// Verifica que los casos de uso fueron llamados correctamente
		verify(listRefreshTokenUseCase).getRefreshTokenValidatedById(request.refreshToken());
		verify(listUserUseCase).getUserById(currentRefreshToken.getUserId());
		verify(authenticateUserUseCase).generateAccessToken(user.getEmail());
		verify(createRefreshTokenUseCase).create(currentRefreshToken.getUserId());

	}
}
