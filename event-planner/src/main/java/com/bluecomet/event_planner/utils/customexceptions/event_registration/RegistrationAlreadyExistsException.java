package com.bluecomet.event_planner.utils.customexceptions.event_registration;

/**
 * @author Priyansu
 */
public class RegistrationAlreadyExistsException extends RuntimeException {
    public RegistrationAlreadyExistsException(String message) {
        super(message);
    }
}
