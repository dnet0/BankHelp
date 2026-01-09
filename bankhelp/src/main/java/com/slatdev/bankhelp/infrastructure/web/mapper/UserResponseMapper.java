package com.slatdev.bankhelp.infrastructure.web.mapper;

import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.infrastructure.web.response.RegisterResponse;

public class UserResponseMapper {

	public static RegisterResponse toResponse(User entity) {
		return new RegisterResponse(entity.getId(), entity.getName(), entity.getEmail(), entity.getRole(),
				entity.getPasswordHash(), entity.getCreatedAt());
	}

	public static User toEntiy(RegisterResponse response) {
		return new User(response.id(), response.name(), response.email(), response.role(), response.passwordHash(),
				response.createdAt());
	}

}
