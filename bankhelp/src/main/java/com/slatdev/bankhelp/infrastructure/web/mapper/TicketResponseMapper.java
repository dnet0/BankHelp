package com.slatdev.bankhelp.infrastructure.web.mapper;

import com.slatdev.bankhelp.domain.model.Ticket;
import com.slatdev.bankhelp.infrastructure.web.response.TicketResponse;

public class TicketResponseMapper {
	public static TicketResponse toResponse(Ticket entity) {
		return new TicketResponse(entity.getId(), entity.getUserId(), entity.getDescription(), entity.getCreatedAt(),
				entity.getStatus());
	}

	public static Ticket toEntiy(TicketResponse response) {
		return new Ticket(response.id(), response.userId(), response.description(), response.createdAt(),
				response.status());
	}
}
