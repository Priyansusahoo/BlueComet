package com.bluecomet.event_planner.utils.customexceptions.event_registration;

/**
 * @author Priyansu
 */
public class RegistrationAlreadyCancelledException extends RuntimeException {
    public RegistrationAlreadyCancelledException(String message) {
        super(message);
    }
}
