package com.slatdev.bankhelp.integration.auth;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.slatdev.bankhelp.application.usecase.refreshtoken.CreateRefreshTokenUseCase;
import com.slatdev.bankhelp.application.usecase.user.ListUserUseCase;
import com.slatdev.bankhelp.application.usecase.user.RegisterUserUseCase;
import com.slatdev.bankhelp.domain.model.RefreshToken;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.model.UserRole;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
class AuthIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private RegisterUserUseCase registerUserUseCase;

	@Autowired
	private CreateRefreshTokenUseCase createRefreshTokenUseCase;

	@Autowired
	private ListUserUseCase listUserUseCase;

	@Container
	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15").withDatabaseName("bankhelp_test")
			.withUsername("test").withPassword("test");

	@DynamicPropertySource
	static void configure(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

	@Test
	void loginShouldReturnAccessAndRefreshToken() throws Exception {
		// Arrange: crear usuario real en BD
		registerUserUseCase.save("test", "test@example.com", UserRole.CLIENT, "Prueba1234*");

		mockMvc.perform(post("/api/auth/login").contentType(MediaType.APPLICATION_JSON).content("""
				{
				    "email": "test@example.com",
				    "password": "Prueba1234*"
				}
				""")).andExpect(status().isOk()).andExpect(jsonPath("$.accessToken").exists())
				.andExpect(jsonPath("$.refreshToken").exists());
	}

	@Test
	void logoutShouldReturnOk() throws Exception {
		// Arrange: crear usuario real en BD
		registerUserUseCase.save("test", "test@example.com", UserRole.CLIENT, "Prueba1234*");
		User user = listUserUseCase.getUserByEmail("test@example.com");
		RefreshToken newRefreshToken = createRefreshTokenUseCase.create(user.getId());
		String body = "{\"refreshToken\": \"" + newRefreshToken.getId().toString() + "\"}";
		mockMvc.perform(post("/api/auth/logout").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isOk());
	}

	@Test
	void registerShouldReturnCreated() throws Exception {
		// Arrange: crear usuario real en BD
		mockMvc.perform(post("/api/auth/register").contentType(MediaType.APPLICATION_JSON).content("""
				{
					"name":"test",
					"email":"test@gmail.com",
					"role":"CLIENT",
					"password":"Test1234*"
				}
				""")).andExpect(status().isCreated());
	}

	@Test
	void refreshShouldReturnOk() throws Exception {
		// Arrange: crear usuario real en BD
		registerUserUseCase.save("test", "test@example.com", UserRole.CLIENT, "Prueba1234*");
		User user = listUserUseCase.getUserByEmail("test@example.com");
		RefreshToken newRefreshToken = createRefreshTokenUseCase.create(user.getId());

		String body = "{\"refreshToken\": \"" + newRefreshToken.getId().toString() + "\"}";

		mockMvc.perform(post("/api/auth/refresh").contentType(MediaType.APPLICATION_JSON).content(body))
				.andExpect(status().isOk());
	}
}
