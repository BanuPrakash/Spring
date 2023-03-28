package com.xiaomi.prj.api;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.xiaomi.prj.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
		Map<String, Object> errorMessageBody = new LinkedHashMap<>();
		errorMessageBody.put("timestamp", new Date());
		errorMessageBody.put("message", ex.getMessage());
		return new ResponseEntity<Object>(errorMessageBody, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		Map<String, Object> errorMessageBody = new LinkedHashMap<>();
		errorMessageBody.put("timestamp", new Date());
		List<String> errors = ex.getBindingResult().getFieldErrors().stream()
				.map(exception -> exception.getDefaultMessage()).collect(Collectors.toList());
		errorMessageBody.put("errors", errors);
		return new ResponseEntity<Object>(errorMessageBody, HttpStatus.NOT_FOUND);

	}
	
}
