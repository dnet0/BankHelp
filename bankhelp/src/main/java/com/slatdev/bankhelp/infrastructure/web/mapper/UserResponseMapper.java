package com.slatdev.bankhelp.infrastructure.web.mapper;

import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.infrastructure.web.response.RegisterResponse;

public class UserResponseMapper {

	public static RegisterResponse toResponse(User entity) {
		return new RegisterResponse(entity.getId(), entity.getName(), entity.getEmail(), entity.getRole(), entity.getCreatedAt());
	}

}
