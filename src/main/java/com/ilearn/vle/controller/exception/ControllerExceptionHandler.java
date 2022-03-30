package com.ilearn.vle.controller.exception;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ilearn.vle.service.exception.TeacherNotFoundException;
import com.ilearn.vle.service.exception.TeacherAlreadyHasProjectException;
import com.ilearn.vle.service.exception.TeacherAlreadyExistsException;
import com.ilearn.vle.service.exception.StudentNotFoundException;
import com.ilearn.vle.service.exception.StudentAlreadyExistsException;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(TeacherNotFoundException.class)
    public ResponseEntity<StandardError> teacherNotFound(TeacherNotFoundException exception, HttpServletRequest request) {
	final var error = new StandardError(LocalDateTime.now(),
					    HttpStatus.NOT_FOUND.value(),
					    exception.getMessage(),
					    request.getRequestURI());
					   
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(TeacherAlreadyHasProjectException.class)
    public ResponseEntity<StandardError> teacherAlreadyHasProject(TeacherAlreadyHasProjectException exception, HttpServletRequest request) {
	final var error = new StandardError(LocalDateTime.now(),
					    HttpStatus.CONFLICT.value(),
					    exception.getMessage(),
					    request.getRequestURI());
	return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(TeacherAlreadyExistsException.class)
    public ResponseEntity<StandardError> teacherAlreadyExists(TeacherAlreadyExistsException exception, HttpServletRequest request) {
	final var error = new StandardError(LocalDateTime.now(),
					    HttpStatus.FOUND.value(),
					    exception.getMessage(),
					    request.getRequestURI());
					   
	return ResponseEntity.status(HttpStatus.FOUND).body(error);
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<StandardError> studentNotFound(StudentNotFoundException exception, HttpServletRequest request) {
	final var error = new StandardError(LocalDateTime.now(),
					    HttpStatus.NOT_FOUND.value(),
					    exception.getMessage(),
					    request.getRequestURI());
					   
	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
    
    @ExceptionHandler(StudentAlreadyExistsException.class)
    public ResponseEntity<StandardError> studentAlreadyExists(StudentAlreadyExistsException exception, HttpServletRequest request) {
	final var error = new StandardError(LocalDateTime.now(),
					    HttpStatus.FOUND.value(),
					    exception.getMessage(),
					    request.getRequestURI());
					   
	return ResponseEntity.status(HttpStatus.FOUND).body(error);
    }
}
