package com.slatdev.bankhelp.domain.model;

import java.time.Instant;
import java.util.UUID;

import com.slatdev.bankhelp.domain.exception.rules.InvalidRefreshTokenIsNotValidException;
import com.slatdev.bankhelp.domain.exception.rules.InvalidRefreshTokenIsRevokedException;
import com.slatdev.bankhelp.domain.exception.validation.InvalidRefreshTokenExpiresAtException;
import com.slatdev.bankhelp.domain.exception.validation.InvalidRefreshTokenIdException;
import com.slatdev.bankhelp.domain.exception.validation.InvalidRefreshTokenUserIdException;

public class RefreshToken {

	private final UUID id;
	private final UUID userId;
	private final Instant expiresAt;
	private boolean revoked;

	public RefreshToken(UUID id, UUID userId, Instant expiresAt, boolean revoked) {
		validateRefreshToken(id, userId, expiresAt);
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

	private static void validateRefreshToken(UUID id, UUID userId, Instant expiresAt) {
		validateId(id);
		validateUserId(userId);
		validateExpiresAt(expiresAt);
	}

	private static void validateId(UUID id) {
		if (id == null) {
			throw new InvalidRefreshTokenIdException("El ID del refresh token no puede ser null");
		}
	}

	private static void validateUserId(UUID userId) {
		if (userId == null) {
			throw new InvalidRefreshTokenUserIdException("El userId del refresh token es obligatorio");
		}
	}

	private static void validateExpiresAt(Instant expiresAt) {
		if (expiresAt == null) {
			throw new InvalidRefreshTokenExpiresAtException("La fecha de expiración es obligatoria");
		}
	}

	public boolean isExpired() {
		return Instant.now().isAfter(this.expiresAt);
	}

	public boolean isRevoked() {
		return this.revoked;
	}

	public void revoke() {
		if (this.revoked) {
			throw new InvalidRefreshTokenIsRevokedException("Este refresh token ya se encuentra revocado");
		}
		this.revoked = true;
	}

	public void ensureIsValid() {
		if (isRevoked()) {
			throw new InvalidRefreshTokenIsNotValidException("El refresh token está revocado");
		}
		if (isExpired()) {
			throw new InvalidRefreshTokenIsNotValidException("El refresh token ha expirado");
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
