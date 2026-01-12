package com.slatdev.bankhelp.application.usecase.ticket;

import java.util.List;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.slatdev.bankhelp.application.exception.InternalServerErrorException;
import com.slatdev.bankhelp.application.exception.user.UserNotFoundException;
import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.repository.TicketRepository;
import com.slatdev.bankhelp.domain.repository.UserRepository;

@Service
public class ListTicketUseCase {
	private static final Logger log = LoggerFactory.getLogger(ListTicketUseCase.class);
	private final TicketRepository ticketRepository;
	private final UserRepository userRepository;

	public ListTicketUseCase(TicketRepository ticketRepository, UserRepository userRepository) {
		this.ticketRepository = ticketRepository;
		this.userRepository = userRepository;
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
		}
	}

	public List<Ticket> getTicketByUserEmail(String userEmail) {
		log.info("[LIST_TICKET_USE_CASE][GET_TICKET_BY_USERID] Inicio");
		String emailHash = DigestUtils.sha256Hex(userEmail);
		try {
			User user = userRepository.findByEmail(userEmail).orElseThrow(() -> {
				log.warn("[LIST_TICKET_USE_CASE][GET_TICKET_BY_USERID] Usuario no reconcido, emailHash={}", emailHash);
				return new UserNotFoundException("Usuario no reconocido");
			});
			UUID userId = user.getId();
			List<Ticket> tickets = ticketRepository.findByUserId(userId);
			log.info("[LIST_TICKET_USE_CASE][GET_TICKET_BY_USERID] Fin | userId={} | total={}", userId, tickets.size());
			return tickets;
		} catch (DataAccessException ex) {
			log.error("[LIST_TICKET_USE_CASE][GET_TICKET_BY_USERID] Error de backend | emailHash={}", emailHash, ex);
			throw new InternalServerErrorException("Error interno al obtener tickets para emailHash=" + emailHash, ex);
		}
	}

}
