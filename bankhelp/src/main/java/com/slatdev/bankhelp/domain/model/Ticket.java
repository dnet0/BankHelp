package com.slatdev.bankhelp.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.slatdev.bankhelp.domain.exception.InvalidTicketException;

public class Ticket {
	// Atributos
	private final UUID id;
	private final UUID userId;
	private final String description;
	private final LocalDateTime createdAt;
	private TicketStatus status;
	// Constructor:

	public Ticket(UUID userId, String description) {
		validateDescription(description);

		this.id = UUID.randomUUID();
		this.userId = userId;
		this.description = description;
		this.createdAt = LocalDateTime.now();
		this.status = TicketStatus.OPEN;
	}

	public Ticket(UUID id, UUID userId, String description, LocalDateTime createdAt, TicketStatus status) {
		validateDescription(description);
		this.id = id;
		this.userId = userId;
		this.description = description;
		this.createdAt = createdAt;
		this.status = status;
	}

	private void validateDescription(String description) {
		if (description == null || description.isBlank()) {
			throw new InvalidTicketException("La descripción no puede ser vacía");
		}
		if (description.length() > 500) {
			throw new InvalidTicketException("La descripción no puede superar los 500 caracteres");
		}
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
		if (this.status == TicketStatus.RESOLVED) {
			throw new InvalidTicketException("El ticket ya esta resuelto");
		}
		this.status = TicketStatus.RESOLVED;
	}

}
