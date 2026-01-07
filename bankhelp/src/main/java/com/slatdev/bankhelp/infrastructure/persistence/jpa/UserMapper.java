package com.slatdev.bankhelp.infrastructure.persistence.jpa;

import com.slatdev.bankhelp.domain.model.User;

public class UserMapper {

	public static UserEntity toEntity(User userDomain) {
		return new UserEntity(userDomain.getId(), userDomain.getName(), userDomain.getEmail(), userDomain.getRole(),
				userDomain.getPasswordHash(), userDomain.getCreatedAt());
	}
	
	public static User toDomain(UserEntity entity) {
		return new User(entity.getId(), entity.getName(), entity.getEmail(), entity.getRole(),
				entity.getPasswordHash(), entity.getCreatedAt());
	}
}
