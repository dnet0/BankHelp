package com.slatdev.bankhelp.infrastructure.exception;

import javax.security.sasl.AuthenticationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.slatdev.bankhelp.application.exception.AplicationConflictException;
import com.slatdev.bankhelp.application.exception.AplicationNotFoundException;
import com.slatdev.bankhelp.application.exception.AplicationSecurityException;
import com.slatdev.bankhelp.application.exception.AplicationValidationException;
import com.slatdev.bankhelp.domain.exception.DomainInputException;
import com.slatdev.bankhelp.domain.exception.DomainRuleException;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	// Errores de aplicación

	@ExceptionHandler(AplicationValidationException.class)
	public ResponseEntity<ApiError> handleAplicationValidationException(AplicationValidationException e) {
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		log.error("[ERROR]: ", e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(AplicationSecurityException.class)
	public ResponseEntity<ApiError> handleAplicationSecurityException(AplicationSecurityException e) {
		ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
		log.error("[ERROR]: ", e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	@ExceptionHandler(AplicationNotFoundException.class)
	public ResponseEntity<ApiError> handleAplicationNotFoundException(AplicationNotFoundException e) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND.value(), e.getMessage());
		log.error("[ERROR]: ", e);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(AplicationConflictException.class)
	public ResponseEntity<ApiError> handleAplicationConflictException(AplicationConflictException e) {
		ApiError error = new ApiError(HttpStatus.CONFLICT.value(), e.getMessage());
		log.error("[ERROR]: ", e);
		return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}

	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException e) {
		ApiError error = new ApiError(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
		log.error("[ERROR]: ", e);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	}

	// Errores de dominio
	@ExceptionHandler(DomainInputException.class)
	public ResponseEntity<ApiError> handleDomainException(DomainInputException e) {
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		log.error("[ERROR]: ", e);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}

	@ExceptionHandler(DomainRuleException.class)
	public ResponseEntity<ApiError> handleInvalidTicketException(DomainRuleException e) {
		ApiError error = new ApiError(HttpStatus.UNPROCESSABLE_CONTENT.value(), e.getMessage());
		log.error("[ERROR]: ", e);
		return ResponseEntity.status(HttpStatus.UNPROCESSABLE_CONTENT).body(error);
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
