package com.slatdev.bankhelp.application;


import java.util.List;

import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.domain.repository.TicketRepository;

public class ListTicketUseCase {

	private final TicketRepository ticketRepository;
	
	public ListTicketUseCase(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	public List<Ticket> getAllTickets(){
		return ticketRepository.getAllTickets();
	}
	
}
