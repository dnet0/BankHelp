package com.slatdev.bankhelp.application.usecase;


import java.util.List;
import java.util.UUID;

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
	public List<Ticket> getTicketByUserId(UUID userId){
		return ticketRepository.findByUserId(userId);
	}
	
}
