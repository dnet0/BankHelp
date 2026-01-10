package com.slatdev.bankhelp.infrastructure.web.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
		@NotBlank(message="El email es obligatorio")
		@Email(message="El email debe tener un formato valido")
		String email, 
	    @NotBlank(message = "La contraseña es obligatoria")
	    @Size(min = 8, max = 100, message = "La contraseña debe tener entre 8 y 100 caracteres")
		@Pattern(
	    regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=*])(?=\\S+$).*$",
	    message = "La contraseña debe contener al menos: una mayúscula, una minúscula, un número, un carácter especial (@#$%^&+=*) y no puede tener espacios"
		)
		String password) {

}
