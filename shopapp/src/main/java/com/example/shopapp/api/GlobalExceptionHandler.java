package com.example.shopapp.api;

import com.example.shopapp.service.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/*
to handle exception thrown from Controller / RestController
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        Map<String, Object> messages = new LinkedHashMap<>();
        messages.put("timestamp", new Date());
        messages.put("message", ex.getMessage());

        return new ResponseEntity<Object>(messages, HttpStatus.NOT_FOUND);//404
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> messages = new LinkedHashMap<>();
        messages.put("timestamp", new Date());

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                        .map(exception -> exception.getDefaultMessage())
                                .collect(Collectors.toList());
        messages.put("errors", errors);

        return new ResponseEntity<Object>(messages, HttpStatus.BAD_REQUEST);//400
    }
}
