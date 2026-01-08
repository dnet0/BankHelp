package com.slatdev.bankhelp.infrastructure.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> generalExceptionHandler(Exception e) {
		log.error("Ocurrio un error inesperado: ", e);
		return new ResponseEntity<String>("Ocurrio un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
