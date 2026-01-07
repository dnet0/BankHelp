package com.slatdev.bankhelp.infrastructure.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.slatdev.bankhelp.application.service.PasswordService;
import com.slatdev.bankhelp.domain.model.User;

@Component
public class BcryptVerifiedPassword implements PasswordService {

	@Override
	public boolean checkPassword(User user, String password) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		return bcrypt.matches(password, user.getPasswordHash());
	}

	@Override
	public String hashPassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		return encoder.encode(password);
	}
}
