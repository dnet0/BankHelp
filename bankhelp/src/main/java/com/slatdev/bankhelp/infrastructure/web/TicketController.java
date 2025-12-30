package com.slatdev.bankhelp.infrastructure.web;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.slatdev.bankhelp.application.CreateTicketUseCase;
import com.slatdev.bankhelp.application.ListTicketUseCase;
import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.domain.repository.TicketRepository;

@RestController
@RequestMapping("api/tickets")
public class TicketController {

	private CreateTicketUseCase createTicketUseCase;
	private ListTicketUseCase listTicketUseCase;
	public TicketController(TicketRepository repository) {
		this.createTicketUseCase = new CreateTicketUseCase(repository);
		this.listTicketUseCase = new ListTicketUseCase(repository);
	}
	
	@PostMapping
	public Ticket create(@RequestParam UUID userId, @RequestParam String description) {
		return createTicketUseCase.create(userId, description);
	}
	@GetMapping
	public List<Ticket> getAllTickets(){
		return listTicketUseCase.getAllTickets();
	}

}

