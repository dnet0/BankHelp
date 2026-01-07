package com.slatdev.bankhelp.domain.repository;

import java.util.Optional;

import com.slatdev.bankhelp.domain.model.User;

public interface UserRepository {

	User save(User user);

	Optional<User> findByEmail(String email);
}
