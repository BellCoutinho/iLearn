package com.ilearn.vle.service.exception;

public class ProjectAlreadyExistsException extends RuntimeException {

    public ProjectAlreadyExistsException(String message) {
	super(message);
    }
}
