package com.bluecomet.event_planner.model.vo;

import com.fasterxml.jackson.annotation.JsonValue;

import static java.util.Locale.ROOT;

public enum RegistrationStatus {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled");

    private final String regStatus;

    private RegistrationStatus(String regStatus) {
        this.regStatus = regStatus;
    }

    @JsonValue
    public String getRegStatus() {
        return regStatus;
    }

    /**
     * Converts a string to an EventStatus enum.
     *
     * @param regStatus the string representation of the Registration Status
     * @return the corresponding RegistrationStatus {@link RegistrationStatus} enum
     * @throws IllegalArgumentException if the status is invalid
     */
    public static RegistrationStatus fromString(String regStatus) {
        return RegistrationStatus.valueOf(regStatus.toUpperCase(ROOT));
    }
}
