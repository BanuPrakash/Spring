package com.xiaomi.prj.api;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
}
