package com.ilearn.vle.service.exception;

public class ClassAlreadyExistsException extends RuntimeException {
    public ClassAlreadyExistsException(String message) {
	super(message);
    }
}
