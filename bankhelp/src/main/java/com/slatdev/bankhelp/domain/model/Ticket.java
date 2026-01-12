package com.slatdev.bankhelp.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.slatdev.bankhelp.domain.exception.rules.InvalidTicketAlreadyResolvedException;
import com.slatdev.bankhelp.domain.exception.validation.InvalidTicketDescriptionException;
import com.slatdev.bankhelp.domain.exception.validation.InvalidTicketIdException;
import com.slatdev.bankhelp.domain.exception.validation.InvalidTicketUserIdException;

public class Ticket {
	// Atributos
	private final UUID id;
	private final UUID userId;
	private final String description;
	private final LocalDateTime createdAt;
	private TicketStatus status;

	// Constructor:
	public Ticket(UUID userId, String description) {
		this(UUID.randomUUID(), userId, description, LocalDateTime.now(), TicketStatus.OPEN);
	}

	public Ticket(UUID id, UUID userId, String description, LocalDateTime createdAt, TicketStatus status) {
		validateTicket(id, userId, description, createdAt);
		this.id = id;
		this.userId = userId;
		this.description = description;
		this.createdAt = createdAt;
		this.status = status;
	}

	public void validateTicket(UUID id, UUID userId, String description, LocalDateTime createdAt) {
		validateId(id);
		validateUserId(userId);
		validateDescription(description);
	}

	private static void validateId(UUID id) {
		if (id == null) {
			throw new InvalidTicketIdException("El ID del refresh token no puede ser null");
		}
	}

	private static void validateUserId(UUID userId) {
		if (userId == null) {
			throw new InvalidTicketUserIdException("El userId del refresh token es obligatorio");
		}
	}

	private void validateDescription(String description) {
		if (description == null || description.isBlank()) {
			throw new InvalidTicketDescriptionException("La descripción no puede ser vacía");
		}
		if (description.length() > 500) {
			throw new InvalidTicketDescriptionException("La descripción no puede superar los 500 caracteres");
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
			throw new InvalidTicketAlreadyResolvedException("El ticket ya esta resuelto");
		}
		this.status = TicketStatus.RESOLVED;
	}

}
