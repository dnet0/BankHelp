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
