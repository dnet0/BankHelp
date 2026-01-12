package com.slatdev.bankhelp.infrastructure.web;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.slatdev.bankhelp.application.usecase.ticket.CreateTicketUseCase;
import com.slatdev.bankhelp.application.usecase.ticket.ListTicketUseCase;
import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.infrastructure.security.JwtAuthenticationFilter;
import com.slatdev.bankhelp.infrastructure.security.WebSecurityConfig;
import com.slatdev.bankhelp.infrastructure.web.request.CreateTicketRequest;

@WebMvcTest(controllers = TicketController.class, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebSecurityConfig.class),
		@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = JwtAuthenticationFilter.class) })
public class TicketControllerSliceTest {
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private CreateTicketUseCase createTicketUseCase;
	@MockitoBean
	private ListTicketUseCase listTicketUseCase;

	@Test
	void createShouldRerturnOK() throws Exception {
		UUID userId = UUID.fromString("711b7895-b102-425c-b092-7c32ee0f4e9b");
		String description = "test";
		CreateTicketRequest request = new CreateTicketRequest(userId, description);
		Ticket ticket = new Ticket(userId, description);
		// Mocks
		when(createTicketUseCase.create(request.userId(), request.description())).thenReturn(ticket);

		// Act
		mockMvc.perform(post("/api/tickets").contentType(MediaType.APPLICATION_JSON).content("""
				{
					"userId": "711b7895-b102-425c-b092-7c32ee0f4e9b",
					"description": "test"
				}
				""")).andExpect(status().isCreated());

		// Verifica que los casos de uso fueron llamados correctamente
		verify(createTicketUseCase).create(request.userId(), request.description());
	}

	@Test
	void getAllTicketsShouldRerturnOK() throws Exception {
		List<Ticket> tickets = new ArrayList<Ticket>();
		// Mocks
		when(listTicketUseCase.getAllTickets()).thenReturn(tickets);

		// Act
		mockMvc.perform(get("/api/tickets").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

		// Verifica que los casos de uso fueron llamados correctamente
		verify(listTicketUseCase).getAllTickets();
	}

	@Test
	void getMyTicketsShouldReturnList() throws Exception {

		org.springframework.security.core.userdetails.User springUser = new org.springframework.security.core.userdetails.User(
				"test@gmail.com", "irrelevant", Collections.emptyList());

		Authentication auth = new UsernamePasswordAuthenticationToken(springUser, null, springUser.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(auth);

		List<Ticket> tickets = List.of(new Ticket(UUID.randomUUID(), "test"));
		
		// Mocks
		when(listTicketUseCase.getTicketByUserEmail("test@gmail.com")).thenReturn(tickets);

		// Act
		mockMvc.perform(get("/api/tickets/mine")).andExpect(status().isOk());

		// Verifica que los casos de uso fueron llamados correctamente
		verify(listTicketUseCase).getTicketByUserEmail("test@gmail.com");
	}

}
