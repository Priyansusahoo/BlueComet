package com.bluecomet.event_planner.dto.request;

import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
/**
 * Represents a request object for fetching events within a specified date range.
 * <p>
 * Ensures that the start date is before the end date.
 * </p>
 *
 * @author Priyansu
 */
public record EventDateRangeRequest(
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
        @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
) {
    /**
     * Validates that the start date is before the end date.
     *
     * @throws IllegalArgumentException if the start date is after the end date.
     */
    public void validate() {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start date must be before end date.");
        }
    }
}
