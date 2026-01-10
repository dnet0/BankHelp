package com.slatdev.bankhelp.infrastructure.web;

import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slatdev.bankhelp.application.usecase.CreateTicketUseCase;
import com.slatdev.bankhelp.application.usecase.ListTicketUseCase;
import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.domain.repository.TicketRepository;
import com.slatdev.bankhelp.domain.repository.UserRepository;
import com.slatdev.bankhelp.infrastructure.security.JwtAuthenticationFilter;
import com.slatdev.bankhelp.infrastructure.web.mapper.TicketResponseMapper;
import com.slatdev.bankhelp.infrastructure.web.request.CreateTicketRequest;
import com.slatdev.bankhelp.infrastructure.web.response.TicketResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tickets")
@Validated
public class TicketController {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	private CreateTicketUseCase createTicketUseCase;
	private ListTicketUseCase listTicketUseCase;

	public TicketController(TicketRepository repository, UserRepository userRepository) {
		this.createTicketUseCase = new CreateTicketUseCase(repository);
		this.listTicketUseCase = new ListTicketUseCase(repository, userRepository);
	}

	@PostMapping
	public ResponseEntity<TicketResponse> create(@Valid @RequestBody CreateTicketRequest request) {
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
	public List<TicketResponse> getMyTickets() {
		log.info("[TICKET][GET_MY_TICKETS] Inicio");
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String email = user.getUsername();
		List<Ticket> tickets = listTicketUseCase.getTicketByUserEmail(email);
		List<TicketResponse> response = tickets.stream().map(TicketResponseMapper::toResponse).toList();
		String emailHash = DigestUtils.sha256Hex(email);
		log.info("[TICKET][GET_MY_TICKETS] Fin | emailHash={} |total={}", emailHash, tickets.size());
		return response;
	}

}
