package com.bluecomet.event_planner.exception;

/**
 * @author Priyansu
 */
public class RegistrationAlreadyExistsException extends RuntimeException {
    public RegistrationAlreadyExistsException(String message) {
        super(message);
    }
}
