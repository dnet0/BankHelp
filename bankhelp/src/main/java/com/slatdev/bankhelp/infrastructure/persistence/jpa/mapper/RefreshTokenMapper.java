package com.slatdev.bankhelp.infrastructure.persistence.jpa.mapper;

import com.slatdev.bankhelp.domain.model.RefreshToken;
import com.slatdev.bankhelp.infrastructure.persistence.jpa.RefreshTokenEntity;

public class RefreshTokenMapper {

	public static RefreshToken toDomain(RefreshTokenEntity entity) {
		return new RefreshToken(entity.getId(), entity.getUserId(), entity.getExpiresAt(), entity.isRevoked());
	}

	public static RefreshTokenEntity toEntity(RefreshToken domain) {
		return new RefreshTokenEntity(domain.getId(), domain.getUserId(), domain.getExpiresAt(), domain.isRevoked());
	}
}
