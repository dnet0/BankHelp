package com.slatdev.bankhelp.infrastructure.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.slatdev.bankhelp.application.CreateTicketUseCase;
import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.domain.repository.TicketRepository;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("api/tickets")
public class TicketController {

	private CreateTicketUseCase createTicketUseCase;
	private TicketRepository ticketRepository;

	@PostConstruct
	public void init() {
		this.ticketRepository = new InMemoryTicketRepository();
		this.createTicketUseCase = new CreateTicketUseCase(this.ticketRepository);
	}
	
	@PostMapping
	public Ticket create(@RequestParam UUID userId, @RequestParam String description) {
		return createTicketUseCase.create(userId, description);
	}

}

class InMemoryTicketRepository implements TicketRepository {
	private final Map<UUID, Ticket> db = new HashMap<UUID, Ticket>();

	@Override
	public Ticket save(Ticket ticket) {
		db.put(ticket.getId(), ticket);
		return ticket;
	}

	@Override
	public List<Ticket> findByUserId(UUID userId) {

		return db.values().stream()
				.filter(t -> t.getUserId().equals(userId))
				.toList();
	}

}
