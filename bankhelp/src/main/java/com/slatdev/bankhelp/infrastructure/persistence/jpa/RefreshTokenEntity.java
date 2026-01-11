package com.slatdev.bankhelp.infrastructure.persistence.jpa;

import java.time.Instant;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "refreshToken")
public class RefreshTokenEntity {
	@Id
	private UUID id;
	private UUID userId;
	private Instant expiresAt;
	private boolean revoked;

	protected RefreshTokenEntity() {
	}

	public RefreshTokenEntity(UUID id, UUID userId, Instant expiresAt, boolean revoked) {
		this.id = id;
		this.userId = userId;
		this.expiresAt = expiresAt;
		this.revoked = revoked;
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

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}

	public boolean isRevoked() {
		return revoked;
	}

	public void setRevoked(boolean revoked) {
		this.revoked = revoked;
	}
}
