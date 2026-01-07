package com.slatdev.bankhelp.infrastructure.web.request;

import com.slatdev.bankhelp.domain.model.UserRole;

public record RegisterRequest(String name, String email, UserRole role, String password) {

}
