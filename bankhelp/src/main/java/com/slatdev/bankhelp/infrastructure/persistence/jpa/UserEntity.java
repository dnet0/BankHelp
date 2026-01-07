package com.slatdev.bankhelp.infrastructure.persistence.jpa;

import java.time.LocalDateTime;
import java.util.UUID;

import com.slatdev.bankhelp.domain.model.UserRole;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity {
	@Id
	private UUID id;
	private String name;
	private String email;
	@Enumerated(EnumType.STRING)
	private UserRole role;
	private String passwordHash;
	private LocalDateTime createdAt;

	public UserEntity() {
	}

	public UserEntity(UUID id, String name, String email, UserRole role, String passwordHash, LocalDateTime createdAt) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.passwordHash = passwordHash;
		this.createdAt = createdAt;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}
