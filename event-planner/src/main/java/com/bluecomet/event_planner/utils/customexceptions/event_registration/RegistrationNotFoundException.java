package com.bluecomet.event_planner.utils.customexceptions.event_registration;

public class RegistrationNotFoundException extends RuntimeException {
    public RegistrationNotFoundException(String message) {
        super(message);
    }
}
