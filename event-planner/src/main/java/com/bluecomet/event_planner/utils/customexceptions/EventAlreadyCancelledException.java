package com.bluecomet.event_planner.utils.customexceptions;

public class EventAlreadyCancelledException extends RuntimeException {
    public EventAlreadyCancelledException(String message) {
        super(message);
    }
}
