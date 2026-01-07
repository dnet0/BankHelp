package com.slatdev.bankhelp.infrastructure.web;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.slatdev.bankhelp.application.usecase.CreateTicketUseCase;
import com.slatdev.bankhelp.application.usecase.ListTicketUseCase;
import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.domain.repository.TicketRepository;
import com.slatdev.bankhelp.infrastructure.security.JwtAuthenticationFilter;

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
	public Ticket create(@RequestParam UUID userId, @RequestParam String description) {
		log.info("[INICIO]: TicketController.create");
		return createTicketUseCase.create(userId, description);
	}
	
	@GetMapping
	public List<Ticket> getAllTickets(){
		log.info("[INICIO]: TicketController.getAllTickets");
		return listTicketUseCase.getAllTickets();
	}

	@GetMapping("/mine")
	public List<Ticket> getMyTickets(@RequestParam UUID userId){
		log.info("[INICIO]: TicketController.getAllTickets");
		return listTicketUseCase.getTicketByUserId(userId);
	}

}

