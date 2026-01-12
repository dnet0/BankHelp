package com.slatdev.bankhelp.application.usecase.refreshtoken;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.slatdev.bankhelp.application.config.RefreshTokenProperties;
import com.slatdev.bankhelp.application.exception.InternalServerErrorException;
import com.slatdev.bankhelp.application.exception.refreshToken.RefreshTokenCreationException;
import com.slatdev.bankhelp.domain.model.RefreshToken;
import com.slatdev.bankhelp.domain.repository.RefreshTokenRepository;

@Service
public class CreateRefreshTokenUseCase {
	private static final Logger log = LoggerFactory.getLogger(CreateRefreshTokenUseCase.class);
	private final RefreshTokenRepository refreshTokenRepository;
	private final RefreshTokenProperties properties;

	public CreateRefreshTokenUseCase(RefreshTokenRepository refreshTokenRepository, RefreshTokenProperties properties) {
		this.refreshTokenRepository = refreshTokenRepository;
		this.properties = properties;
	}

	public RefreshToken create(UUID userId) {
		log.info("[CREATE_REFRESH_TOKEN_USE_CASE][CREATE] Inicio");
		try {
			Instant expires = Instant.now().plus(properties.getExpirationDays(), ChronoUnit.DAYS);
			RefreshToken newRefreshToken = refreshTokenRepository.save(new RefreshToken(userId, expires));
			log.info("[CREATE_REFRESH_TOKEN_USE_CASE][CREATE] Fin | userId={} | resultado=OK", userId);
			return newRefreshToken;
		} catch (DataAccessException exception) {
			log.error("[CREATE_REFRESH_TOKEN_USE_CASE][CREATE] Error interno al crear el refreshToken", exception);
			throw new InternalServerErrorException("Error interno al crear el refreshToken", exception);

		} catch (Exception exception) {
			log.error("[CREATE_REFRESH_TOKEN_USE_CASE][CREATE] Error al crear el refreshToken", exception);
			throw new RefreshTokenCreationException("Error al crear el refreshToken", exception);
		}
	}
}
