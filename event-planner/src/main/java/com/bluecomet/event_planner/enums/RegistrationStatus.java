package com.bluecomet.event_planner.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.stream.Stream;

public enum RegistrationStatus {
    PENDING("Pending"),
    CONFIRMED("Confirmed"),
    CANCELLED("Cancelled");

    private final String regStatus;

    RegistrationStatus(String regStatus) {
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
        return Stream.of(RegistrationStatus.values())
                .filter(s -> s.regStatus.equalsIgnoreCase(regStatus))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid RegistrationStatus: " + regStatus));
    }
}
