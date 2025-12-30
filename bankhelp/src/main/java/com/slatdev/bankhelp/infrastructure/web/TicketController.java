package com.slatdev.bankhelp.infrastructure.web;

import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.slatdev.bankhelp.application.CreateTicketUseCase;
import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.domain.repository.TicketRepository;

@RestController
@RequestMapping("api/tickets")
public class TicketController {

	private CreateTicketUseCase createTicketUseCase;

	public TicketController(TicketRepository repository) {
		this.createTicketUseCase = new CreateTicketUseCase(repository);
	}
	
	@PostMapping
	public Ticket create(@RequestParam UUID userId, @RequestParam String description) {
		return createTicketUseCase.create(userId, description);
	}

}

