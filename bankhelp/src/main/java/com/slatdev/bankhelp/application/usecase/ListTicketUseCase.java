package com.slatdev.bankhelp.application.usecase;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.slatdev.bankhelp.application.exception.InternalServerErrorException;
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
	    try {
	        List<Ticket> tickets = ticketRepository.getAllTickets();
	        log.info("[LIST_TICKET_USE_CASE][GET_ALL_TICKETS] Fin | resultado=OK | total={}", tickets.size());
	        return tickets;
	    } catch (DataAccessException ex) {
	        log.error("[LIST_TICKET_USE_CASE][GET_ALL_TICKETS] Error de backend", ex);
	        throw new InternalServerErrorException("Error interno al listar tickets", ex);
	    } catch (Exception ex) {
	        log.error("[LIST_TICKET_USE_CASE][GET_ALL_TICKETS] Error inesperado", ex);
	        throw new InternalServerErrorException("Error inesperado al listar tickets", ex);
	    }
	}


	public List<Ticket> getTicketByUserId(UUID userId) {
	    log.info("[LIST_TICKET_USE_CASE][GET_TICKET_BY_USERID] Inicio | userId={}", userId);
	    try {
	        List<Ticket> tickets = ticketRepository.findByUserId(userId);
	        log.info("[LIST_TICKET_USE_CASE][GET_TICKET_BY_USERID] Fin | userId={} | total={}", userId, tickets.size());
	        return tickets;
	    } catch (DataAccessException ex) {
	        log.error("[LIST_TICKET_USE_CASE][GET_TICKET_BY_USERID] Error de backend | userId={}", userId, ex);
	        throw new InternalServerErrorException("Error interno al obtener tickets para userId=" + userId, ex);
	    } catch (Exception ex) {
	        log.error("[LIST_TICKET_USE_CASE][GET_TICKET_BY_USERID] Error inesperado | userId={}", userId, ex);
	        throw new InternalServerErrorException("Error inesperado al obtener tickets para userId=" + userId, ex);
	    }
	}


}
