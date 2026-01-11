package com.slatdev.bankhelp.infrastructure.exception;

import javax.security.sasl.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.slatdev.bankhelp.application.exception.InvalidRefreshTokenException;
import com.slatdev.bankhelp.application.exception.TicketCreationException;
import com.slatdev.bankhelp.application.exception.UserCreationException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(UserCreationException.class)
	public ResponseEntity<ApiError> handleUserCreation(UserCreationException e) {
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		log.error("[ERROR]: ", e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(TicketCreationException.class)
	public ResponseEntity<ApiError> handleTicketCreation(TicketCreationException e) {
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		log.error("[ERROR]: ", e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(InvalidRefreshTokenException.class)
	public ResponseEntity<ApiError> handleRereshTokenException(InvalidRefreshTokenException e) {
		ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
		log.error("[ERROR]: ", e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiError> handleAuthException(AuthenticationException e) {
		ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
		log.error("[ERROR]: ", e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleAuthException(MethodArgumentNotValidException e) {
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		log.error("[ERROR]: ", e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneric(Exception e) {
		ApiError error = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());

		log.error("Ocurrio un error inesperado: ", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}
