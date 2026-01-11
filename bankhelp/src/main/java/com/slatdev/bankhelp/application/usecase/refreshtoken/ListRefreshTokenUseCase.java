package com.slatdev.bankhelp.application.usecase.refreshtoken;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.slatdev.bankhelp.application.exception.InternalServerErrorException;
import com.slatdev.bankhelp.application.exception.InvalidRefreshTokenException;
import com.slatdev.bankhelp.domain.model.RefreshToken;
import com.slatdev.bankhelp.domain.repository.RefreshTokenRepository;

@Service
public class ListRefreshTokenUseCase {
	private static final Logger log = LoggerFactory.getLogger(ListRefreshTokenUseCase.class);
	private final RefreshTokenRepository refreshTokenRepository;

	public ListRefreshTokenUseCase(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public RefreshToken getRefreshTokenById(UUID id) {
		log.info("[LIST_REFRESHTOKEN_USE_CASE][GET_REFRESHTOKEN_BY_ID] Inicio");
		try {
			RefreshToken refreshToken = refreshTokenRepository.findById(id).orElseThrow(() -> {
				log.warn("[LIST_REFRESHTOKEN_USE_CASE][GET_REFRESHTOKEN_BY_ID] Intento de busquedad fallido");
				throw new InvalidRefreshTokenException("No se ha encontrado el refreshToken");
			});
			log.info("[LIST_REFRESHTOKEN_USE_CASE][GET_REFRESHTOKEN_BY_ID] Fin");
			return refreshToken;
		} catch (DataAccessException ex) {
			log.error(
					"[LIST_REFRESHTOKEN_USE_CASE][GET_REFRESHTOKEN_BY_ID] Error inesperado al obtener el refreshToken",
					ex);
			throw new InternalServerErrorException("Error inesperado al obtener el refreshToken", ex);
		}
	}

	public RefreshToken getRefreshTokenValidatedById(UUID id) {
		log.info("[LIST_REFRESHTOKEN_USE_CASE][GET_REFRESHTOKEN_VALIDATED_BY_ID] Inicio");
		try {
			RefreshToken refreshToken = refreshTokenRepository.findById(id).orElseThrow(() -> {
				log.warn("[LIST_REFRESHTOKEN_USE_CASE][GET_REFRESHTOKEN_VALIDATED_BY_ID] Intento de busquedad fallido");
				throw new InvalidRefreshTokenException("No se ha encontrado el refreshToken");
			});
			if (!refreshToken.isActive()) {
				throw new InvalidRefreshTokenException("El refresh token ya está expirado o revocado");
			}
			log.info("[LIST_REFRESHTOKEN_USE_CASE][GET_REFRESHTOKEN_VALIDATED_BY_ID] Fin");
			return refreshToken;
		} catch (DataAccessException ex) {
			log.error(
					"[LIST_REFRESHTOKEN_USE_CASE][GET_REFRESHTOKEN_VALIDATED_BY_ID] Error inesperado al obtener el refreshToken",
					ex);
			throw new InternalServerErrorException("Error inesperado al obtener el refreshToken", ex);
		}
	}
}
