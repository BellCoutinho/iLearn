package com.ilearn.vle.service.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
	super(message);
    }
}
