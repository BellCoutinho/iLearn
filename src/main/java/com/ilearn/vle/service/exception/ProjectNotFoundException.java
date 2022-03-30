package com.ilearn.vle.service.exception;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(String message) {
	super(message);
    }
}
