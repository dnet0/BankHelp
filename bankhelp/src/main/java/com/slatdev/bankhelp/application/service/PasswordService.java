package com.slatdev.bankhelp.application.service;

import com.slatdev.bankhelp.domain.model.User;

public interface PasswordService {

	boolean checkPassword(User user, String password);

	String hashPassword(String password);

}
