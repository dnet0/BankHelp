package com.slatdev.bankhelp.application.usecase.user;

import java.util.Optional;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import com.slatdev.bankhelp.application.exception.InternalServerErrorException;
import com.slatdev.bankhelp.application.exception.UserCreationException;
import com.slatdev.bankhelp.application.service.PasswordService;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.model.UserRole;
import com.slatdev.bankhelp.domain.repository.UserRepository;

public class RegisterUserUseCase {
	private static final Logger log = LoggerFactory.getLogger(RegisterUserUseCase.class);
	private final UserRepository userRepository;
	private final PasswordService passwordService;

	public RegisterUserUseCase(UserRepository userRepository, PasswordService passwordService) {
		this.userRepository = userRepository;
		this.passwordService = passwordService;
	}

	public User save(String name, String email, UserRole role, String password) {
		log.info("[REGISTER_USER_USE_CASE][SAVE] Inicio");
		// Lógica de validación aquí (email único, contraseña válida, etc.)
		Optional<User> userWithSameEmail = userRepository.findByEmail(email);
		if (userWithSameEmail.isPresent()) {
			String emailHash = DigestUtils.sha256Hex(email);
			log.warn("[REGISTER_USER_USE_CASE][SAVE] Intento de registor fallido, emailHash={}", emailHash);
			throw new UserCreationException("El email ya esta en uso");
		}
		// La contraseña debe tener mas de 8 caracteres;
		String regexCorrectPassword = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).{8,}";

		if (password.isBlank() || !password.matches(regexCorrectPassword)) {
			log.warn("[REGISTER_USER_USE_CASE][SAVE] Constraseña invalida introducida");
			throw new UserCreationException(
					"La contraseña introducida no es valida debe contener al menos 8 caracteres y debe contener números y caracteres especiales (como @, #, $, ¡ o *).");
		}
		try {
			String passwordHash = passwordService.hashPassword(password);
			User newUser = new User(name, email, role, passwordHash);
			User savedUser = userRepository.save(newUser);
			log.info("[REGISTER_USER_USE_CASE][SAVE] Fin | userId={}", savedUser.getId());
			return savedUser;
		} catch (DataAccessException exception) {
			log.error("[REGISTER_USER_USE_CASE][SAVE] Error interno al guardar el usuario");
			throw new InternalServerErrorException("Error interno al guardar un usuario", exception);
		} catch (Exception exception) {
			log.error("[REGISTER_USER_USE_CASE][SAVE] Error al guardar el usuario");
			throw new UserCreationException("Error al guardar un usuario", exception);
		}
	}

}
