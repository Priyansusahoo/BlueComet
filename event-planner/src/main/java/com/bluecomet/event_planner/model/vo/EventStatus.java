package com.bluecomet.event_planner.model.vo;

import com.fasterxml.jackson.annotation.JsonValue;

import static java.util.Locale.ROOT;

/**
 * @author Priyansu
 */
public enum EventStatus {
    UPCOMING("Upcoming"),
    ONGOING("Ongoing"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String status;

    private EventStatus(String status) {
        this.status = status;
    }

    @JsonValue
    public String getStatus() {
        return status;
    }

    /**
     * Converts a string to an EventStatus enum.
     *
     * @param status the string representation of the status
     * @return the corresponding EventStatus {@link EventStatus} enum
     * @throws IllegalArgumentException if the status is invalid
     */
    public static EventStatus fromString(String status) throws IllegalArgumentException {
        return EventStatus.valueOf(status.toUpperCase(ROOT));
    }
}
