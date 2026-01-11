package com.slatdev.bankhelp.infrastructure.persistence.jpa;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.slatdev.bankhelp.domain.model.User;
import com.slatdev.bankhelp.domain.repository.UserRepository;
import com.slatdev.bankhelp.infrastructure.persistence.jpa.mapper.UserMapper;

@Repository
public class UserRepositoryAdapter implements UserRepository {

	@Autowired
	private SpringDataUserRepository jpaRepo;

	@Override
	public User save(User user) {
		UserEntity entity = jpaRepo.save(UserMapper.toEntity(user));
		return UserMapper.toDomain(entity);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return jpaRepo.findByEmail(email).map(UserMapper::toDomain);
	}

	@Override
	public Optional<User> findById(UUID id) {
		return jpaRepo.findById(id).map(UserMapper::toDomain);
	}

}
