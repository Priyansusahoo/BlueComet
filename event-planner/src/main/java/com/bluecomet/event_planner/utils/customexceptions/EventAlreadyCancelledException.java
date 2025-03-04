package com.bluecomet.event_planner.utils.customexceptions;
/**
 * @author Priyansu
 */
public class EventAlreadyCancelledException extends RuntimeException {
    public EventAlreadyCancelledException(String message) {
        super(message);
    }
}
