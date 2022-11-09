package com.adobe.prj.api;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.adobe.prj.service.NotFoundException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(NotFoundException ex) {
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("message", ex.getMessage());
		body.put("timestamp", new Date());
		return new ResponseEntity<Object>(body, HttpStatus.NOT_FOUND);
	}
}
