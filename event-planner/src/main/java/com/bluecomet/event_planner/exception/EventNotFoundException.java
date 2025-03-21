package com.bluecomet.event_planner.exception;
/**
 * @author Priyansu
 */
public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message) {
        super(message);
    }
}
