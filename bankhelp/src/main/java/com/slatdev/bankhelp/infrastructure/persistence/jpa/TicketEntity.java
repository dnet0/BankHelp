package com.slatdev.bankhelp.infrastructure.persistence.jpa;

import java.time.LocalDateTime;
import java.util.UUID;

import com.slatdev.bankhelp.domain.model.TicketStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class TicketEntity {
	@Id
	private UUID id;
	private UUID userId;
	private String description;
	private LocalDateTime createdAt;
	@Enumerated(EnumType.STRING)
	private TicketStatus status;

	protected TicketEntity() {
	}

	public TicketEntity(UUID id, UUID userId, String description, LocalDateTime createdAt, TicketStatus status) {
		this.id = id;
		this.userId = userId;
		this.description = description;
		this.createdAt = createdAt;
		this.status = status;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getUserId() {
		return userId;
	}

	public void setUserId(UUID userId) {
		this.userId = userId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

}
