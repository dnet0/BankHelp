package com.slatdev.bankhelp.domain.repository;

import java.util.List;
import java.util.UUID;

import com.slatdev.bankhelp.domain.model.Ticket;

public interface TicketRepository {

	Ticket save(Ticket ticket);

	List<Ticket> getAllTickets();

	List<Ticket> findByUserId(UUID userId);

}
