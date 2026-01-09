package com.slatdev.bankhelp.infrastructure.web;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.slatdev.bankhelp.application.usecase.CreateTicketUseCase;
import com.slatdev.bankhelp.application.usecase.ListTicketUseCase;
import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.domain.repository.TicketRepository;
import com.slatdev.bankhelp.infrastructure.security.JwtAuthenticationFilter;
import com.slatdev.bankhelp.infrastructure.web.mapper.TicketResponseMapper;
import com.slatdev.bankhelp.infrastructure.web.request.CreateTicketRequest;
import com.slatdev.bankhelp.infrastructure.web.response.TicketResponse;

@RestController
@RequestMapping("/api/tickets")

public class TicketController {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	private CreateTicketUseCase createTicketUseCase;
	private ListTicketUseCase listTicketUseCase;

	public TicketController(TicketRepository repository) {
		this.createTicketUseCase = new CreateTicketUseCase(repository);
		this.listTicketUseCase = new ListTicketUseCase(repository);
	}

	@PostMapping
	public ResponseEntity<TicketResponse> create(@RequestBody CreateTicketRequest request) {
		log.info("[TICKET][CREATE] Inicio");
		Ticket ticket = createTicketUseCase.create(request.userId(), request.description());
		TicketResponse response = TicketResponseMapper.toResponse(ticket);
		log.info("[TICKET][CREATE] Fin | idTicket={} | userId={}", response.id(), response.userId());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping
	public List<TicketResponse> getAllTickets() {
		log.info("[TICKET][GET_ALL_TICKETS] Inicio");
		List<Ticket> tickets = listTicketUseCase.getAllTickets();
		List<TicketResponse> response = tickets.stream().map(TicketResponseMapper::toResponse).toList();
		log.info("[TICKET][GET_ALL_TICKETS] Fin | total={}", tickets.size());
		return response;
	}

	@GetMapping("/mine")
	public List<TicketResponse> getMyTickets(@RequestParam UUID userId) {
		log.info("[TICKET][GET_MY_TICKETS] Inicio | userId={}", userId);
		List<Ticket> tickets = listTicketUseCase.getTicketByUserId(userId);
		List<TicketResponse> response = tickets.stream().map(TicketResponseMapper::toResponse).toList();
		log.info("[TICKET][GET_MY_TICKETS] Fin | userId={} | total={}", userId, tickets.size());
		return response;
	}

}
