package com.slatdev.bankhelp.infrastructure.web.request;

import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTicketRequest(
		@NotNull(message="El ID de usuario es obligatorio")
		UUID userId, 
		@NotBlank(message="La descripción es obligatorio")
		String description) {

}
