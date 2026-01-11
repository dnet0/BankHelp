package com.slatdev.bankhelp.application.usecase.user;

import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.slatdev.bankhelp.application.exception.InternalServerErrorException;
import com.slatdev.bankhelp.application.exception.UserCreationException;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.repository.UserRepository;

@Service
public class ListUserUseCase {
	private static final Logger log = LoggerFactory.getLogger(RegisterUserUseCase.class);
	private final UserRepository userRepository;

	public ListUserUseCase(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User getUserById(UUID id) {
		log.info("[LIST_USER_USE_CASE][GET_USER_BY_ID] Inicio");
		try {
			User user = userRepository.findById(id).orElseThrow(() -> {
				log.warn("[LIST_USER_USE_CASE][GET_USER_BY_ID] Intento de busquedad fallido, userId={}", id);
				throw new UserCreationException("El id no coincide con ningun usuario");
			});
			log.info("[LIST_USER_USE_CASE][GET_USER_BY_ID] Fin | userId={} | resultado=OK", id);
			return user;
		} catch (DataAccessException ex) {
			log.error("[LIST_USER_USE_CASE][GET_USER_BY_ID] Error de backend | userId={}", id);
			throw new InternalServerErrorException("Error interno al obtener el usuario para id=" + id, ex);
		} catch (Exception ex) {
			log.error("[LIST_USER_USE_CASE][GET_USER_BY_ID] Error inesperado | userId={}", id, ex);
			throw new InternalServerErrorException("Error inesperado al obtener el usuario para userId=" + id, ex);
		}
	}

	public User getUserByEmail(String email) {
		log.info("[LIST_USER_USE_CASE][GET_USER_BY_EMAIL] Inicio");
		String emailHash = DigestUtils.sha256Hex(email);
		try {
			User user = userRepository.findByEmail(email).orElseThrow(() -> {
				log.warn("[LIST_USER_USE_CASE][GET_USER_BY_EMAIL] Intento de busquedad fallido, emailHash={}",
						emailHash);
				throw new UserCreationException("El email no coincide con ningun usuario");
			});
			log.info("[LIST_USER_USE_CASE][GET_USER_BY_EMAIL] Fin | emailHash={} | resultado=OK", emailHash);
			return user;
		} catch (DataAccessException ex) {
			log.error("[LIST_USER_USE_CASE][GET_USER_BY_EMAIL] Error de backend | emailHash={}", emailHash);
			throw new InternalServerErrorException("Error interno al obtener el usuario para emailHash=" + emailHash,
					ex);
		} catch (Exception ex) {
			log.error("[LIST_USER_USE_CASE][GET_USER_BY_EMAIL] Error inesperado | emailHash={}", emailHash, ex);
			throw new InternalServerErrorException("Error inesperado al obtener el usuario para emailHash=" + emailHash,
					ex);
		}
	}
}
