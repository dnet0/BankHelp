package com.slatdev.bankhelp.integration.ticket;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

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

import com.slatdev.bankhelp.application.usecase.auth.AuthenticateUserUseCase;
import com.slatdev.bankhelp.application.usecase.ticket.CreateTicketUseCase;
import com.slatdev.bankhelp.application.usecase.user.RegisterUserUseCase;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.model.UserRole;
import com.slatdev.bankhelp.domain.repository.UserRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@Transactional
public class TicketIntegrationTest {
	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private CreateTicketUseCase createTicketUseCase;
	@Autowired
	private RegisterUserUseCase registerUserUseCase;
	@Autowired
	private AuthenticateUserUseCase authenticateUserUseCase;
	@Autowired
	private UserRepository userRepository;

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
	void createShouldReturnCreated() throws Exception {

		registerUserUseCase.save("test", "test@example.com", UserRole.AGENT, "Prueba1234*");
		User user = userRepository.findByEmail("test@example.com").orElseThrow();
		UUID userId = user.getId();
		String accessToken = authenticateUserUseCase.authenticate("test@example.com", "Prueba1234*");

		mockMvc.perform(post("/api/tickets").header("Authorization", "Bearer " + accessToken)
				.contentType(MediaType.APPLICATION_JSON).content("""
						    {
						    	"userId":"%s",
						        "description": "test"
						    }
						""".formatted(userId))).andExpect(status().isCreated());
	}

	@Test
	void getAllTicketsShouldRerturnOK() throws Exception {
		registerUserUseCase.save("test", "test@example.com", UserRole.AGENT, "Prueba1234*");
		String accessToken = authenticateUserUseCase.authenticate("test@example.com", "Prueba1234*");
		createTicketUseCase.create(UUID.randomUUID(), "test");

		mockMvc.perform(get("/api/tickets").header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk());
	}

	@Test
	void getMyTicketsShouldReturnList() throws Exception {
		registerUserUseCase.save("test", "test@example.com", UserRole.AGENT, "Prueba1234*");
		String accessToken = authenticateUserUseCase.authenticate("test@example.com", "Prueba1234*");
		createTicketUseCase.create(UUID.randomUUID(), "test");

		mockMvc.perform(get("/api/tickets/mine").header("Authorization", "Bearer " + accessToken))
				.andExpect(status().isOk());
	}
}
