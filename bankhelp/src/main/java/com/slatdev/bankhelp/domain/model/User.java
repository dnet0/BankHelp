package com.slatdev.bankhelp.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

import com.slatdev.bankhelp.domain.exception.validation.InvalidUserEmailException;
import com.slatdev.bankhelp.domain.exception.validation.InvalidUserIdException;
import com.slatdev.bankhelp.domain.exception.validation.InvalidUserNameException;
import com.slatdev.bankhelp.domain.exception.validation.InvalidUserPasswordHashException;

public class User {

	private final UUID id;
	private final String name;
	private final String email;
	private final UserRole role;
	private final String passwordHash;
	private final LocalDateTime createdAt;

	public User(UUID id, String name, String email, UserRole role, String passwordHash, LocalDateTime createdAt) {
		validateUser(id, name, email, passwordHash);

		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.passwordHash = passwordHash;
		this.createdAt = createdAt;
	}

	public User(String name, String email, UserRole role, String passwordHash) {
		this(UUID.randomUUID(), name, email, role, passwordHash, LocalDateTime.now());
	}

	public void validateUser(UUID id, String name, String email, String passwordHash) {
		validateId(id);
		validateName(name);
		validateEmail(email);
		validatePasswordHash(passwordHash);

	}

	private static void validateId(UUID id) {
		if (id == null) {
			throw new InvalidUserIdException("El ID del usuario no puede ser null");
		}
	}

	private void validateName(String name) {
		if (name == null || name.isBlank()) {
			throw new InvalidUserNameException("El nombre no puede ser vacía");
		}
	}

	private void validateEmail(String email) {
		if (email == null || email.isBlank()) {
			throw new InvalidUserEmailException("El email no puede ser vacía");
		}
	}

	private void validatePasswordHash(String passwordHash) {
		if (passwordHash == null || passwordHash.isBlank()) {
			throw new InvalidUserPasswordHashException("La contraseña no puede ser vacía");
		}
	}

	public UUID getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public UserRole getRole() {
		return role;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

}
