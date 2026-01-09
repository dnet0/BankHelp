package com.slatdev.bankhelp.infrastructure.web.response;

import java.time.LocalDateTime;
import java.util.UUID;

import com.slatdev.bankhelp.domain.model.UserRole;

public record RegisterResponse(UUID id, String name, String email, UserRole role, String passwordHash,
		LocalDateTime createdAt) {
}
