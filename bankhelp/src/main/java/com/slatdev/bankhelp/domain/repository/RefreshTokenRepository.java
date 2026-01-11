package com.slatdev.bankhelp.domain.repository;

import java.util.Optional;
import java.util.UUID;

import com.slatdev.bankhelp.domain.model.RefreshToken;

public interface RefreshTokenRepository {
	Optional<RefreshToken> findById(UUID id);

	Optional<RefreshToken> findByUserId(UUID userId);

	RefreshToken save(RefreshToken refreshToken);

	void update(RefreshToken refreshToken);

	void delete(RefreshToken refreshToken);
}
