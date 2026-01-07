package com.slatdev.bankhelp.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class User {

	private final UUID id;
	private final String name;
	private final String email;
	private final UserRole role;
	private final String passwordHash;
	private final LocalDateTime createdAt;

	public User(UUID id, String name, String email, UserRole role, String passwordHash, LocalDateTime createdAt) {
		validateName(name);
		validateEmail(email);
		
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.passwordHash = passwordHash;
		this.createdAt = createdAt;
	}

	public User(String name, String email, UserRole role, String passwordHash) {
		validateName(name);
		validateEmail(email);
		
		this.id = UUID.randomUUID();
		this.name = name;
		this.email = email;
		this.role = role;
		this.passwordHash = passwordHash;
		this.createdAt = LocalDateTime.now();
	}

	private void validateName(String name) {
		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("El nombre no puede ser vacía");
		}
	}
	private void validateEmail(String email) {
		if (email == null || email.isBlank()) {
			throw new IllegalArgumentException("El email no puede ser vacía");
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
