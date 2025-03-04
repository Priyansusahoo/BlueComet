package com.bluecomet.event_planner.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;
/**
 * @author Priyansu
 */
public enum EventStatus {
    UPCOMING("Upcoming"),
    ONGOING("Ongoing"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String status;

    EventStatus(String status) {
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
    public static EventStatus fromString(String status) {
        return Stream.of(EventStatus.values())
                .filter(s -> s.status.equalsIgnoreCase(status))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid EventStatus: " + status));
    }
}
