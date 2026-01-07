package com.slatdev.bankhelp.application.usecase;

import java.util.Optional;

import com.slatdev.bankhelp.application.service.PasswordService;
import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.model.UserRole;
import com.slatdev.bankhelp.domain.repository.UserRepository;

public class RegisterUserUseCase {
	private final UserRepository userRepository;
	private final PasswordService passwordService;
	
	public RegisterUserUseCase(UserRepository userRepository, PasswordService passwordService) {
		this.userRepository = userRepository;
		this.passwordService = passwordService;
	}

	public User save(String name, String email, UserRole role, String password) {
		// Lógica de validación aquí (email único, contraseña válida, etc.)
		Optional<User> userWithSameEmail = userRepository.findByEmail(email);
		if (userWithSameEmail.isPresent()) {
			throw new IllegalArgumentException("El email ya esta en uso");
		}
		// La contraseña debe tener mas de 8 caracteres;
		String regexCorrectPassword = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";

		if (password.isBlank() || password.matches(regexCorrectPassword))
			throw new IllegalArgumentException(
					"La contraseña introducida no es valida debe contener mas de 7 caracteres y debe contener números y caracteres especiales (como @, #, $, ¡ o*).");

		String passwordHash = passwordService.hashPassword(password);
		User newUser = new User(name, email, role, passwordHash);
		return this.userRepository.save(newUser);
	}


}
