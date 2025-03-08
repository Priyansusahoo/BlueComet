package com.bluecomet.event_planner.utils.customexceptions.event_registration;

/**
 * @author Priyansu
 */
public class RegistrationNotFoundException extends RuntimeException {
    public RegistrationNotFoundException(String message) {
        super(message);
    }
}
