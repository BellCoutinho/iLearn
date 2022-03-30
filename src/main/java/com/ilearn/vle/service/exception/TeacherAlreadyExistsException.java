package com.ilearn.vle.service.exception;

public class TeacherAlreadyExistsException extends RuntimeException {
    public TeacherAlreadyExistsException(String message) {
	super(message);
    }
}
