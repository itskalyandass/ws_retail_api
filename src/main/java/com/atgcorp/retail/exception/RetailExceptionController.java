package com.atgcorp.retail.exception;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import io.micrometer.core.instrument.util.StringUtils;

@ControllerAdvice
public class RetailExceptionController {
	@ExceptionHandler(value = BadRequestException.class)
	public ResponseEntity<Object> exception(BadRequestException exception) {
		return new ResponseEntity<>(
				StringUtils.isNotBlank(exception.getMessage()) ? exception.getMessage() : "Invalid data",
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = {JDBCException.class, HibernateException.class})
	public ResponseEntity<Object> exception(RuntimeException exception) {
		return new ResponseEntity<>(
				StringUtils.isNotBlank(exception.getMessage()) ? exception.getMessage() : "Invalid data",
				HttpStatus.BAD_REQUEST);
	}
}
