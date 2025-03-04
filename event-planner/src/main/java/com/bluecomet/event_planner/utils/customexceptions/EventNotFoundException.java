package com.bluecomet.event_planner.utils.customexceptions;
/**
 * @author Priyansu
 */
public class EventNotFoundException extends RuntimeException {
    public EventNotFoundException(String message) {
        super(message);
    }
}
