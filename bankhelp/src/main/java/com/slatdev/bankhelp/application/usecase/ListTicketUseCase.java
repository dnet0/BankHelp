package com.slatdev.bankhelp.application.usecase;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.domain.repository.TicketRepository;

public class ListTicketUseCase {
	private static final Logger log = LoggerFactory.getLogger(ListTicketUseCase.class);
	private final TicketRepository ticketRepository;

	public ListTicketUseCase(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	public List<Ticket> getAllTickets() {
		log.info("[LIST_TICKET_USE_CASE][GET_ALL_TICKETS] Inicio");
		List<Ticket> tickets = ticketRepository.getAllTickets();
		log.info("[LIST_TICKET_USE_CASE][GET_ALL_TICKETS] Fin | resultado=OK");
		return tickets;
	}

	public List<Ticket> getTicketByUserId(UUID userId) {
		log.info("[LIST_TICKET_USE_CASE][GET_TICKET_BY_USERID] Inicio");
		List<Ticket> tickets = ticketRepository.findByUserId(userId);
		log.info("[LIST_TICKET_USE_CASE][GET_TICKET_BY_USERID] Fin | userId={}", userId);
		return tickets;
	}

}
