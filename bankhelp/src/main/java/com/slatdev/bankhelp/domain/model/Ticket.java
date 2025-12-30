package com.slatdev.bankhelp.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ticket {
	// Atributos
	private final UUID id;
	private final UUID userId;
	private final String description;
	private final LocalDateTime createdAt;
	private TicketStatus status;
	// Constructor:

	public Ticket(UUID userId, String description) {
		this.id = UUID.randomUUID();
		this.userId = userId;
		this.description = description;
		this.createdAt = LocalDateTime.now();
		this.status = TicketStatus.OPEN;
	}

	public Ticket(UUID id, UUID userId, String description, LocalDateTime createdAt, TicketStatus status) {
		super();
		this.id = id;
		this.userId = userId;
		this.description = description;
		this.createdAt = createdAt;
		this.status = status;
	}

	// Metodos publicos:
	public UUID getId() {
		return id;
	}

	public UUID getUserId() {
		return userId;
	}

	public String getDescription() {
		return description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void resolve() {
		this.status = TicketStatus.RESOLVED;
	}

}
