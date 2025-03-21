package com.bluecomet.event_planner.exception;

/**
 * @author Priyansu
 */
public class RegistrationAlreadyCancelledException extends RuntimeException {
    public RegistrationAlreadyCancelledException(String message) {
        super(message);
    }
}
