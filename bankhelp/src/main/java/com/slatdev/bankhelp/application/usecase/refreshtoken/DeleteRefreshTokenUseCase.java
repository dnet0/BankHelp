package com.slatdev.bankhelp.application.usecase.refreshtoken;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.slatdev.bankhelp.application.exception.InternalServerErrorException;
import com.slatdev.bankhelp.application.exception.refreshToken.RefreshTokenNotFoundException;
import com.slatdev.bankhelp.domain.model.RefreshToken;
import com.slatdev.bankhelp.domain.repository.RefreshTokenRepository;

public class DeleteRefreshTokenUseCase {
	private static final Logger log = LoggerFactory.getLogger(DeleteRefreshTokenUseCase.class);
	private final RefreshTokenRepository refreshTokenRepository;

	public DeleteRefreshTokenUseCase(RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
	}

	public void deleteById(UUID id) {
		log.info("[DELETE_REFRESH_TOKEN_USE_CASE][DELETE_BY_USERID] Inicio | id={}", id);
		try {
			RefreshToken refresToken = refreshTokenRepository.findByUserId(id).orElseThrow(() -> {
				log.warn("[DELETE_REFRESH_TOKEN_USE_CASE][DELETE_BY_ID] Usuario no reconcido, userId={}", id);
				return new RefreshTokenNotFoundException("RefreshToken no reconocido");
			});
			refreshTokenRepository.delete(refresToken);
		} catch (DataAccessException exception) {
			log.error("[DELETE_REFRESH_TOKEN_USE_CASE][DELETE_BY_ID] Error interno al eliminar el refreshToken | id={}", id,
					exception);
			throw new InternalServerErrorException("Error interno al eliminar el refreshToken para id=" + id,
					exception);
		}
		log.info("[DELETE_REFRESH_TOKEN_USE_CASE][DELETE_BY_ID] Fin | userId={} | resultado=OK", id);
	}

	public void deleteByUserId(UUID userId) {
		log.info("[DELETE_REFRESH_TOKEN_USE_CASE][DELETE_BY_USERID] Inicio | userId={}", userId);
		try {
			RefreshToken refresToken = refreshTokenRepository.findByUserId(userId).orElseThrow(() -> {
				log.warn("[DELETE_REFRESH_TOKEN_USE_CASE][DELETE_BY_USERID] Usuario no reconcido, userId={}", userId);
				return new RefreshTokenNotFoundException("RefresToken no reconocido");
			});
			refreshTokenRepository.delete(refresToken);
		} catch (DataAccessException exception) {
			log.error("[DELETE_REFRESH_TOKEN_USE_CASE][DELETE_BY_USERID] Error interno al eliminar el refreshToken | id={}",
					userId, exception);
			throw new InternalServerErrorException("Error interno al eliminar el refreshToken para userId=" + userId,
					exception);
		}
		log.info("[DELETE_REFRESH_TOKEN_USE_CASE][DELETE_BY_USERID] Fin | userId={} | resultado=OK", userId);
	}
}
