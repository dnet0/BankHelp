package com.slatdev.bankhelp.domain.model;

import java.time.Instant;
import java.util.UUID;

import com.slatdev.bankhelp.domain.exception.InvaliRefreshTokenException;

public class RefreshToken {

	private final UUID id;
	private final UUID userId;
	private final Instant expiresAt;
	private boolean revoked;

	public RefreshToken(UUID id, UUID userId, Instant expiresAt, boolean revoked) {
		this.id = id;
		this.userId = userId;
		this.expiresAt = expiresAt;
		this.revoked = revoked;
	}

	public RefreshToken(UUID userId, Instant expiresAt, boolean revoked) {
		this(UUID.randomUUID(), userId, expiresAt, revoked);
	}

	public RefreshToken(UUID userId, Instant expiresAt) {
		this(UUID.randomUUID(), userId, expiresAt, false);
	}

	public boolean isExpired() {
		return Instant.now().isAfter(this.expiresAt);
	}

	public boolean isRevoked() {
		return this.revoked;
	}

	public void revoke() {
		if (this.revoked) {
			throw new InvaliRefreshTokenException("Este refresh token ya se encuentra revocado");
		}
		this.revoked = true;
	}

	public void ensureIsValid() {
		if (isRevoked()) {
			throw new InvaliRefreshTokenException("El refresh token está revocado");
		}
		if (isExpired()) {
			throw new InvaliRefreshTokenException("El refresh token ha expirado");
		}
	}

	public boolean isActive() {
		return !isRevoked() && !isExpired();
	}

	public UUID getId() {
		return id;
	}

	public UUID getUserId() {
		return userId;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}
}
