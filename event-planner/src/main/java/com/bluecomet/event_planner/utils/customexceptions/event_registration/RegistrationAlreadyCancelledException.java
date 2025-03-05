package com.bluecomet.event_planner.utils.customexceptions.event_registration;

public class RegistrationAlreadyCancelledException extends RuntimeException {
    public RegistrationAlreadyCancelledException(String message) {
        super(message);
    }
}
