package com.slatdev.bankhelp.infrastructure.web.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.slatdev.bankhelp.domain.model.TicketStatus;

public record TicketResponse(UUID id, UUID userId, String description, LocalDateTime createdAt, TicketStatus status) {
}
