package com.slatdev.bankhelp.infrastructure.web.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record LogoutRequest(@NotNull UUID refreshToken) {
}
