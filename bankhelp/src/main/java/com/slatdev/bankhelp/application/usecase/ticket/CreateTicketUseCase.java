package com.slatdev.bankhelp.application.usecase.ticket;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.slatdev.bankhelp.application.exception.InternalServerErrorException;
import com.slatdev.bankhelp.application.exception.TicketCreationException;
import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.domain.repository.TicketRepository;

@Service
public class CreateTicketUseCase {
	private static final Logger log = LoggerFactory.getLogger(CreateTicketUseCase.class);
	private final TicketRepository ticketRepository;

	public CreateTicketUseCase(TicketRepository ticketRepository) {
		this.ticketRepository = ticketRepository;
	}

	public Ticket create(UUID userId, String description) {
		log.info("[CREATE_TICKET_USE_CASE][CREATE] Inicio | userId={}", userId);
		Ticket newTicket = new Ticket(userId, description);
		Ticket savedTicket;
		try {
			savedTicket = ticketRepository.save(newTicket);
			log.info("[CREATE_TICKET_USE_CASE][CREATE] Fin | idTicket={} | userId={}", savedTicket.getId(),
					savedTicket.getUserId());
			return savedTicket;
		} catch (DataAccessException exception) {
			log.error("[CREATE_TICKET_USE_CASE][CREATE] Error interno al crear ticket | userId={}", userId, exception);
			throw new InternalServerErrorException("Error interno al crear ticket para userId=" + userId, exception);
		} catch (Exception exception) {
			log.error("[CREATE_TICKET_USE_CASE][CREATE] Error al crear ticket | userId={}", userId, exception);
			throw new TicketCreationException("Error al crear ticket para userId=" + userId, exception);
		}
	}

}
