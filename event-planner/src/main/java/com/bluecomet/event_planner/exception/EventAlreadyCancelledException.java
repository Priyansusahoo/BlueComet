package com.bluecomet.event_planner.exception;
/**
 * @author Priyansu
 */
public class EventAlreadyCancelledException extends RuntimeException {
    public EventAlreadyCancelledException(String message) {
        super(message);
    }
}
