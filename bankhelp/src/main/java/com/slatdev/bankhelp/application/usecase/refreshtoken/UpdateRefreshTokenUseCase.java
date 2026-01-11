package com.slatdev.bankhelp.application.usecase.refreshtoken;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.slatdev.bankhelp.application.exception.InternalServerErrorException;
import com.slatdev.bankhelp.application.exception.RefreshTokenCreationException;
import com.slatdev.bankhelp.domain.model.RefreshToken;
import com.slatdev.bankhelp.domain.repository.RefreshTokenRepository;

public class UpdateRefreshTokenUseCase {
	private static final Logger log = LoggerFactory.getLogger(UpdateRefreshTokenUseCase.class);
	private final RefreshTokenRepository refreshTokenRepository;

	public UpdateRefreshTokenUseCase(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public RefreshToken updateToRevoke(RefreshToken refreshToken) {
		log.info("[UPDATE_REFRESH_TOKEN_USE_CASE][UPDATE_TO_REVOKE] Inicio");
		try {
			refreshToken.revoke();
			refreshTokenRepository.update(refreshToken);
			log.info("[UPDATE_REFRESH_TOKEN_USE_CASE][UPDATE_TO_REVOKE] Fin | resultado=OK");
			return null;
		} catch (DataAccessException exception) {
			log.error("[UPDATE_REFRESH_TOKEN_USE_CASE][UPDATE_TO_REVOKE] Error interno al revocar el refreshToken",
					exception);
			throw new InternalServerErrorException("Error interno al revocar el refreshToken", exception);

		} catch (Exception exception) {
			log.error("[UPDATE_REFRESH_TOKEN_USE_CASE][UPDATE_TO_REVOKE] Error al revocar el refreshToken", exception);
			throw new RefreshTokenCreationException("Error al revocar el refreshToken", exception);
		}
	}
}
