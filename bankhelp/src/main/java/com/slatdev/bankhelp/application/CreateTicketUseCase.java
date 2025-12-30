package com.slatdev.bankhelp.application;

import java.util.UUID;

import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.domain.repository.TicketRepository;

public class CreateTicketUseCase {

	private final TicketRepository ticketRepository;

	public CreateTicketUseCase(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	public Ticket create(UUID userId, String description) {
		Ticket newticket = new Ticket(userId, description);
		return ticketRepository.save(newticket);
	}

}
