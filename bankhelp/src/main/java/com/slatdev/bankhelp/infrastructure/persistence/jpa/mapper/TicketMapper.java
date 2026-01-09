package com.slatdev.bankhelp.infrastructure.persistence.jpa.mapper;

import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.infrastructure.persistence.jpa.TicketEntity;

public class TicketMapper {

	public static TicketEntity toEntity(Ticket ticket) {
		return new TicketEntity(ticket.getId(), ticket.getUserId(), ticket.getDescription(), ticket.getCreatedAt(),
				ticket.getStatus());
	}

	public static Ticket toDomain(TicketEntity entity) {
		return new Ticket(entity.getId(), entity.getUserId(), entity.getDescription(), entity.getCreatedAt(),
				entity.getStatus());
	}
}
