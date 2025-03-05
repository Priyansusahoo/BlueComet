package com.bluecomet.event_planner.utils.customexceptions.event_registration;

public class RegistrationAlreadyExistsException extends RuntimeException {
    public RegistrationAlreadyExistsException(String message) {
        super(message);
    }
}
