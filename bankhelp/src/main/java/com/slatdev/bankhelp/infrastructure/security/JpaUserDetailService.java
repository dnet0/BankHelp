package com.slatdev.bankhelp.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.slatdev.bankhelp.infrastructure.persistence.jpa.SpringDataUserRepository;
import com.slatdev.bankhelp.infrastructure.persistence.jpa.UserEntity;

@Service
public class JpaUserDetailService implements UserDetailsService {

	@Autowired
	private SpringDataUserRepository jpaRepo;

	public JpaUserDetailService(SpringDataUserRepository jpaRepo) {
		this.jpaRepo = jpaRepo;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = this.jpaRepo.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

		return User.builder()
				.username(user.getEmail())
				.password(user.getPasswordHash())
				.roles(user.getRole().name())
				.build();
	}

}
